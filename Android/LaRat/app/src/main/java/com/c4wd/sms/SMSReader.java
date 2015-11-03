package com.c4wd.sms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cory on 10/15/15.
 */
public class SMSReader {

    private Context context;
    private Cursor cursor;
    private Uri location;
    private int INBOX_FLAG; //0x0 = inbox, 0x1 = sent
    private String box_type;

    public SMSReader(String smsBox, Context context) {
        this.location = Uri.parse(smsBox);
        if (smsBox.compareTo(SMSConstants.INBOX) == 0) {
            this.INBOX_FLAG = 0x0;
        } else {
            this.INBOX_FLAG = 0x1;
        }
    }

    public List<SMSThreadInfo> getThreads() {
        List<SMSThreadInfo> results = new LinkedList();

        String number_type = (INBOX_FLAG == 0x0) ? "creator" : "address";

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

        if(cursor.moveToFirst()) {
            for(int i=0; i < cursor.getCount(); i++) {
                SMSThreadInfo sms = new SMSThreadInfo();
                sms.setThreadId(cursor.getLong(cursor.getColumnIndexOrThrow("thread_id")));
                sms.setContactId(cursor.getString(cursor.getColumnIndexOrThrow("person")).toString());
                sms.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(number_type)).toString());
                results.add(sms);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return results;
    }

    public void selectThread(long threadId) {
        
    }
}
