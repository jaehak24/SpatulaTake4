package com.common.base.task;

import android.content.Context;
import android.os.AsyncTask;

import com.common.listener.OnTaskFinishedListener;

public abstract class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {
    //비동기 작업을 위한
    //멀티쓰레드 프로세스를 위해서는 비동기식 작업이 필요

    protected Context mContext;
    protected OnTaskFinishedListener<T> mOnTaskFinishedListener;

    public BaseAsyncTask(Context context, OnTaskFinishedListener<T> onTaskFinishedListener){
        mContext =context;
        mOnTaskFinishedListener=onTaskFinishedListener;

    }
    @Override
    protected abstract T doInBackground(Void... Params);

    @Override
    protected void onPostExecute(T data){
        super.onPostExecute(data);
        if(mOnTaskFinishedListener !=null){
            mOnTaskFinishedListener.onTaskFinished(data);
        }
    }

}
