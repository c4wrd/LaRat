package com.c4wd.sms;

/**
 * Created by cory on 10/15/15.
 */
public class SMSThreadInfo {

    private String number;
    private String contactId;
    private long threadId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
}
