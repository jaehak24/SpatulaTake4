package com.week;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import com.OnCalendarClickListener;

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
        initWeekAdapter(Context context, )
    }
}
