package com.liqg.basicslibrary.business;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.liqg.library.log.MyLog;
import com.liqg.library.utils.Md5Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;


/**
 * Created by liqg
 * 2015/10/31
 */
public class Constant {

static String tag="Constant";

    /**
     * 列表每次加载数据条数
     */
    public static final int LIST_DATA_SIZE = 10;
    public static final String URL = "";
    public static final String NEW_VERSION_APK_PATH =  "appPath";
    public static final String NEW_VERSION_APK_NAME = "app.apk";
    public static final String DB_NAME = "app";

    private static String deviceId;


    public static final int CONNECT_TIME_OUT= 15000;//网络请求连接超时;
    public static final int READ_TIME_OUT= 15000;//网络请求读取超时;



    public static boolean checkCAmeraPermission() {
        Camera camera = null;
        try{
            camera = camera.open();
            camera.release();
            camera = null;
            return  true;
        }catch(Exception e){
            e.printStackTrace();
    return false;
        }
    }


    public static class Url {
        static String DOMAIN =  "http://api.nohttp.net";
        public    static final String UPDATA_APK = DOMAIN + "/phone/update";

//        /**
//         * 登录
//         */
//        public static final String LOGIN = DOMAIN + "/phone/login";
//        /**
//         * 修改密码
//         */
//        public static final String MODIFY_PWD = DOMAIN + "/phone/modifyPwd";

    }

    public static String getDeviceId(Context context) {
        if (deviceId == null || deviceId.isEmpty()) {
//            TelephonyManager tm = (TelephonyManager) activity
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            String imei = tm.getDeviceId();
//
//            WifiManager wifiManager = (WifiManager) activity
//                    .getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wifiinfo = wifiManager.getConnectionInfo();
//
//            String mac = wifiinfo.getMacAddress();

            String serial = Build.SERIAL;


            String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String wlanMac = wm.getConnectionInfo().getMacAddress();

            String devID =
                    Build.BOARD+"_" +
                            Build.BRAND+"_" +
                            Build.DEVICE+"_" +
                            Build.DISPLAY+"_" +
                            Build.HOST+"_" +
                            Build.ID+"_" +
                            Build.MANUFACTURER+"_" +
                            Build.MODEL+"_" +
                            Build.PRODUCT+"_" +
                            Build.TAGS+"_" +
                            Build.TYPE+"_" +
                            Build.USER ; //13 digits
            MyLog.d(tag, "WLANMAC:" + wlanMac);
            MyLog.d(tag, "SERIAL:" + serial);
            MyLog.d(tag, "AndroidID:" + androidID);
            MyLog.d(tag, "DevIDShort:" + devID);

            deviceId = Md5Util.strToMD5(serial + wlanMac+ androidID+ devID);
        }
        return deviceId;
    }
    /**
     * 用户身份
     */
    public static class UserRole {
        /**
         * 游客
         */
        public static final String VISITOR = "0";
        /**
         * 自然人
         */
        public static final String INDIVIDUAL = "1";
        /**
         * 企业
         */
        public static final String ENTERPRISE = "2";

    }

    /**
     * 功能模块打开方式
     * 与configJson文件对应
     */
    public static class OpenWith {
        /**
         * web浏览器
         */
        public static final String WEB = "web";
        /**
         * web浏览器  新闻专用
         */
        public static final String NEWS_WEB = "newsWeb";
        /**
         * 新闻列表
         */
        public static final String NEWS_LIST = "newsList";
        /**
         * 机构列表
         */
        public static final String ORG_LIST = "orgList";
        /**
         * 机构详情
         */
        public static final String ORG = "org";
        /**
         * 机构详情
         */
        public static final String MUL_LIST = "mulList";
        /**
         * 征期日历
         */
        public static final String MY_CALENDAR = "calendar";
        /**
         * 新闻类搜索
         */
        public static final String NEWS_SEARCH = "newsSearch";
        /**
         * 机构类搜索
         */
        public static final String ORG_SEARCH = "orgSearch";
        /**
         * 多级列表搜索
         */
        public static final String MUL_SEARCH = "mulSearch";
        /**
         * 办税服务
         */
        public static final String BAN_SHUI_FU_WU = "bsfw";
        /**
         * 满意度调查
         */
        public static final String WEB_SURVEY = "web_survey";
        /**
         * 收藏
         */
        public static final String NEWS_FAVORITE= "news_favorite";
        //        /**
//         * 服务状态（办税红绿灯）
//         */
//        public static final String SERVICE_STATUS = "serviceStatus";

    }


    public static boolean isDebug(Context context) {


        PackageInfo pkginfo = null;
        try {
            pkginfo = context.getPackageManager().getPackageInfo(context.getPackageName()
                    , PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pkginfo != null) {
            ApplicationInfo info = pkginfo.applicationInfo;
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }

        return false;
    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取APP崩溃异常报告
     *
     * @param ex
     * @return
     */
    public static String getCrashReport(Context context, Throwable ex) {
        StringBuffer sb = new StringBuffer();
        ex.printStackTrace();
        sb.append("BRAND:" + Build.BRAND + "\n");
        sb.append("MODEL:" + Build.MODEL + "\n");
        sb.append("SDK :" + Build.VERSION.SDK_INT + "\n");
        sb.append("RELEASE:" + Build.VERSION.RELEASE + "\n");
        sb.append("Exception: " + ex.getMessage() + "\n");

        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            sb.append(stackTraceElement.toString() + "\n");
        }
        return sb.toString();
    }
}
