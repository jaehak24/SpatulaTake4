package com.common.spatulatake4.util;

import com.common.spatulatake4.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class spatulaUtils {
    public static String timeStamp2Time(long time){
        return new SimpleDateFormat("HH:mm", Locale.KOREA).format(new Date(time));
    }
    //이벤트 설정의 태그 색을 설정
    static int getEventSetColor(int color){
        switch (color){
            case 0:
                return R.color.holiday_text_color;
            case 1:
                return R.color.color_schedule_blue;
            case 2:
                return R.color.color_schedule_green;
            case 3:
                return  R.color.color_schedule_orange;
            case 4:
                return R.color.color_schedule_pink;
            case 5:
                return R.color.color_schedule_yellow;
            default:
                return R.color.holiday_text_color;

        }
    }
    public static int getScheduleBlockView(int color){
        switch(color){
            case 0:
                return R.drawable.purplr_schedule_left_block;
            case 1:
                return R.drawable.blue_schedule_left_block;
            case 2:
                return R.drawable.green_schedule_left_block;
            case 3:
                return R.drawable.pink_schedule_left_block;
            case 4:
                return R.drawable.orange_schedule_left_block;
            case 5:
                return R.drawable.yellow_schedule_left_block;
            default:
                return R.drawable.purplr_schedule_left_block;

        }
    }
}


