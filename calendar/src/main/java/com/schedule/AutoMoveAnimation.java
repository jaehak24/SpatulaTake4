package com.schedule;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

public class AutoMoveAnimation extends Animation {
    private View mView;
    private int mDistance;
    private float mPositionY;

    public AutoMoveAnimation(View view, int distance) {
        mView =view;
        mDistance=distance;
        setDuration(200);
        setInterpolator(new DecelerateInterpolator(1.5f));
        mPositionY=mView.getY();
    }

    @Override
    protected void applyTransformation(float interpolateTime, Transformation t){
        super.applyTransformation(interpolateTime, t);
        mView.setY(mPositionY+interpolateTime*mDistance);
    }
}
