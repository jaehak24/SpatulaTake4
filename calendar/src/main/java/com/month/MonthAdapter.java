package com.month;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.common.calendar.R;

import org.joda.time.DateTime;

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


    //선언
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        if (mViews.get(position) == null) {
            int date[] = getYearAndMonth(position);

            //MonthView 불러오기
            MonthView monthView = new MonthView(mContext, mArray, date[0], date[1]);
            //MonthView의 아이디 불러오기
            monthView.setId(position);
            //MonthView의 레이아웃 파라미터
            monthView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //화면 갱신
            monthView.invalidate();
            //화면 UI에서 클릭햇을 때의 작용을 받아 들일 함수
            monthView.setOnDateClickListener(mMonthCalendarView);
            //핼렬에 해당 위치의 좌표와 MonthView 요소 값을 넣음
            mViews.put(position, monthView);
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    private int[] getYearAndMonth(int position) {
        int date[] = new int[2];
        DateTime time = new DateTime();
        time = time.plusMonths(position - mMonthCount / 2);
        //getYearAndMonth 에서 1. 연도 2. 월을 가져옴
        date[0] = time.getYear();
        date[1] = time.getMonthOfYear() - 1;
        return date;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public SparseArray<MonthView> getViews() {
        return mViews;
    }

    public int getMonthCount() {
        return mMonthCount;
    }
}
