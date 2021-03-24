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

    //밑에 올라오는 리스트의 열을 올려주는 아이템을 올려주는 메소드
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
    //뷰의 아이템과 오브젝트의 일치성 판별
    @Override
    public boolean isViewFromObject(View view, Object object){return view==object;}


    //올려놓은 스케줄 destroy
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }
    public SparseArray<WeekView> getViews() {
        return mViews;
    }
    public int getWeekCount(){return mWeekCount;}

    public WeekView instanceWeekView(int position){
        WeekView weekView=new WeekView(mContext,mArray,mStartDate.plusWeeks(position-mWeekCount/2));
        weekView.setId(position);
        weekView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        weekView.setOnWeekClickListener(mWeekCalendarView);
        weekView.invalidate();
        mViews.put(position,weekView);
        return weekView;
    }
}
