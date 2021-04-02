package com.common.spatulatake4.task.schedule;

import android.content.Context;

import com.common.base.task.BaseAsyncTask;
import com.common.bean.Schedule;
import com.common.data.ScheduleDao;
import com.common.listener.OnTaskFinishedListener;

public class UpdateScheduleTask extends BaseAsyncTask<Boolean> {
    private Schedule mSchedule;

    public UpdateScheduleTask(Context context, OnTaskFinishedListener<Boolean> onTaskFinishedListener, Schedule schedule) {
        super(context, onTaskFinishedListener);
        mSchedule = schedule;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (mSchedule != null) {
            ScheduleDao dao = ScheduleDao.getInstance(mContext);
            return dao.updateSchedule(mSchedule);
        } else {
            return false;
        }
    }
}
