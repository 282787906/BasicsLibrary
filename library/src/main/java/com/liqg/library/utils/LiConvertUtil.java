package com.liqg.library.utils;

import android.content.Context;

/**
 * Created by liqg
 * 2016/3/31 09:24
 * Note :
 */
public class LiConvertUtil {

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
