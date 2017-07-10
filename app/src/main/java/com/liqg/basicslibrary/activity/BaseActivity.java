package com.liqg.basicslibrary.activity;

import android.content.Intent;
import android.os.Bundle;

import com.liqg.library.activity.PermissionActivity;

public class BaseActivity extends PermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void startActivity(Class<?> cls) {

        startActivity(new Intent(mContext, cls));
    }

}
