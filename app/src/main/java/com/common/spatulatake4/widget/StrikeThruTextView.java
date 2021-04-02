package com.common.spatulatake4.widget;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class StrikeThruTextView extends AppCompatTextView {
    public StrikeThruTextView(Context context){this(context, null);}

    public StrikeThruTextView(Context context, AttributeSet attrs){this(context,attrs,0);}

    public StrikeThruTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs,defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        TextPaint paint=getPaint();
        paint.setAntiAlias(true);
        //strike_thru_text 취소선 글자 중앙을 가로지르는
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
    }
}
