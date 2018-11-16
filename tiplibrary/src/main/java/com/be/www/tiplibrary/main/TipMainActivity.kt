package com.be.www.tiplibrary.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import com.be.www.tiplibrary.R
import com.be.www.tiplibrary.layoutanimaion.LayoutAnimationActivity

class TipMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_main)
        initToolbar()
        initRv()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.subtitle = "小知识点"
    }

    private fun initRv() {
        var rv = findViewById<RecyclerView>(R.id.tip_main_rv)

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = MainAdapter(listOf("LayoutAnimation", "Databinding"))

        (rv.adapter as MainAdapter).setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClickListener(position: Int) {
                when (position) {
                    0 -> startActivity(Intent(this@TipMainActivity, LayoutAnimationActivity::class.java))
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
