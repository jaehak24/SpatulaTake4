package com.common.spatulatake4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.common.bean.EventSet;
import com.common.spatulatake4.R;

public class MainActivity extends AppCompatActivity {
    public static final String ADD_EVENT_SET_ACTION = "";
    //Test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoEventSetFragment(EventSet eventSet) {
    }

    public void resetMainTitleDate(int year, int month, int day) {
    }
}