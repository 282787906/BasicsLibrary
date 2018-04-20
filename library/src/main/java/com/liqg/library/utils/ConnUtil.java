package com.liqg.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * permission ( android.permission.ACCESS_NETWORK_STATE)
 * Created by liqg on 2015/11/4.
 */
public class ConnUtil {
    /**
     *
     *
     * @param context
     * @return wifi | mobile | null
     */
@Deprecated
    public static String getNetType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return networkInfo.getTypeName().toLowerCase();

        } else {
            return null;
        }
    }

    /**
     *
     *
     * @param context
     * @return wifi | mobile | null
     */

    public static ConnType getConnType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (networkInfo.getTypeName().toLowerCase().equals("wifi")){
                return ConnType.WIFI;
            } else{
                return  ConnType.MOBILE;
            }
        } else {
            return ConnType.NULL;
        }
    }
    public enum  ConnType{
        WIFI,MOBILE,NULL
    }
}
