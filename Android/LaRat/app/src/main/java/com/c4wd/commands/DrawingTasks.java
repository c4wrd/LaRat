package com.c4wd.commands;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.c4wd.drawing.BouncingBall;
import com.c4wd.drawing.OpenGLRenderer;
import com.c4wd.drawing.OverlayService;
import com.c4wd.larat.R;

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
            OverlayService overlayService = new OverlayService(context.getContext());

            GLSurfaceView view = new GLSurfaceView(context.getContext());
            view.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            view.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            view.setRenderer(new OpenGLRenderer());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(80, 80);
            view.setLayoutParams(layoutParams);

            overlayService.addView(view);
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
            OverlayService overlayService = new OverlayService(context.getContext());

            BouncingBall view = new BouncingBall(context.getContext());
            view.maxX = overlayService.getWindowManager().getDefaultDisplay().getWidth();
            view.maxY = overlayService.getWindowManager().getDefaultDisplay().getHeight();

            overlayService.addView(view);
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
            OverlayService overlayService = new OverlayService(context.getContext());
            overlayService.setGravity(Gravity.CENTER);

            ImageView view = new ImageView(context.getContext());
            Bitmap bm = BitmapFactory.decodeResource(context.getContext().getResources(), R.drawable.crack);
            view.setImageBitmap(bm);
            view.setLayoutParams(new ViewGroup.LayoutParams(100, 100));

            overlayService.addView(view);
        }
    }

    public static class ClearViewTask extends AsyncTask<Object, Void, Void> {

        private CommandContext context;

        @Override
        protected Void doInBackground(Object... objects) {
            this.context = (CommandContext)(objects[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void returnValue) {
            OverlayService overlayService = new OverlayService(context.getContext());

            if (context.getArguments().size() == 1) {
                try {
                    overlayService.removeView(Integer.parseInt((String) context.getArgument(0)));
                } catch (Exception ex) {
                    ex.printStackTrace(); //not a valid number passed, ignore
                }
            } else {
                overlayService.removeAllViews();
            }
        }

    }
}
