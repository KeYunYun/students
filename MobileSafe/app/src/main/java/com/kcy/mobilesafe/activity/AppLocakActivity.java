package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.db.dao.AppLockDao;
import com.kcy.mobilesafe.db.domain.AppInfo;
import com.kcy.mobilesafe.engine.AppInfoProider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/6/3.
 */

public class AppLocakActivity extends Activity{
    private     Button bt_nulock,bt_lock;
    private  LinearLayout ll_lock,ll_nulock;
    private TextView  tv_lock,tv_unlock;
    private  ListView lv_lock,lv_unlock;
   private List<AppInfo> appInfolist,mLockList,mnNuLockList;
    private  AppLockDao mDo;
    private   TranslateAnimation mTranslateAnimation;
    List<String> lockPackageList;
    private Myadapter mLockAdapter,mUnlockAdapter;
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接收消息填充数据适配器
           mLockAdapter= new Myadapter(true);
            lv_lock.setAdapter(mLockAdapter);

            mUnlockAdapter=new Myadapter(false);
            lv_unlock.setAdapter(mUnlockAdapter);
        }
    };
    class Myadapter extends BaseAdapter{
        private boolean isLock;
        public Myadapter(boolean isLock){
            this.isLock=isLock;
        }

        @Override
        public int getCount() {
            if(isLock){
                tv_lock.setText("已加锁的程序数目："+ mLockList.size());
                return mLockList.size();

            }else {
                tv_unlock.setText("未加锁的程序数目："+mnNuLockList.size());
                return mnNuLockList.size();
            }

        }

        @Override
        public AppInfo getItem(int position) {
            if(isLock){
                return mLockList.get(position);
            }else {
                return mnNuLockList.get(position);
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
             convertView = View.inflate(getApplicationContext(),R.layout.listview_lock_item,null);
              holder=new ViewHolder();
              holder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                holder.iv_lock= (ImageView)convertView. findViewById(R.id.iv_lock);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
              final AppInfo appInfo= getItem(position);
                final View animation=convertView;
                holder.iv_icon.setBackgroundDrawable(appInfo.icon);
                holder.tv_name.setText(appInfo.name);
                if(isLock){
                    holder.iv_lock.setBackgroundResource(R.drawable.lock);
                }else {
                    holder.iv_lock.setBackgroundResource(R.drawable.unlock);
                }
                holder.iv_lock.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        animation.startAnimation(mTranslateAnimation);
                        //动画执行做事件监听，动画完成才处理数据
                        mTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if(isLock){
                                    //删除一个结合

                                    mLockList.remove(appInfo);
                                    mnNuLockList.add(appInfo);
                                    //。删除数据库中的数据
                                    mDo.delete(appInfo.packName);
                                    //刷新
                                    mLockAdapter.notifyDataSetChanged();
                                }else {
                                        //删除一个结合
                                        mnNuLockList.remove(appInfo);
                                        mLockList.add(appInfo);
                                        //。删除数据库中的数据
                                        mDo.insert(appInfo.packName);
                                        //刷新
                                        mUnlockAdapter.notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                });


            return convertView;
        }
    }
    private   class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        ImageView iv_lock;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_loack);
        initUI();
        initDate();
        initAniMation();
    }

    private void initAniMation() {
        mTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,
                  Animation.RELATIVE_TO_SELF,1,
                  Animation.RELATIVE_TO_SELF,0,
                  Animation.RELATIVE_TO_SELF,0);
        mTranslateAnimation.setDuration(500);
    }

    private void initDate() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                //获得运用
                appInfolist= AppInfoProider.getAppInfoList(getApplicationContext());
                //区分加锁 未加锁的运用
                mLockList=new ArrayList<AppInfo>();
                mnNuLockList=new ArrayList<AppInfo>();
                //获得数据库中已加锁的结合
                 mDo= AppLockDao.getInstance(getApplicationContext());
                 lockPackageList= mDo.findAll();
                for(AppInfo appinfo: appInfolist){
                    //判断是否加锁
                    if(lockPackageList.contains(appinfo.packName)){
                        mLockList.add(appinfo);
                    }else {
                        mnNuLockList.add(appinfo);
                    }
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();

    }

    private void initUI() {

        bt_nulock = (Button) findViewById(R.id.bt_unlock);
        bt_lock = (Button) findViewById(R.id.bt_lock);

         ll_lock= (LinearLayout) findViewById(R.id.ll_lock);
         ll_nulock= (LinearLayout) findViewById(R.id.ll_nulock);

        tv_lock= (TextView) findViewById(R.id.tv_lock);
        tv_unlock= (TextView) findViewById(R.id.tv_unlock);

       lv_lock= (ListView) findViewById(R.id.lv_lock);
         lv_unlock= (ListView) findViewById(R.id.lv_unlock);

        bt_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_lock.setVisibility(View.VISIBLE);
                ll_nulock.setVisibility(View.GONE);
                //切换图片
                bt_nulock.setBackgroundResource(R.drawable.tab_left_default);
                bt_lock.setBackgroundResource(R.drawable.tab_right_pressed);
                mUnlockAdapter.notifyDataSetChanged();
                mLockAdapter.notifyDataSetChanged();
            }
        });
        bt_nulock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_lock.setVisibility(View.GONE);
                ll_nulock.setVisibility(View.VISIBLE);
                //切换图片
                bt_nulock.setBackgroundResource(R.drawable.tab_left_pressed);
                bt_lock.setBackgroundResource(R.drawable.tab_right_default);
                mUnlockAdapter.notifyDataSetChanged();
                mLockAdapter.notifyDataSetChanged();
            }
        });
    }
}
