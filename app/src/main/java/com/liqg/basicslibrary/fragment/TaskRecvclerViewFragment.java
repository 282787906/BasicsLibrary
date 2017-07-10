package com.liqg.basicslibrary.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.liqg.basicslibrary.R;
import com.liqg.basicslibrary.adapter.RecyclerViewTaskAdapter;
import com.liqg.library.refreshrecyclerview.BaseRecyclerViewAdapter;
import com.liqg.library.refreshrecyclerview.IRefreshBaseList;


public class TaskRecvclerViewFragment extends BaseRefreshRecvclerViewFragment implements View.OnClickListener, IRefreshBaseList {

    private Button fragment_refresh_recvcler_view_but_Refresh;
    private Button fragment_refresh_recvcler_view_but_Next;
    private Button fragment_refresh_recvcler_view_but_End;
    private Button fragment_refresh_recvcler_view_but_Empty;
    private Button fragment_refresh_recvcler_view_but_Error;


    private static final String TASK_BEAN = "taskListParam";



    public static BaseFragment newInstance( ) {
        TaskRecvclerViewFragment fragment = new TaskRecvclerViewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIRefreshBaseList = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);

        initView(view);
        return view;
    }

    private void initView(View view) {
        fragment_refresh_recvcler_view_but_Refresh = (Button) view.findViewById(R.id.fragment_refresh_recvcler_view_but_Refresh);
        fragment_refresh_recvcler_view_but_Next = (Button) view.findViewById(R.id.fragment_refresh_recvcler_view_but_Next);
        fragment_refresh_recvcler_view_but_End = (Button) view.findViewById(R.id.fragment_refresh_recvcler_view_but_End);
        fragment_refresh_recvcler_view_but_Empty = (Button) view.findViewById(R.id.fragment_refresh_recvcler_view_but_Empty);
        fragment_refresh_recvcler_view_but_Error = (Button) view.findViewById(R.id.fragment_refresh_recvcler_view_but_Error);
        fragment_refresh_recvcler_view_but_Empty.setOnClickListener(this);
        fragment_refresh_recvcler_view_but_Error.setOnClickListener(this);
        fragment_refresh_recvcler_view_but_Refresh.setOnClickListener(this);
        fragment_refresh_recvcler_view_but_Next.setOnClickListener(this);
        fragment_refresh_recvcler_view_but_End.setOnClickListener(this);
//        if (mTaskListParam.getStatus().equals(Constant.TaskStatus.Send_Finished)||mTaskListParam.getStatus().equals(Constant.TaskStatus.Take_Finished)){
//        mRecyclerView.setPadding(0, (int) ResUtil.getDimension(mContext,R.dimen.common_margin_small),0,0);}

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_refresh_recvcler_view_but_Refresh:
                mRecyclerView.smoothScrollToPosition(0);
                mRecyclerView.forceRefresh();
                break;
            case R.id.fragment_refresh_recvcler_view_but_Next:

                mRecyclerViewAdapter.setPullLoadMoreEnable(false);
                mRecyclerViewAdapter.notifyItemChanged(dataList.size() + 1);
                break;
            case R.id.fragment_refresh_recvcler_view_but_End:

                mRecyclerView.setEnd();
                // mRecyclerViewAdapter.notifyItemChanged(dataList.size()+1);
                break;


            case R.id.fragment_refresh_recvcler_view_but_Empty:
                dataList.clear();
                notifyData();
                break; case R.id.fragment_refresh_recvcler_view_but_Error:

                break;
        }
    }

    @Override
    public BaseRecyclerViewAdapter instanceAdapter() {
        mRecyclerViewAdapter =new RecyclerViewTaskAdapter(mContext);
        mRecyclerViewAdapter.setDataList( dataList);
        return  mRecyclerViewAdapter;
    }

    /**
     * 本地数据初始化
     */
    @Override
    public void initLocalData() {

        dataList.clear();


        for (int i = 0; i < 10; i++) {
            dataList.add("title"+i);
        }
        mRecyclerViewAdapter.setDataList(dataList);
      notifyData();
    }

    /**
     * 设置是否允许刷新
     */
    @Override
    public boolean enableRefresh() {
        return true;
    }

    /**
     * 设置是否允许加载更多
     */
    @Override
    public boolean enableLoadMore() {
        return true;
    }

    /**
     * 初始化时调用
     * 准备网络请求的参数
     */
    @Override
    public boolean enableInitNetwork() {
        return false;
    }

    /**
     * 网络初始化前调用
     * 准备网络请求的参数
     */
    @Override
    public void onBeforeInit() {
//        getJson = new GetTask(mContext, RequestType.GET, Constant.Url.URL_GETTESK);
//        getJson.requestParams.put("statusid", mTaskListParam.getStatus());
//        getJson.requestParams.put("tid", 0);
//        getJson.requestParams.put("pagesize", LIST_DATA_SIZE);
//        getJson.setInit(true);
    }

    /**
     * 初始化成功时调用
     */
    @Override
    public void onInitSuccess() {
        loadData();
    }

    /**
     * 刷新前调用
     * 准备网络请求的参数
     */
    @Override
    public void onBeforeRefresh() {
//        getJson.requestParams.put("tid", 0);
//        getJson.setInit(true);
    }

    /**
     * 刷新成功时调用
     */
    @Override
    public void onRefreshSuccess() {
        dataList.clear();
        for (int i = 0; i < 16; i++) {
            dataList.add("title"+i);
        }
        loadData();
    }

    /**
     * 加载更多前调用
     * 准备网络请求的参数
     */
    @Override
    public void onBeforeLoadMore() {
//        getJson.requestParams.put("tid", ((TaskBean) (dataList.get(dataList.size() - 1))).getTaskId());
//        getJson.setInit(false);

    }

    /**
     * 加载更多成功时调用
     */
    @Override
    public void onLoadMoreSuccess() {
        for (int i = 0; i < 10; i++) {
            dataList.add("title"+i);
        }

        loadData();
    }

    private void loadData() {
//        List<TaskBean> taskBeanList = (List<TaskBean>) getJson.getAnalysisResult();
//        for (TaskBean taskBean : taskBeanList) {
//            dataList.add(taskBean);
//        }
        notifyData();
    }

    public void removeTask(int position) {
        dataList.remove(position);
        mRecyclerViewAdapter.notifyItemRemoved(position);
    }
}
