package com.common.spatulatake4.task.eventset;

import android.content.Context;

import com.common.base.task.BaseAsyncTask;
import com.common.bean.EventSet;
import com.common.data.EventSetDao;
import com.common.listener.OnTaskFinishedListener;

import java.util.List;

public class LoadEventSetTask extends BaseAsyncTask<List<EventSet>> {
    private Context mContext;
    public LoadEventSetTask(Context context, OnTaskFinishedListener<List<EventSet>> onTaskFinishedListener){
        super(context, onTaskFinishedListener);
        mContext=context;
    }

    @Override
    protected List<EventSet> doInBackground(Void... params){
        EventSetDao Dao=EventSetDao.getInstance(mContext);
        return Dao.getAllEventSet();
    }
}
