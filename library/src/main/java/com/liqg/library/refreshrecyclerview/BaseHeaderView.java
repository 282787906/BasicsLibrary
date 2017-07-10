package com.liqg.library.refreshrecyclerview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.liqg.library.utils.ConvertUtil;


/**
 * Created by liqg
 * 2017/1/18 08:57
 * Note :
 */
public abstract class BaseHeaderView extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_FINISH = 3;

    protected Context mContext;

    private View mContentView;
    protected String mTag;
    private int headerHeight ;//头部高度dp
    private final int MIN_HEADER_HEIGHT = 50;//头部最小高度



    public BaseHeaderView(Context context) {
        super(context);
        mTag = getClass().getSimpleName();
        mContext = context;
        headerHeight=setHeaderHeight();
        if (headerHeight< MIN_HEADER_HEIGHT){
            headerHeight= MIN_HEADER_HEIGHT;
        }
        setBackgroundColor(setHeaderBgColor());
        addContentView(initView(mContext));
     onInitialize();
    }

    protected abstract int setHeaderBgColor();

    /**
     * 头部高度
     * @return
     */
    protected abstract int setHeaderHeight();



    protected abstract View initView(Context context);

    private void addContentView(View view) {

        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));//recyclerView里不加这句话的话宽度就会比较窄

        mContentView = view;
        addView(mContentView);
        mContentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
         lp.height =  ConvertUtil. dp2px(mContext, headerHeight);
        realHeight = lp.height;
        lp.topMargin = -lp.height;
        mContentView.setLayoutParams(lp);
    }

    private int mState;

    public void setState(int state) {
        if (state == mState) {
            return;
        }
        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {//初始值Initialize
                    onLessThreshold();
                } else if (mState == STATE_REFRESHING) {//刷新完成 提示 Finish
                    onFinish();
                }
                break;
            case STATE_READY: //超过阈值  是否刷新  OverThreshold

                onOverThreshold();
                break;
            case STATE_REFRESHING:// 刷新  Refreshing
                onRefreshing();
                break;
            case STATE_FINISH: //完成 恢复初始值Initialize
                onInitialize();
                break;
            default:
        }

        mState = state;
    }

    protected abstract void onFinish();

    protected abstract void onOverThreshold();

    protected abstract void onRefreshing();

    protected abstract void onInitialize();

    protected abstract void onLessThreshold();

    public void setTopMargin(int height) {
        if (mContentView == null)
            return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.topMargin = height;
        mContentView.setLayoutParams(lp);
    }

    //
    public int getTopMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.topMargin;
    }

    public void setHeight(int height) {
        if (mContentView == null)
            return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = height;
        mContentView.setLayoutParams(lp);
    }

    private int realHeight;

    /**
     * 得到这个headerView真实的高度，而且这个高度是自己定的
     *
     * @return
     */
    public int getRealHeight() {
        return realHeight;
    }


}
