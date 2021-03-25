package com.schedule;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.CalendarUtils;
import com.OnCalendarClickListener;
import com.common.calendar.R;
import com.month.MonthCalendarView;
import com.month.MonthView;
import com.week.WeekCalendarView;
import com.week.WeekView;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.List;


public class ScheduleLayout extends FrameLayout {

    private final int DEFAULT_MONTH = 0;
    private final int DEFAULT_WEEK = 1;

    private MonthCalendarView mcvCalendar;
    private WeekCalendarView wcvCalendar;
    private RelativeLayout rlMonthCalendar;
    private ScheduleRecyclerView rvScheduleList;
    private int mDefaultView;
    private ScheduleState mState;
    private int mRowSize;
    private int mMinDistance;
    private int mAutoScrollDistance;
    private GestureDetector mGestureDetector;
    private boolean mIsAutoChandeMonthRow;
    private boolean mCurrentRowIsSix=true;
    private RelativeLayout rlScheduleList;
    private int mCurrentSelectYear;
    private int mCurrentSelectMonth;
    private int mCurrentSelectDay;
    private OnCalendarClickListener mOnCalendarClickListener;
    private float mDownPosition[]=new float[2] ;
    private boolean mIsScrolling=false;


    public ScheduleLayout(Context context){this(context, null);}

    public ScheduleLayout(Context context, AttributeSet attrs){this(context, attrs, 0);}

