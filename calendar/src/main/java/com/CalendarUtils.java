
package com;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class CalendarUtils {
    private static CalendarUtils sUtils;
    private Map<String, int[]> sAllHolidays = new HashMap<>();
    private Map<String, List<Integer>> sMonthTaskHint = new HashMap<>();



    //synchronized 멀티쓰레드 동기화 억제
    public static synchronized CalendarUtils getInstance(Context context) {
        if (sUtils == null) {
            sUtils = new CalendarUtils();
            sUtils.initAllHolidays(context);
        }
        return sUtils;
    }

    private void initAllHolidays(Context context) {
        try {
            InputStream is = context.getAssets().open("assets/holiday.json");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

            //자바객체를 JSOn 파일로 바꿔주는 모델 Gson
            sAllHolidays = new Gson().fromJson(baos.toString(), new TypeToken<Map<String, int[]>>() {
            }.getType());

            //IOException 어떠한 입출력 예외의 발생을 통지하는 시그널을 발생
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addTaskHints(int year, int month, int day){
        String key=hashKey(year, month);
        List<Integer> hints=sUtils.sMonthTaskHint.get(key);
        if(hints==null){
            hints=new ArrayList<>();
            hints.add(day);
            sUtils.sMonthTaskHint.put(key,hints);
            return true;
        } else {
            if(!hints.contains(day)) {
                hints.add(day);
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean removeTaskHint(int year, int month, int day){
        String key=hashKey(year, month);
        List<Integer> hints=sUtils.sMonthTaskHint.get(key);
        if(hints==null){
            hints=new ArrayList<>();
            sUtils.sMonthTaskHint.put(key,hints);
        } else {
            if(hints.contains(day)) {
                Iterator<Integer> i=hints.iterator();
                while(i.hashNext()){
                    Integer next=i.next();
                    if(next==day){
                        i.remove();
                        break;
                    }
                }
            }else{
                return false;
            }
        }
        return false;
    }

    public List<Integer> getTaskHints(int year, int month) {
        String key = hashKey(year, month);
        List<Integer> hints = sUtils.sMonthTaskHint.get(key);
        if (hints == null) {
            hints = new ArrayList<>();
            sUtils.sMonthTaskHint.put(key, hints);
        }
        return hints;
    }

    private static String hashKey(int year, int month){
        return String.format("%s,%s",year,month);

    }


    /**
     *
     *
     * @param year
     * @param month
     * @return
     */


    //31인지 26일인지 30일인지 문
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     *현재 위치한 월일 1일로 돌아가서 요일을 배정
     *
     * @param year
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getWeeksAgo(int lastYear, int lastMonth, int lastDay, int year, int month, int day){
        Calendar start= Calendar.getInstance();
        Calendar end=Calendar.getInstance();
        start.set(lastYear, lastMonth, lastDay);
        end.set(year,month,day);

    }
}
