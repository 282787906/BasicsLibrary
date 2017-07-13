package com.liqg.library.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.liqg.library.R;

/**
 * Created by Liqg on 2017/7/13.
 */

public class BottomDialog extends  BaseBottomDialog {
    public BottomDialog(Context context) {
        super(context);
    }

    @Override
    protected View initView() {
          view = LayoutInflater.from(mContext).inflate(R.layout.view_customer_dialog_msg, null);

        return  view;
    }
}
