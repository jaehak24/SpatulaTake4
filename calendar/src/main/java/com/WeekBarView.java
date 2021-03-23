package com;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.common.calendar.R;

//주 바 만드는 Java
public class WeekBarView extends View {
    private int mWeekTextColor;
    private int mWeekSize;
    private Paint mPaint;
    private DisplayMetrics mDisplayMetrics;
    private String[] mWeekString;

    public WeekBarView(Context context){this(context,null);}

    public WeekBarView(Context context, AttributeSet attrs){this(context, attrs, 0);}

    public WeekBarView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.WeekBarView);
        mWeekTextColor=array.getColor(R.styleable.WeekBarView_week_text_size, Color.parseColor("#4588E3"));
        mWeekSize=array.getInteger(R.styleable.WeekBarView_week_text_size,13);
        mWeekString=context.getResources().getStringArray(R.array.calendar_week);
        array.recycle();
    }

    private void initPaint(){
        mDisplayMetrics=getResources().getDisplayMetrics();
        mPaint=new Paint();
        mPaint.setColor(mWeekTextColor);
        //경계에 중간선을 삽입하여 색상차가 뚜렷한 경계 부근에 증긴색을 삽입하여 도형이나 글꼴이
        //주변 배경에과 부드럽게 잘 어울리도록 하는 기법 setAntialias
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mWeekSize*mDisplayMetrics.scaledDensity);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode==MeasureSpec.AT_MOST){
            heightSize=mDisplayMetrics.densityDpi*30;
        }
        if(widthMode==MeasureSpec.AT_MOST){
            widthSize=mDisplayMetrics.densityDpi*300;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas){
        int width=getWidth();
        int height=getHeight();
        int columnWidth=width/7;
        for(int i=0; i<mWeekString.length; i++){
            String text=mWeekString[i];
            int fontWidth=(int) mPaint.measureText(text);
            int startX=columnWidth*i+(columnWidth-fontWidth)/2;
            //ascent 글자의 상단에서 baseline까지의 거리
            int startY=(int)(height/2-(mPaint.ascent()+mPaint.descent())/2);
            canvas.drawText(text, startX, startY, mPaint);
        }
    }
}
