package com.be.www.myviewlibrary.pie

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

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
 * Created on 2019-08-29-22:20.
 * author : Frank
 * email : lzkFrank@163.com
 *
 */
class PieView : View {
    private lateinit var mPaint: Paint

    private var mArcW = 10f.Ndp2px()//环形宽度
    private var mSelectedW = 15f.Ndp2px()//选择后的外环宽度
    private val mCenter = floatArrayOf(0f, 0f)
    private val mAngles = mutableListOf<PieAngle>() //角度列表，由计算得出

    private var selectorIndex = -1//选择的区域

    private var mColors = mutableListOf(Color.BLUE) //颜色列表
        set(value) {
            if (value.isEmpty()) return
            mColors.clear()
            mColors.addAll(value)
        }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initView()
    }

    private fun initView() {
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        mCenter[0] = measuredWidth / 2f
        mCenter[1] = measuredHeight / 2f

        for (i in 0 until mAngles.size) {
            drawArc(canvas, mAngles[i])
        }
    }

    private fun drawArc(canvas: Canvas, angle: PieAngle) {

    }

    data class PieAngle(var start: Float = 0f, var end: Float = 0f)

    private fun Float.Ndp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)
}