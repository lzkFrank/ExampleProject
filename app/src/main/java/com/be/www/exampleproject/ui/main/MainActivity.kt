package com.be.www.exampleproject.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.be.www.exampleproject.R
import com.be.www.myviewlibrary.MyViewMainActivity
import com.be.www.tiplibrary.main.TipMainActivity

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initRv()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.subtitle = "目录"
        // toolbar.setNavigationIcon(R.drawable.abc_ab_share_pack_mtrl_alpha)
        // toolbar.inflateMenu(R.menu.main_menu)
    }

    private fun initRv() {
        var datas = listOf("小知识点", "自定义view")

        val rv = findViewById<RecyclerView>(R.id.main_rv)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        divider.setDrawable(resources.getDrawable(R.drawable.ic_launcher_background));
        rv.addItemDecoration(divider)

        rv.itemAnimator = DefaultItemAnimator()

        rv.adapter = MainAdapter(datas)

        (rv.adapter as MainAdapter).setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClickListener(position: Int) {
                when (position) {
                    0 -> startActivity(Intent(this@MainActivity, TipMainActivity::class.java))
                    1 -> startActivity(Intent(this@MainActivity, MyViewMainActivity::class.java))
                }
            }
        })
    }

    /**
     * 当toolbar成为supportActionBar之后，直接对toolbar设置menu失效
     * 并且toolbar的menu不会走onOptionsItemSelected()方法
     * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> finish()
            R.id.main_set -> Toast.makeText(this@MainActivity, "Set", Toast.LENGTH_SHORT).show()
            R.id.main_change -> Toast.makeText(this@MainActivity, "Change", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
