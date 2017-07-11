package com.liqg.library.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by liqg
 * 2016/3/31 08:35
 * Note :
 */
public class LiEnvironment {
    /**
     * 是否有SdCard
     *
     * return
     */
    public static boolean isSdCardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取失败返回null
     *
     * @return
     */
    public static String getSdCardRoot(Context context) {
        if (Environment.isExternalStorageEmulated()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else {
            return null;
        }
    }

    /**
     * data/data/PackageName/
     * 也可以直接使用context.getFilesDir(); activity.getCacheDir()获取指定目录
     *
     * @return
     */
    public static String getAppRoot(Context context) {

        return Environment.getDataDirectory().getAbsolutePath() + "/data/" + LiPackageUtil.getPackageName(context) + "/";
    }
}
