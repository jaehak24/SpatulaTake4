package com.common.spatulatake4.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.common.bean.EventSet;
import com.common.listener.OnTaskFinishedListener;
import com.common.spatulatake4.R;
import com.common.spatulatake4.activity.AddEventSetActivity;
import com.common.spatulatake4.adapter.SelectEventSetAdapter;
import com.common.spatulatake4.task.eventset.LoadEventSetTask;

import java.net.CookieHandler;
import java.util.List;

public class SelectEventSetDialog extends Dialog implements View.OnClickListener, OnTaskFinishedListener<List<EventSet>> {

    public static int ADD_EVENT_SET_CODE=1;
    private int mId;
    private OnSelectEventSetListener mOnSelectEventSetListener;
    private Context mContext;

    private ListView lvEventSets;
    private SelectEventSetAdapter mSelectEventSetAdapter;
    private List<EventSet> mEventSets;


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

        new LoadEventSetTask(getContext(),this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        lvEventSets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectEventSetAdapter.setSelectPosition(position);
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvCancel:
                dismiss();
                break;
            case R.id.tvConfirm:
                if(mOnSelectEventSetListener !=null){
                    mOnSelectEventSetListener.onSelectEventSet(mEventSets.get(mSelectEventSetAdapter.getSelectPosition()));
                }
                dismiss();
                break;
            case R.id.tvAddEventSet:
                ((Activity) mContext).startActivityForResult(new Intent(mContext, AddEventSetActivity.class),ADD_EVENT_SET_CODE);
        }

    }
    public void addEventSet(EventSet eventSet) {
        mEventSets.add(eventSet);
        mSelectEventSetAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskFinished(List<EventSet> data) {
        mEventSets = data;
        EventSet eventSet = new EventSet();
        eventSet.setName(getContext().getString(R.string.menu_no_category));
        mEventSets.add(0, eventSet);
        int position = 0;
        for (int i = 0; i < mEventSets.size(); i++) {
            if (mEventSets.get(i).getId() == mId) {
                position = i;
                break;
            }
        }
        mSelectEventSetAdapter = new SelectEventSetAdapter(mContext, mEventSets, position);
        lvEventSets.setAdapter(mSelectEventSetAdapter);
    }

    public interface OnSelectEventSetListener{
        void onSelectEventSet(EventSet eventSet);
    }
}
