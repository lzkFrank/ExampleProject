package com.be.www.myviewlibrary.wheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

/***
 *                     .::::.
 *                   .::::::::.
 *                  :::::::::::
 *              ..:::::::::::'
 *            '::::::::::::'
 *            .::::::::::
 *         '::::::::::::::.
 *              .::::::::::::.
 *             ::::::::::::::::
 *            ::::'`:::::::::'        .::.
 *           ::::'   ':::::'       .:::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.::::::::::'     ':::::.
 *      .::'        '::::::::::::::'         ``::::.
 *  ...:::          '::::::::::::'              ``::.
 * ``````            ':::::::::'                  ::::..
 *                    '::::::'                    ':'````..
 * Created on 2019-07-11-17:28.
 * author : Frank
 * email : lzkFrank@163.com
 * 字符绘制器
 */
abstract class CharPlotter {
    companion object {
        fun getInstance(context: Context) = CharPlotterImp(context)
    }

    abstract fun initPaint(paint: Paint)
    abstract fun onMeasure()
    abstract fun getWidth(): Float
    abstract fun getHeight(): Float
    abstract fun draw(canvas: Canvas)
    abstract fun setData(start: Char, end: Char, seq: CharArray)
    abstract fun isCW(): Boolean //是否为顺时针
    abstract fun getTotalCount(): Int //需要旋转的次数
    abstract fun setSchedule(count: Int, ratio: Float) //当前次数，旋转式比例
    abstract fun setIndex(index:Int) //控制器的序号
}