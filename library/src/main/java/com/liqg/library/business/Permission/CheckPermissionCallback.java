package com.liqg.library.business.Permission;

import java.util.List;

/**
 * Created by liqg
 * 2016/7/11 14:34
 * Note :
 */
public interface CheckPermissionCallback {
    /**
     * 版本小于6.0
     */
    void   LowerThan_M(List<Permission> permissionList);
    /**
     * 权限授权完成
     */
    void checkOverPass();

    /**
     * 权限申请未授权且点击提示框取消按钮
     */
    void checkOverCancel();
}
