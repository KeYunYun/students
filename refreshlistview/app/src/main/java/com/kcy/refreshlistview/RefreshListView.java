package com.kcy.refreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by kcy on 2017/6/10.
 */

public class RefreshListView extends ListView {
    private View mHeaderview;

    public RefreshListView(Context context) {
        super(context);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //初始化布局
    private void init(){
        initHeaderView();
    }

    private void initHeaderView() {
        mHeaderview=View.inflate(getContext(),R.layout.layout_header_list,null);
        addHeaderView(mHeaderview);
    }
}
