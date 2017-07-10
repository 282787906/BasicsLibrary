package com.liqg.library.view.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liqg.library.R;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public abstract class BaseMiddleDialog extends BaseDialog {

    private TextView mTvCancel;
    private View mViewLine;
    protected TextView mTvOK;
    protected LinearLayout mLlBg;


    public BaseMiddleDialog(Context context) {
        super(context);
    }



    public BaseMiddleDialog setOk(String okStr, final OkDialogListener okDialogListener) {
         mTvOK = (TextView) view.findViewById(R.id.customer_dialog_tv_OK);
        mLlBg = (LinearLayout) view.findViewById(R.id.customer_dialog_ll_bg);

        if (mTvOK == null) {
            Log.e(mTag, "mTvOK==null");
            return this;
        }
        mTvOK.setText(okStr);
        mTvOK.setVisibility(View.VISIBLE);
        mLlBg.setVisibility(View.VISIBLE);
        mTvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okDialogListener.onOkClick();
                dismiss();
            }
        });
        return this;
    }

    public BaseMiddleDialog setCancel(String cancelStr, final CancelDialogListener cancelDialogListener) {
        mTvCancel = (TextView) view.findViewById(R.id.customer_dialog_tv_Cancel);
        mViewLine = view.findViewById(R.id.customer_dialog_view_line);
         mLlBg = (LinearLayout) view.findViewById(R.id.customer_dialog_ll_bg);
         if (mTvCancel == null) {
            Log.e(mTag, "mTvCancel==null");
            return this;
        }
        mLlBg.setVisibility(View.VISIBLE);
        mTvCancel.setVisibility(View.VISIBLE);
        mViewLine.setVisibility(View.VISIBLE);
        mTvCancel.setText(cancelStr);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialogListener.onCancelClick();
                dismiss();
            }
        });
        return this;
    }



    public interface OkDialogListener {
        void onOkClick();

    }

    public interface CancelDialogListener {
        void onCancelClick();
    }

}
