package com.common.spatulatake4.fragment;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.OnCalendarClickListener;
import com.common.base.app.BaseFragment;
import com.common.bean.Schedule;
import com.common.listener.OnTaskFinishedListener;
import com.common.spatulatake4.Dialog.SelectDateDialog;
import com.common.spatulatake4.R;
import com.common.spatulatake4.activity.MainActivity;
import com.common.spatulatake4.adapter.ScheduleAdapter;
import com.common.spatulatake4.task.schedule.LoadScheduleTask;
import com.schedule.ScheduleLayout;

import java.util.Calendar;
import java.util.List;

public class ScheduleFragment extends BaseFragment implements OnCalendarClickListener, View.OnClickListener,
        OnTaskFinishedListener<List<Schedule>>, SelectDateDialog.OnSelectDateListener {

    private ScheduleLayout slSchedule;
    private EditText etInputContent;
    private RelativeLayout rlNoTask;
    private int mCurrentSelectYear, mCurrentSelectMonth,mCurrentSelectDay;
    private ScheduleAdapter mScheduleAdapter;



    public static ScheduleFragment getInstance() {return new ScheduleFragment();}

    @Nullable
    @Override
    protected void bindView(){
        slSchedule=searchViewById(R.id.slSchedule);
        etInputContent=searchViewById(R.id.etInputContent);
        rlNoTask=searchViewById(R.id.rlNoTask);
        slSchedule.setOnCalendarClickListener(this);
        searchViewById(R.id.ibMainClock).setOnClickListener(this);
        searchViewById(R.id.ibMainClock).setOnClickListener(this);
        initScheduleList();
        initBottomInputBar();
    }

    @Override
    protected void initData(){
        super.initData();
        initData();
    }
    @Override
    protected void bindData(){
        super.bindData();
        resetScheduleList();
    }

    private void resetScheduleList() {
        new LoadScheduleTask(mActivity, this, mCurrentSelectYear, mCurrentSelectMonth, mCurrentSeectDay);
    }
    private void initDate(){
        Calendar calendar=Calendar.getInstance();
        setCurrentSelectDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setCurrentSelectDate(int year, int month, int day) {
        mCurrentSelectDay=day;
        mCurrentSelectMonth=month;
        mCurrentSelectYear=year;
        if(mActivity instanceof MainActivity){
            ((MainActivity) mActivity).resetMainTitleDate(year,month,day);
        }
    }
    @Override
    public void onTaskFinished(List<Schedule> data){
        mScheduleAdapter.changeAllData(data);
        rlNoTask.setVisibility(data.size()==0 ?View.VISIBLE: View.GONE);
        updateTaskHintUi(data.size());
    }

    private void updateTaskHintUi(int size) {
        if (size == 0) {
            slSchedule.removeTaskHint(mCurrentSelectDay);
        } else {
            slSchedule.addTaskHint(mCurrentSelectDay);
        }
    }

    private void initBottomInputBar() {
    }

    private void initScheduleList() {
    }

}
