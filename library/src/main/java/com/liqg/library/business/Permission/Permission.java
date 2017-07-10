package com.liqg.library.business.Permission;

import android.content.Context;

/**
 * Created by liqg
 * 2016/7/11 14:26
 * Note :
 */
public class Permission {
    /**
     * 权限名
     */
    private String permissionName;
    /**
     * 权限中文名
     */
    private String reason;



    public Permission(String permissionName, String reason ) {
        this.permissionName = permissionName;
        this.reason = reason;
    }
    public Permission(String permissionName, Context context, int reasonResId ) {
        this.permissionName = permissionName;
        this.reason = context.getString(reasonResId);
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getReason() {
        return reason;
    }
}
