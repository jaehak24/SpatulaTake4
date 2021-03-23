package com.common.util;

import android.content.Context;

//디스플레이 픽셀 덴시티 설정 메소드 유틸리티
public class DensityUtil {
    public static int dipToSp(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int spToDip(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().scaledDensity * f) + 0.5f);
    }

}
