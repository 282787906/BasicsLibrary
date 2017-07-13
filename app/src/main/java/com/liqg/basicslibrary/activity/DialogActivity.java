package com.liqg.basicslibrary.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liqg.basicslibrary.R;
import com.liqg.library.utils.DialogUtil;
import com.liqg.library.utils.WaitDialog;
import com.liqg.library.view.dialog.BaseMiddleDialog;
import com.liqg.library.view.dialog.BottomDialog;
import com.liqg.library.view.dialog.CodeDialog;
import com.liqg.library.view.dialog.MsgDialog;
import com.liqg.library.view.dialog.ProgressDialog;

public class DialogActivity extends BaseActivity implements View.OnClickListener {

    protected Button activityDialogBtnDialogUtil;
    protected Button activityDialogBtnWaitDialog;
    protected Button activityDialogBtnProgressDialog;
    protected Button activityDialogBtnCodeDialog;
    protected Button activityDialogBtnMsgDialog;
    protected Button activityDialogBtnBottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_dialog);
        initView();
    }

    private void initView() {
        activityDialogBtnDialogUtil = (Button) findViewById(R.id.activity_dialog_btn_DialogUtil);
        activityDialogBtnDialogUtil.setOnClickListener(DialogActivity.this);
        activityDialogBtnWaitDialog = (Button) findViewById(R.id.activity_dialog_btn_WaitDialog);
        activityDialogBtnWaitDialog.setOnClickListener(DialogActivity.this);
        activityDialogBtnProgressDialog = (Button) findViewById(R.id.activity_dialog_btn_ProgressDialog);
        activityDialogBtnProgressDialog.setOnClickListener(DialogActivity.this);
        activityDialogBtnCodeDialog = (Button) findViewById(R.id.activity_dialog_btn_CodeDialog);
        activityDialogBtnCodeDialog.setOnClickListener(DialogActivity.this);
        activityDialogBtnMsgDialog = (Button) findViewById(R.id.activity_dialog_btn_MsgDialog);
        activityDialogBtnMsgDialog.setOnClickListener(DialogActivity.this);
        activityDialogBtnBottomDialog = (Button) findViewById(R.id.activity_dialog_btn_BottomDialog);
        activityDialogBtnBottomDialog.setOnClickListener(DialogActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_dialog_btn_DialogUtil) {
            WaitDialog.show(mContext);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WaitDialog.hide();
                }
            }, 3000);
        } else if (view.getId() == R.id.activity_dialog_btn_WaitDialog) {
            DialogUtil.showOkCancel(mContext, "body", "ok", "cancel", new DialogUtil.OkCancelDialogListener() {
                @Override
                public void onOkClickListener() {

                }

                @Override
                public void onCancelClickListener() {

                }
            });
        } else if (view.getId() == R.id.activity_dialog_btn_ProgressDialog) {
            final ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setTxt("Loading");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.fail("error");
                }
            }, 2000);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.success();
                }
            }, 3000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.success();
                }
            }, 5000);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 7000);


        } else if (view.getId() == R.id.activity_dialog_btn_CodeDialog) {
            CodeDialog codeDialog = new CodeDialog(mContext, new CodeDialog.CodeDialogListener() {
                @Override
                public void onReSendClick() {

                }
            });
            codeDialog.setBody("验证短信已发送").
                    setOk("确定", new BaseMiddleDialog.OkDialogListener() {
                        @Override
                        public void onOkClick() {

                        }
                    }).
                    setCancel("取消", new BaseMiddleDialog.CancelDialogListener() {
                        @Override
                        public void onCancelClick() {

                        }
                    });
        } else if (view.getId() == R.id.activity_dialog_btn_MsgDialog) {
            MsgDialog msgDialog = new MsgDialog(mContext);
            msgDialog.setBody("body")
                    .setOk("ok", new BaseMiddleDialog.OkDialogListener() {
                        @Override
                        public void onOkClick() {

                        }
                    }).setCancel("cancel", new BaseMiddleDialog.CancelDialogListener() {
                @Override
                public void onCancelClick() {

                }
            });
        } else if (view.getId() == R.id.activity_dialog_btn_BottomDialog) {
            BottomDialog msgDialog = new BottomDialog(mContext);
//            msgDialog .setOk("ok", new BaseMiddleDialog.OkDialogListener() {
//                        @Override
//                        public void onOkClick() {
//
//                        }
//                    }).setCancel("cancel", new BaseMiddleDialog.CancelDialogListener() {
//                @Override
//                public void onCancelClick() {
//
//                }
//            });
        }
    }
}
