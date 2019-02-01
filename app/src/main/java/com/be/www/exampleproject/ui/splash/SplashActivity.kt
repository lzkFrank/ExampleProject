package com.be.www.exampleproject.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.be.www.exampleproject.R
import com.be.www.exampleproject.ui.main.MainActivity
import android.view.WindowManager
import android.os.Build


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullP()
        preventReset()
        setContentView(R.layout.activity_splash)
    }

    /**
     * 防止个别机型重复初始化启动页面
     * */
    private fun preventReset() {
        if (!this.isTaskRoot && intent != null) {
            val action = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ startActivity(Intent(this@SplashActivity, MainActivity::class.java)) }
                , 1500L)
    }

    /**
     * 9.0全屏视屏
     * */
    private fun fullP() {
        if (Build.VERSION.SDK_INT >= 28) {
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }
    }
}
