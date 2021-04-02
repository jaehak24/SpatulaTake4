package com.common.spatulatake4.task.schedule;

import android.content.Context;

import com.common.base.task.BaseAsyncTask;
import com.common.bean.EventSet;
import com.common.data.EventSetDao;
import com.common.data.ScheduleDao;
import com.common.listener.OnTaskFinishedListener;

public class RemoveScheduleTask extends BaseAsyncTask<Boolean> {

    private long mId;

    public RemoveScheduleTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, long id) {
        super(context, onTaskFinishedListener);
        mId = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ScheduleDao dao = ScheduleDao.getInstance(mContext);
        return dao.removeSchedule(mId);
    }
}
