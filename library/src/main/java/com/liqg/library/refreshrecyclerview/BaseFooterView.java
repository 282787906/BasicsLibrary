package com.liqg.library.refreshrecyclerview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.liqg.library.utils.ConvertUtil;


/**
 * Created by liqg
 * 2017/1/18 09:01
 * Note :
 */
public abstract class BaseFooterView extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_END = 3;
    protected Context mContext;

    private View mContentView;

    protected String mTag;
    private int footerHeight=50;//dp

    public BaseFooterView(Context context) {
        super(context);
        mTag = getClass().getSimpleName();
        mContext = context;

        addContentView(initView(context));
    }

    protected abstract View initView(Context context);

    private void addContentView(View view) {

        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = view;

        addView(mContentView);
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = ConvertUtil. dp2px(mContext, footerHeight);

        mContentView.setLayoutParams(lp);
        onInitialize();
    }


    private int mState;

    public void setState(int state) {
        try {
            if (state == mState) {
                return;
            }
            if (state == STATE_READY) {
                onOverThreshold();

            } else if (state == STATE_LOADING) {
                onLoading();
            } else if (state == STATE_NORMAL) {
                onInitialize();
            } else if (state == STATE_END) {
                onNoMore();
            }
            mState = state;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void onLoading();

    protected abstract void onInitialize();

    protected abstract void onOverThreshold();
    protected abstract void onNoMore();


    public void setBottomMargin(int height) {
        if (height < 0)
            return;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }


    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = 0;//这里如果设为0那么layoutManger就会抓不到
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        lp.width = LayoutParams.MATCH_PARENT;
        mContentView.setLayoutParams(lp);
    }

}