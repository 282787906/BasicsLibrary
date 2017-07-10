package com.liqg.library.view.dialog;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liqg.library.R;
import com.liqg.library.utils.ToastUtil;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public class CodeDialog extends BaseMiddleDialog {

    private TextView mTvBody;
    private EditText mEtCode;
    private Button mButReSendCode;
    /**
     * 验证码重新发送间隔（毫秒）
     */
    private long sendCodeWait=30000;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

    CodeDialogListener codeDialogListener;

    public CodeDialog(Context context, CodeDialogListener codeDialogListener) {
        super(context);
        this.codeDialogListener = codeDialogListener;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.view_customer_dialog_code, null);
        mTvBody = (TextView) view.findViewById(R.id.customer_dialog_code_tv_Body);
        mEtCode = (EditText) view.findViewById(R.id.customer_dialog_code_et_msgCode);
        mButReSendCode = (Button) view.findViewById(R.id.customer_dialog_code_but_ReSendCode);
        mButReSendCode.setEnabled(false);
        new CountDownTimer(sendCodeWait, 1000) {
            public void onTick(long millisUntilFinished) {
                mButReSendCode.setText((int) (millisUntilFinished / 1000) + "S后重发");
            }

            public void onFinish() {
                mButReSendCode.setEnabled(true);
                mButReSendCode.setText(R.string.getCode);
            }
        }.start();
        mButReSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeDialogListener.onReSendClick();
                mButReSendCode.setEnabled(false);
                new CountDownTimer(sendCodeWait, 1000) {
                    public void onTick(long millisUntilFinished) {
                        mButReSendCode.setText((int) (millisUntilFinished / 1000) + "S后重发");
                    }

                    public void onFinish() {
                        mButReSendCode.setEnabled(true);
                        mButReSendCode.setText(R.string.getCode);
                    }
                }.start();
            }
        });
        return view;
    }

    public CodeDialog setBody(String body) {
        mTvBody.setText(body);
        return this;
    }

    public CodeDialog setCancel(String okStr, final CancelDialogListener cancelDialogListener) {

        super.setCancel(okStr,cancelDialogListener);
        return  this;
    }

    public CodeDialog setOk(String okStr, final OkDialogListener okDialogListener) {
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
                String code = mEtCode.getText().toString().trim();
                if (code.length() == 6) {
                    setCode(code);
                    okDialogListener.onOkClick();
                    dismiss();
                } else {
                    ToastUtil.shortToast(mContext, R.string.hint_code);
                }
            }
        });
        return this;
    }


    @Override
    protected void dismissChild() {
        //        rlMiddle.startAnimation(alphaAnimationIn);
    }

    public interface CodeDialogListener {
        void onReSendClick();
    }
}
