package com.liqg.library.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by liqg
 * on 2015/11/8.
 */
public class ToastUtil {

    static Toast toast_Single;

    private static String tag = ToastUtil.class.getSimpleName();

    @Deprecated
    public static void showSingleToast(Context context, String str, int duration) {
        if (context == null) {
            Log.e(tag, "syncToast activity == null");
            return;
        }
        if (str == null || str.equals("")) {
            Log.e(tag, "syncToast str == null");
            return;
        }
        try {
            if (toast_Single == null) {
                toast_Single = Toast.makeText(context, str, duration);
            } else {
                toast_Single.setText(str);
                toast_Single.setDuration(duration);
            }
            toast_Single.setGravity(Gravity.CENTER, 0, 0);
            toast_Single.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void longToast(Context context, int strId) {
        longToast(context, ResUtil.getString(context, strId));
    }

    public static void longToast(Context context, String str) {
        if (context == null) {
            Log.e(tag, "context == null");
            return;
        }
        if (str == null || str.equals("")) {
            Log.e(tag, "str == null");
            return;
        }
        try {
            if (toast_Single == null) {
                toast_Single = Toast.makeText(context, str, Toast.LENGTH_LONG);
            } else {
                toast_Single.setText(str);
                toast_Single.setDuration(Toast.LENGTH_LONG);
            }
            toast_Single.setGravity(Gravity.CENTER, 0, 0);
            toast_Single.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shortToast(Context context, int strId) {
        shortToast(context, ResUtil.getString(context, strId));
    }

    public static void shortToast(Context context, String str) {
        if (context == null) {
            Log.e(tag, "activity == null");
            return;
        }
        if (str == null || str.equals("")) {
            Log.e(tag, "str == null");
            return;
        }
        try {
            if (toast_Single == null) {
                toast_Single = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            } else {
                toast_Single.setText(str);
                toast_Single.setDuration(Toast.LENGTH_SHORT);
            }
            toast_Single.setGravity(Gravity.CENTER, 0, 0);
            toast_Single.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideSingleToast() {
        if (toast_Single != null) {
            toast_Single.cancel();
        }
    }


    /**
     *
     * @param context
     * @param str
     * @param handler
     * @param milliseconds 1000<milliseconds<3500
     * @param syncToastCallback
     */
    public static void syncToast(Context context, String str, Handler handler, int milliseconds, final SyncToastCallback syncToastCallback) {

        if (context == null) {
            Log.e(tag, "syncToast activity == null");
            return;
        }
        if (str == null || str.equals("")) {
            Log.e(tag, "syncToast str == null");
            return;
        }

        if (milliseconds < 1000) {
            milliseconds = 1000;
        } else if (milliseconds > 3500) {
            milliseconds = 3500;
        }
        final Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
                syncToastCallback.onHide();
            }
        }, milliseconds);
    }

    public interface SyncToastCallback {
        public void onHide();
    }
}
