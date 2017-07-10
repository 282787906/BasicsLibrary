package com.liqg.library.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.liqg.library.R;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public class WaitDialog {

    private static AlertDialog dialog;

    public static void show(Context context) {

        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_wait);
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = (int) ResUtil.getDimension(context, R.dimen.wait_dialog_WH);
        params.height = (int) ResUtil.getDimension(context, R.dimen.wait_dialog_WH);
        window.setAttributes(params);

        dialog.setCancelable(false);
    }

    public static void hide() {

        if (dialog != null && dialog.isShowing()) {

            dialog.dismiss();
        }
    }
}
