package com.common.spatulatake4.task.eventset;

import android.content.Context;

import com.common.base.task.BaseAsyncTask;
import com.common.bean.EventSet;
import com.common.data.EventSetDao;
import com.common.listener.OnTaskFinishedListener;

import java.util.Map;

public class LoadEventSetMapTask extends BaseAsyncTask<Map<Integer, EventSet>> {

    private Context mContext;

    public LoadEventSetMapTask(Context context, OnTaskFinishedListener<Map<Integer, EventSet>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        mContext = context;
    }

    @Override
    protected Map<Integer, EventSet> doInBackground(Void... params) {
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.getAllEventSetMap();
    }

}
