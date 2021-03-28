package com.common.spatulatake4.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

public class NoSlideDrawerLayout extends DrawerLayout {
    private View vMenu;
    private boolean mCanMove;

    public NoSlideDrawerLayout(Context context){super(context);}

    public NoSlideDrawerLayout(Context context, AttributeSet attrs){super(context,attrs);}

    public NoSlideDrawerLayout(Context context,AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        vMenu=findViewWithTag("menu");
    }

    // 현재 어플 상태에 따라 터치 이벤트 정리
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            int width=vMenu.getWidth();
            mCanMove=ev.getX() >=width || ev.getX()< 15;
        }
        try {
            return mCanMove &&super.onInterceptTouchEvent(ev);
        }catch (Exception e){
            return false;
        }
    }
}
