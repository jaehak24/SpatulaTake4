package com.common.spatulatake4.task.eventset;

import android.content.Context;

import com.common.base.task.BaseAsyncTask;
import com.common.bean.EventSet;
import com.common.bean.Schedule;
import com.common.data.EventSetDao;
import com.common.data.ScheduleDao;
import com.common.listener.OnTaskFinishedListener;

public class RemoveEventSetTask extends BaseAsyncTask<Boolean> {
    private int mID;

    public RemoveEventSetTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, int id){
        super(context, onTaskFinishedListener);
        mID=id;
    }

    @Override
    protected Boolean doInBackground(Void... params){
        ScheduleDao scheduleDao=ScheduleDao.getInstance(mContext);
        scheduleDao.removeScheduleByEventSetId(mID);
        EventSetDao dao=EventSetDao.getInstance(mContext);
        return dao.removeEventSet(mID);
    }
}
