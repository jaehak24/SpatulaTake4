package com.month;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.View;

import com.CalendarUtils;
import com.common.
import com.common.data.ScheduleDao;

public class MonthView extends View {
    private static final int NUM_COLUMNS=7;
    private static final int NUM_ROWS=6;
    private Paint mPaint;
    private Paint mLunarPaint;
    private int mNormalDayColor;
    private int mSelectDayColor;
    private int mSelectBGColor;
    private int mSelectBGTodayColor;
    private int mCurrentDayColor;
    private int mHintCircleColor;
    private int mLunarTextColor;
    private int mHolidayTextColor;
    private int mLastOrNextMonthTextColor;
    private int mCurrYear, mCurrMonth, mCurrDay;
    private int mSelYear, mSelMonth, mSelDay;
    private int mColumnSize, mRowSize, mSelectCircleSize;
    private int mDaySize;
    private int mLunarTextSize;
    private int mWeekRow; // 현재 몇 주차입니다.
    private int mCircleRadius = 6;
    private int[][] mDaysText;
    private int[] mHolidays;
    private String[][] mHolidayOrLunarText;
    private boolean mIsShowLunar;
    private boolean mIsShowHint;
    private boolean mIsShowHolidayHint;
    private DisplayMetrics mDisplayMetrics;
    private OnMonthClickListener mDateClickListener;
    private GestureDetector mGestureDetector;
    private Bitmap mRestBitmap, mWorkBitmap;

    public MonthView(Context context, int year, int month) {
        this(context, null, year, month);
    }
    public MonthView(Context context, TypedArray array, int year, int month) {
        this(context, array, null, year, month);
    }
    public MonthView(Context context, TypedArray array, AttributeSet attrs, int year, int month) {
        this(context, array, attrs, 0, year, month);
    }
    public MonthView(Context context, TypedArray array, AttributeSet attrs, int defStyleAttr, int year, int month) {
        super(context, attrs, defStyleAttr);
        initAttrs(array, year, month);
        initPaint();
        initMonth();
        initGestureDetector();
        initTaskHint();
    }

    private void initTaskHint() {
        if (mIsShowHint) {
            // 데이터에서 도트 힌드 데이터 가져오기
            ScheduleDao dao = ScheduleDao.getInstance(getContext());
            CalendarUtils.getInstance(getContext()).addTaskHints(mSelYear, mSelMonth, dao.getTaskHintByMonth(mSelYear, mSelMonth));
        }
    }

}
