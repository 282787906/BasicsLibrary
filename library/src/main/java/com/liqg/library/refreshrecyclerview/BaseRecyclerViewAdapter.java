package com.liqg.library.refreshrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liqg
 * 2017/1/18 09:00
 * Note :
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    protected static final int TYPE_HEADER = 436874;
    protected static final int TYPE_ITEM = 256478;
    protected static final int TYPE_FOOTER = 9621147;

    private int layouyId;    protected Context mContext;

    private RecyclerView.ViewHolder vhItem;
    protected boolean loadMore;

    protected List<T> dataList;

    public BaseRecyclerViewAdapter(Context context) {
        this. mContext=context;
        this.layouyId =getItemLayout();
    }

    protected abstract int getItemLayout();

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }



    /**
     * 初始化itemView
     * @param itemView
     * @return
     */
    public abstract RecyclerView.ViewHolder setItemViewHolder(View itemView);

    private T getObject(int position){
        if(dataList!=null && dataList.size()>=position)return dataList.get(position-1);//如果有header
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layouyId,null);

            this.vhItem = setItemViewHolder(itemView);

            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            View headerView = instanceHeaderView(parent.getContext());
            if (headerView==null) {
                headerView=new DefaultHeaderView(parent.getContext());
            }
            return new VHHeader(headerView);
        } else if(viewType==TYPE_FOOTER){
            View footerView = instanceFooterView(parent.getContext());
            if (footerView==null) {
                footerView=new DefaultFooterView(parent.getContext());
            }
            return new VHFooter(footerView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    /**
     * 自定义footerView
     * @param context
     * @return
     */
    protected abstract View instanceFooterView(Context context);

    /**
     * 自定义 HeaderView
     * if return null use DefaultHeaderView
     *  extends BaseHeaderView
     * @param context
     * @return
     */
    protected abstract BaseHeaderView instanceHeaderView(Context context);

    public void setPullLoadMoreEnable(boolean enable){
        this.loadMore = enable;
    }
    public boolean getPullLoadMoreEnable(){return loadMore;}

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {//相当于getView
        if (vhItem!=null && holder.getClass() == vhItem.getClass()) {
            //cast holder to VHItem and set data
            initItemView(holder,position,getObject(position));
        }else if (holder instanceof BaseRecyclerViewAdapter.VHHeader) {
            //cast holder to VHHeader and set data for header.

        }else if(holder instanceof BaseRecyclerViewAdapter.VHFooter){
            if(!loadMore)((VHFooter)holder).footerView.hide();//第一次初始化显示的时候要不要显示footerView
        }
    }

    @Override
    public int getItemCount() {
        return (dataList==null ||dataList.size()==0)?1:dataList.size() + 2;//如果有header,若list不存在或大小为0就没有footView，反之则有
    }//这里要考虑到头尾部，多以要加2

    /**
     * 根据位置判断这里该用哪个ViewHolder
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0)return TYPE_HEADER;
        else if(isPositonFooter(position))return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    protected boolean isPositonFooter(int position){//这里的position从0算起
        if (dataList == null && position == 1) return true;//如果没有item
        return position == dataList.size() + 1;//如果有item(也许为0)
    }

    //        class VHItem extends RecyclerView.ViewHolder {
    //            public VHItem(View itemView) {
    //                super(itemView);
    //            }
    //            public View getItemView(){return itemView;}
    //        }
    //
    protected class VHHeader extends RecyclerView.ViewHolder {
        public VHHeader(View headerView) {
            super(headerView);
        }
    }

    protected class VHFooter extends RecyclerView.ViewHolder {
        public BaseFooterView footerView;

        public VHFooter(View itemView) {
            super(itemView);
            footerView = (BaseFooterView)itemView;
        }
    }
    /**
     * 赋值itemView
     * @return
     */
    public abstract void initItemView(RecyclerView.ViewHolder itemHolder, int posion, T entity);

}