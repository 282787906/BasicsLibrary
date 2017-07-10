package com.liqg.basicslibrary.http;

import android.content.Context;

import com.liqg.library.http.RequestType;

/**
 * Created by liqg
 * 2016/7/20 09:04
 * Note :
 */
public class DownloadApk extends BaseDownload {


    public DownloadApk(Context context, RequestType requestType, String url, String path, String fileName) {
        super(context, requestType, url, path, fileName);
    }

    @Override
    protected boolean analysis(Object object) {
        return true;
    }
}
