package com.common.base.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseFragment extends Fragment {

    private Activity mActivity;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mActivity = getActivity();
        mView = initContentView(inflater, container);
        if (mView == null)
            throw new NullPointerException("Fragment content view is null.");
        bindView();
        return mView;

    }
    @Override
    public void onResume() {
        super.onResume();
        bindData();
    }

    protected abstract void bindView();

    //정적 데이터 바인딩
    protected void bindData(){

    }
    //동적 데이터 용청
    protected void initData(){

    }

    protected abstract View initContentView(LayoutInflater inflater, @Nullable ViewGroup container);

    protected <VT extends View> VT searchViewById(int id) {
        if (mView == null)
            throw new NullPointerException("Fragment content view is null.");
        VT view = (VT) mView.findViewById(id);
        if (view == null)
            throw new NullPointerException("This resource id is invalid.");
        return view;
    }
}
