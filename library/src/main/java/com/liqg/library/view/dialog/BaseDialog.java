package com.liqg.library.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.liqg.library.R;
import com.liqg.library.utils.ConvertUtil;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public abstract class BaseDialog {
    protected Context mContext;
    protected AlertDialog dialog;
    protected long animDuration = 200;
    private RelativeLayout rlBg;
    private AlphaAnimation alphaAnimationIn;
    private AlphaAnimation alphaAnimationOut;
    protected View view;
    String mTag;

    public BaseDialog(Context context) {
        mContext = context;
        mTag = this.getClass().getSimpleName();
        dialog = new AlertDialog.Builder(context, R.style.mydialog).create();

        dialog.show();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && keyEvent.getRepeatCount() == 0) {
                    dismiss();
                }
                return false;
            }
        });
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        params.width = ConvertUtil.dp2px(context, width);
        params.height = ConvertUtil.dp2px(context, height);
        params.width = width;
        params.height = height;
        window.setContentView(initBg(), params);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK  &&!setCancelable()) {
                    return true;
                }
                return false;
            }
        });
    }

    protected abstract boolean setCancelable();


    private View initBg() {
        rlBg = new RelativeLayout(mContext);
        rlBg.setBackgroundColor(Color.argb(100, 100, 100, 100));
        if (setClickBgCancelable()) {
            rlBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        rlBg.addView(initView(), layoutParams);

        alphaAnimationIn = new AlphaAnimation(0, 1);
        alphaAnimationIn.setDuration(animDuration);
        alphaAnimationIn.setFillAfter(true);

        rlBg.startAnimation(alphaAnimationIn);
        return rlBg;
    }

    protected abstract boolean setClickBgCancelable();

    protected abstract View initView();

    public void dismiss() {

        alphaAnimationOut = new AlphaAnimation(1, 0);
        alphaAnimationOut.setDuration(animDuration);
        alphaAnimationOut.setFillAfter(true);
        alphaAnimationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        dismissChild();
        rlBg.startAnimation(alphaAnimationOut);
    }

    protected abstract void dismissChild();

    protected int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }


}
