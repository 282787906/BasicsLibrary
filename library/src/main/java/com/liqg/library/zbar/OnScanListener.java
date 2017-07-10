package com.liqg.library.zbar;

/**
 * Created by liqg
 * 2016/12/20 10:04
 * Note :
 */
public interface OnScanListener {
    void onScanSuccess(String result);
    void onScanError(Exception ex, String result);
    void onScanStart();
}
