package com.common.bean;

import java.io.Serializable;

//Java 직렬화 다른 시스템에서도 사용할 수 있게끔 출력 값을 byte로 설정하는 것
//을 직렬화라고 함 JVM에 상주되어 있는 객체 데이터를 변환하는 기술과 직렬화된 바이트 형태의 데이터를 객체로
//변환해서 JVM으로 상주시키는 형태를 같이 이야기

//앱에서 주고 받을 데이터를 직렬화한다고 생각하면 됨
//본 클래스는 이벤트 데이터다.
public class Schedule implements Serializable {

    private int id;
    private int color;
    private String title;
    private String desc; //설명
    private String location; //위치
    private int state; //상태
    private long time;
    private int year;
    private int month;
    private int day;
    private int eventSetId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location == null ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getEventSetId() {
        return eventSetId;
    }

    public void setEventSetId(int eventSetId) {
        this.eventSetId = eventSetId;
    }
}
