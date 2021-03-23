package com.common.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.common.bean.EventSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Data Access Object 데이터를 조화하거난 조작하는 기능을 전담하도록 만든 오브젝트
public class EventSetDao {
    private JleeDBLiteHelper mHelper;

    //EventSet에 있는 변수를 SQL에 저장
    private EventSetDao(Context context){mHelper=new JleeDBLiteHelper(context);}

    public static EventSetDao getInstance(Context context){return new EventSetDao(context);}

    //EventSet의 데이터를 넣는 과정
    public int addEventSet(EventSet eventSet){
        SQLiteDatabase db=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(JleeDBConfig.EVENT_SET_NAME, eventSet.getName());
        values.put(JleeDBConfig.EVENT_SET_COLOR, eventSet.getColor());
        values.put(JleeDBConfig.EVENT_SET_ICON, eventSet.getIcon());
        long row=db.insert(JleeDBConfig.EVENT_SET_TABLE_NAME, null,values);
        db.close();
        return row>0? getLastEventSetId():0;//row가 0보다 크면 getLastEventSetId()이고 아니면 0이 출력
    }

    //Mapping 메소드
    public Map<Integer,EventSet> getAllEventSetMap(){
        Map<Integer,EventSet> eventSets = new HashMap<>(); //해쉬맵 호출
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor=db.query(JleeDBConfig.EVENT_SET_TABLE_NAME,null,null,null,null,null, null);
        EventSet eventSet;
        while(cursor.moveToNext()){
            eventSet=new EventSet();
            eventSet.setId(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_ID)));
            eventSet.setName(cursor.getString(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_NAME)));
            eventSet.setColor(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_COLOR)));
            eventSet.setIcon(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_ICON)));
            eventSets.put(eventSet.getId(),eventSet);
        }
        cursor.close();
        db.close();;
        mHelper.close();
        return eventSets;
    }

    //모든 Event를 SQL에 set 한다.
    public List<EventSet> getAllEventSet(){
        List<EventSet> eventSets=new ArrayList<>();
        SQLiteDatabase db=mHelper.getReadableDatabase();//DB 선언
        Cursor cursor=db.query(JleeDBConfig.EVENT_SET_TABLE_NAME,null,null,null,null,null,null);
        EventSet eventSet;
        while(cursor.moveToNext()){
            eventSet=new EventSet();
            eventSet.setId(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_ID)));
            eventSet.setName(cursor.getString(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_NAME)));
            eventSet.setColor(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_COLOR)));
            eventSet.setIcon(cursor.getInt(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_COLOR)));
            eventSets.add(eventSet);
        }
        cursor.close();
        mHelper.close();
        db.close();
        return eventSets;
    }
    //SQL Eventset 제거
    public boolean removeEventSet(int id){
        SQLiteDatabase db=mHelper.getWritableDatabase();
        int row=db.delete(JleeDBConfig.EVENT_SET_TABLE_NAME, String.format("%s=?", JleeDBConfig.EVENT_SET_ID), new String[]{String.valueOf(id)});
        db.close();
        mHelper.close();
        return row !=0;
    }

    //마지막 컬럼에 도달했을 때 해당 컬럼 인덱스를 id로 설정==>마지막 이벤트 컬럼 아이디를 아이디로 설정
    private int getLastEventSetId() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(JleeDBConfig.EVENT_SET_TABLE_NAME, null, null, null, null, null, null);
        int id = 0;
        if (cursor.moveToLast()) {
            id = cursor.getInt(cursor.getColumnIndex(JleeDBConfig.EVENT_SET_ID));
        }
        cursor.close();
        db.close();
        mHelper.close();
        return id;
    }

}
