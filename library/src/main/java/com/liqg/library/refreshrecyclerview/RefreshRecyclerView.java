package com.liqg.library.refreshrecyclerview;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;


public class RefreshRecyclerView extends RecyclerView {
    private int footerHeight = -1;
    LinearLayoutManager layoutManager;
    // -- footer view
    private BaseFooterView mFooterView;
    private BaseHeaderView mHeaderView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean isBottom;
    private boolean mIsFooterReady = false;
    private LoadMoreListener loadMoreListener;

    // -- header view
    private boolean mEnablePullRefresh = true;
    private boolean mIsRefreshing;
    private boolean isHeader;
    private boolean mIsHeaderReady = false;
    private Timer timer;
    private float oldY;
    Handler handler = new Handler();
    private OnRefreshListener refreshListener;
    private BaseRecyclerViewAdapter adapter;
    private int maxPullHeight = 50;//最多下拉高度的px值

    private static final int MAX_PULL_LENGTH = 150;//最多下拉150dp
    private View.OnClickListener footerClickListener;

    Context mContext;

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView(context);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void setAdapter(BaseRecyclerViewAdapter adapter){
        super.setAdapter(adapter);
        this.adapter = adapter;
    }

    public boolean ismPullLoading() {
        return mPullLoading;
    }

    public boolean ismIsRefreshing() {
        return mIsRefreshing;
    }

