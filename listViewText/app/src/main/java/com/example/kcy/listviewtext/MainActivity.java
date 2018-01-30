package com.example.kcy.listviewtext;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv_simple=(ListView) findViewById(R.id.lv_simple);
        mcontext=this;
        //创建一个adapt对象
        MylisView mylisView=new MylisView();
        //将adapt设置给ListView
        lv_simple.setAdapter(mylisView);
    }

    class MylisView extends BaseAdapter{


        @Override
        //告诉ListView要显示多少个条目
        public int getCount() {
            return 10;
        }

        @Override
        //根据postion获取listView上对应的Bean数据
        //该方法不影响数据的展示，可以先不实现
        public Object getItem(int position) {
            return 0;
        }

        @Override
        //用来获取postion行的id
        public long getItemId(int position) {
            return 0;
        }

        @Override
        //用来放回一个view对象作为条目上的内容来展示
        //必须实现，该方法返回怎么样的View，条目上就显示怎么样的view
        //屏幕上每显示一个条目，就执行一次getview（）
        //convertView   复用旧的view
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = null;
            if(convertView !=null){
                view =(TextView) convertView;
            }else{
                view =new TextView(mcontext);
            }
            //创建一个TextView对象
            view.setText("position"+position);
            //设置TextView的内容
            view.setTextSize(30);
            //设置显示字体的大小
            return view;
        }
    }
}
