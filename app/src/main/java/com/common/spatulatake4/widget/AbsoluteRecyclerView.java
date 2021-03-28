package com.common.spatulatake4.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class AbsoluteRecyclerView extends RecyclerView {

    //리사이클러뷰를 사라지게 하는 세팅
    public AbsoluteRecyclerView(Context context){
        super(context);
        setFocusable(false);
    }

    public AbsoluteRecyclerView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        setFocusable(false);
    }
    public AbsoluteRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle){
        super(context,attrs, defStyle);
        setFocusable(false);
    }
    //리사이클러 뷰에서 스케줄 목록 추가
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,expandSpec);
    }

    // 터치를 했을 때 이벤트가 발생
    @Override
    public boolean onTouchEvent(MotionEvent e){return true;}
}
