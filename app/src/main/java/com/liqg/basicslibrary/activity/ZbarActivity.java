package com.liqg.basicslibrary.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liqg.basicslibrary.R;
import com.liqg.library.business.Permission.CheckPermissionCallback;
import com.liqg.library.business.Permission.Permission;
import com.liqg.library.utils.ToastUtil;
import com.liqg.library.zbar.OnScanListener;
import com.liqg.library.zbar.ScanView;

import java.util.ArrayList;
import java.util.List;

public class ZbarActivity extends BaseActivity implements View.OnClickListener {

    protected ScanView scanView;
    protected TextView activityZbarTvInfo;
boolean hasPermission=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_zbar);

        activityZbarTvInfo = (TextView) findViewById(R.id.activity_zbar_tv_Info);
        scanView = (ScanView) findViewById(R.id.activity_zbar_ScanView);
        scanView.setOnClickListener(ZbarActivity.this);


        final List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission(Manifest.permission.CAMERA,"xaingji"));
        checkPermission(permissions,  new CheckPermissionCallback() {
            @Override
            public void LowerThan_M(List<Permission> permissionList) {//6.0以下只能自行判断 如try catch  获取返回值等
                 if (checkCameraPermission()){
                    hasPermission=true;
                    initView();

                }else {
                    showPremissionDialog(permissionList,null);
                }
            }

            @Override
            public void checkOverPass() {
                hasPermission=true;
                initView();
            }

            @Override
            public void checkOverCancel() {
                ToastUtil.syncToast(mContext, "权限被拒绝，该功能无法使用", handler, 2000, new ToastUtil.SyncToastCallback() {
                    @Override
                    public void onHide() {
                        finish();
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_zbar_ScanView) {
            scanView.reScan();
        }
    }

    private void initView() {

        scanView.init(new OnScanListener() {
            @Override
            public void onScanSuccess(String result) {
                activityZbarTvInfo.setText(result);
            }

            @Override
            public void onScanError(Exception ex, String result) {

                activityZbarTvInfo.setText(ex.getMessage());
            }

            @Override
            public void onScanStart() {
                activityZbarTvInfo.setText("");

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hasPermission) {

        scanView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasPermission) {

        scanView.onResume();
        }
    }
}
