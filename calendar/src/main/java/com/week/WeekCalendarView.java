package com.week;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import com.OnCalendarClickListener;
import com.common.calendar.R;


public class WeekCalendarView extends ViewPager implements OnWeekClickListener{

    private OnCalendarClickListener mOnCalendarClickListener;
    private weekAdapter mWeekAdapter;

    public WeekCalendarView(Context context){this(context,null);}

    public WeekCalendarView(Context context, AttributeSet attrs){
        super(context, attrs);
        initAttrs(context, attrs);
        addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initAttrs(Context context, AttributeSet attrs){
        initWeekAdapter(context, context.obtainStyledAttributes(attrs, R.styleable.WeekCalendarView) )
    }

    private void initWeekAdapter(Context context, TypedArray array) {
        mWeekAdapter=new WeekAdapter(context, array, this);
        setAdapter(mWeekAdapter);
        setCurrentItem(mWeekAdapter.getWeekCount()/2,false);
    }
}

