package com.common.spatulatake4.task.eventset;




import android.content.Context;
import android.view.View;

import com.common.base.app.BaseActivity;
import com.common.base.task.BaseAsyncTask;
import com.common.bean.EventSet;
import com.common.data.EventSetDao;
import com.common.listener.OnTaskFinishedListener;

public class AddEventSetTask extends BaseAsyncTask<EventSet> {

    private EventSet mEventSet;

    public AddEventSetTask(Context context, OnTaskFinishedListener<EventSet> onTaskFinishedListener, EventSet eventSet) {
        super(context, onTaskFinishedListener);
        mEventSet = eventSet;
    }

    @Override
    protected EventSet doInBackground(Void... params) {
        if (mEventSet != null) {
            EventSetDao dao = EventSetDao.getInstance(mContext);
            int id = dao.addEventSet(mEventSet);
            if (id != 0) {
                mEventSet.setId(id);
                return mEventSet;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
