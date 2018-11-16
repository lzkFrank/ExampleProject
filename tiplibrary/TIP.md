# 小只是点

## LayoutAnimation-布局动画的简单实现

### 基本使用
*LayoutAnimation* 是个适用于*ViewGroup* 的动画(线性布局和*ListView* 等类型的效果比较好)。

先在*res* 中创建*anim* 文件夹，在其中创建*item\_enter\_one.xml* 动画效果的资源（item从底边展开）。

```
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="500"
    android:shareInterpolator="true">
    <alpha
        android:fromAlpha="0"
        android:toAlpha="1" />
    <scale
        android:fromXScale="0.5"
        android:fromYScale="0"
        android:pivotX="50%"
        android:pivotY="100%"
        android:toXScale="1"
        android:toYScale="1" />
</set>
```

*fromXScale=“0.5”* 即*X* 轴的缩放比例是*0.5* 开始，（*android:toXScale="1"* ）到正常；*android:pivotX="50%"* 是指*X* 轴以自身的*50%* 宽度（即中间）为锚点，*android:pivotY="100%"* 是指*Y* 轴以自身高度的全部（即底部）为锚点，开始缩放。**android 的*X* 轴是向右为正，*Y* 轴是向下为正，（0，0）点是控件的左上角。**

创建名为*layout\_child\_anim1.xml* 的*LayoutAnimation* 动画资源。

```
<layoutAnimation xmlns:android="http://schemas.android.com/apk/res/android"
    android:animation="@anim/item_enter_one"
    android:animationOrder="normal"
    android:delay="5%" />
```

其中的*android:delay="5%"* 是延迟时间，*5%* 是总持续时间的百分比（因为此动画的持续时间是500ms,所以就是25毫秒）。如果不使用百分比，那么他的单位就是秒，**0.5**就代表*0.5* 秒。*animation* 引用的要展示的动画资源。*animationOrder* 是指动画的执行顺序：

属性|效果
-------- | ---
normal | 顺序执行
reverse | 倒序执行
random | 随机顺序

只需要为控件设置上*layoutAnimation* 属性就可以实现了。就是如此简单。

```
 <android.support.v7.widget.RecyclerView
        android:id="@+id/lm_rv"
        android:layoutAnimation="@anim/layout_child_anim1"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

**但是**：此类只适合于初始化使用，虽然*adapter* 可以重复调用刷新(*kotlin*)，

```
rv.adapter.notifyItemChanged(1)
rv.scheduleLayoutAnimation()
```

但是会调用全局的刷新，如果项目需求是全局刷新，那么还是乖乖的使用*RecyclerView* 的*itemAnimator* 动画吧！

> 乍见之欢，不如久处不厌。
    