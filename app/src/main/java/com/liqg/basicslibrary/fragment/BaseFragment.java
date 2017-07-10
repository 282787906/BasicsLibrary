package com.liqg.basicslibrary.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
protected String TAG;
    LayoutInflater layoutInflater;
    Context mContext;
    View view;
    public static BaseFragment fragment;
    Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getActivity();
TAG= getClass().getSimpleName();
        handler=new Handler();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onResume() {
        mContext = getActivity();
        super.onResume();
        //        MobclickAgent.onPageStart(getClassName());
    }

    @Override
    public void onPause() {
        super.onPause();
        //        MobclickAgent.onPageEnd(getClassName());
    }


}
