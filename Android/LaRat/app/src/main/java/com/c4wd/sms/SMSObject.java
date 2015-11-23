package com.c4wd.sms;

import com.orm.SugarApp;
import com.orm.SugarRecord;

/**
 * Created by cory on 10/9/15.
 */
public class SMSObject extends SugarRecord<SMSObject> {
    private long threadId;
    private String body;
    private String address;
    private String date;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
