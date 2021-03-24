package com.month;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.common.calendar.R;

public class MonthAdapter extends PagerAdapter {
    private SparseArray<MonthView> mViews;
    private Context mContext;
    private TypedArray mArray;
    private MonthCalendarView mMonthCalendarView;
    private int mMonthCount;

    public MonthAdapter(Context context, TypedArray array, MonthCalendarView monthCalendarView){
        mContext=context;
        mArray=array;
        mMonthCalendarView=monthCalendarView;
        mViews=new SparseArray<>();
        mMonthCount=array.getInteger(R.styleable.MonthCalendarView_month_count,48);
    }
    @Override
    public int getCount(){return mMonthCount;}

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        if(mViews.get(position)==null){

        }
    }
}
