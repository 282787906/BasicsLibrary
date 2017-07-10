package com.liqg.basicslibrary.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.liqg.basicslibrary.R;
import com.liqg.basicslibrary.business.Constant;
import com.liqg.library.http.BaseHttp;
import com.liqg.library.http.CallServer;
import com.liqg.library.http.RequestType;
import com.liqg.library.http.ResponseCallback;
import com.liqg.library.utils.ConnUtil;
import com.liqg.library.utils.ResUtil;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.net.ProtocolException;
import java.util.Map;

/**
 * Created by liqg
 * 2016/7/18 14:50
 * Note :
 */
public abstract class BaseSubmit extends BaseHttp {
    String tag = BaseSubmit.class.getSimpleName();
    Request<String> request;
    Gson gson;

    public Request<String> getRequest() {
        return request;
    }


    public BaseSubmit(Context context, RequestType requestType, String url) {
        super(context, requestType, url);
        request = NoHttp.createStringRequest(url, requestMethod);
        request.setCacheMode(CacheMode.DEFAULT);

        gson = new Gson();
    }

    @Override
    public void submit(final ResponseCallback requestCallback) {
        super.requestCallback = requestCallback;
        // 检查网络连接
        if (ConnUtil.getNetType(context) == null) {
            // 无网络连接
            onSubmitFailure(ResUtil.getString(context, R.string.noNetWork));
            return;
        }
        Log.d(tag, "url:" + url);

        request.removeAll();
        for (Map.Entry<String, String> e : requestParams.getUrlParams().entrySet()) {
            request.set(e.getKey(), e.getValue());
            Log.d(tag, "param  key:" + e.getKey() + "  value:" + e.getValue());
        }
        for (Map.Entry<String, Binary> e : requestParams.getBinaryParams().entrySet()) {
            request.add(e.getKey(), e.getValue());
            Log.d(tag, "param  key:" + e.getKey() + "  value:" + e.getValue().getFileName());
        }
        request.setConnectTimeout(Constant.CONNECT_TIME_OUT);
        request.setReadTimeout(Constant.READ_TIME_OUT);
        CallServer.getRequestInstance().add(0, request, new OnResponseListener<String>() {

            @Override
            public void onStart(int what) {

            }


            @Override
            public void onSucceed(int what, Response<String> response) {//请求返回  返回数据根据通信协议先解析底层
                int responseCode = response.getHeaders().getResponseCode();// 服务器响应码
                int bodyLength = response.get().length();
                Log.d(tag, "bodyLength：" + bodyLength);
                if (response.getException()!=null){

                int exceptionLength = response.getException().toString().length();
                Log.d(tag, "exceptionLength：" + exceptionLength);
                }
                int networkMillisLength = String.valueOf(response.getNetworkMillis()).length();
                Log.d(tag, "networkMillisLength：" + networkMillisLength);


                if (responseCode == 200) {

                    onSubmitSuccess(response.get());
                    Log.d(tag, " response getException：" + response.getException());
                    Log.d(tag, " response getContentDisposition：" + response.getHeaders().getContentDisposition());
                    Log.d(tag, " response getContentLength：" + response.getHeaders().getContentLength());
                    Log.d(tag, " response get：" + response.get());
                    Log.d(tag, " response getNetworkMillis：" + response.getNetworkMillis());
                    Log.d(tag, " response getLocation：" + response.getHeaders().getLocation());
                } else {

                    onSubmitFailure(responseCode,  response.get(), response.get());
                    //                    requestCallback.onFail(responseCode, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Exception exception = response.getException();
                //                requestCallback.onFail(responseCode, exception.getMessage());

                if (exception instanceof NetworkError) {// 网络不好
                    setErrorMsg(R.string.error_please_check_network);
                } else if (exception instanceof TimeoutError) {// 请求超时
                    setErrorMsg(R.string.error_timeout);
                } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                    setErrorMsg(R.string.error_not_found_server);
                } else if (exception instanceof URLError) {// URL是错的
                    setErrorMsg(R.string.error_url_error);
                } else if (exception instanceof NotFoundCacheError) {
                    // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                    setErrorMsg(R.string.error_not_found_cache);
                } else if (exception instanceof ProtocolException) {
                    setErrorMsg(R.string.error_system_unsupport_method);
                } else {
                    setErrorMsg(exception.getMessage());
                }
                onSubmitFailure(response.responseCode(), getErrorMsg(), exception.getMessage());
                Logger.e("错误：" + exception.getMessage());
            }


            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 获取解析结果
     */
    public Object getAnalysisResult() {
        return analysisResult;
    }

    /**
     * 设置解析结果
     *
     * @param analysisResult 解析结果
     */
    public void setAnalysisResult(Object analysisResult) {
        this.analysisResult = analysisResult;
    }

    protected Object analysisResult;
}
