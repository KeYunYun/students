package com.kcy.mobilesafe.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kcy on 2017/4/25.
 * 能够获得焦点的自定义TextView
 */

public class FocusTextView extends android.support.v7.widget.AppCompatTextView {
   //通过Java代码创建控件
    public FocusTextView(Context context) {
        super(context);
    }
    //由系统调用（带属性 +上下文+）
    public FocusTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }
    //由系统调用（带属性 +上下文+ 布局文件的构造函数）
    public FocusTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写获取焦点,由系统调用
    @Override
    public boolean isFocused() {
        return true;
    }
}
