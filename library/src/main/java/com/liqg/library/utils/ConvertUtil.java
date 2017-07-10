package com.liqg.library.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by liqg
 * 2017/1/13 09:49
 * Note :
 */
public class ConvertUtil {

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public  static String InputStream2String( InputStream is ){

        byte[] buf = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        try {
            for (int i; (i = is.read(buf)) != -1;) {
                baos.write(buf, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Convert Exception "+e.getMessage();
        }
        try {
           return baos.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Convert Exception "+e.getMessage();
        }
    }
}
