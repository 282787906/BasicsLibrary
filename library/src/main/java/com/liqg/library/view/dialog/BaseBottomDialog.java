package com.liqg.library.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.liqg.library.R;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public class BaseBottomDialog extends BaseDialog {
    private RelativeLayout rlBottom;

    public BaseBottomDialog(Context context) {
        super(context);
    }


    @Override
    protected boolean setCancelable() {
        return true;
    }

    @Override
    protected boolean setClickBgCancelable() {
        return true;
    }

    @Override
    protected View initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.view_base_dialog, null);
        rlBottom = (RelativeLayout) view.findViewById(R.id.dialog_rl_bottom);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, rlBottom.getLayoutParams().height, 0);
        translateAnimation.setDuration(animDuration);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator(mContext, null));
        rlBottom.startAnimation(translateAnimation);

        return view;
    }

    @Override
    protected void dismissChild() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, rlBottom.getLayoutParams().height);

        translateAnimation.setDuration(animDuration);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator(mContext, null));
        rlBottom.startAnimation(translateAnimation);
    }


}
