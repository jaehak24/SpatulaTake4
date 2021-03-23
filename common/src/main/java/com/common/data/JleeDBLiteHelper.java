package com.common.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//TABLESET 작업과 DROP 작업을 정의
public class JleeDBLiteHelper extends SQLiteOpenHelper {
    public JleeDBLiteHelper(Context context){
        super(context, JleeDBConfig.DATABASE_NAME, null, JleeDBConfig.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(JleeDBConfig.CREATE_EVENT_SET_TABLE_SQL);
        db.execSQL(JleeDBConfig.CREATE_SCHEDULE_TABLE_SQL);
    }

    //Db 업그레이드 메소드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion!=newVersion){
            db.execSQL(JleeDBConfig.DROP_SCHEDULE_TABLE_SQL);
            db.execSQL(JleeDBConfig.DROP_SCHEDULE_TABLE_SQL);
            onCreate(db);
        }
    }
}
