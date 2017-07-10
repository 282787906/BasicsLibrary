package com.liqg.library.log;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.liqg.library.utils.DateUtil;
import com.liqg.library.utils.LiPackageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Administrator
 */

public class LogHelper {
    static String tag = "LogHelper";
    static FileOutputStream out;
    static String Separator1 = "----";
    static String Separator2 = "\n";
    private static String logPath;
    private static String logName = "";
    private static String path = "Log";
    static Context context;

    private static boolean enableInFile;

    /**
     */
    public static void init(Context context, String path, String name, boolean enableInFile) {
        PackageManager pm = context.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED != pm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, LiPackageUtil.getPackageName(context))) {
            Log.e(tag, "Unset SdCard Permission:" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return;
        }

        LogHelper.context = context;
        LogHelper.enableInFile = enableInFile;
        if (path != null && path != "") {
            logName = name + ".txt";
        }
        if (path != null && path != "") {
            LogHelper.path = path;
        }
        defaultLogPath();

        File dir = new File(logPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(tag, "mkdirs fail:" + logPath);
                LogHelper.path = "Log";
                defaultLogPath();
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        Log.e(tag, "mkdirs fail :" + logPath);
                    }
                }
            }

        }
        if (!enableInFile) {//不写文件  删除目录


        }

    }

    private static void defaultLogPath() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            logPath = Environment.getExternalStorageDirectory() + "/" +
                    LiPackageUtil.getPackageName(context) + "/" + path;
        } else {
            logPath = context.getFilesDir() + "/" + path;
        }
    }

    /**
     * Log write
     *
     * @param msg
     */

    public static void write(String msg) {
        if (context == null || logPath == null) {
            Log.e(tag, "LogHelper init fail");
            return;
        }
        if (logName == "") {
            logName = "log_" + DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_DATE) + ".txt";
        }

        String logMessage = "\n\n" + DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_MICROSECOND) + Separator2 + msg;
        LogQueueUtils.set(logMessage);
        if (enableInFile) {
            writeStream(logMessage, logPath + "/" + logName, true);
        }
    }

    /**
     * Exception write
     *
     * @param msg
     * @param fileName
     */
    public static void write(String msg, String fileName) {
        if (context == null || logPath == null) {
            Log.e(tag, "LogHelper init fail");
            return;
        }
        if (fileName == null || fileName == "") {
            logName = "log_" + DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_DATE) + ".txt";
        }
        String logMessage = DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_DATE) + Separator2 + msg;
        LogQueueUtils.set(logMessage);

        if (enableInFile) {
            writeStream(logMessage, logPath + "/" + logName, true);
        }
    }

    private static void writeStream(String logMessage, String pathAndName, boolean append) {
        try {
            out = new FileOutputStream(pathAndName, append);
            byte buf[] = logMessage.getBytes();
            out.write(buf);
        } catch (FileNotFoundException e) {
            Log.e(tag, "writeStream FileNotFoundException:" + pathAndName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(tag, "writeStream:" + e.toString());
        }
    }


    private String ReplaceSeparator(String info) {

        String tempString = "";
        for (int i = 0; i < info.length(); i++) {
            if (info.substring(i, i + 1).equals("!")) {
                tempString += "@#";
            } else if (info.substring(i, i + 1).equals("@")) {
                tempString += "@@";
            } else if (info.substring(i, i + 1).equals("#")) {
                tempString += "##";
            } else {
                tempString += info.substring(i, i + 1);
            }

        }

        return tempString;

    }

}
