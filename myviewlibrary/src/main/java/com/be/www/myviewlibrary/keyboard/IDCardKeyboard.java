package com.be.www.myviewlibrary.keyboard;


import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.widget.EditText;
import android.widget.PopupWindow;

public class IDCardKeyboard {

    private final Context context;
    private EditText mEdittext;
    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private PopupWindow pop;

    public IDCardKeyboard(Context context, KeyboardView keyboardView, EditText editText, PopupWindow pop, int xml) {
    	this.pop = pop;
        this.context = context;
        this.mEdittext = editText;
        mKeyboard = new Keyboard(context, xml);
        mKeyboardView = keyboardView;
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(actionListener);
    }

    private KeyboardView.OnKeyboardActionListener actionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEdittext.getText();
            int index = mEdittext.getSelectionStart();//光标位置
            switch (primaryCode) {

                case Keyboard.KEYCODE_DELETE://回退
                    if (editable != null && editable.length() > 0) {
                        if (index > 0) {
                            editable.delete(index - 1, index);
                        }
                    }
                    break;
                case 9995://重输
                    mEdittext.setText("");
                    break;
                case 9994://左移
                    if (index > 0) {
                        mEdittext.setSelection(index - 1);
                    }
                    break;
                case 9996://右移
                    if (index < mEdittext.length()) {
                        mEdittext.setSelection(index + 1);
                    }
                    break;
                case 9997://完成
                    pop.dismiss();
                    break;

                case 9990:
                    if(editable.length()==0){
                        return;
                    }
                    if(editable.charAt(0)==48&&index<=1){
                        return;
                    }
                    editable.insert(index,"00");
                    break;
                case 48:
                    if(editable.length()>0){
                        if(editable.charAt(0)==48&&index<=1){
                            return;
                        }
                    }
                    editable.insert(index, Character.toString((char) primaryCode));
                    break;

                case 46:
                    if(editable.length()==0){
                        editable.insert(index,"0.");
                        return;
                    }
                    editable.insert(index, Character.toString((char) primaryCode));
                    break;

                default:
                    editable.insert(index, Character.toString((char) primaryCode));
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }
        @Override
        public void swipeUp() {

        }
    };

}
