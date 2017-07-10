package com.liqg.basicslibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liqg.basicslibrary.R;
import com.liqg.basicslibrary.http.SubmitCommon;
import com.liqg.library.refreshrecyclerview.BaseRecyclerViewAdapter;
import com.liqg.library.refreshrecyclerview.IRefreshBaseList;
import com.liqg.library.refreshrecyclerview.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.liqg.basicslibrary.R.id.fragment_list_ll_ErrorBg;


/**
 * Created by liqg
 * 2016/11/7 13:07
 * Note :
 */
public class BaseRefreshRecvclerViewFragment extends BaseFragment {
    protected IRefreshBaseList mIRefreshBaseList;
    protected RefreshRecyclerView mRecyclerView;
    protected BaseRecyclerViewAdapter mRecyclerViewAdapter;

    protected List<Object> dataList;
    private LinearLayout ll_errorBg;
    private TextView tv_errorMsg;
    private RelativeLayout rl_errorImg;
    SubmitCommon getJson;
    Gson mGson;
    protected int LIST_DATA_SIZE = 15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_refresh_recvcler_view, container, false);

        mRecyclerView = (RefreshRecyclerView) view.findViewById(R.id.MyRecyclerView);
        ll_errorBg = (LinearLayout) view.findViewById(fragment_list_ll_ErrorBg);
        rl_errorImg = (RelativeLayout) view.findViewById(R.id.fragment_list_rl_ErrorImg);
        tv_errorMsg = (TextView) view.findViewById(R.id.fragment_list_tv_ErrorMsg);
        mGson = new Gson();
        dataList = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_errorBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_errorBg.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.forceRefresh();
            }
        });
        mIRefreshBaseList.instanceAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerView.setPullRefreshEnable(mIRefreshBaseList.enableRefresh());
        mRecyclerViewAdapter.setPullLoadMoreEnable(mIRefreshBaseList.enableLoadMore());
        mRecyclerView.setPullLoadEnable(mIRefreshBaseList.enableLoadMore());


        mRecyclerView.setPullRefreshEnable(true);
        mRecyclerViewAdapter.setPullLoadMoreEnable(true);
        mRecyclerView.setPullLoadEnable(true);

        //mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIRefreshBaseList.onBeforeRefresh();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.stopRefresh();
                        mIRefreshBaseList.onRefreshSuccess();
                    }
                }, 1000);
            }
        });
        mRecyclerView.setLoadMoreListener(new RefreshRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mIRefreshBaseList.onBeforeLoadMore();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.stopLoadMore();
                        mIRefreshBaseList.onLoadMoreSuccess();
                    }
                }, 1000);
            }
        });
        mIRefreshBaseList.initLocalData();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (mIRefreshBaseList.enableInitNetwork()) {
                    mIRefreshBaseList.onBeforeInit();
                    mRecyclerView.forceRefresh();
                }
            }
        });
    }

    protected void notifyData() {
        mRecyclerViewAdapter.notifyDataSetChanged();
        if (dataList.size() == 0) {
            mRecyclerView.setPullRefreshEnable(false);
            mRecyclerView.setVisibility(View.GONE);
            tv_errorMsg.setText("暂无数据");
            rl_errorImg.setBackgroundResource(R.mipmap.list_empty);
            ll_errorBg.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setPullRefreshEnable(mIRefreshBaseList.enableRefresh());
            ll_errorBg.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);


            if (mIRefreshBaseList.enableLoadMore() && dataList.size() < LIST_DATA_SIZE) {
                mRecyclerView.setEnd();

            }
        }
    }


}
