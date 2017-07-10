package com.liqg.library.log;

import android.content.Context;
import android.os.Build;

import com.liqg.library.utils.DateUtil;
import com.liqg.library.utils.LiPackageUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public abstract class ExceptionHandler implements UncaughtExceptionHandler {

    private Context mContext;


    public ExceptionHandler(Context context) {

        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    public void uncaughtException(Thread thread, Throwable ex) {
        if (ex != null) {
            saveCrashInfo2File(ex);
            ex.printStackTrace();
            onExceptionListener(thread, ex);
        }

    }

    private void saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();

        sb.append("Version:" + LiPackageUtil.getVersionName(mContext) + "\n");
        try {
            sb.append("BRAND:" + Build.class.getDeclaredField("BRAND").get(null).toString() + "\n");
            sb.append("MODEL:" + Build.class.getDeclaredField("MODEL").get(null).toString() + "\n\n");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (String string : LogQueueUtils.get()) {
            sb.append(string + "\n\n");

        }
        sb.append(result);
        sb.append("\n------------------------------------------------------------------------------------------------------");

        LogHelper.write(sb.toString(), "exception_" + DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_CROSECOND) + ".txt");


    }

    public abstract void onExceptionListener(Thread thread, Throwable ex);
}