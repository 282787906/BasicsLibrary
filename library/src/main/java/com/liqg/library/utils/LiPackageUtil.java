package com.liqg.library.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class LiPackageUtil {

    public static String getVersionName(Context context) {
        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (info != null) {
                return info.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            if (info != null) {
                return info.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getPackageName(Context context) {
        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            if (info != null) {
                return info.packageName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(context), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }
}