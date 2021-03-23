package com.common.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.common.bean.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private JleeDBLiteHelper mHelper;

    private ScheduleDao(Context context){mHelper=new JleeDBLiteHelper(context);}

    public static ScheduleDao getInstance(Context context){return new ScheduleDao(context);}

    public int addSchedule(Schedule schedule){
        SQLiteDatabase db=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(JleeDBConfig.SCHEDULE_TITLE,schedule.getTitle());
        values.put(JleeDBConfig.SCHEDULE_COLOR,schedule.getColor());
        values.put(JleeDBConfig.SCHEDULE_DESC,schedule.getDesc());
        values.put(JleeDBConfig.SCHEDULE_STATE, schedule.getState());
        values.put(JleeDBConfig.SCHEDULE_LOCATION, schedule.getLocation());
        values.put(JleeDBConfig.SCHEDULE_TIME, schedule.getTime());
        values.put(JleeDBConfig.SCHEDULE_YEAR, schedule.getYear());
        values.put(JleeDBConfig.SCHEDULE_MONTH, schedule.getMonth());
        values.put(JleeDBConfig.SCHEDULE_DAY, schedule.getDay());
        values.put(JleeDBConfig.SCHEDULE_EVENT_SET_ID, schedule.getEventSetId());
        long row = db.insert(JleeDBConfig.SCHEDULE_TABLE_NAME, null, values);
        db.close();
        return row >0?getLastScheduleId():0;
    }

    //마지막 스케줄 아이템을 아이디로 설정
    private int getLastScheduleId(){
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor=db.query(JleeDBConfig.SCHEDULE_TABLE_NAME,null,null,null,null,null,null,null);
        int id=0;
        if(cursor.moveToLast()){
            id=cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_ID));
        }
        cursor.close();
        db.close();
        mHelper.close();
        return id;

    }

    //날짜에 따라 데이터를 받음
    public List<Schedule> getScheduleByDate(int year, int month, int day) {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JleeDBConfig.SCHEDULE_TABLE_NAME, null,
                String.format("%s=? and %s=? and %s=?", JleeDBConfig.SCHEDULE_YEAR,
                        JleeDBConfig.SCHEDULE_MONTH, JleeDBConfig.SCHEDULE_DAY), new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day)}, null, null, null);
        Schedule schedule;
        while (cursor.moveToNext()) {
            schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_ID)));
            schedule.setColor(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_COLOR)));
            schedule.setTitle(cursor.getString(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_TITLE)));
            schedule.setLocation(cursor.getString(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_LOCATION)));
            schedule.setDesc(cursor.getString(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_DESC)));
            schedule.setState(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_STATE)));
            schedule.setYear(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_YEAR)));
            schedule.setMonth(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_MONTH)));
            schedule.setDay(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_DAY)));
            schedule.setTime(cursor.getLong(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_TIME)));
            schedule.setEventSetId(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_EVENT_SET_ID)));
            schedules.add(schedule);
        }
        cursor.close();
        db.close();
        mHelper.close();
        return schedules;
    }
    //스케줄 입력 시 월 단위로 힌트 문구
    public List<Integer> getTaskHintByMonth(int year, int month) {
        List<Integer> taskHint = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JleeDBConfig.SCHEDULE_TABLE_NAME, new String[]{JleeDBConfig.SCHEDULE_DAY},
                String.format("%s=? and %s=?", JleeDBConfig.SCHEDULE_YEAR,
                        JleeDBConfig.SCHEDULE_MONTH), new String[]{String.valueOf(year), String.valueOf(month)}, null, null, null);
        while (cursor.moveToNext()) {
            taskHint.add(cursor.getInt(0));
        }
        cursor.close();
        db.close();
        mHelper.close();
        return taskHint;
    }
    //스케줄 입력 시 주 단위로 힌트 문구
    public List<Integer> getTaskHintByWeek(int firstYear, int firstMonth, int firstDay, int endYear, int endMonth, int endDay) {
        List<Integer> taskHint = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor1 = db.query(JleeDBConfig.SCHEDULE_TABLE_NAME, new String[]{JleeDBConfig.SCHEDULE_DAY},
                String.format("%s=? and %s=? and %s>=?", JleeDBConfig.SCHEDULE_YEAR, JleeDBConfig.SCHEDULE_MONTH, JleeDBConfig.SCHEDULE_DAY),
                new String[]{String.valueOf(firstYear), String.valueOf(firstMonth), String.valueOf(firstDay)}, null, null, null);
        while (cursor1.moveToNext()) {
            taskHint.add(cursor1.getInt(0));
        }
        cursor1.close();
        Cursor cursor2 = db.query(JleeDBConfig.SCHEDULE_TABLE_NAME, new String[]{JleeDBConfig.SCHEDULE_DAY},
                String.format("%s=? and %s=? and %s<=?", JleeDBConfig.SCHEDULE_YEAR, JleeDBConfig.SCHEDULE_MONTH, JleeDBConfig.SCHEDULE_DAY),
                new String[]{String.valueOf(endYear), String.valueOf(endMonth), String.valueOf(endDay)}, null, null, null);
        while (cursor2.moveToNext()) {
            taskHint.add(cursor2.getInt(0));
        }
        cursor2.close();
        db.close();
        mHelper.close();
        return taskHint;
    }
    // 스케줄 제거
    public boolean removeSchedule(long id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int row = db.delete(JleeDBConfig.SCHEDULE_TABLE_NAME, String.format("%s=?", JleeDBConfig.SCHEDULE_ID), new String[]{String.valueOf(id)});
        db.close();
        mHelper.close();
        return row != 0;
    }

    //이벤트 아이디로 스케줄 제거
    public void removeScheduleByEventSetId(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(JleeDBConfig.SCHEDULE_TABLE_NAME, String.format("%s=?", JleeDBConfig.SCHEDULE_EVENT_SET_ID), new String[]{String.valueOf(id)});
        db.close();
        mHelper.close();
    }

    //스케줄 업데이트
    public boolean updateSchedule(Schedule schedule) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JleeDBConfig.SCHEDULE_TITLE, schedule.getTitle());
        values.put(JleeDBConfig.SCHEDULE_COLOR, schedule.getColor());
        values.put(JleeDBConfig.SCHEDULE_DESC, schedule.getDesc());
        values.put(JleeDBConfig.SCHEDULE_STATE, schedule.getState());
        values.put(JleeDBConfig.SCHEDULE_LOCATION, schedule.getLocation());
        values.put(JleeDBConfig.SCHEDULE_YEAR, schedule.getYear());
        values.put(JleeDBConfig.SCHEDULE_MONTH, schedule.getMonth());
        values.put(JleeDBConfig.SCHEDULE_TIME, schedule.getTime());
        values.put(JleeDBConfig.SCHEDULE_DAY, schedule.getDay());
        values.put(JleeDBConfig.SCHEDULE_EVENT_SET_ID, schedule.getEventSetId());
        int row = db.update(JleeDBConfig.SCHEDULE_TABLE_NAME, values, String.format("%s=?", JleeDBConfig.SCHEDULE_ID), new String[]{String.valueOf(schedule.getId())});
        db.close();
        mHelper.close();
        return row > 0;
    }

    //이벤트 아이디로 스케줄을 가져옴
    public List<Schedule> getScheduleByEventSetId(int id) {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JleeDBConfig.SCHEDULE_TABLE_NAME, null,
                String.format("%s=?", JleeDBConfig.SCHEDULE_EVENT_SET_ID), new String[]{String.valueOf(id)}, null, null, null);
        Schedule schedule;
        while (cursor.moveToNext()) {
            schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_ID)));
            schedule.setColor(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_COLOR)));
            schedule.setTitle(cursor.getString(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_TITLE)));
            schedule.setDesc(cursor.getString(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_DESC)));
            schedule.setLocation(cursor.getString(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_LOCATION)));
            schedule.setState(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_STATE)));
            schedule.setYear(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_YEAR)));
            schedule.setMonth(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_MONTH)));
            schedule.setDay(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_DAY)));
            schedule.setTime(cursor.getLong(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_TIME)));
            schedule.setEventSetId(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.SCHEDULE_EVENT_SET_ID)));
            schedules.add(schedule);
        }
        cursor.close();
        db.close();
        mHelper.close();
        return schedules;
    }


}
