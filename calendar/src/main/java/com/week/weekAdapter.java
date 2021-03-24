package com.week;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.SparseArray;

import androidx.viewpager.widget.PagerAdapter;

import org.joda.time.DateTime;

public class weekAdapter extends PagerAdapter {
    private SparseArray<WeekView> mViews;
    private Context mContent;
    private TypedArray mArray;
    private DateTime mStartDate;
    private int mWeekCount=220;


    public weekAdapter(Context context, TypedArray array,WeekCalendarView weekCalendarView)
}
