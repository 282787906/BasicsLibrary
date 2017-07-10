package com.liqg.library.business;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * Activity管理
 * <p>
 * Created by liqg
 * 2015/11/10 18:38
 * Note :
 */
public class AppManager {
    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    private Stack<Activity> activityStack;
    private static AppManager appManager;
    String tag = AppManager.class.getSimpleName();

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            //            activity.finish();//会导致activity旋转后执行两次onDestroy  界面消失
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivityExceptByName(String cName) {
        Activity mainActivity = null;
        for (Activity activity : activityStack) {
            if (null != activity) {

                if (!activity.getClass().getSimpleName().equals(cName)) {
                    activity.finish();
                } else {
                    mainActivity = activity;
                }
                Log.d(tag, "finishAllActivityExceptByName:" + cName);
            }
        }

        activityStack.clear();
        activityStack.add(mainActivity);


        //        for (int i = 0 ; i <activityStack.size(); i++){
        //            if (null != activityStack.get(i)){
        //
        //                if (!activityStack.get(i).getClass().getSimpleName().equals(cName)){
        //                activityStack.get(i).finish();
        //
        //                activityStack.remove(activityStack.get(i));}
        //                else {
        //                    Log.d(tag,"finishAllActivityExceptByName:"+cName);
        //                }
        //            }
        //        }
        Log.d(tag, "activityStack.size(): " + activityStack.size());
    }
    //    /**
    //     * 获得所有IdeaCodeActivity
    //     * @return
    //     */
    //    public List<BaseActivity> getAllActivity(){
    //        ArrayList<BaseActivity> listActivity = new ArrayList<BaseActivity>();
    //        for (Activity activity : activityStack) {
    //            listActivity.add((BaseActivity)activity);
    //        }
    //        return listActivity;
    //    }
    //    /**
    //     * 根据Activity名称返回指定的Activity
    //     * @param name
    //     * @return
    //     */
    //    public BaseActivity getActivityByName(String name){
    //        for (Activity ia : activityStack) {
    //            if (ia.getClass().getName().indexOf(name) >= 0) {
    //                return (BaseActivity)ia;
    //            }
    //        }
    //        return null;
    //    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
