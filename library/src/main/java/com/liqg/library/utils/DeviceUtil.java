package com.liqg.library.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;

/**
 *
 * Created by liqg on 2015/11/4.
 */
public class DeviceUtil {

    public static boolean isSdCardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getSdCardRoot() {

        return Environment.getExternalStorageDirectory().getPath();

    }
    public static boolean isSimExist(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * IMEI
     *
     * @return imei
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * android.permission.ACCESS_WIFI_STATE
     *
     * @return MAC
     */
    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiinfo = wifiManager.getConnectionInfo();

        return wifiinfo.getMacAddress();
    }
}
