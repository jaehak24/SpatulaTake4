package com.common.spatulatake4.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import com.OnCalendarClickListener;
import com.common.spatulatake4.R;
import com.common.spatulatake4.util.DataUtils;
import com.month.MonthCalendarView;
import com.month.MonthView;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectDateDialog extends Dialog implements View.OnClickListener, OnCalendarClickListener {


    private OnSelectDateListener mOnSelectDateListener;
    private String[] mMonthText;
    private TextView tvDate;
    private MonthCalendarView mcvCalendar;
    private EditText etTime;
    private int mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay;


    public SelectDateDialog(Context context, OnSelectDateListener onSelectEventSetListener, int year, int month, int day, int position){
        super(context, R.style.DialogFullScreen );
        mOnSelectDateListener=onSelectEventSetListener;
        initView();
        initDate(year,month, day, position);
    }


    //밑의 스크롤 뷰에 입력한 날짜 정보를 기입
    private void initDate(int year, int month, int day, int position) {
        setCurrentSelectDate(year,month,day);
        if(position!=-1){
            mcvCalendar.setCurrentItem(position,false);
            mcvCalendar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout(){
                    MonthView monthView=mcvCalendar.getCurrentMonthView();
                    monthView.setSelectYearMonth(mCurrentSelectYear,mCurrentSelectMonth,mCurrentSelectDay);
                    monthView.invalidate();
                }
            });
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvCancel:
                dismiss();
                break;
            case R.id.tvConfirm:
                confirm();
                dismiss();
                break;

        }
    }

    //확인을 입력 했을 때 생기는 event
    private void confirm() {
        //입력한 시간 값이
        if(mOnSelectDateListener!= null){
            long time;
            String text=etTime.getText().toString();
            if(TextUtils.isEmpty(text)){
                time=0; //아무것도 입력 안햇을 때
            } else{
                Pattern timePattern1=Pattern.compile("[0-9][0-9]:[0-9][0-9]]");
                Pattern timePattern2 = Pattern.compile("[0-9][0-9]:[0-9]");
                Matcher timeFormat1 = timePattern1.matcher(text);
                Matcher timeFormat2 = timePattern2.matcher(text);

                //timeFormat1 timeFormat2의 입력한 값이 같을 경우
                if(timeFormat1.matches()||timeFormat2.matches()){
                    time= DataUtils.date2TimeStamp(String.format("%s-%s-%s %s",mCurrentSelectYear,mCurrentSelectMonth,mCurrentSelectDay,text),
                            "yyyy-MM-dd HH:mm");

                }else{
                    //timeFormat1 입력한 값이 같지 않을 경우
                    Pattern hourPattern1=Pattern.compile("[0-9][0-9]");
                    Pattern hourPattern2 = Pattern.compile("[0-9]");
                    Matcher hourFormat1 = hourPattern1.matcher(text);
                    Matcher hourFormat2 = hourPattern2.matcher(text);
                    if (hourFormat1.matches() || hourFormat2.matches()) {
                        time = DataUtils.date2TimeStamp(String.format("%s-%s-%s %s", mCurrentSelectYear, mCurrentSelectMonth, mCurrentSelectDay, text),
                                "yyyy-MM-dd HH");
                    } else {
                        time = 0;
                    }
                }
                //선택한 날짜만 입력함
                mOnSelectDateListener.onSelectDate(mCurrentSelectYear,mCurrentSelectMonth,mCurrentSelectDay,time,mcvCalendar.getCurrentItem());

            }
        }
    }
    @Override//달력에 날짜를 입력했을 때
    public void onClickDate(int year, int month, int day){
        setCurrentSelectDate(year,month,day);
    }

    @Override
    public void onPageChange(int year, int month, int day){}

    //선택한 날짜를 입력
    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectDay=day;
        mCurrentSelectMonth=month;
        mCurrentSelectYear=year;
        Calendar calendar=Calendar.getInstance();
        if(year==calendar.get(Calendar.YEAR)){
            tvDate.setText(mMonthText[month]);
        } else{
            tvDate.setText(String.format("%s%s",String.format(getContext().getString(R.string.calendar_year),year)
                    ,mMonthText[month]));
        }
    }

    private void initView(){
        setContentView(R.layout.dialog_select_date);
        mMonthText=getContext().getResources().getStringArray(R.array.calendar_month);
        findViewById(R.id.tvCancel).setOnClickListener(this);
        findViewById(R.id.tvConfirm).setOnClickListener(this);
        tvDate=(TextView) findViewById(R.id.tvDate);
        mcvCalendar=(MonthCalendarView) findViewById(R.id.mcvCalendar);
        mcvCalendar.setOnCalendarClickListener(this);
        etTime=(EditText)findViewById(R.id.etTime);
        etTime.addTextChangedListener(mTextWatcher);

    }

    private TextWatcher mTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(count==3){
                if(after==4){//입력 다이알로그에서 날짜, 이름을 받는 리스너
                    etTime.removeTextChangedListener(mTextWatcher);
                    etTime.setText(String.format("%s%s",s.charAt(0),s.charAt(1)));
                    etTime.setSelection(etTime.getText().length());
                    etTime.addTextChangedListener(mTextWatcher);

                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()==3){//문자열의 길이가 3이면 즉, 시간만 입력했을 경우
                if(s.charAt(2)==':'){//시간에 : 를 사용자가 입력했을 경우
                    etTime.setText(String.format("%s", String.format("%s%s", s.charAt(0), s.charAt(1))));
                    etTime.setSelection(etTime.getText().length());
                } else{// 사용자가 : 를 시간에 입력하지 않은 경우 24시간제인지 12시간제인지
                    etTime.setText(String.format("%s:%s",String.format("%s%s",s.charAt(0),s.charAt(1))));
                }

            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()==2){
                Integer hour=Integer.parseInt(s.toString());
                if(hour>23){ //23시간 이상으 시간을 입력했을 때
                etTime.removeTextChangedListener(mTextWatcher);
                etTime.setText(String.valueOf(s.charAt(0)));
                etTime.setSelection(etTime.getText().length());
                etTime.addTextChangedListener(mTextWatcher);
                }
            }else if(s.length()==5){ //60분 이상의 값이 입력됬을 경우
                Integer min=Integer.parseInt(String.format("%s%s",s.charAt(3),s.charAt(4)));
                if(min>59){
                    etTime.removeTextChangedListener(mTextWatcher);
                    etTime.setText(s.toString().substring(0,s.length()-1));
                    etTime.setSelection(etTime.getText().length());
                    etTime.addTextChangedListener(mTextWatcher);
                }
            } else if(s.length()>5){ //
                etTime.removeTextChangedListener(mTextWatcher);
                etTime.setText(s.toString().substring(0,5));
                etTime.setSelection(etTime.getText().length());
                etTime.addTextChangedListener(mTextWatcher);
            }

        }

    };
    public interface OnSelectDateListener {
        void onSelectDate(int year, int month, int day, long time, int position);
    }
}
