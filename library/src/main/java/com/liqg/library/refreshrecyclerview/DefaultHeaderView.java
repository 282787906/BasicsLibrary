package com.liqg.library.refreshrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.liqg.library.R;


public class DefaultHeaderView extends BaseHeaderView  {

    private final int ROTATE_ANIM_DURATION = 200;//箭头动画时长

    private View mProgressBar;
    private ImageView mIvArrow;
    private TextView mTvHint;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    protected DefaultHeaderView(Context context) {
        super(context);

    }

    @Override
    protected int setHeaderBgColor() {
        return 0;
    }

    @Override
    protected int setHeaderHeight() {
        return 50;
    }

    @Override
    public View initView(Context context) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycler_default_headerview,null);
        mIvArrow = (ImageView) view. findViewById(R.id.recycler_default_headerview_iv_arrow);
        mTvHint = (TextView) view.findViewById(R.id.recycler_default_headerview_tv_hint);
        mProgressBar = view.findViewById(R.id.recycler_default_headerview_progressBar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        return view;
    }


    @Override
    public void onInitialize() {
        mIvArrow.clearAnimation();
        mIvArrow.setVisibility(View. VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTvHint.setText(R.string.header_initialize);
    }

    @Override
    public void onFinish() {
        mIvArrow.clearAnimation();
        mIvArrow.setVisibility(View. GONE);
        mProgressBar.setVisibility(View.GONE);
        mTvHint.setText(R.string.header_finish);
    }
    @Override
    public void onLessThreshold() {
        mIvArrow.startAnimation(mRotateDownAnim);
        mIvArrow.setVisibility(View. VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTvHint.setText(R.string.header_lessThreshold);
    }
    @Override
    public void onOverThreshold() {

        mIvArrow.startAnimation(mRotateUpAnim);
        mIvArrow.setVisibility(View. VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTvHint.setText(R.string.header_overThreshold);

    }

    @Override
    public void onRefreshing() {
        mIvArrow.clearAnimation();
        mIvArrow.setVisibility(View. GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTvHint.setText("正在刷新");
    }


}
