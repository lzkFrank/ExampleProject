package com.be.www.myviewlibrary.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.be.www.myviewlibrary.R
import kotlinx.android.synthetic.main.activity_num_whell.*

class NumWhellActivity : AppCompatActivity() {
    private var delay = 100L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_num_whell)
        setData()
    }

    private fun setData() {
        wheel.setText("1234.3345", "12.32")
        Handler().postDelayed({
            setData()
        }, delay)
        delay += 50
    }
}
