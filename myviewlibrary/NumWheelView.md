# 轮转切换数字的View
### 控件效果展示

![效果图](https://github.com/lzkFrank/BlogsUtils/blob/master/NumWheelView.gif?raw=true)

### 控件使用
控件的使用非常简单，只需要在布局中引入控件
```
<com.be.www.myviewlibrary.wheel.NumWheelView
        android:id="@+id/wheel"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="20dp"
        android:layout_marginTop="20dp"
        app:fontFamily="sans-serif"
        app:duration="1000"
        app:text_size="100dp"
        app:text_color="@color/main_bg"
        android:background="@color/blue" />
```
在设置数据的时候，只需要设置给定起始值，结束值即可
```
wheel.setText("1234.3345", "22.32")
```
### 属性介绍

属性|功能
----|---- 
fontFamily|字体样式
duration|动画时长
text_size|字体大小
text_color|文字颜色

### 代码解析

功能的实现只有两个类：**NumWheelView** 和 **CharPlotterImp**

* 先说下主体的 **NumWheelView**，代码逻辑非常简单
```
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
```
根据数据是否为整数，进行对应的长度补全，以结束值为基准。都是小数的情况，就进行整数位和小数位的补全。之后将数据拆分为char的结构，放入对应的渲染器中：
```
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
```
之后根据最大的差值，算出需要轮转的最大圈数，执行动画。

* CharPlotterImp中的代码就是单个字符的绘制。以顺时针为例：
```
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
```
根据index判断是第几个字符，乘以单个宽度，得到移动x轴的距离。
根据单签移动的比例，确定canvas向y轴移动的距离。
drawText()绘制位置和其他绘制不同，其绘制的起始位置是 *Text* 的左下方位置。所以第二个和第三个参数，是绘制文字左下角。
根据当前是第几轮绘制（轮数根据最大差值得出）,获取对应的数值。
进行绘制。

* 当动画运行起来之后，就出现对应的效果了。差值最小的最先停止，最大的最后停止。

[Github](https://github.com/lzkFrank/ExampleProject)

> 君子千里同舟，小人隔墙易宿


