package com.be.www.myviewlibrary.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import com.be.www.myviewlibrary.R
import kotlinx.android.synthetic.main.activity_num_whell.*

class NumWhellActivity : AppCompatActivity() {
    private var delay = 5*1000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_num_whell)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setData()
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setData() {
        wheel.setText("1234.3345", "22.32")
        Handler().postDelayed({
            setData()
        }, delay)
    }
}
