package com.c4wd.command;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.c4wd.larat.R;
import com.larat.drawing.BouncingBall;
import com.larat.drawing.OpenGLRenderer;

/**
 * Created by cory on 10/7/15.
 */
public class DrawingTasks {

    private static final int LayoutParamFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;


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

            GLSurfaceView view = new GLSurfaceView(context.getContext());
            view.setRenderer(new OpenGLRenderer());
            view.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(80, 80);
            view.setLayoutParams(layoutParams);

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
                    LayoutParamFlags,
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

    public static class CrackScreenTask extends AsyncTask<Object, Void, Void> {

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
                    LayoutParamFlags,
                    PixelFormat.TRANSLUCENT);

            params.gravity = Gravity.CENTER;
            params.x = 0;
            params.y = 0;

            ImageView view = new ImageView(context.getContext());
            Bitmap bm = BitmapFactory.decodeResource(context.getContext().getResources(), R.drawable.crack);
            view.setImageBitmap(bm);
            view.setLayoutParams(new ViewGroup.LayoutParams(100, 100));

            windowManager.addView(view, params);
        }
    }
}
