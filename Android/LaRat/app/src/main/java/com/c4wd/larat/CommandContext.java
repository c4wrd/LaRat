package com.c4wd.larat;

import android.content.Context;

import java.util.List;

/**
 * Created by cory on 10/4/15.
 */
public class CommandContext {

    private Context context;
    private List<Object> arguments;

    public CommandContext(Context context, List<Object> arguments) {
        this.context = context;
        this.arguments = arguments;
    }

    public Context getContext() {
        return this.context;
    }

    public List<Object> getArguments() {
        return this.arguments;
    }

    public Object getArgument(int index) {
        return this.arguments.get(index);
    }
}
