package com.be.www.exampleproject.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.be.www.exampleproject.R
import com.be.www.exampleproject.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止个别机型重复初始化启动页面
        if (!this.isTaskRoot && intent != null) {
            val action = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                finish()
            }
        }
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ startActivity(Intent(this@SplashActivity, MainActivity::class.java)) }
                , 1500L)
    }
}
