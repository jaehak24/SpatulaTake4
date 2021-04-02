package com.common.base.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        bindView();
        initData();
    }

    @Override
    protected void onStop(){super.onStop();}
    //onCreate 액티비티가 생성되었을 때의 활동 주기, 필수적으로 구현 필요
    //onStart 활동이 사용자에게 표시되고, 앱은 활동을 포그라운드에 보내 상호작용 할 수 있도록 준비함
    //ex> 앱이 UI를 관리하는 코드를 초기화
    //onResume 이상태에서 앱은 사용자와 상호작용을 함, 앱에서 포커스가 끝날 때까지 앱이 이 상태에 머뭄
    //방해되는 이벤트가 발생하면 ONPause 메소드가 호출됨
    //OnPause() 사용자가 활동을 떠나는 것을 나타내는 첫 번째 신호로 이 메서드를 호출
    //OnStop() 사용자의 활동이 더 이상 표시되지 않으면 중단됨 상태에 들어감
    //ONPause 대신 onstop을 사용하면 멀티 윈도우 모등서 활동을 보고 있더라고 UI 관련 작업이 계속 진행됩니다.
    //OnStop을 사용하면 CPU를 비교적 많이 소모하는 종료 작업을 실행해야 합니다.

    //onDestroy는 화동이 소멸되기 전에 호출
    //1. 사용자가 화동을 완전히 닫거나 활동에서 finish() 가 호출되어 활동이 종료되는 경우
    //2. 구성 변경으로 인해 시스템이 일시적으로 활동을 소멸시키는 경우우

   @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
    protected abstract void bindView();

    @Override
    protected void onDestroy(){super.onDestroy();}

    //동적 데이터 연동--서버에서 실시간으로 변환되어 적용되어지는 데이터
    protected void initData(){}

    //정적 데이터 연동--메모리에 데이터를 올려놓은 상태에서 필요할 때마다, 데이터를 호출하여 사용하는 데이터
    protected void bindData(){}



    protected <VT extends View> VT searchViewByID(int id){
        VT view=(VT)findViewById(id);
        if(view==null)
            throw new NullPointerException("This resource id is invalid,");
        return view; //findviewbyId를 했을 때 nullpointer error 검사 함수
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindData();
    }

}
