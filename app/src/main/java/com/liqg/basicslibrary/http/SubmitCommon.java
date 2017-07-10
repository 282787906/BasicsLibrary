package com.liqg.basicslibrary.http;

import android.content.Context;

import com.liqg.library.http.RequestType;

/**
 * Created by Liqg on 2017/7/4.
 */

public class SubmitCommon extends BaseSubmit {
    public SubmitCommon(Context context, RequestType requestType, String url) {
        super(context, requestType, url);
    }

    @Override
    protected boolean analysis(String result) throws Exception {
        setAnalysisResult(result);
        return true;
    }
}
