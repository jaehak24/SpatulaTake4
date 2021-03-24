package com.month;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import com.OnCalendarClickListener;

public class MonthCalendarView extends ViewPager implements OnMonthClickListener {
    private MonthAdapter mMonthAdapter;
    private OnCalendarClickListener mOnCalendarClickListener;

    public MonthCalendarView(Context context){this(context,null);}

    public MonthCalendarView(Context context, AttributeSet attrs){
        super(context, attrs);
        initAttrs(context, attrs);

        addOnPageChangeListener(mOnPageChangeListener);
    }

    private OnPageChangeListener mOnPageChangeListener=new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            MonthView monthView =mMonthAdapter.getViews().get(getCurrentItem());


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private void initAttrs(Context context, AttributeSet attrs) {
    }


}
