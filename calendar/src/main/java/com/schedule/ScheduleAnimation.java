package com.schedule;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

import com.common.bean.Schedule;


public class ScheduleAnimation extends Animation {
    private ScheduleLayout mScheduleLayout;
    private ScheduleState mState;
    private float mDistanceY;

    public ScheduleAnimation(ScheduleLayout scheduleLayout, ScheduleState scheduleState, float distanceY){
        mScheduleLayout=scheduleLayout;
        mState=scheduleState;
        mDistanceY=distanceY;
        setDuration(300);
        setInterpolator(new DecelerateInterpolator());

    }

    //옆에서 나오는 schedulelayout 구성
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t ){
        super.applyTransformation(interpolatedTime, t);
        if(mState==ScheduleState.OPEN){
            mScheduleLayout.onCalendarScroll(mDistanceY);
        } else{
            mScheduleLayout.onCalendarScroll(-mDistanceY);
        }
    }


}
