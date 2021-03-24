package com.week;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.View;

import com.CalendarUtils;
import com.common.calendar.R;
import com.common.data.ScheduleDao;

import org.joda.time.DateTime;

import java.util.Date;

public class WeekView extends View{
    private static final int NUM_COLUMNS = 7;
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
    private int mCurrYear, mCurrMonth, mCurrDay;
    private int mSelYear, mSelMonth, mSelDay;
    private int mColumnSize, mRowSize, mSelectCircleSize;
    private int mDaySize;
    private int mLunarTextSize;
    private int mCircleRadius = 6;
    private int[] mHolidays;
    private String mHolidayOrLunarText[];
    private boolean mIsShowLunar;
    private boolean mIsShowHint;
    private boolean mIsShowHolidayHint;
    private DateTime mStartDate;
    private DisplayMetrics mDisplayMetrics;
    private OnWeekClickListener mOnWeekClickListener;
    private GestureDetector mGestureDetector;


    public WeekView(Context context, DateTime dateTime) {
        this(context, null, dateTime);
    }

    public WeekView(Context context, TypedArray array, DateTime dateTime) {
        this(context, array, null, dateTime);
    }

    public WeekView(Context context, TypedArray array, AttributeSet attrs, DateTime dateTime) {
        this(context, array, attrs, 0, dateTime);
    }

    public WeekView(Context context, TypedArray array, AttributeSet attrs, int defStyleAttr, DateTime dateTime) {
        super(context, attrs, defStyleAttr);
        initAttrs(array, dateTime);
        initPaint();
        initWeek();
        initGestureDetector();
    }

    private void initTaskHint(DateTime date) {
        if (mIsShowHint) {
            // 데이터에서 도트 힌트 데이터 가져오기
            ScheduleDao dao = ScheduleDao.getInstance(getContext());
            if (CalendarUtils.getInstance(getContext()).getTaskHints(date.getYear(), date.getMonthOfYear() - 1).size() == 0)
                CalendarUtils.getInstance(getContext()).addTaskHints(date.getYear(), date.getMonthOfYear() - 1, dao.getTaskHintByMonth(mSelYear, mSelMonth));
        }
    }

    private void initAttrs(TypedArray array, DateTime dateTime){
        if(array !=null){
            mSelectDayColor = array.getColor(R.styleable.WeekCalendarView_week_selected_text_color, Color.parseColor("#FFFFFF"));
            mSelectBGColor = array.getColor(R.styleable.WeekCalendarView_week_selected_circle_color, Color.parseColor("#E8E8E8"));
            mSelectBGTodayColor = array.getColor(R.styleable.WeekCalendarView_week_selected_circle_today_color, Color.parseColor("#FF8594"));
            mNormalDayColor = array.getColor(R.styleable.WeekCalendarView_week_normal_text_color, Color.parseColor("#575471"));
            mCurrentDayColor = array.getColor(R.styleable.WeekCalendarView_week_today_text_color, Color.parseColor("#FF8594"));
            mHintCircleColor = array.getColor(R.styleable.WeekCalendarView_week_hint_circle_color, Color.parseColor("#FE8595"));
            mLunarTextColor = array.getColor(R.styleable.WeekCalendarView_week_lunar_text_color, Color.parseColor("#ACA9BC"));
            mHolidayTextColor = array.getColor(R.styleable.WeekCalendarView_week_holiday_color, Color.parseColor("#A68BFF"));
            mDaySize = array.getInteger(R.styleable.WeekCalendarView_week_day_text_size, 13);
            mLunarTextSize = array.getInteger(R.styleable.WeekCalendarView_week_day_lunar_text_size, 8);
            mIsShowHint = array.getBoolean(R.styleable.WeekCalendarView_week_show_task_hint, true);
            mIsShowLunar = array.getBoolean(R.styleable.WeekCalendarView_week_show_lunar, true);
            mIsShowHolidayHint = array.getBoolean(R.styleable.WeekCalendarView_week_show_holiday_hint, true);
        }
        else {
            mSelectDayColor = Color.parseColor("#FFFFFF");
            mSelectBGColor = Color.parseColor("#E8E8E8");
            mSelectBGTodayColor = Color.parseColor("#FF8594");
            mNormalDayColor = Color.parseColor("#575471");
            mCurrentDayColor = Color.parseColor("#FF8594");
            mHintCircleColor = Color.parseColor("#FE8595");
            mLunarTextColor = Color.parseColor("#ACA9BC");
            mHolidayTextColor = Color.parseColor("#A68BFF");
            mDaySize = 13;
            mDaySize = 8;
            mIsShowHint = true;
            mIsShowLunar = true;
            mIsShowHolidayHint = true;
        }
        mStartDate = dateTime;
        /*mRestBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_rest_day);
        mWorkBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_work_day);
        int holidays[] = CalendarUtils.getInstance(getContext()).getHolidays(mStartDate.getYear(), mStartDate.getMonthOfYear());
         */
        int row=CalendarUtils.getWeekRow(mStartDate.getYear(),mStartDate)
    }



}
