package com.liqg.library.view.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liqg.library.R;


/**
 * Created by liqg
 * 2015/11/13 09:05
 * Note :
 */
public class ProgressDialog extends BaseDialog {
    private LinearLayout rlMiddle;
    ProgressBar mProgress;
    private TextView tvStr;
    private ImageView ivFinish;

    public ProgressDialog(Context context) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.view_progress_dialog, null);
        rlMiddle = (LinearLayout) view.findViewById(R.id.view_progress_dialog_ll_bg);
        mProgress = (ProgressBar) view.findViewById(R.id.view_progress_dialog_CustomerProgress);
        tvStr = (TextView) view.findViewById(R.id.view_progress_dialog_tv_txt);
        ivFinish = (ImageView) view.findViewById(R.id.view_progress_dialog_ivFinish);
        return view;
    }

    public void setTxt(String str) {
        tvStr.setText(str);
    }

    @Override
    protected void dismissChild() {
        //        rlMiddle.startAnimation(alphaAnimationIn);
    }


    public void success() {
        mProgress.setVisibility(View.GONE);
        ivFinish.setImageResource(R.mipmap.wc);
        AlphaAnimation  alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(animDuration);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1,0.5f,1, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(animDuration);
        scaleAnimation.setFillAfter(true);

        ivFinish.setVisibility(View.VISIBLE);
        AnimationSet set=new AnimationSet(true);    //创建动画集对象
        set.addAnimation(alphaAnimation) ;
        set.addAnimation(scaleAnimation);
        ivFinish.startAnimation(set);

        setTxt("success");
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void fail(String error) {
        mProgress.setVisibility(View.GONE);
        ivFinish.setImageResource(R.mipmap.cuo);
        AlphaAnimation  alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(animDuration);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1,0.5f,1, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(animDuration);
        scaleAnimation.setFillAfter(true);

        ivFinish.setVisibility(View.VISIBLE);
        AnimationSet set=new AnimationSet(true);    //创建动画集对象
        set.addAnimation(alphaAnimation) ;
        set.addAnimation(scaleAnimation);
        ivFinish.startAnimation(set);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        setTxt(error);

    }
}
