package com.be.www.tiplibrary.layoutanimaion

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.be.www.tiplibrary.R
import com.be.www.tiplibrary.main.MainAdapter

/***
 *布局探究一
 * LayoutAnimation使用研究
 * */
class LayoutAnimationActivity : AppCompatActivity() {
    enum class Anim(val type: Int, val describ: String) {
        RECYCLER_VIEW_LINEAR(0, "Recycler测试"),
        RECYCLER_VIEW_GRID(1, "Recycler测试"),
        LINEAR_LAYOUT(2, "Linealayout测试")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_animation)
        intiToolbar()
        initView(Anim.RECYCLER_VIEW_LINEAR)
    }

    private fun intiToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.subtitle = "布局动画1"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView(type: Anim) {
        when (type) {
            Anim.LINEAR_LAYOUT -> showLinear()
            Anim.RECYCLER_VIEW_LINEAR -> showRvLinear()
            Anim.RECYCLER_VIEW_GRID -> showRvGrid()
        }
    }

    private fun showLinear() {
        var ll = findViewById<LinearLayout>(R.id.lm_ll)
        ll.visibility = View.VISIBLE
    }

    private fun showRvLinear() {
        var rv = findViewById<RecyclerView>(R.id.lm_rv)
        rv.visibility = View.VISIBLE
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = MainAdapter(getList())
        /**定时调用刷新*/
        Handler().postDelayed({
            rv.adapter.notifyItemChanged(1)
            rv.scheduleLayoutAnimation()
        }, 5 * 1000)
    }

    private fun showRvGrid() {
        var rv = getRv()
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = MainAdapter(getList())
    }

    private fun getRv(): RecyclerView {
        var rv = findViewById<RecyclerView>(R.id.lm_rv)
        rv.visibility = View.VISIBLE
        return rv
    }

    private fun getList() =
            listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty")
}
