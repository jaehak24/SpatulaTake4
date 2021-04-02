package com.common.spatulatake4.task.schedule;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.common.bean.EventSet;
import com.common.listener.OnTaskFinishedListener;
import com.common.spatulatake4.R;
import com.common.spatulatake4.adapter.EventSetAdapter;

import java.util.List;

public class SelectEventSetDialog extends Dialog implements View.OnClickListener, OnTaskFinishedListener<List<EventSet>> {

    public static int ADD_EVENT_SET_CODE=1;
    private int mId;
    private OnSelectEventSetListener mOnSelectEventSetListener;
    private Context mContext;

    private ListView lvEventSets;

    public SelectEventSetDialog(Context context, OnSelectEventSetListener onSelectEventSetListener, int id){
        super(context, R.style.DialogFullScreen);
        mContext=context;
        mOnSelectEventSetListener=onSelectEventSetListener;
        mId = id;
        initView();

    }

    private void initView() {
        setContentView(R.layout.dialog_select_event_set);
        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.tvConfirm).setOnClickListener(this);
        findViewById(R.id.tvAddEventSet).setOnClickListener(this);
        lvEventSets = (ListView) findViewById(R.id.lvEventSets);
        initData();
    }

    private void initData() {
        new LoadEvenSetTast
    }

    @Override
    public void OnClick(View v){

    }

    public interface OnSelectEventSetListener{
        void onSelectEventSet(EventSet eventSet);
    }
}
