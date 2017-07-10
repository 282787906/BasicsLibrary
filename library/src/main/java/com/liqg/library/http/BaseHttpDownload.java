package com.liqg.library.http;

import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;


/**
 * Created by liqg
 * 2015/11/3.
 */
public abstract class BaseHttpDownload {
    private int statusCode;
    private Object responseBody;
    public ResponseCallbackProgress responseCallbackProgress;
    private String errorMsg = "";
    protected Context context;
    protected String url;
    protected String path;
    protected String fileName;
    public RequestParams requestParams;
    protected RequestMethod requestMethod;

    public BaseHttpDownload(Context context, RequestType requestType, String url, String path, String fileName) {
        this.context = context;
        this.url = url;
        this.path = path;
        this.fileName = fileName;
        requestParams = new RequestParams();
        if (RequestType.POST == requestType) {
            requestMethod = RequestMethod.POST;
        } else {
            requestMethod = RequestMethod.GET;
        }
    }

    public abstract void submit(ResponseCallbackProgress requestCallback);

    protected abstract boolean analysis(Object object);

    protected void onSubmitSuccess(Object responseBody) {

        setResponseBody(responseBody);
        if (analysis(responseBody)) {
            responseCallbackProgress.onSuccess();
        } else {
            responseCallbackProgress.onFail(0, getErrorMsg());
        }
    }

    protected void onSubmitFailure(int statusCode, String error, Object responseBody) {

        setStatusCode(statusCode);
        setResponseBody(responseBody);
        responseCallbackProgress.onFail(statusCode, error);
    }

    protected void onSubmitFailure(String error) {
        responseCallbackProgress.onFail(1, error);
    }

    protected void onDownloadProgress(int progress, long fileCount) {
        responseCallbackProgress.onProgress(progress, fileCount);
    }

    protected void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    protected void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
