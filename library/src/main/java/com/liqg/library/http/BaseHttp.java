package com.liqg.library.http;

import android.content.Context;

import com.yanzhenjie.nohttp.RequestMethod;


/**
 * Created by liqg
 * 2015/11/3.
 */
public abstract class BaseHttp {
    private int statusCode;
    private String result;
    public ResponseCallback requestCallback;

    private String errorMsg = "";
    protected Context context;
    protected String url;
    public RequestParams requestParams;
    protected RequestMethod requestMethod;

    public BaseHttp(Context context, RequestType requestType, String url) {
        this.context = context;
        this.url = url;
        requestParams = new RequestParams();
        if (RequestType.POST == requestType) {
            requestMethod = RequestMethod.POST;
        }else if (RequestType.HEAD== requestType) {
            requestMethod = RequestMethod.HEAD;
        } else {
            requestMethod = RequestMethod.GET;
        }
    }

    public abstract void submit(ResponseCallback requestCallback);

    protected abstract boolean analysis(String result) throws Exception;

    protected void onSubmitSuccess(String result) {

        setResult(result);
        try {
            if (analysis(result)) {
                requestCallback.onSuccess();
            } else {
                requestCallback.onFail(0, getErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            requestCallback.onFail(0, e.getMessage());
        }
    }

    protected void onSubmitFailure(int statusCode, String error, String result) {

        setStatusCode(statusCode);
        setResult(result);
        requestCallback.onFail(statusCode, error);
    }

    protected void onSubmitFailure(String error) {
        requestCallback.onFail(1, error);
    }


    protected void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    protected void setResult(String result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResult() {
        return result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setErrorMsg(int errorMsgResId) {
        this.errorMsg = context.getString(errorMsgResId);
    }
}
