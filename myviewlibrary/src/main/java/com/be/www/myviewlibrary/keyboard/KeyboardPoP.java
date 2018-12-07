package com.be.www.myviewlibrary.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.be.www.myviewlibrary.R;

import java.lang.reflect.Method;

/**
 * Created by frankliu on 2018/12/7.
 */

public class KeyboardPoP {

    public static PopupWindow initPOPNumPasteView(Context context, EditText et, int xml, View.OnClickListener listener){
        View popLayout = LayoutInflater.from(context).inflate(R.layout.widget_keyboard_deep_view,null);
        View view = LayoutInflater.from(context).inflate(R.layout.view_keyboard_top_verfy,null);
        view.findViewById(R.id.paste_tv).setOnClickListener(listener);
        LinearLayout topContent = popLayout.findViewById(R.id.top_view_content);
        topContent.addView(view);
        KeyboardView   keyboardView   =  popLayout.findViewById(R.id.customKeyboard);
        PopupWindow    popupWindow    = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        IDCardKeyboard customKeyboard = new IDCardKeyboard(context, keyboardView, et,popupWindow,xml);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                return false;
            }
        });
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        return popupWindow;

    }

    public static PopupWindow initPOPNumView(final Context context,View topView, EditText et, int xml){
        View popLayout = LayoutInflater.from(context).inflate(R.layout.widget_keyboard_deep_view,null);
        LinearLayout topContent = popLayout.findViewById(R.id.top_view_content);
        if(topView == null){
            topContent.setVisibility(View.GONE);
        }else {
            topContent.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            topContent.addView(topView,params);
        }
        KeyboardView keyboardView =  popLayout.findViewById(R.id.customKeyboard);
        PopupWindow popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        IDCardKeyboard customKeyboard = new IDCardKeyboard(context, keyboardView, et,popupWindow,xml);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                return false;
            }
        });
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        return popupWindow;
    }
    /**pop显示**/
    public static void showKeyBoard(PopupWindow popupWindow, EditText et){
        popupWindow.showAtLocation(et, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**关闭系统键盘***/
    public static void closeKeyboard(EditText et, Activity context) {
        //1 屏蔽掉系统默认输入法
        if (Build.VERSION.SDK_INT <= 10) {
            et.setInputType(InputType.TYPE_NULL);
        } else {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls                     = EditText.class;
                Method          setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(et, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**eDit点击监听**/
    public static void initEdit(final Activity activity, final EditText et, final PopupWindow popupWindow){
        et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                KeyboardPoP.showKeyBoard(popupWindow, et);
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                return false;
            }
        });

    }

    public static void onFoucsed(EditText et, final PopupWindow popupWindow){
        et.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                }else {
                    popupWindow.dismiss();
                }

            }});
    }
    public static PopupWindow getKeyBoardNumHaveTop(Activity activity,EditText et ,View topView, int xml){
        PopupWindow popupWindow = KeyboardPoP.initPOPNumView(activity, topView,et, xml);
        KeyboardPoP.closeKeyboard(et, activity);
        KeyboardPoP.initEdit(activity, et, popupWindow);
        KeyboardPoP.onFoucsed(et, popupWindow);
        return popupWindow;
    }

    public static PopupWindow getKeyBoardNum(Activity activity,EditText et , int xml){
        return getKeyBoardNumHaveTop(activity,et,null,xml);
    }

    public static void showKeyBoardHavePaste(Activity activity,EditText et,View.OnClickListener listener){
        PopupWindow popupWindow = KeyboardPoP.initPOPNumPasteView(activity,et, R.xml.num_keyboard,listener);
        KeyboardPoP.closeKeyboard(et, activity);
        KeyboardPoP.initEdit(activity, et, popupWindow);
        KeyboardPoP.onFoucsed(et, popupWindow);
    }

    public interface PasteListener{
        void onPaste();
    }
}
