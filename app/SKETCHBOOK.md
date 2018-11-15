#速记本
##一、控件点击水波纹效果
### 先上效果图

![效果图](https://user-gold-cdn.xitu.io/2018/11/15/16717a3ecd51da25?w=366&h=600&f=gif&s=7482093)

### ripple的使用（需要V21以上）
```
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/dark_blue">
    <item android:drawable="@color/blue"/>
</ripple>
```
其中item的颜色是控件正常状态的背景色，ripple中的颜色是点击时出现的颜色（会以半透明的形式展示出来）。ripple颜色的变化效果要比selector的效果更加柔和，以渐变的形式出现。
```
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/dark_blue">
    <item android:id="@android:id/mask" android:drawable="@drawable/ic_launcher_foreground"/>
</ripple>
```
**mask的作用**：只能在规定范围内显示水波动画,范围边界由mask遮罩对象指定（可以换一些比原有背景小的图片资源，可以看出效果）。

将ripple文件设置为控件的背景色就可以了。对于没有设置点击事件的控件，是没有变化效果的。需要为控件设置 `android:clickable="true"`

```
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/main_item_tv"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:clickable="true"
    android:background="@drawable/item_blue_selector"
    android:gravity="center"
    android:textSize="17sp" />
```

>对于低版本来说，我还是选择selector作为适配方案。截止到现在，21及以上版本已经覆盖了90%的设备。

### foreground设置

现在的点击水波纹效果只有在抬起手指的时候才能看到，不符合一些项目的需求。

其实在控件除了设置*background*,还可以设置*foreground*。
```
android:foreground="?attr/selectableItemBackgroundBorderless"
```

在控件上加上这一句就可以看到你们想要的效果了。*selectableItemBackgroundBorderless*是系统提供的可以一个半透明灰色水波效果，在按住控件时，即可展示。(
*如果不需要提前变蓝的效果，设置纯蓝色背景即可。*)

>我有一壶酒，足以慰风尘。尽倾江海里，赠饮天下人。

* [GitHub](https://github.com/lzkFrank/ExampleProject)