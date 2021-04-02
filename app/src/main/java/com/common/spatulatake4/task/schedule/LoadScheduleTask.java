package com.common.spatulatake4.task.schedule;

import android.content.Context;

import com.common.base.task.BaseAsyncTask;
import com.common.bean.Schedule;
import com.common.data.ScheduleDao;
import com.common.listener.OnTaskFinishedListener;

import java.util.List;

public class LoadScheduleTask extends BaseAsyncTask<List<Schedule>> {

    private int mDay;
    private int mMonth;
    private int mYear;

    public LoadScheduleTask(Context context, OnTaskFinishedListener<List<Schedule>> onTaskFinishedListener, int year, int month, int day){
        super(context, onTaskFinishedListener);
        mYear=year;
        mMonth=month;
        mDay=day;
    }

    @Override
    protected List<Schedule> doInBackground(Void... params){
        ScheduleDao dao=ScheduleDao.getInstance(mContext);
        return dao.getScheduleByDate(mYear,mMonth,mDay);
    }
}
