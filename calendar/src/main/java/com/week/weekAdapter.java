package com.week;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.common.calendar.R;

import org.joda.time.DateTime;

public class weekAdapter extends PagerAdapter {
    private SparseArray<WeekView> mViews;
    private Context mContext;
    private TypedArray mArray;
    private WeekCalendarView mWeekCalendarView;
    private DateTime mStartDate;
    private int mWeekCount = 220;


    public weekAdapter(Context context, TypedArray array,WeekCalendarView weekCalendarView){
        mContext=context;
        mArray=array;
        mWeekCalendarView=weekCalendarView;
        mViews=new SparseArray<>();
        initStartDate();
        mWeekCount=array.getInteger(R.styleable.WeekCalendarView_week_count,220);

    }

    private void initStartDate() {
        mStartDate=new DateTime();
        mStartDate=mStartDate.plusDays(-mStartDate.getDayOfWeek()%7);// 현재시간- 요일변수%/7
    }

    @Override
    public int getCount() {return mWeekCount;}

    //밑에 올라오는 리스트의 열을 올려주는 아이템
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        for(int i=0; i<3; i++){
            if(position - 2 + i >= 0 && position - 2 + i < mWeekCount && mViews.get(position - 2 + i) == null){
                instanceWeekView(position-2+i);
            }
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }
    //
    @Override
    public boolean isViewFromObject(View view, Object object){return view==object;}
}
