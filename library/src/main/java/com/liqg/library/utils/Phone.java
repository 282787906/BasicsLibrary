package com.liqg.library.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by liqg on 2015/3/27.
 */
public class Phone {

    /**
     * 只进入拨号界面，不拨打
     *
     * @param context
     * @param pNo
     */
    public static void readyCall(Context context, String pNo) {

        Uri uri = Uri.parse("tel:" + pNo);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    /**
     * 只进入拨号界面，不拨打
     *
     * @param context
     * @param pNo
     */
    public static void call(Context context, String pNo) {
        Uri uri = Uri.parse("tel:" + pNo);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 验证号码 手机号 固话均可
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
                + "(^0[3-9] {1}d{2}-?d{7,8}$)|"
                + "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
                + "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //判断，返回布尔值
    public static boolean isPhoneNumber(String phoneNumber) {
        boolean isValid = false;
        //   Pattern pattern= Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
        //13********* ,15********,18*********
        try {
            Pattern regex = Pattern.compile("^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$");
            Matcher matcher = regex.matcher(phoneNumber);
            isValid = matcher.matches();
        } catch (Exception e) {
            isValid = false;
        }
       /* if(isValid||isPhoneNumberValid(phoneNumber) ){
            isValid= true;
        }else{
            isValid=false;
        }*/
        return isValid;
    }
}


