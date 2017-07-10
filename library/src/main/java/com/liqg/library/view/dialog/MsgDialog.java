package com.liqg.library.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.liqg.library.R;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public class MsgDialog extends BaseMiddleDialog {

    private TextView mTvBody;



    public MsgDialog(Context context) {
        super(context);
    }


    @Override
    protected boolean setCancelable() {
        return false;
    }

    @Override
    protected boolean setClickBgCancelable() {
        return false;
    }

    @Override
    protected View initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.view_customer_dialog_msg, null);
        mTvBody = (TextView) view.findViewById(R.id.customer_dialog_msg_tv_Body);

//        mTvCancel = (TextView) view.findViewById(R.id.customer_dialog_tv_Cancel);
//        mViewLine = view.findViewById(R.id.customer_dialog_view_line);
//        mTvOK = (TextView) view.findViewById(R.id.customer_dialog_tv_OK);
//        mLlBg = (LinearLayout) view.findViewById(R.id.customer_dialog_ll_bg);
//        if (mLlBg != null) {
//            mLlBg.setVisibility(View.GONE);
//            mTvCancel.setVisibility(View.GONE);
//            mViewLine.setVisibility(View.GONE);
//            mTvOK.setVisibility(View.GONE);
//        }
        return view;
    }

    public MsgDialog setBody(String body) {
        mTvBody.setText(body);
        return this;
    }

//    public MsgDialog setOk(String okStr, final OkDialogListener okDialogListener) {
//        if (mTvOK == null) {
//            Log.e(TAG, "mTvOK==null");
//            return this;
//        }
//        mTvOK.setText(okStr);
//        mTvOK.setVisibility(View.VISIBLE);
//        mLlBg.setVisibility(View.VISIBLE);
//        mTvOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                okDialogListener.onOkClick();
//                dismiss();
//            }
//        });
//        return this;
//    }
//
//    public MsgDialog setCancel(String okStr, final CancelDialogListener cancelDialogListener) {
//        if (mTvCancel == null) {
//            Log.e(TAG, "mTvCancel==null");
//            return this;
//        }
//        mLlBg.setVisibility(View.VISIBLE);
//        mTvCancel.setVisibility(View.VISIBLE);
//        mViewLine.setVisibility(View.VISIBLE);
//        mTvCancel.setText(okStr);
//        mTvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cancelDialogListener.onCancelClick();
//                dismiss();
//            }
//        });
//        return this;
//    }

    @Override
    protected void dismissChild() {
        //        rlMiddle.startAnimation(alphaAnimationIn);
    }
}
