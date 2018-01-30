package com.example.kcy.datebasecreattext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kcy on 2017/4/5.
 */

public class QueryAdater extends BaseAdapter {
    Context context;
    ArrayList<InfoBean> list;
    public QueryAdater(Context context, ArrayList<InfoBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view=null;
        if(convertView!=null){
            view=convertView;
        }{
            view=View.inflate(context,R.layout.show_data,null);
        }
        //找到控件
        TextView id= (TextView) view.findViewById(R.id.lv_id);
        TextView name= (TextView) view.findViewById(R.id.lv_name);
        TextView phone= (TextView) view.findViewById(R.id.lv_phone);
        InfoBean infoBean=list.get(position);
        id.setText(infoBean.id);
        name.setText(infoBean.name);
        phone.setText(infoBean.phone);
        return view;
    }
}
