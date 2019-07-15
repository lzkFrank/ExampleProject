package com.be.www.myviewlibrary.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.view.MenuItem
import com.be.www.myviewlibrary.R
import com.be.www.myviewlibrary.keyboard.KeyboardPoP

class KeyboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyboard)

        initToolbar()
        showKeyboard()
    }

    private fun initToolbar() {
//        setSupportActionBar(findViewById(R.id.in_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showKeyboard() {
        KeyboardPoP.getKeyBoardNumHaveTop(this, findViewById<AppCompatEditText>(R.id.kb_edit), null, R.xml.num_keyboard)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
