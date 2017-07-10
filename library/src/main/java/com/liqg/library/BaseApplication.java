package com.liqg.library;

import android.app.Application;
import android.content.Context;
import android.os.Looper;

import com.liqg.library.log.ExceptionHandler;


/**
 * Created by liqg
 * 2015/10/23.
 */
public abstract class BaseApplication extends Application {
    protected String tag = BaseApplication.class.getSimpleName();
    protected Context context;
    static BaseApplication application;
    /**
     * Loading界面重新加载等待时间(毫秒)
     */
    public long pauseToReloadTime = 1200000;//20分钟
    public long pauseTime = 0;


    public static BaseApplication   getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = initApplication();
        context = application.getApplicationContext();
        // 注册App异常崩溃处理器
        new ExceptionHandler(context) {
            @Override
            public void onExceptionListener(final Thread thread, final Throwable throwable) {
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        throwable.printStackTrace();
                        onUnCatchEclipcation(  thread,   throwable);
                        Looper.loop();
                    }
                }.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

    }

    protected abstract void onUnCatchEclipcation(Thread thread, Throwable throwable);

    protected abstract BaseApplication initApplication();




    /**
     * 应用Resume时间，应用从后台回前端时调用
     */
    protected abstract void onApplicationResume();

    public   void onAppResume() {
        onApplicationResume();
        pauseTime =0;
    }

    public   void onAppPause() {
        pauseTime = System.currentTimeMillis();
    }
}
