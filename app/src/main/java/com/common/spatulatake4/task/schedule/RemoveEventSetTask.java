package com.common.spatulatake4.task.schedule;

import android.content.Context;

import com.common.base.task.BaseAsyncTask;
import com.common.bean.EventSet;
import com.common.data.EventSetDao;
import com.common.data.ScheduleDao;
import com.common.listener.OnTaskFinishedListener;

public class RemoveEventSetTask extends BaseAsyncTask<Boolean> {

    private int mId;

    public RemoveEventSetTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, int id){
        super(context, onTaskFinishedListener);
        mId=id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ScheduleDao scheduleDao = ScheduleDao.getInstance(mContext);
        scheduleDao.removeScheduleByEventSetId(mId);
        EventSetDao dao = EventSetDao.getInstance(mContext);
        return dao.removeEventSet(mId);
    }
}
