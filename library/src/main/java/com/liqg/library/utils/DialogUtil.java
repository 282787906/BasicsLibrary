package com.liqg.library.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.liqg.library.R;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public class DialogUtil {

    private static AlertDialog dialog;

    public static void showOkCancel(Context context, String mBody, String okStr, String cancelStr, final OkCancelDialogListener okCancelDialogListener) {

        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.customer_dialog);

        if (cancelStr==null||cancelStr.equals("")){
           window.findViewById(R.id.customer_dialog_tv_Cancel).setVisibility(View.GONE);
        }else {
            ((TextView) window.findViewById(R.id.customer_dialog_tv_Cancel)).setText(cancelStr);
        }


        ((TextView) window.findViewById(R.id.customer_dialog_tv_OK)).setText(okStr);
        ((TextView) window.findViewById(R.id.customer_dialog_tv_Body)).setText(mBody);

        window.findViewById(R.id.customer_dialog_tv_OK).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                okCancelDialogListener.onOkClickListener();
            }
        });
        window.findViewById(R.id.customer_dialog_tv_Cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                okCancelDialogListener.onCancelClickListener();
            }
        });
        dialog.setCancelable(false);
    }

    public interface OkCancelDialogListener {
        void onOkClickListener();

        void onCancelClickListener();
    }

}
