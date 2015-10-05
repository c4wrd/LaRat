package com.c4wd.larat;

/**
 * Created by cory on 10/4/15.
 */

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;

import com.parse.ParseInstallation;

/**
 * Created by Cory on 8/10/2015.
 */
public class Constants {
    public static String CLIENT_ID;
    public static String PHONE_NUMBER;
    public static String DEVICE_ID;
    public static String SDK_VERSION;
    public static String PROVIDER;
    public static boolean IS_SETUP = false;
    public static Location LAST_LOCATION;
    private static LocationManager locManager;

    private static void updateWithNewLocation(Location location) {
        if (location != null) {
            LAST_LOCATION = location;
        }
    }

    public static void updateLocation(Context context) {
        locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, mLocationListener);
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private static final android.location.LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public static void setupInternals(Context context) {
        TelephonyManager telephonyManager =((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE));
        updateLocation(context);

        CLIENT_ID = ParseInstallation.getCurrentInstallation().getObjectId();
        DEVICE_ID = android.os.Build.MODEL;
        DEVICE_ID = DEVICE_ID.replace(" ", "");
        SDK_VERSION = Integer.valueOf(android.os.Build.VERSION.SDK).toString(); //Build.VERSION.RELEASE;
        PROVIDER = telephonyManager.getNetworkOperatorName();
        if(PROVIDER.startsWith("Searching"))
            PROVIDER = null;
        if(PROVIDER == null)
            PROVIDER = "Not Activated";
        PROVIDER = PROVIDER.replace(" ", "_");
        PHONE_NUMBER = telephonyManager.getLine1Number();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        IS_SETUP = true;
    }
}