package com.be.www.myviewlibrary

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.PopupWindow
import com.be.www.myviewlibrary.keyboard.KeyboardPoP

class MyViewMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_view_main)
        var toolbar: Toolbar = this.findViewById(R.id.mv_toolbar);
        var popupWindow: PopupWindow = KeyboardPoP.getKeyBoardNumHaveTop(this, findViewById(R.id.mv_edit), null, R.xml.num_keyboard)
    }
}
