package com.liqg.library.utils;

import android.content.Context;

/**
 * Created by liqg on 2015/11/4.
 */
public class ResUtil {

    /**
     * @param context
     * @param resId
     * @return
     */
    public static String getString(Context context, int resId) {
        if (context == null) {
            return "activity==null";
        }
        try {
            return context.getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return "getString Exception";
        }

    }

    /**
     * @param context
     * @param resId
     * @return
     */
    public static float getDimension(Context context, int resId) {

        if (context == null) {
            return -1;
        }
        try {
            return context.getResources()
                    .getDimension(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }


    }

    /**
     * @param context
     * @param resId
     * @return
     */
    public static int getColor(Context context, int resId) {
        if (context == null) {
            return -1;
        }
        try {
            return context.getResources().getColor(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }

    }
}