    public ScheduleLayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs,defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.ScheduleLayout));
        initDate();
        initGestureDetector();
    }

    private void initGestureDetector() {
        mGestureDetector=new GestureDetector(getContext(),new OnscheduleScrollListener(this));
    }

    private void initDate() {
        Calendar calendar= Calendar.getInstance();
        resetCurrentSelectDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
    }

    private void resetCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectYear=year;
        mCurrentSelectMonth=month;
        mCurrentSelectDay=day;
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        mcvCalendar=(MonthCalendarView)findViewById(R.id.mcvCalendar);
        wcvCalendar=(WeekCalendarView)findViewById(R.id.wcvCalendar);
        rlMonthCalendar=(RelativeLayout)findViewById(R.id.rlMonthCalendar);
        rvScheduleList=(ScheduleRecyclerView)findViewById(R.id.rvSchedulerList);
        bindingMonthAndWeekCalendar();
    }

    private void bindingMonthAndWeekCalendar() {
        mcvCalendar.setOnCalendarClickListener(mMonthCalendarClickListener);
        wcvCalendar.setOnCalendarClickListener(mWeekCalendarClickListener);
        //초기화

        Calendar calendar=Calendar.getInstance();
        if(mIsAutoChandeMonthRow){
            mCurrentRowIsSix=CalendarUtils.getMonthRows(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH))==6;
        }
        if(mDefaultView==DEFAULT_MONTH){
            wcvCalendar.setVisibility(INVISIBLE);
            mState=ScheduleState.OPEN;
            //6번째 횡, 즉 마지막 주일 경우
            if(!mCurrentRowIsSix){;
                rvScheduleList.setY(rlScheduleList.getY()-mRowSize);
            }
            //weekCalendarView로 커스텀 뷰
        } else if (mDefaultView==DEFAULT_MONTH){
            wcvCalendar.setVisibility(INVISIBLE);
            mState=ScheduleState.OPEN;
            int row=CalendarUtils.getWeekRow(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            rlMonthCalendar.setY(-row*mRowSize);
            rlScheduleList.setY(rlScheduleList.getY()-5*mRowSize);
        }
    }


    private void initAttrs(TypedArray array) {
        mDefaultView=array.getInt(R.styleable.ScheduleLayout_default_view,DEFAULT_MONTH);
        array.recycle();
        mState=ScheduleState.OPEN;
        mRowSize=getResources().getDimensionPixelSize(R.dimen.week_calendar_height);
        mMinDistance=getResources().getDimensionPixelSize(R.dimen.calendar_min_distance);
        mAutoScrollDistance=getResources().getDimensionPixelSize(R.dimen.auto_scroll_distance);
            }

            //calendar을 아래로 스크롤 햇을 때의 y좌표 값을 가지는 메소드
    public void onCalendarScroll(float distanceY) {
        MonthView monthView = mcvCalendar.getCurrentMonthView();
        distanceY = Math.min(distanceY, mAutoScrollDistance);
        float calendarDistanceY = distanceY / (mCurrentRowIsSix ? 5.0f : 4.0f);
        int row = monthView.getWeekRow() - 1;
        int calendarTop = -row * mRowSize;
        int scheduleTop = mRowSize;
        float calendarY = rlMonthCalendar.getY() - calendarDistanceY * row;
        calendarY = Math.min(calendarY, 0);
        calendarY = Math.max(calendarY, calendarTop);
        rlMonthCalendar.setY(calendarY);
        float scheduleY = rlScheduleList.getY() - distanceY;
        if (mCurrentRowIsSix) {
            scheduleY = Math.min(scheduleY, mcvCalendar.getHeight());
        } else {
            scheduleY = Math.min(scheduleY, mcvCalendar.getHeight() - mRowSize);
        }
        scheduleY = Math.max(scheduleY, scheduleTop);
        rlScheduleList.setY(scheduleY);
    }
    private OnCalendarClickListener mMonthCalendarClickListener = new OnCalendarClickListener() {
        @Override
        public void onClickDate(int year, int month, int day) {
            wcvCalendar.setOnCalendarClickListener(null);
            int weeks = CalendarUtils.getWeeksAgo(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay, year, month, day);
            resetCurrentSelectDate(year, month, day);
            int position = wcvCalendar.getCurrentItem() + weeks;
            if (weeks != 0) {
                wcvCalendar.setCurrentItem(position, false);
            }
            resetWeekView(position);
            wcvCalendar.setOnCalendarClickListener(mWeekCalendarClickListener);
        }

        @Override
        public void onPageChange(int year, int month, int day) {
            computeCurrentRowsIsSix(year, month);
        }
    };


    //달력을 가로로 넘길 때 해당 움직임 애니메이션
    private void computeCurrentRowsIsSix(int year, int month) {
        if(mIsAutoChandeMonthRow){
            boolean isSixRow=CalendarUtils.getMonthRows(year,month)==6;
            if(mCurrentRowIsSix!=isSixRow){
                mCurrentRowIsSix=isSixRow;
                if(mState==ScheduleState.OPEN){
                    if(mCurrentRowIsSix){//열 길이가 많아졌을 때
                        AutoMoveAnimation animation=new AutoMoveAnimation(rlScheduleList, mRowSize);
                    }
                } else{
                    //열 길이가 줄어들었을 때
                    AutoMoveAnimation animation=new AutoMoveAnimation(rlScheduleList,-mRowSize);
                }
            }
        }
    }

    private void resetWeekView(int position) {
        WeekView weekview=wcvCalendar.getCurrentWeekView();
        if(weekview !=null){
            weekview.setSelectYearMonth(mCurrentSelectYear,mCurrentSelectMonth,mCurrentSelectDay);
            weekview.invalidate();
        }else {
            WeekView mewWeekView=wcvCalendar.getWeekAdapter().instanceWeekView(position);
            mewWeekView.setSelectYearMonth(mCurrentSelectYear,mCurrentSelectMonth,mCurrentSelectDay);
            mewWeekView.invalidate();
            wcvCalendar.setCurrentItem(position);
        }
        if(mOnCalendarClickListener!=null){
            mOnCalendarClickListener.onClickDate(mCurrentSelectYear,mCurrentSelectMonth,mCurrentSelectDay);
        }
    }

    //주 view에서 클릭햇을 때의 스케줄을 보여주는 메소드
    private OnCalendarClickListener mWeekCalendarClickListener =new OnCalendarClickListener() {
        @Override
        public void onClickDate(int year, int month, int day) {
            mcvCalendar.setOnCalendarClickListener(null);
            int months=CalendarUtils.getMonthsAgo(mCurrentSelectYear,mCurrentSelectMonth,year,month);
            resetCurrentSelectDate(year,month,day);
            if(month!=0){
                int position=mcvCalendar.getCurrentItem()+months;
                mcvCalendar.setCurrentItem(position,false);
            }
            resetMonthView();
            mcvCalendar.setOnCalendarClickListener(mMonthCalendarClickListener);
            if(mIsAutoChandeMonthRow){
                mCurrentRowIsSix=CalendarUtils.getMonthRows(year,month)==6;
            }

        }
        @Override
        public void onPageChange(int year, int month, int day) {
            if(mIsAutoChandeMonthRow){
                if(mCurrentSelectMonth!=month){
                    mCurrentRowIsSix=CalendarUtils.getMonthRows(year,month)==6;
                }
            }

        }
    };

    private void resetMonthView() {
        MonthView monthView = mcvCalendar.getCurrentMonthView();
        if (monthView != null) {
            monthView.setSelectYearMonth(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
            monthView.invalidate();
        }
        if (mOnCalendarClickListener != null) {
            mOnCalendarClickListener.onClickDate(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
        }
        resetCalendarPosition();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int height=MeasureSpec.getSize(heightMeasureSpec);
        resetViewHeight(rlScheduleList,height-mRowSize);
        resetViewHeight(this,height);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }


    //뷰의 세로를 리셋해주는 함수
    private void resetViewHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams.height != height) {
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }


    @Override
    protected void onLayout(boolean changed,int left, int top, int right, int bottom){
        super.onLayout(changed,left,top,right,bottom);
    }

    //
    @Override
    public boolean dispatchTouchEvent(MotionEvent e){
        switch (e.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mDownPosition[0]=e.getRawX();
                mDownPosition[1]=e.getRawY();
                mGestureDetector.onTouchEvent(e);
                break;
        }
        return super.dispatchTouchEvent(e);
    }


    //recycleview가 떳을 때 해당 레이아웃 터치를 인터셉트해야함
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e){
        if (mIsScrolling) {
            return true;
        }
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                float x = e.getRawX();
                float y = e.getRawY();
                float distanceX = Math.abs(x - mDownPosition[0]);
                float distanceY = Math.abs(y - mDownPosition[1]);
                if (distanceY > mMinDistance && distanceY > distanceX * 2.0f) {
                    return (y > mDownPosition[1] && isRecyclerViewTouch()) || (y < mDownPosition[1] && mState == ScheduleState.OPEN);
                }
                break;
        }
        return super.onInterceptTouchEvent(e);

    }


    //탭 버튼을 다시 눌럿을 때 아래쪽 recycleView 팝업
    private boolean isRecyclerViewTouch() {
        return mState==ScheduleState.CLOSE&&(rvScheduleList.getChildCount()==0||rvScheduleList.isScrollTop());
    }

    //몇개의 주가 있는지에 따라서 초기화 했을 때의 달력 y 좌표값이 다르다.
    private void resetCalendarPosition() {
        if (mState == ScheduleState.OPEN) {
            rlMonthCalendar.setY(0);
            //6주인 달
            if (mCurrentRowIsSix) {
                rlScheduleList.setY(mcvCalendar.getHeight());
            } else {
                rlScheduleList.setY(mcvCalendar.getHeight() - mRowSize);
            }
        } else {
            rlMonthCalendar.setY(-CalendarUtils.getWeekRow(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay) * mRowSize);
            rlScheduleList.setY(mRowSize);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            //아래로 밀엇을 때
            case MotionEvent.ACTION_DOWN:
                mDownPosition[0] = event.getRawX();
                mDownPosition[1] = event.getRawY();
                resetCalendarPosition();
                return true;
            case MotionEvent.ACTION_MOVE:
                transferEvent(event);
                mIsScrolling = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                transferEvent(event);
                changeCalendarState();
                resetScrollingState();
                return true;
        }
        return super.onTouchEvent(event);
    }

    //
    private void resetScrollingState() {
        mDownPosition[0] = 0;
        mDownPosition[1] = 0;
        mIsScrolling = false;
    }

    //레이아웃 높이에 따른 상태 변환
    private void changeCalendarState() {
        if (rlScheduleList.getY() > mRowSize * 2 &&
                //스케줄 열의 2배보다 크고 monthView의 크기보다 작을 경우
                rlScheduleList.getY() < mcvCalendar.getHeight() - mRowSize) { // 중간에 위치
            //mAutoScrollDistance 내려가기 애니메이션 적용
            ScheduleAnimation animation = new ScheduleAnimation(this, mState, mAutoScrollDistance);
            animation.setDuration(300);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    changeState();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            rlScheduleList.startAnimation(animation);
        } else if (rlScheduleList.getY() <= mRowSize * 2) { // 상단 끝에 위치
            ScheduleAnimation animation = new ScheduleAnimation(this, ScheduleState.OPEN, mAutoScrollDistance);
            animation.setDuration(50);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mState == ScheduleState.OPEN) {
                        changeState();
                    } else {
                        resetCalendar();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            rlScheduleList.startAnimation(animation);
        } else {
            ScheduleAnimation animation = new ScheduleAnimation(this, ScheduleState.CLOSE, mAutoScrollDistance);
            animation.setDuration(50);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mState == ScheduleState.CLOSE) {
                        mState = ScheduleState.OPEN;
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            rlScheduleList.startAnimation(animation);
        }
    }

    private void resetCalendar() {
        if (mState == ScheduleState.OPEN) {
            mcvCalendar.setVisibility(VISIBLE);
            wcvCalendar.setVisibility(INVISIBLE);
        } else {
            mcvCalendar.setVisibility(INVISIBLE);
            wcvCalendar.setVisibility(VISIBLE);
        }
    }

    private void changeState() {
        if (mState == ScheduleState.OPEN) {
            mState = ScheduleState.CLOSE;
            mcvCalendar.setVisibility(INVISIBLE);
            wcvCalendar.setVisibility(VISIBLE);
            rlMonthCalendar.setY((1 - mcvCalendar.getCurrentMonthView().getWeekRow()) * mRowSize);
            checkWeekCalendar();
        } else {
            mState = ScheduleState.OPEN;
            mcvCalendar.setVisibility(VISIBLE);
            wcvCalendar.setVisibility(INVISIBLE);
            rlMonthCalendar.setY(0);
        }
    }

    //스케줄의 시간을 가져오는 메소드
    private void checkWeekCalendar() {
        WeekView weekView = wcvCalendar.getCurrentWeekView();
        DateTime start = weekView.getStartDate();
        DateTime end = weekView.getEndDate();
        DateTime current = new DateTime(mCurrentSelectYear, mCurrentSelectMonth + 1, mCurrentSelectDay, 23, 59, 59);
        int week = 0;
        while (current.getMillis() < start.getMillis()) {
            week--;
            start = start.plusDays(-7);
        }
        current = new DateTime(mCurrentSelectYear, mCurrentSelectMonth + 1, mCurrentSelectDay, 0, 0, 0);
        if (week == 0) {
            while (current.getMillis() > end.getMillis()) {
                week++;
                end = end.plusDays(7);
            }
        }
        if (week != 0) {
            int position = wcvCalendar.getCurrentItem() + week;
            if (wcvCalendar.getWeekViews().get(position) != null) {
                wcvCalendar.getWeekViews().get(position).setSelectYearMonth(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
                wcvCalendar.getWeekViews().get(position).invalidate();
            } else {
                WeekView newWeekView = wcvCalendar.getWeekAdapter().instanceWeekView(position);
                newWeekView.setSelectYearMonth(mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay);
                newWeekView.invalidate();
            }
            wcvCalendar.setCurrentItem(position, false);
        }
    }

    //scheduleLayout을 올려고 내렷을 때의 가시성 변환
    private void transferEvent(MotionEvent event) {
        if (mState == ScheduleState.CLOSE) {
            mcvCalendar.setVisibility(VISIBLE);
            wcvCalendar.setVisibility(INVISIBLE);
            mGestureDetector.onTouchEvent(event);
        } else {
            mGestureDetector.onTouchEvent(event);
        }
    }
    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        mOnCalendarClickListener = onCalendarClickListener;
    }

    private void resetMonthViewDate(final int year, final int month, final int day, final int position) {
        if (mcvCalendar.getMonthViews().get(position) == null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetMonthViewDate(year, month, day, position);
                }
            }, 50);
        } else {
            mcvCalendar.getMonthViews().get(position).clickThisMonth(year, month, day);
        }
    }

    /*연월일 초기화*/

    public void initData(int year, int month, int day) {
        int monthDis = CalendarUtils.getMonthsAgo(mCurrentSelectYear, mCurrentSelectMonth, year, month);
        int position = mcvCalendar.getCurrentItem() + monthDis;
        mcvCalendar.setCurrentItem(position);
        resetMonthViewDate(year, month, day, position);
    }

    //여러 개의 DB 데이터 힌트 추가
    public void addTaskHints(List<Integer> hints) {
        CalendarUtils.getInstance(getContext()).addTaskHints(mCurrentSelectYear, mCurrentSelectMonth, hints);
        if (mcvCalendar.getCurrentMonthView() != null) {
            mcvCalendar.getCurrentMonthView().invalidate();
        }
        if (wcvCalendar.getCurrentWeekView() != null) {
            wcvCalendar.getCurrentWeekView().invalidate();
        }
    }
    //여러 개의 DB 데이터 힌트 삭제
    public void removeTaskHints(List<Integer> hints) {
        CalendarUtils.getInstance(getContext()).removeTaskHints(mCurrentSelectYear, mCurrentSelectMonth, hints);
        if (mcvCalendar.getCurrentMonthView() != null) {
            mcvCalendar.getCurrentMonthView().invalidate();
        }
        if (wcvCalendar.getCurrentWeekView() != null) {
            wcvCalendar.getCurrentWeekView().invalidate();
        }
    }
    //단일 DB 데이터 힌트 추가
    public void addTaskHint(Integer day) {
        if (mcvCalendar.getCurrentMonthView() != null) {
            if (mcvCalendar.getCurrentMonthView().addTaskHint(day)) {
                if (wcvCalendar.getCurrentWeekView() != null) {
                    wcvCalendar.getCurrentWeekView().invalidate();
                }
            }
        }
    }

    //단일 DB 데이터 힌트 삭제
    public void removeTaskHint(Integer day) {
        if (mcvCalendar.getCurrentMonthView() != null) {
            if (mcvCalendar.getCurrentMonthView().removeTaskHint(day)) {
                if (wcvCalendar.getCurrentWeekView() != null) {
                    wcvCalendar.getCurrentWeekView().invalidate();
                }
            }
        }
    }

    public ScheduleRecyclerView getSchedulerRecyclerView() {
        return rvScheduleList;
    }

    public MonthCalendarView getMonthCalendar() {
        return mcvCalendar;
    }

    public WeekCalendarView getWeekCalendar() {
        return wcvCalendar;
    }

    public int getCurrentSelectYear() {
        return mCurrentSelectYear;
    }

    public int getCurrentSelectMonth() {
        return mCurrentSelectMonth;
    }

    public int getCurrentSelectDay() {
        return mCurrentSelectDay;
    }

}
