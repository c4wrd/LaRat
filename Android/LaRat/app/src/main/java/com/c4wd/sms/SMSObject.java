package com.c4wd.sms;

import com.orm.SugarApp;
import com.orm.SugarRecord;

/**
 * Created by cory on 10/9/15.
 */
public class SMSObject extends SugarRecord<SMSObject> {

    private String id;
    private String threadId;
    private String body;
    private String address;
    private String date;

    

}
