package com.be.www.tiplibrary.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.be.www.tiplibrary.R

/**
 * Created by frankliu on 2018/11/15.
 */
class MainAdapter(var mDatas: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tip_main, parent, false))
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemHolder: ItemHolder = holder as ItemHolder
        itemHolder.itemTv.text = mDatas[position]
        itemHolder.itemTv.setOnClickListener {
            listener?.onItemClickListener(position)
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTv: TextView = itemView.findViewById(R.id.main_item_tv)
    }

    fun setOnItemClickListener(l: OnItemClickListener) {
        this.listener = l
    }

    @FunctionalInterface
    interface OnItemClickListener {
        fun onItemClickListener(position: Int)
    }
}