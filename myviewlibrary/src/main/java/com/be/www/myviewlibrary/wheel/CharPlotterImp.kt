package com.be.www.myviewlibrary.wheel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import java.lang.IllegalStateException

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
 * Created on 2019-07-11-17:43.
 * author : Frank
 * email : lzkFrank@163.com
 *
 */
class CharPlotterImp(var mContext: Context) : CharPlotter() {
    private val TAG = "CharPlotterImp"
    private val FIDUCIAL = "8"

    private val paddings = floatArrayOf(0.5f.Ndp2px(), 5f.Ndp2px(), 1f.Ndp2px(), 5f.Ndp2px())
    private val center = floatArrayOf(0f, 0f)
    private val mTextBounds = Rect()

    private var mPaint: Paint? = null
    private var mSeq: CharArray? = null
    private var mStart = '0'
    private var mEnd = '0'
    private var mWidth = 0f
    private var mHeight = 0f
    private var mCount = 0
    private var mRatio = 0f
    private var mIndex = 0

    override fun initPaint(paint: Paint) {
        mPaint = paint
    }

    override fun onMeasure() {
        mPaint?.getTextBounds(FIDUCIAL, 0, 1, mTextBounds)
        mHeight = mTextBounds.height() + paddings[1] + paddings[3]
        mWidth = mTextBounds.width() + paddings[0] + paddings[2]
        center[0] = mWidth / 2
        center[1] = mHeight / 2
    }

    override fun getWidth() = mWidth

    override fun getHeight() = mHeight

    override fun draw(canvas: Canvas) {
        mPaint ?: throw IllegalStateException("paint can't be null")
        if (mCount < getTotalCount() && isNum(mStart) && isNum(mEnd)) {
            if (isCW()) transCWDraw(canvas) else transACWDraw(canvas)
        } else {
            norDraw(canvas)
        }
    }

    private fun transCWDraw(canvas: Canvas) {
        val tx = mWidth * mIndex
        val ty = -mHeight * mRatio
        val top = mStart + mCount
        val bottom = top + 1
        Log.i(TAG, "transCW: $mStart -- $mCount -- $mEnd -- ${mIndex}");

        canvas.save()
        canvas.translate(tx, ty)
        canvas.drawText(top.toString(), paddings[0], mHeight - paddings[3], mPaint)
        canvas.drawText(bottom.toString(), paddings[0], mHeight - paddings[3] + mHeight, mPaint)
        canvas.restore()
    }

    private fun transACWDraw(canvas: Canvas) {
        val tx = mWidth * mIndex
        val ty = mHeight * mRatio
        val top = mStart - mCount - 1
        val bottom = top + 1
        Log.i(TAG, "transACW: $mStart -- $mCount -- $mEnd -- $mIndex")

        canvas.save()
        canvas.translate(tx, ty)
        canvas.drawText(top.toString(), paddings[0], mHeight - paddings[3] - mHeight, mPaint)
        canvas.drawText(bottom.toString(), paddings[0], mHeight - paddings[3], mPaint)
        canvas.restore()
    }

    private fun norDraw(canvas: Canvas) {
        val tx = mWidth * mIndex
        var y = mHeight - paddings[3]
        canvas.save()
        if (isDot(mEnd)) {
            mPaint?.getTextBounds(mEnd.toString(), 0, 1, mTextBounds)
            paddings[0] = (mWidth - mTextBounds.width()) / 2
        }
        canvas.translate(tx, 0f)
        canvas.drawText(mEnd.toString(), paddings[0], y, mPaint)
        canvas.restore()
    }

    override fun setData(start: Char, end: Char, seq: CharArray) {
        this.mStart = start
        this.mEnd = end
        this.mSeq = seq
    }

    override fun isCW() = mStart < mEnd

    override fun getTotalCount() = if (isDot(mStart) || isDot(mEnd)) 0 else Math.abs(mEnd - mStart)

    override fun setSchedule(count: Int, ratio: Float) {
        this.mCount = count
        this.mRatio = ratio
    }


    override fun setIndex(index: Int) {
        this.mIndex = index
    }

    private fun isNum(num: Char) = num in '0'..'9'
    private fun isDot(num: Char) = num == '.'
    private fun Float.Ndp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, mContext.resources.displayMetrics)
}