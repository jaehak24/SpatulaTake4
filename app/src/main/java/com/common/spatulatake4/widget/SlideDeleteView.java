package com.common.spatulatake4.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

import com.schedule.AutoMoveAnimation;

public class SlideDeleteView extends FrameLayout {

    private boolean mIsMove=false;
    private OnContentClickListener mOnContentClickListener;
    private boolean mIsOpen=false;
    private int mWidth;
    private View vDeleteView;
    private int mDeleteViewWidth;

    public SlideDeleteView(Context context){this(context,null);}

    public SlideDeleteView(Context context, AttributeSet attrs){this(context,attrs,0);}

    public SlideDeleteView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downX, moveX;

    @Override
    protected void onFinishInflate(){super.onFinishInflate();}

    private long startTime, endTime;


    @Override
    public boolean onTouchEvent(MotionEvent e){
        switch (e.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                downX=e.getX();
                moveX=downX;
                startTime=System.currentTimeMillis();
                mIsMove=false;
                return true;
                //스케줄 그룹을 슬라이드 해서 삭제 표시르 보여줌
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(e.getX()-moveX)>=5){
                    moveChild(e.getX()-moveX);
                    moveX=e.getX();
                    mIsMove=true;
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                endTime=System.currentTimeMillis();
                moveX=e.getX();
                determineSpeed();
                isClickContentView(e);
                return true;
        }
        return super.onTouchEvent(e);
    }

    //너무 많이 스와이프 하지 않을 경우 해당 아이템을 스와이프 안 한 걸로 인식
    private void isClickContentView(MotionEvent e) {
        if (!mIsMove && Math.abs(e.getX() - downX) < 5 && mOnContentClickListener != null) {
            if (mIsOpen) {
                close(true);
            } else {
                mOnContentClickListener.onContentClick();
            }
        }
        downX = 0;
        moveX = 0;
    }

    //반복문으로 검사해서 해당 스케줄 그룹의 차일드가 나온다면 해당 스케줄을 제거
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if ("delete".equals(child.getTag())) {
                vDeleteView = child;
                mDeleteViewWidth = child.getWidth();
                child.layout(right, 0, right + mDeleteViewWidth, child.getHeight());
            }
        }
    }

    private void close(boolean b) {
        if (isOpen()) {
            mIsOpen = false;
            if (b) {
                vDeleteView.startAnimation(new AutoMoveAnimation((long) (mWidth - vDeleteView.getX())));
            } else {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.setX(child.getX() + mDeleteViewWidth);
                }
            }
        }
    }

    //swipe 속도 애니메이션
    private void determineSpeed() {
        if ((moveX - downX) / (endTime - startTime) < -0.5) {
            mIsOpen = true;
            vDeleteView.startAnimation(new AutoMoveAnimation((long) (mWidth - mDeleteViewWidth / 2 - vDeleteView.getX())));
        } else if ((moveX - downX) / (endTime - startTime) > 0.5) {
            mIsOpen = false;
            vDeleteView.startAnimation(new AutoMoveAnimation((long) (mWidth - vDeleteView.getX())));
        } else {
            determineTheState();
        }
    }


    //터치햇을 때 열렷으면 닫히고 닫혓으면 열리는 옵션

    private void determineTheState() {
        if (vDeleteView.getX() < mWidth - mDeleteViewWidth / 2) { // open
            mIsOpen = true;
            if (vDeleteView.getX() != mWidth - mDeleteViewWidth) {
                vDeleteView.startAnimation(new AutoMoveAnimation((long) (mWidth - mDeleteViewWidth / 2 - vDeleteView.getX())));
            }
        } else { // close
            mIsOpen = false;
            if (vDeleteView.getX() != mWidth) {
                vDeleteView.startAnimation(new AutoMoveAnimation((long) (mWidth - vDeleteView.getX())));
            }
        }
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    //슬라이드 했을 때 얼마나 움직일지를 설정 움직인 x좌표 값을 받는다.
    private void moveChild(float distanceX) {
        if(vDeleteView!=null){
            if(vDeleteView.getX()>=mWidth-mDeleteViewWidth&&vDeleteView.getX()<=mWidth){
                distanceX *= 1.5;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    float positionX = child.getX() + distanceX;
                    if (child != vDeleteView) {
                        positionX = Math.max(positionX, -mDeleteViewWidth);
                        positionX = Math.min(positionX, 0);
                    } else {
                        //슬라이드 거리 맥시멈 설정
                        positionX = Math.max(positionX, mWidth - mDeleteViewWidth);
                        positionX = Math.min(positionX, mWidth);
                    }
                    child.setX(positionX);
                }
            }
        }
    }

    public interface OnContentClickListener {
        void onContentClick();
    }

    private class AutoMoveAnimation extends Animation {

        private AutoMoveAnimation(long duration) {
            setDuration(Math.max(duration * 10, mDeleteViewWidth / 5));
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (isOpen()) {
                moveChild(-mDeleteViewWidth / 10);
            } else {
                moveChild(mDeleteViewWidth / 10);
            }
        }
    }
}
