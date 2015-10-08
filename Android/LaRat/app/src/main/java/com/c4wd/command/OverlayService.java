package com.c4wd.command;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cory on 10/7/15.
 */
public class OverlayService extends Service {

    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private View view;
    private Context context;

    public OverlayService(Context context, View view, WindowManager.LayoutParams params, WindowManager manager) {
        this.windowManager = manager;
        this.context = context;
        this.view = view;
        this.params = params;
    }

    @Override
    public void onCreate() {
        windowManager.addView(view, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null)
            windowManager.removeView(view);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}