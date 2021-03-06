package com.common.spatulatake4.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.common.spatulatake4.R;

public class ConfirmDialog extends Dialog implements View.OnClickListener {
    private final String mTitle;
    private final OnClickListener mOnClickListener;
    private final boolean mAutoDismiss;
    private TextView tvTitle;

    public ConfirmDialog(Context context, int id, OnClickListener onClickListener) {
        this(context, context.getString(id), onClickListener);
    }

    public ConfirmDialog(Context context, String title, OnClickListener onClickListener) {
        this(context, title, onClickListener, true);
    }

    public ConfirmDialog(Context context, String title, OnClickListener onClickListener, boolean autoDismiss) {
        super(context, R.style.DialogFullScreen);
        mTitle = title;
        mOnClickListener = onClickListener;
        mAutoDismiss = autoDismiss;
        initView();
    }

    private void initView(){
        setContentView(R.layout.dialog_confirm);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(mTitle);
        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.tvConfirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvCancel:
                if(mOnClickListener!=null){
                    mOnClickListener.onCancel();
                }
                dismiss();
                break;
            case R.id.tvConfirm:
                if (mOnClickListener !=null){
                    mOnClickListener.onConfirm();
                }
                dismiss();
                break;
        }
    }

    public interface OnClickListener{
        void onCancel();

        void onConfirm();
    }
    @Override
    public void dismiss() {
        if (mAutoDismiss) {
            super.dismiss();
        }
    }
}
