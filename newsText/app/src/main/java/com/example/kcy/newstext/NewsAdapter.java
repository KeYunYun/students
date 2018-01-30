package com.example.kcy.newstext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by kcy on 2017/4/5.
 */

public class NewsAdapter extends BaseAdapter {
    private  ArrayList<NewBean> list;
    Context context;
    public NewsAdapter(ArrayList<NewBean> list , Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        if(convertView!=null){
            view=convertView;
        }else {
           view= View.inflate(context,R.layout.news_layout,null);
            //将布局装换成view对象的布局ID
            //resource：    root:将layout用root（viewGroop）包一层,一般值为null
        }
            //获取view的子控件对象
        SmartImageView img_icon = (SmartImageView) view.findViewById(R.id.imageView3);
        TextView tv_des= (TextView) view.findViewById(R.id.textView2);
        TextView tv_title= (TextView) view.findViewById(R.id.textView3);
        TextView tv_comment= (TextView) view.findViewById(R.id.tv_comment);
        TextView tv_type= (TextView) view.findViewById(R.id.tv_type);
        //获取list集合中position对应的数据
        NewBean newbean =list.get(position);

       img_icon.setImageUrl(newbean.icon_url);
        tv_comment.setText("评论："+newbean.comment);

        tv_title.setText(newbean.title);
        tv_des.setText(newbean.des);
            //获取list集合中的数据
        System.out.println("newbean.type"+newbean.type);
        switch (newbean.type){
            case 3: tv_type.setText("头条");break;
            case 1: tv_type.setText("体育");break;
            case 2:
                tv_type.setText("娱乐");break;
            default:break;

        }
        return view;
    }
}
