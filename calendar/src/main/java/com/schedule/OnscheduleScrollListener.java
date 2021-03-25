package com.schedule;

import android.view.GestureDetector;
import android.view.MotionEvent;


public class OnscheduleScrollListener extends GestureDetector.SimpleOnGestureListener {
    private ScheduleLayout mScheduleLayout;
    public OnscheduleScrollListener(ScheduleLayout scheduleLayout){
        mScheduleLayout=scheduleLayout;
    }
    @Override
    public boolean onDown(MotionEvent e) {return true;}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
        mScheduleLayout.onCalendarScroll(distanceY);
        return true;
    }
}
