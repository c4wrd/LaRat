package com.c4wd.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cory on 10/15/15.
 */
public class SMSReader {

    private Context context;
    private Cursor cursor;
    private Uri location;
    private String box_type;

    public SMSReader(String smsBox, Context context) {
        this.location = Uri.parse(smsBox);
        this.context = context;
    }

    public void cacheMessages(long thread_id) {
        this.getMessages(thread_id, false);
    }

    public List<SMSObject> getMessages(long thread_id, boolean include_cached) {
        List<SMSObject> messages = new LinkedList<SMSObject>();

        /*  if (include_cached) {
                Iterator<SMSObject> iterator = SMSObject.findAll(SMSObject.class);
                while (iterator.hasNext()) {
                    messages.add(iterator.next());
                }
            }
        */

        ContentResolver resolver = this.context.getApplicationContext().getContentResolver();

        try {
            this.cursor = resolver.query(
                    Uri.parse(SMSConstants.SMS),
                    null,
                    "thread_id=" + thread_id,
                    null,
                    null
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if(cursor.moveToFirst()) {

            for(int i=0; i < cursor.getCount(); i++) {
                SMSObject sms = new SMSObject();
                try {
                    sms.setThreadId(cursor.getLong(cursor.getColumnIndexOrThrow("thread_id")));
                    sms.setBody(cursor.getString(cursor.getColumnIndexOrThrow("body")).toString());
                    sms.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    sms.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    messages.add(sms);
                } catch (Exception ex) {
                   ex.printStackTrace();
                }

                cursor.moveToNext();
            }

        }

        cursor.close();
        return messages;
    }

    public List<SMSThreadInfo> getThreads() {
        List<SMSThreadInfo> results = new LinkedList();
        List<Long> thread_ids = new LinkedList<Long>();

        String number_type = "address";

        this.cursor = this.context.getContentResolver().query(
                this.location,
                new String[]{
                        "thread_id",
                        "person",
                        number_type
                },
                null,
                null,
                "thread_id DESC"
        );
        try {
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    SMSThreadInfo sms = new SMSThreadInfo();
                    long thread_id = cursor.getLong(cursor.getColumnIndexOrThrow("thread_id"));
                    String number = cursor.getString(cursor.getColumnIndexOrThrow(number_type));
                    String person = cursor.getString(cursor.getColumnIndexOrThrow("person"));

                    if (!thread_ids.contains(thread_id)) {
                        thread_ids.add(thread_id);
                        sms.setThreadId(thread_id);
                        sms.setNumber(number);
                        if (person != null) {
                            sms.setContactId(person);
                        } else {
                            sms.setContactId("Unknown");
                        }
                        if (number != null)
                            results.add(sms);
                    }

                    cursor.moveToNext();
                }
            }
        } catch (Exception ex) {
            Log.d("LARAT-ERROR", ex.getMessage());
        }
        cursor.close();
        return results;
    }
}
