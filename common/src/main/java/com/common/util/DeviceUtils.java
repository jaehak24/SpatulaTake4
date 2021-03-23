package com.common.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

//어플리에키션 소프트 키보드를 가려주는 메소드 closeSoftInput
public class DeviceUtils {
    public static void closeSoftInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void openSoftInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }
}
