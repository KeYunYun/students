package com.kcy.carouselimage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private int[] imageResIds;
    private  ArrayList<View> pointViews;
    private   List<ImageView> imageviewList;
    private LinearLayout ll_point_container;
    private     String [] contentDesc;
    private TextView tv_des;
    private int loatPosition =0;
    private boolean isUIable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        initDate();

        initAdapter();
        new Thread(){
            @Override
            public void run() {
                super.run();
                isUIable=true;
                while (isUIable){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
                            Log.i("run", "run: 111");
                        }
                    });

                }
            }
        }.start();
    }

    private void initDate() {

        imageResIds = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
        ImageView imageview;
        imageviewList = new ArrayList<ImageView>();

        pointViews = new ArrayList<View>();
        View point;


        contentDesc = new String[]{" 1. 写个类继承View",
                "2. 拷贝包含包名的全路径到xml中 " ,
                "3. 界面中找到该控件, 设置初始信息 " ,
                "4. 根据需求绘制界面内容" ,
                "5. 响应用户的触摸事件"};
        tv_des.setText(contentDesc[0]);
        for(int i=0;i<imageResIds.length;i++){
            imageview=new ImageView(this);
            imageview.setBackgroundResource(imageResIds[i]);
            imageviewList.add(imageview);
            point=new View(this);
            point.setBackgroundResource(R.drawable.select_bg_point);

            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(20,20);
            if(i!=0){
             params.leftMargin=10;
            }
            point.setEnabled(false);
            ll_point_container.addView(point,params);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);

        viewpager.setAdapter(new MyAdapter());
        //图片资源数组
        //设置监听
        int pos=Integer.MAX_VALUE/5+1;
        viewpager.setCurrentItem(pos);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滚动时调用
            }

            @Override
            public void onPageSelected(int position) {
                //选中时调用
                int newPostion=position%imageviewList.size();
                tv_des.setText(contentDesc[newPostion]);

             //   Log.i("onPageSelected", "onPageSelected: "+ll_point_container.getChildCount());

/*                for (int i=0;i<ll_point_container.getChildCount();i++){
                    View child=ll_point_container.getChildAt(position);
                    child.setEnabled(i==position);
                    Log.i("onPageSelected", "onPageSelected:III "+i);
                    Log.i("onPageSelected", "onPageSelected:position "+position);
                }*/
                ll_point_container.getChildAt(loatPosition).setEnabled(false);
                ll_point_container.getChildAt(newPostion).setEnabled(true);
                loatPosition=newPostion;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滚动状态变化时
            }
        });

    }

    private void initUI() {
        //设置数据
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        ll_point_container= (LinearLayout) findViewById(R.id.ll_point_container);
        tv_des= (TextView) findViewById(R.id.tv_des);

    }
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //当划到新的条目，又返回,view 是否可以被复用

            return view==object;//固定写法
        }
        //返回要显示的条目内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //container:viewPager 容器

            // 把view对象添加到container中
            int newPostion=position%imageviewList.size();
            ImageView imageView =imageviewList.get(newPostion);
            //把view返回给
            container.addView(imageView);

            return imageView;
        }
        //object要销毁的对象
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View)object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isUIable=false;
    }
}
