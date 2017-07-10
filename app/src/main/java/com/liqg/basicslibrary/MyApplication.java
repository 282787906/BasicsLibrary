package com.liqg.basicslibrary;

import com.liqg.library.BaseApplication;
import com.liqg.library.business.AppManager;
import com.liqg.library.utils.DialogUtil;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by Liqg on 2017/7/3.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        NoHttp.initialize(this);
        Logger.setDebug(true);
    }

    @Override
    protected void onUnCatchEclipcation(Thread thread, Throwable throwable) {
        DialogUtil.showOkCancel(AppManager.getAppManager().currentActivity(), throwable.getLocalizedMessage(), "确定", null, new DialogUtil.OkCancelDialogListener() {
            @Override
            public void onOkClickListener() {
                AppManager.getAppManager().AppExit();
            }

            @Override
            public void onCancelClickListener() {

                AppManager.getAppManager().AppExit();
            }
        });
    }

    @Override
    protected BaseApplication initApplication() {
        return this;
    }

    @Override
    protected void onApplicationResume() {

    }
}
