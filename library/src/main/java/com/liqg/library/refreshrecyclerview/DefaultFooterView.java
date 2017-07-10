package com.liqg.library.refreshrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.liqg.library.R;

/**
 * Created by liqg
 * 2017/1/18 09:01
 * Note :
 */
public class DefaultFooterView extends BaseFooterView {

    private View mProgressBar;
    private TextView mHintView;

    public DefaultFooterView(Context context) {
        super(context);
    }

    @Override
    public View initView(Context context) {

        View moreView = LayoutInflater.from(context).inflate(R.layout.recycler_default_footerview, null);
        mProgressBar = moreView.findViewById(R.id.recycler_default_footerview_progressBar);
        mHintView = (TextView) moreView.findViewById(R.id.recycler_default_footerview_tv_hint);

        return moreView;
    }

    @Override
    public void onInitialize() {

        mProgressBar.setVisibility(GONE);
        mHintView.setVisibility(VISIBLE);
        mHintView.setText(R.string.footer_initialize);

    }

    @Override
    public void onOverThreshold() {
        mProgressBar.setVisibility(GONE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.footer_overThreshold);

    }

    @Override
    public void onLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mHintView.setVisibility(GONE);
        mHintView.setText(R.string.footer_loading);

    }
    @Override
    public void onNoMore() {
        mProgressBar.setVisibility(GONE);
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(R.string.footer_noMore);

    }
}