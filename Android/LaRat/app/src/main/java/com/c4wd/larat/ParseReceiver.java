package com.c4wd.larat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import com.c4wd.command.Command;
import com.c4wd.command.CommandContext;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cory on 10/4/15.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {
    protected void onPushReceive(Context context, Intent intent) {
        Command.initCommands();
        JsonObject data = getDataFromIntent(intent);
        String command = null;
        List<String> arguments = new LinkedList<String>();

        try {
            command = data.get("command").getAsString();
            if(data.has("args")) {
                String[] args = data.get("args").getAsString().split(","); //comma seperated arguments
                for (int i = 0; i < args.length; i++) {
                    String arg = args[i];
                    arguments.add(arg);
                }
            }
        } catch (Exception ex) {
            Log.d("com.cw4d.larat.error", ex.toString());
        }

        Log.i("Command Received", command);

        CommandContext ctx = new CommandContext(context, arguments);

        AsyncTask task = Command.getTask(command);

        task.execute(ctx);
    }

    private JsonObject getDataFromIntent(Intent intent) {
        JsonParser parser = new JsonParser();
        JsonElement data = null;
        try {
            data = parser.parse(URLDecoder.decode(intent.getExtras().getString(ParseConstants.PARSE_DATA_KEY), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data.getAsJsonObject();
    }
}
