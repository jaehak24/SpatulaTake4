
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

import static java.util.Calendar.DAY_OF_WEEK;


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
                while(i.hasNext()){
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
        return calendar.get(DAY_OF_WEEK);
    }


    //몇 주 지났는지를 return
    public static int getWeeksAgo(int lastYear, int lastMonth, int lastDay, int year, int month, int day){
        Calendar start= Calendar.getInstance();
        Calendar end=Calendar.getInstance();
        start.set(lastYear, lastMonth, lastDay);
        end.set(year,month,day);
        int week=start.get(Calendar.DAY_OF_WEEK);
        start.add(Calendar.DATE, -week);
        week=end.get(Calendar.DAY_OF_WEEK);
        end.add(Calendar.DATE,7-week);
        float v=(end.getTimeInMillis()-start.getTimeInMillis())/(3600*1000*24*7*1.0f);
        return (int)(v-1);

    }

    //몇 개월 지났는지를 return
    public static int getMonthsAgo(int lastYear, int lastMonth, int year, int month){
        return (year-lastYear)*12+(month-lastMonth);
    }

    //몇 주차인지를 표기
    public static int getWeekRow(int year, int month, int day){
        int week=getFirstDayWeek(year, month);
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,month,day);
        int lastweek=calendar.get(DAY_OF_WEEK);
        if(lastweek==7)
            day--;
        return (day+week-1)/7;
    }

    /*쉬는 날 세팅 중국
    public static String getHolidayFromSolar(int year, int month, int day) {
        String message = "";
        if (month == 0 && day == 1) {
            message = "元旦";
        } else if (month == 1 && day == 14) {
            message = "情人节";
        } else if (month == 2 && day == 8) {
            message = "妇女节";
        } else if (month == 2 && day == 12) {
            message = "植树节";
        } else if (month == 3) {
            if (day == 1) {
                message = "愚人节";
            } else if (day >= 4 && day <= 6) {
                if (year <= 1999) {
                    int compare = (int) (((year - 1900) * 0.2422 + 5.59) - ((year - 1900) / 4));
                    if (compare == day) {
                        message = "清明节";
                    }
                } else {
                    int compare = (int) (((year - 2000) * 0.2422 + 4.81) - ((year - 2000) / 4));
                    if (compare == day) {
                        message = "清明节";
                    }
                }
            }
        } else if (month == 4 && day == 1) {
            message = "劳动节";
        } else if (month == 4 && day == 4) {
            message = "青年节";
        } else if (month == 4 && day == 12) {
            message = "护士节";
        } else if (month == 5 && day == 1) {
            message = "儿童节";
        } else if (month == 6 && day == 1) {
            message = "建党节";
        } else if (month == 7 && day == 1) {
            message = "建军节";
        } else if (month == 8 && day == 10) {
            message = "教师节";
        } else if (month == 9 && day == 1) {
            message = "国庆节";
        } else if (month == 10 && day == 11) {
            message = "光棍节";
        } else if (month == 11 && day == 25) {
            message = "圣诞节";
        }
        return message;
    }

     */

    /*휴가 날짜 받는 날
    public int[] getHolidays(int year, int month) {
        int holidays[];
        if (sUtils.sAllHolidays != null) {
            holidays = sUtils.sAllHolidays.get(year + "" + month);
            if (holidays == null) {
                holidays = new int[42];
            }
        } else {
            holidays = new int[42];
        }
        return holidays;
    }

     */
    public static int getMonthRows(int year, int month){
        int size=getFirstDayWeek(year,month)+getMonthDays(year,month)-1;
        return size%7==0? size/7:(size/7)+1;
    }


}
