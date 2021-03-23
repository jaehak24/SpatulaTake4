package com.common.bean;

import java.io.Serializable;

//Java 직렬화 다른 시스템에서도 사용할 수 있게끔 출력 값을 byte로 설정하는 것
//을 직렬화라고 함 JVM에 상주되어 있는 객체 데이터를 변환하는 기술과 직렬화된 바이트 형태의 데이터를 객체로
//변환해서 JVM으로 상주시키는 형태를 같이 이야기

//앱에서 주고 받을 데이터를 직렬화한다고 생각하면 됨
//본 클래스는 이벤트 데이터다.
public class EventSet implements Serializable {
    private int id;
    private String name;
    private int color;
    private int icon;
    private boolean isChecked;

    public int getId(){return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
