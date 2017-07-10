package com.liqg.library.refreshrecyclerview;

/**
 * Created by liqg
 * 2016/11/7 11:05
 * Note :
 */
public interface IRefreshBaseList {

    /**
     * 实例化Adapter
     * 需继承MyBaseAdapter
     */
    BaseRecyclerViewAdapter instanceAdapter();

    /**
     * 本地数据初始化
     */
    void initLocalData();

    /**
     * 设置是否允许刷新
     */
    boolean enableRefresh();

    /**
     * 设置是否允许加载更多
     */
    boolean enableLoadMore();

    /**
     * 初始化时调用
     * 准备网络请求的参数
     */
    boolean enableInitNetwork();

    /**
     * 网络初始化前调用
     * 准备网络请求的参数
     */
    void onBeforeInit();

    /**
     * 初始化成功时调用
     */
    void onInitSuccess();


    /**
     * 刷新前调用
     * 准备网络请求的参数
     */
    void onBeforeRefresh();

    /**
     * 刷新成功时调用
     */
    void onRefreshSuccess();

    /**
     * 加载更多前调用
     * 准备网络请求的参数
     */
    void onBeforeLoadMore();

    /**
     * 加载更多成功时调用
     */
    void onLoadMoreSuccess();


}
