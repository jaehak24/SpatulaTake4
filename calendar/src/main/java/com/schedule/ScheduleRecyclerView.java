package com.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ScheduleRecyclerView extends RecyclerView {
    public ScheduleRecyclerView(Context context){this(context, null);}

    public ScheduleRecyclerView(Context context, @Nullable AttributeSet attrs){
        this(context, attrs, 0);
    }
    public ScheduleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    //스크롤 상단에서 어느 정도 움직였는지 알려주는 offset이다.
    //얼마나 움직였는지 확인하고자 할 때 -1을 곱해주면 된다.
    public boolean isScrollTop() {return computeVerticalScrollOffset()==0;}

    public void requestChildFocus(View child,View focused){
        super.requestChildFocus(child,focused);

        //포커스가 다른 레이아웃으로 옮겨갈 때
        if(getOnFocusChangeListener() !=null){
            getOnFocusChangeListener().onFocusChange(child,false);
            getOnFocusChangeListener().onFocusChange(focused, true);
        }
    }

}
