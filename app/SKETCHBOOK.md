#速记本
##一、控件点击水波纹效果

####ripple的使用（需要V21以上）
```
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/dark_blue">
    <item android:drawable="@color/blue"/>
</ripple>
```
其中item的颜色是item正常的背景色，ripple中的颜色是点击时出现的颜色（会以半透明的形式展示出来）。ripple颜色的变化是要比selector变化的更加柔和，不会突然变化。
```
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/dark_blue">
    <item android:id="@android:id/mask" android:drawable="@drawable/ic_launcher_background"/>
</ripple>
```
mask的作用是：只能在规定范围内显示水波动画,范围边界由mask遮罩对象指定（可以换一些比原有北京小的图片资源，就可以看出效果）。

之后将上边的资源设置为控件的背景色就可以了。对于没有设置点击事件的控件，是没有变化效果。需要为控件设置 `android:clickable="true"`

>对于低版本来说，我还是选择selector作为适配方案。截止到现在，21及以上版本已经覆盖了90%的设备。

####foreground设置

现在的点击水波纹效果只有在抬起手指的时候才能看到，可以能不符合一些项目的审美需求。

其实在控件除了设置*background*,还可以设置*foreground*。
```
android:foreground="?attr/selectableItemBackgroundBorderless"
```

在控件上加上这一句就可以看到你们想要的效果了。*selectableItemBackgroundBorderless*是系统提供的可以一个半透明灰色水波效果，在按住控件时，即可展示。



