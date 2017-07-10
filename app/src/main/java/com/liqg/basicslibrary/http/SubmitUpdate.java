package com.liqg.basicslibrary.http;


import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.liqg.basicslibrary.module.ModuleUpdate;
import com.liqg.library.http.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/23.
 */
public class SubmitUpdate extends BaseSubmit {
      public SubmitUpdate(Context context, RequestType requestType, String url) {
        super(context, requestType, url);
    }


    @Override
    protected boolean analysis(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            ModuleUpdate updateModule = gson.fromJson(jsonObject.getString("data"), ModuleUpdate.class);
            setAnalysisResult(updateModule);

            return true;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            setErrorMsg("analysis JsonSyntaxException");
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            setErrorMsg("analysis JSONException");
            return false;
        }
    }
}
