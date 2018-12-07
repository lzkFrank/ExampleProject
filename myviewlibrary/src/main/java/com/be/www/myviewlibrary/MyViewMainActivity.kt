package com.be.www.myviewlibrary

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.MenuItem
import com.be.www.myviewlibrary.key.KeyboardActivity

/**
 * -                   .::::.
 * -                 .::::::::.
 * -                :::::::::::
 * -            ..:::::::::::'
 * -          '::::::::::::'
 * -          .::::::::::
 * -       '::::::::::::::.
 * -            .::::::::::::.
 * -           ::::::::::::::::
 * -          ::::'`:::::::::'        .::.
 * -         ::::'   ':::::'       .:::::::.
 * -       .::::'      ::::     .:::::::'::::.
 * -      .:::'       :::::  .:::::::::' ':::::.
 * -     .::'        :::::.::::::::::'     ':::::.
 * -    .::'        '::::::::::::::'         ``::::.
 * -...:::          '::::::::::::'              ``::.
 * ``````            ':::::::::'                  ::::..
 * -                  '::::::'                    ':'````..
 * Created on 2018/12/7.
 * author : Frank
 * 自定义view模块
 */
class MyViewMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_view_main)
        initToolbar()

        initList()
    }

    private fun initToolbar() {
        var toolbar:Toolbar = findViewById(R.id.mv_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun initList() {
        var datas = listOf("自定义键盘")

        val rv = findViewById<RecyclerView>(R.id.mv_list)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        //        divider.setDrawable(resources.getDrawable(R.drawable.ic_launcher_background));
        rv.addItemDecoration(divider)

        rv.itemAnimator = DefaultItemAnimator()

        rv.adapter = ListAdapter(datas)

        (rv.adapter as ListAdapter).setOnItemClickListener(object : ListAdapter.OnItemClickListener {
            override fun onItemClickListener(position: Int) {
                when (position) {
                    0 -> startActivity(Intent(this@MyViewMainActivity, KeyboardActivity::class.java))
                    1 -> startActivity(Intent(this@MyViewMainActivity, MyViewMainActivity::class.java))
                }
            }
        })
    }
}