    private void updateFooterHeight(float delta) {
        if(mFooterView==null)return;
        int bottomMargin = mFooterView.getBottomMargin();
//        Log.i("Alex3","初始delta是"+delta);
        if(delta>50)delta = delta/6;
        if(delta>0) {//越往下滑越难滑
            if(bottomMargin>maxPullHeight)delta = delta*0.65f;
            else if(bottomMargin>maxPullHeight * 0.83333f)delta = delta*0.7f;
            else if(bottomMargin>maxPullHeight * 0.66667f)delta = delta*0.75f;
            else if(bottomMargin>maxPullHeight >> 1)delta = delta*0.8f;
            else if(bottomMargin>maxPullHeight * 0.33333f)delta = delta*0.85f;
            else if(bottomMargin>maxPullHeight * 0.16667F && delta > 20)delta = delta*0.2f;//如果是因为惯性向下迅速的俯冲
            else if(bottomMargin>maxPullHeight * 0.16667F)delta = delta*0.9f;
//            Log.i("Alex3","bottomMargin是"+mFooterView.getBottomMargin()+" delta是"+delta);
        }

        int height = mFooterView.getBottomMargin() + (int) (delta+0.5);

        if (mEnablePullLoad && !mPullLoading) {
            if (height > 150){//必须拉超过一定距离才加载更多
//            if (height > 1){//立即刷新
                mFooterView.setState(BaseFooterView.STATE_READY);
                mIsFooterReady = true;
            } else {
                mFooterView.setState(BaseFooterView.STATE_NORMAL);
                mIsFooterReady = false;
            }
        }
        mFooterView.setBottomMargin(height);


    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 20) {
            this.smoothScrollBy(0,-bottomMargin);
            //一松手就立即开始加载
            if(mIsFooterReady){
                startLoadMore();
            }
        }
    }


    public void setLoadMoreListener(LoadMoreListener listener){
        this.loadMoreListener = listener;
    }

    public void initView(Context context){
        layoutManager = new LinearLayoutManager(context);//自带layoutManager，请勿设置
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        layoutManager.offsetChildrenVertical(height*2);//预加载2/3的卡片
        this.setLayoutManager(layoutManager);
          maxPullHeight = dp2px(getContext() ,MAX_PULL_LENGTH);//最多下拉150dp
        this.footerClickListener = new footerViewClickListener();
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if(isBottom)resetFooterHeight();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://开始惯性移动
                        break;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItemPosition = layoutManager.findLastVisibleItemPosition();
                 if(lastItemPosition == layoutManager.getItemCount()-1 && mEnablePullLoad) {//如果到了最后一个
                    isBottom = true;
                    mFooterView = (BaseFooterView)layoutManager.findViewByPosition(layoutManager.findLastVisibleItemPosition());//一开始还不能hide，因为hide得到最后一个可见的就不是footerview了
                    if(mFooterView!=null) {mFooterView.setOnClickListener(footerClickListener);}
                    if(footerHeight==-1 && mFooterView!=null){
                        mFooterView.show();
                        mFooterView.setState(BaseFooterView.STATE_NORMAL);
                        footerHeight = mFooterView.getMeasuredHeight();//这里的测量一般不会出问题
                      }
                    updateFooterHeight(dy);
                }else if (lastItemPosition == layoutManager.getItemCount() - 1 && isEnd) {//如果到了最后一个 并且isend
                     isBottom = true;
                     mFooterView = (BaseFooterView) layoutManager.findViewByPosition(layoutManager.findLastVisibleItemPosition());//一开始还不能hide，因为hide得到最后一个可见的就不是footerview了
                       if (mFooterView != null) {
                         mEnablePullLoad = false;
                         setEnd();

                     }
                     if (footerHeight == -1 && mFooterView != null) {
                         mFooterView.show();
                         mFooterView.setState(BaseFooterView.STATE_NORMAL);
                         footerHeight = mFooterView.getMeasuredHeight();//这里的测量一般不会出问题

                     }
                     updateFooterHeight(dy);
                 }else if(lastItemPosition == layoutManager.getItemCount()-1 && mEnablePullLoad){//如果到了倒数第二个
                    startLoadMore();//开始加载更多
                }
                else {
                    isBottom = false;
                }
            }
        });
    }

    /**
     * 设置是否开启上拉加载更多的功能
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mPullLoading = false;
        mEnablePullLoad = enable;
        if(adapter!=null)adapter.setPullLoadMoreEnable(enable);//adapter和recyclerView要同时设置
        if(mFooterView==null)return;
        if (!mEnablePullLoad) {
//            this.smoothScrollBy(0,-footerHeight);
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
            mFooterView.setBottomMargin(0);
            //make sure "pull up" don't show a line in bottom when listview with one page
        } else {
            mFooterView.show();
            mFooterView.setState(BaseFooterView.STATE_NORMAL);
            mFooterView.setVisibility(VISIBLE);
            //make sure "pull up" don't show a line in bottom when listview with one page
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * 停止loadmore
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            if(mFooterView==null)return;
            mFooterView.show();
            mFooterView.setState(BaseFooterView.STATE_NORMAL);
        }
    }

    private void startLoadMore() {
        if(mPullLoading)return;
        mPullLoading = true;
        if(mFooterView!=null)mFooterView.setState(BaseFooterView.STATE_LOADING);
         mIsFooterReady = false;
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }

    /**
     * 在刷新时要执行的方法
     */
    public interface LoadMoreListener{
        public void onLoadMore();
    }

    /**
     * 点击loadMore后要执行的事件
     */
    class footerViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startLoadMore();
        }
    }


    private void updateHeaderHeight(float delta) {
        mHeaderView = (BaseHeaderView) layoutManager.findViewByPosition(0);
        if(delta>0){//如果是往下拉
            int topMargin = mHeaderView.getTopMargin();
            if(topMargin>maxPullHeight * 0.33333f)delta = delta*0.5f;
            else if(topMargin>maxPullHeight * 0.16667F)delta = delta*0.55f;
            else if(topMargin>0)delta = delta*0.6f;
            else if(topMargin<0)delta = delta*0.6f;//如果没有被完全拖出来
            mHeaderView.setTopMargin(mHeaderView.getTopMargin() + (int)delta);
        } else{//如果是推回去
            if(!mIsRefreshing || mHeaderView.getTopMargin()>0) {//在刷新的时候不把margin设为负值以在惯性滑动的时候能滑回去
                this.scrollBy(0, (int) delta);//禁止既滚动，又同时减少触摸
                 mHeaderView.setTopMargin(mHeaderView.getTopMargin() + (int) delta);
            }
        }
        if(mHeaderView.getTopMargin()>0 && !mIsRefreshing){
            mIsHeaderReady = true;
            mHeaderView.setState(BaseHeaderView.STATE_READY);
        }//设置为ready状态
        else if(!mIsRefreshing){
            mIsHeaderReady = false;
            mHeaderView.setState(BaseHeaderView.STATE_NORMAL);
        }//设置为普通状态并且缩回去
    }

    @Override
    public void smoothScrollToPosition(final int position) {
        super.smoothScrollToPosition(position);
        final Timer scrollTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int bottomCardPosition = layoutManager.findLastVisibleItemPosition();
                if(bottomCardPosition<position+1){//如果要向下滚
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollBy(0,50);
                        }
                    });
                }else if(bottomCardPosition>position){//如果要向上滚
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollBy(0,-50);
                        }
                    });
                }else {
                    if(scrollTimer!=null)scrollTimer.cancel();
                }
            }
        };
        scrollTimer.schedule(timerTask,0,20);

    }

    /**
     * 在用户非手动强制刷新的时候，通过一个动画把头部一点点冒出来
     */
    private void smoothShowHeader(){
        if(mHeaderView==null)return;
//        if(layoutManager.findFirstVisibleItemPosition()!=0){//如果刷新完毕的时候header不在视野内
//            mHeaderView.setTopMargin(0);
//            return;
//        }
        if(timer!=null)timer.cancel();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(mHeaderView==null){
                    if(timer!=null)timer.cancel();
                    return;
                }
                if(mHeaderView.getTopMargin()<0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mIsRefreshing) {//如果目前是ready状态或者正在刷新状态
                                mHeaderView.setTopMargin(mHeaderView.getTopMargin() +5);
                            }
                        }
                    });
                } else if(timer!=null){//如果已经完全缩回去了，但是动画还没有结束，就结束掉动画
                    timer.cancel();
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask,0,10);
    }

    /**
     * 在用户松手的时候让头部自动收缩回去
     */
    private void resetHeaderHeight() {
        if(mHeaderView==null)mHeaderView = (BaseHeaderView) layoutManager.findViewByPosition(0);
        if(layoutManager.findFirstVisibleItemPosition()!=0){//如果刷新完毕的时候用户没有注视header
            mHeaderView.setTopMargin(-mHeaderView.getRealHeight());
            return;
        }
        if(timer!=null)timer.cancel();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(mHeaderView==null)return;
                 if(mHeaderView.getTopMargin()>-mHeaderView.getRealHeight()){//如果header没有完全缩回去
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mIsHeaderReady || mIsRefreshing) {//如果目前是ready状态或者正在刷新状态
                                 int delta = mHeaderView.getTopMargin() / 9;
                                if (delta < 5) delta = 5;
                                if (mHeaderView.getTopMargin() > 0)
                                    mHeaderView.setTopMargin(mHeaderView.getTopMargin() - delta);
                            } else {//如果是普通状态
                                 mHeaderView.setTopMargin(mHeaderView.getTopMargin() - 5);
                            }
                        }
                    });
                } else if(timer!=null){//如果已经完全缩回去了，但是动画还没有结束，就结束掉动画
                    timer.cancel();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHeaderView.setState(mHeaderView.STATE_FINISH);
                        }
                    });
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask,0,10);
    }


    /**
     * 头部是通过onTouchEvent控制的
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if(!mEnablePullRefresh)break;
                int delta = (int)(event.getY()-oldY);
                oldY = event.getY();
                if (layoutManager.findViewByPosition(0) instanceof BaseHeaderView) {
                    isHeader = true;
                    updateHeaderHeight(delta);//更新margin高度
                }else{
                    isHeader = false;
                    if(mHeaderView!=null && !mIsRefreshing)mHeaderView.setTopMargin(-mHeaderView.getRealHeight());
                }
                break;
//            case MotionEvent.ACTION_DOWN:
//                Log.i("Alex", "touch down");
//                oldY = event.getY();
//                if(timer!=null)timer.cancel();
//                break;
            case MotionEvent.ACTION_UP:
                 if(mIsHeaderReady && !mIsRefreshing)startRefresh();
                if(isHeader)resetHeaderHeight();//抬手之后恢复高度
                break;
            case MotionEvent.ACTION_CANCEL:
                 break;

        }
        return super.onTouchEvent(event);
    }

    /**
     * 因为设置了子元素的onclickListener之后，ontouch方法的down失效，所以要在分发前获取手指的位置
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                 oldY = ev.getY();
                if (timer != null) timer.cancel();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setOnRefreshListener(OnRefreshListener listener){
        this.refreshListener = listener;
    }

    /**
     * 设置是否支持下啦刷新的功能
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mIsRefreshing = false;
        mEnablePullRefresh = enable;
        if(mHeaderView==null)return;
        if (!mEnablePullRefresh) {
            mHeaderView.setOnClickListener(null);
        } else {
            mHeaderView.setState(BaseHeaderView.STATE_NORMAL);
            mHeaderView.setVisibility(VISIBLE);
        }
    }

    /**
     * 停止下拉刷新，并且通过动画让头部自己缩回去
     */
    public void stopRefresh() {
        if (mIsRefreshing == true) {
            mIsRefreshing = false;
            mIsHeaderReady = false;
            if(mHeaderView==null)return;
            mHeaderView.setState(BaseFooterView.STATE_NORMAL);
            resetHeaderHeight();
        }
    }

    /**
     * 在用户没有用手控制的情况下，通过动画把头部露出来并且执行刷新
     */
    public void forceRefresh(){
        if(mHeaderView==null)mHeaderView = (BaseHeaderView) layoutManager.findViewByPosition(0);
        if(mHeaderView!=null)mHeaderView.setState(BaseHeaderView.STATE_REFRESHING);
        mIsRefreshing = true;
        mIsHeaderReady = false;
        smoothShowHeader();
        setEnd2Normal();
        if (refreshListener != null)refreshListener.onRefresh();

    }


    private void startRefresh() {
        mIsRefreshing = true;
        setEnd2Normal();
        mHeaderView.setState(BaseHeaderView.STATE_REFRESHING);
        mIsHeaderReady = false;
        if (refreshListener != null) refreshListener.onRefresh();

    }

    public interface OnRefreshListener{
        public void onRefresh();
    }


    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;//设置屏幕密度，用来px向dp转化

        if (dp == 0) {
            return 0;
        }
        return (int) (dp * density + 0.5f);
    }

    boolean isEnd = false;

    public void setEnd() {
        mEnablePullLoad = false;
        if (mFooterView != null) {
            mFooterView.setOnClickListener(null);
            mFooterView.setState(BaseFooterView.STATE_END);
        }
        isEnd = true;
    }
    public void setEnd2Normal() {
        setPullLoadEnable(true);
        isEnd = false;
    }
}