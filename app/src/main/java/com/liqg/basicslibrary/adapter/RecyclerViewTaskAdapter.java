package com.liqg.basicslibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liqg.basicslibrary.R;
import com.liqg.library.refreshrecyclerview.BaseHeaderView;
import com.liqg.library.refreshrecyclerview.BaseRecyclerViewAdapter;


/**
 * Created by liqg
 * 2016/10/31 13:04
 * Note :
 */
public class    RecyclerViewTaskAdapter extends BaseRecyclerViewAdapter {


    public RecyclerViewTaskAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.task_item;
    }

    @Override
    public RecyclerView.ViewHolder setItemViewHolder(View itemView) {

            return new TaskViewHolder(itemView );
        }

    @Override
    protected View instanceFooterView(Context context) {
        return null;
    }

    @Override
    protected BaseHeaderView instanceHeaderView(Context context) {
        return null;
    }

    @Override
    public void initItemView(RecyclerView.ViewHolder itemHolder, final int postion, final Object bean) {

        TaskViewHolder viewHolder = (TaskViewHolder) itemHolder;

        viewHolder.mTaskitemFinishTvOrderId.setText(dataList.get(postion - 1).toString());


//        viewHolder.mTaskitemLlBg.setOnClickListener(new View.OnClickListener() {//列表项点击  根据状态打开详情
//            @Override
//            public void onClick(View v) {
//                taskBean = (TaskBean) dataList.get(postion - 1);
//                if (taskBean.getTaskStatus().equals(Constant.TaskStatus.Take_Finished)) {//取衣
//                    Intent intent = new Intent(context, TaskDetailTakeActivity.class);
//                    intent.putExtra("taskBean", taskBean);
//                    context.startActivity(intent);
//                } else if (
//                        taskBean.getTaskStatus().equals(Constant.TaskStatus.Send_Finished)) {//送衣
//                    Intent intent = new Intent(context, TaskDetailSendActivity.class);
//                    intent.putExtra("taskBean", taskBean);
//                    context.startActivity(intent);
//                }
//
//            }
//        });


    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        private int position;
        //基本信息
//        LinearLayout mTaskitemLlBg;

        TextView mTaskitemFinishTvOrderId;

//        TextView mOrderinfoFinishTvDate;
//        RelativeLayout mOrderinfoFinishRlLine;

        public TaskViewHolder(View view) {
            super(view);
//            mTaskitemLlBg = (LinearLayout) view.findViewById(R.id.taskitem_ll_bg);
            mTaskitemFinishTvOrderId = (TextView) view.findViewById(R.id.tastId);
//            mOrderinfoFinishRlLine = (RelativeLayout) view.findViewById(R.id.orderinfo_finish_rl_line);
//            mOrderinfoFinishTvDate = (TextView) view.findViewById(R.id.orderinfo_finish_tv_date);

        }


    }
}