package com.be.www.myviewlibrary.wheel

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.IntDef
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.be.www.myviewlibrary.R
import java.math.BigDecimal

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
 * Created on 2019-07-10-15:59.
 * author : Frank
 * email : lzkFrank@163.com
 * 数字轮盘动画效果
 */
class NumWheelView : View {
    private val mSeq = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    private val DOT = "."
    private var plotters = mutableListOf<CharPlotter>()
    private var mAnimator: ValueAnimator? = null

    private val mPaint: Paint by lazy { Paint() }
    private var mWidthMeasureSpec: Int? = null
    private var mHeightMeasureSpec: Int? = null

    private var mTypeface = Typeface.DEFAULT
    private var mTextColor = ContextCompat.getColor(context, android.R.color.black)
    private var mTextSize = 15f.Ndp2px()
    private var mDuration = 1000

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
        initPaint()
    }

    private fun initAttrs(attrs: AttributeSet?) {
        attrs ?: return
        val ta = context.obtainStyledAttributes(attrs, R.styleable.NumWheelView)
        for (i in 0 until ta.indexCount) {
            val attr = ta.getIndex(i)
            when (attr) {
                R.styleable.NumWheelView_fontFamily -> getFont(ta, attr)
                R.styleable.NumWheelView_text_color -> mTextColor = ta.getColor(attr, mTextColor)
                R.styleable.NumWheelView_text_size -> mTextSize = ta.getDimension(attr, mTextSize)
                R.styleable.NumWheelView_duration -> mDuration = ta.getInteger(attr, mDuration)
            }
        }
        ta.recycle()
    }

    private fun getFont(ta: TypedArray, attr: Int) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            mTypeface = ta.getFont(attr)
        }
        if (mTypeface == null) {
            mTypeface = Typeface.DEFAULT
        }
    }

    private fun initPaint() {
        mPaint.typeface = mTypeface
        mPaint.textSize = mTextSize
        mPaint.color = mTextColor
    }

    @Throws(IllegalArgumentException::class)
    fun setText(start: String, end: String) {
        if (!isNumber(start) || !isNumber(end)) throw IllegalArgumentException("start and end must be Number")

        initData(start, end)
        resetWH()

        initAnimator()
    }

    private fun initAnimator() {
        val plotter = plotters.maxBy { it.getTotalCount() }
        if (plotter == null) {
            start(10)
        } else {
            Log.i(TAG, "setText: ${plotter.getTotalCount()}")
            start(plotter.getTotalCount())
        }
    }

    private fun initData(start: String, end: String) {
        plotters.clear()
        when {
            start.NisInt() && end.NisInt() -> {
                val s = if (start.length < end.length) repair(start, end.length) else sub(start, end.length)
                initPlotter(s, end)
            }
            start.NisInt() -> {
                val s = if (start.length < end.length) repair(start, end.length) else sub(start, end.length)
                initPlotter(s, end)
            }
            end.NisInt() -> {
                val st = start.split(DOT)
                val s = if (st[0].length < end.length) repair(st[0], end.length) else sub(st[0], end.length)
                initPlotter(s, end)
            }
            else -> {
                val st = start.split(DOT)
                val en = end.split(DOT)
                val integer = if (st[0].length < en[0].length) repair(st[0], en[0].length) else sub(st[0], en[0].length)
                initPlotter("$integer$DOT${st[1]}", end)
            }
        }
    }

    private fun repair(num: String, length: Int): String {
        if (length == 0) return num
        return String.format("%${length}s", num).replace(" ", "0")
    }

    private fun sub(num: String, length: Int): String {
        if (num.length <= length) return num
        return num.substring(num.length - length)
    }

    private fun initPlotter(start: String, end: String) {
        val sArr = start.toCharArray()
        val eArr = end.toCharArray()

        for (i in 0 until eArr.size) {
            if (i < sArr.size) {
                plotters.add(getPlotter(sArr[i], eArr[i], i))
            } else {
                plotters.add(getPlotter('0', eArr[i], i))
            }
        }
    }

    private fun getPlotter(s: Char, e: Char, index: Int): CharPlotter {
        val p = CharPlotter.getInstance(context)
        p.initPaint(mPaint)
        p.setData(s, e, mSeq)
        p.setIndex(index)
        p.onMeasure()
        return p
    }

    private fun start(count: Int) {
        mAnimator?.cancel()

        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofFloat(0f, count.toFloat())
            mAnimator?.let {
                it.duration = mDuration.toLong()
                it.interpolator = DecelerateInterpolator()
                it.addUpdateListener { a ->
                    val k = a.animatedValue as Float
                    plotters.forEach { plotter -> plotter.setSchedule(k.toInt(), k % 1) }
                    invalidate()
                }
            }
        }

        mAnimator?.start()
    }

    val TAG = "NumWheelView"
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.mWidthMeasureSpec = widthMeasureSpec
        this.mHeightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun resetWH() {
        mWidthMeasureSpec ?: return
        mHeightMeasureSpec ?: return
        requestLayout()
    }

    private fun measureHeight(measureSpec: Int): Int {
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            View.MeasureSpec.EXACTLY -> result = size
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {
                result = if (plotters.isEmpty()) {
                    val p = getPlotter('0', '0', 0)
                    p.onMeasure()
                    (p.getHeight() + 0.9).toInt()
                } else {
                    (plotters[0].getHeight() + 0.9).toInt()
                }
            }
        }
        result = if (mode == View.MeasureSpec.AT_MOST) Math.min(result, size) else result
        return (result + paddingTop + paddingBottom + 1f.Ndp2px()).toInt()
    }

    private fun measureWidth(measureSpec: Int): Int {
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        var result = 0
        when (mode) {
            View.MeasureSpec.EXACTLY -> result = size
            View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED -> {
                result = if (plotters.isEmpty()) {
                    0
                } else {
                    val w = plotters.sumByDouble { it.getWidth().toDouble() }
                    (w + 0.9 + 2f.Ndp2px()).toInt()
                }
            }
        }
        result = if (mode == View.MeasureSpec.AT_MOST) Math.min(result, size) else result
        return result + paddingLeft + paddingRight
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        plotters.forEach {
            it.draw(canvas)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimator?.cancel()
    }

    private fun isNumber(c: Char) = mSeq.contains(c)
    private fun isNumber(num: String) = try {
        BigDecimal(num)
        true
    } catch (e: Exception) {
        false
    }

    private fun String.NisInt() = !this.contains(DOT)
    private fun Float.Ndp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)
}