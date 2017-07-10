package com.liqg.library.log;

import android.util.Log;

public class MyLog {

    public synchronized static void write(LogLevel logLevel, String msg) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        LogHelper logHelper = new LogHelper();

        logHelper.write(logLevel + LogHelper.Separator1 +
                stacks[2].getClassName() + LogHelper.Separator2 +
                stacks[2].getMethodName() + LogHelper.Separator1 +
                stacks[2].getLineNumber() + LogHelper.Separator1 +
                Thread.currentThread().getId() + LogHelper.Separator2 +
                msg);


    }

    public synchronized static void v(String tag, String msg) {
        write(LogLevel.VERBOSE, msg);
        Log.v(tag, msg);
    }

    public synchronized static void d(String tag, String msg) {
        write(LogLevel.DEBUG, msg);
        Log.d(tag, msg);
    }

    public synchronized static void i(String tag, String msg) {
        write(LogLevel.INFO, msg);
        Log.i(tag, msg);
    }

    public synchronized static void w(String tag, String msg) {
        write(LogLevel.WARN, msg);
        Log.w(tag, msg);
    }

    public synchronized static void e(String tag, String msg) {
        write(LogLevel.ERROR, msg);
        Log.e(tag, msg);
    }
}
