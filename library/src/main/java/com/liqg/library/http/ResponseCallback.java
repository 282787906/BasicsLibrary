package com.liqg.library.http;

/**
 * Created by liqg on 2014/11/7.
 */
public abstract class ResponseCallback {

    public abstract void onSuccess();

    public abstract void onFail(int errorCode,String errorMsg);


}
