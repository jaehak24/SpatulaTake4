package com.common.spatulatake4.task.eventset;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.common.spatulatake4.R;

public class SelectColorDialog extends Dialog implements View.OnClickListener {
    private OnSelectColorListener mOnSelectColorListener;
    private View[] mColorBorderView;
    private View[] mColorView;
    private int mColor;


    public SelectColorDialog(Context context, OnSelectColorListener onselectColorListener){
        super(context, R.style.DialogFullScreen);
        mOnSelectColorListener=onselectColorListener;
        setContentView(R.layout.dialog_select_color);
        initView();
    }

    private void initView() {
        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.tvConfirm).setOnClickListener(this);
        mColorBorderView=new View[6];
        mColorView=new View[6];
        LinearLayout llTopColor=(LinearLayout) findViewById(R.id.llTopColor);
        LinearLayout llBottomColor=(LinearLayout) findViewById(R.id.llBottomColor);
        for (int i = 0; i < llTopColor.getChildCount(); i++) {
            RelativeLayout child = (RelativeLayout) llTopColor.getChildAt(i);
            //layout 
            mColorBorderView[i] = child.getChildAt(0);
            mColorView[i] = child.getChildAt(1);
            final int finalI = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeColor(finalI);
                }
            });
        }
        for (int i = 0; i < llBottomColor.getChildCount(); i++) {
            RelativeLayout child = (RelativeLayout) llBottomColor.getChildAt(i);
            mColorBorderView[i + 3] = child.getChildAt(0);
            mColorView[i + 3] = child.getChildAt(1);
            final int finalI = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeColor(finalI + 3);
                }
            });
        }
    }

    //선택한 컬러로 색상 값을 변경
    private void changeColor(int position) {
        mColor=position;
        for(int i=0;i<mColorBorderView.length;i++){
            mColorBorderView[i].setVisibility(position==i?View.VISIBLE:View.GONE);
        }
    }

    //확인 버튼과 cancel 버튼을 눌럿을 때의 온클릭 이벤트 설정
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvCancel:
                dismiss();
                break;
            case R.id.tvConfirm:
                if(mOnSelectColorListener !=null){
                    mOnSelectColorListener.onSelectColor(mColor);
                }
                dismiss();
                break;

        }
    }


    //선택한 색상을 이벤트 리스너로 설정
    public interface OnSelectColorListener {
        void onSelectColor(int color);
    }

}
