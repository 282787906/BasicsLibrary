package com.liqg.library.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.liqg.library.BaseApplication;
import com.liqg.library.business.AppManager;
import com.liqg.library.business.Permission.CheckPermissionCallback;
import com.liqg.library.business.Permission.Permission;
import com.liqg.library.utils.ConvertUtil;
import com.liqg.library.utils.DialogUtil;
import com.liqg.library.utils.FileUtil;
import com.liqg.library.utils.LiEnvironment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Activity 基类
 * Created by liqg
 * 2015/11/10 18:48
 * Note :
 */
public abstract class PermissionActivity extends AppCompatActivity {
    protected Context mContext;
    protected String TAG;
    protected Handler handler;
    protected Activity activity;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        TAG = getClass().getSimpleName();
        mContext=this;
        handler = new Handler();
        AppManager.getAppManager().addActivity(this);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }


    @Override
    protected void onPause() {

        super.onPause();


        BaseApplication.getInstance().onAppPause();

    }

    @Override
    protected void onResume() {
        super.onResume();


        BaseApplication.getInstance().onAppResume();


    }

    /**
     * 必要权限授权界面返回
     */
    private final int NEEDFUL_PERMISSIONS_RESULT = 101;

    private CheckPermissionCallback checkPermissionCallback;

    List<Permission> permissionList;

    protected void checkPermission(Permission permission,   CheckPermissionCallback checkPermissionCallback) {
        List<Permission> temp = new ArrayList<>();
        temp.add(permission);
        checkPermission(temp,checkPermissionCallback);

    }
    /**
     * 权限 验证 6.0   6.0以下只能自行判断 如try catch  获取返回值等
     * （必要应用程序启动时调用 如loading界面，
     * 非必要权限再使用时验证）
     *
     * @param checkPermissionCallback
     */
    protected void checkPermission(List<Permission> permissionList,   CheckPermissionCallback checkPermissionCallback) {
        this.checkPermissionCallback = checkPermissionCallback;
        this.permissionList = permissionList;
        List<Permission> temp = new ArrayList<>();
        for (Permission permission : permissionList) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(permission.getPermissionName()) == PackageManager.PERMISSION_DENIED) {//启动时检测是否时必要权限且没有授权
                    temp.add(permission);
                }
            } else {
                //  6.0以下只能自行判断 如try catch  获取返回值等
                checkPermissionCallback.LowerThan_M(permissionList);
                return;
            }
        }

        if (temp.size() > 0) {
            String[] permissions = new String[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                permissions[i] = temp.get(i).getPermissionName();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                      requestPermissions(permissions, NEEDFUL_PERMISSIONS_RESULT);

            } else {
                showPremissionDialog(permissionList, permissions[0]);
            }
        } else {
            checkPermissionCallback.checkOverPass();
        }
    }

    public void showPremissionDialog(List<Permission> permissions, String permissionsName ) {
        String reason = "";
        for (Permission permission : permissions) {
            if (permission.getPermissionName().equals(permissionsName)) {
                reason = permission.getReason();
                break;
            }
        }
            DialogUtil.showOkCancel(AppManager.getAppManager().currentActivity(),   "我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！", "去设置", "取消", new DialogUtil.OkCancelDialogListener() {
                @Override
                public void onOkClickListener() {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivityForResult(intent, NEEDFUL_PERMISSIONS_RESULT);
                }

                @Override
                public void onCancelClickListener() {
                    checkPermissionCallback.checkOverCancel();
                }
            });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

             for (int g : grantResults) {
                if (g == PackageManager.PERMISSION_GRANTED) {
                    checkPermission(permissionList,  checkPermissionCallback);
                    return;
                }
            }
            showPremissionDialog(permissionList, permissions[0]);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NEEDFUL_PERMISSIONS_RESULT == requestCode) {
            checkPermission(permissionList,  checkPermissionCallback);
        }
    }
    /**
     * 6.0以下判断相机是否可用
     *  返回true 表示可以使用  返回false表示不可以使用
     */
    protected boolean checkCameraPermission() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }
    /**
     * 6.0以下判断SD卡是否可用
     *  返回true 表示可以使用  回false表示不可以使用
     */
    protected boolean checkSDPermission() {
        try {

             FileUtil.writeFile( LiEnvironment.getSdCardRoot(mContext),"liqgtest","test".getBytes());
            InputStream is= FileUtil.readFile( LiEnvironment.getSdCardRoot(mContext)+"liqgtest");
            if (ConvertUtil.InputStream2String(is).equals("test")){

                return  true;
            }else {

                Log.e(TAG,"checkSDPermission equals false "+ConvertUtil.InputStream2String(is));
                return  false;
            }
        }catch (Exception ex){
            Log.e(TAG,"checkSDPermission Exception "+ex.getMessage());
            return  false;
        }

    }
}
