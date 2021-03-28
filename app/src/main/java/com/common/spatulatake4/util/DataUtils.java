package com.common.spatulatake4.util;

import java.text.SimpleDateFormat;
import java.util.Date;


// 시간 데이터에 대한 포맷 설정
public class DataUtils {
    public static String timeStamp2Date(long time, String format){
        if(time==0){
            return "";
        }
        if(format==null||format.isEmpty()){
            format="yyyy-MM--dd HH::mm::ss";
        }
        SimpleDateFormat sdt=new SimpleDateFormat(format);
        return sdt.format(new Date(time));
}

// 타임스탬프==나라 별 날짜 시간을 보내줌
public static long date2TimeStamp(String date, String format){
    try{
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.parse(date).getTime();
    }catch (Exception e){
        //에러가 생길 시 에러 문구 출력
        e.printStackTrace();
    }
    return 0;}
}
