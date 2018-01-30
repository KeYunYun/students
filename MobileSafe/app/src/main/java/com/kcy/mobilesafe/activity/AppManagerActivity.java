package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.db.domain.AppInfo;
import com.kcy.mobilesafe.engine.AppInfoProider;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/6/1.
 */

public class AppManagerActivity extends Activity implements View.OnClickListener{
    PopupWindow popup;
    private List<AppInfo> appInfoLis;
    private     AppInfo mAppinfo;
    private MyAdapter myadapter;
    TextView tv_des;
    private  ListView lv_app_list;
    List<AppInfo> mSystemList;
    List<AppInfo> mSdList;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myadapter=new MyAdapter();
            lv_app_list.setAdapter(myadapter);
            if(tv_des!=null&&mSdList!=null){
                tv_des.setText("用户应用("+mSdList.size()+")");
            }


        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_uninstall:
                if(mAppinfo.isSystem){
                    Toast.makeText(getApplicationContext(),"不能卸载",Toast.LENGTH_SHORT).show();
                }else {
                    //开启卸载界面
                    Intent intent = new Intent("android.intent.action.DELETE");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse("package:"+mAppinfo.getPackName()));
                    startActivity(intent);
                }

                break;
            case  R.id.tv_start:
                //通过桌面去启动应用
                PackageManager pm=getPackageManager();
               Intent launchIntent= pm.getLaunchIntentForPackage(mAppinfo.getPackName());
               if(launchIntent!=null){
                   startActivity(launchIntent);
               }else {
                   Toast.makeText(getApplicationContext(), "不能开启", Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.tv_share:
                //分享（第三方平台）
                //
               Intent intent= new Intent(Intent.ACTION_SEARCH);
                intent.putExtra(intent.EXTRA_TEXT,"分享一个应用，名称为"+mAppinfo.getName());
                intent.setType("text/plain");
                startActivity(intent);
                break;
            default:break;
        }
        if(popup!=null){
            popup.dismiss();
        }

    }

    class MyAdapter extends BaseAdapter{

        //获取数据适配器中条目类型的总数，修改成两种（文本和图片）
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }
        //指向索引的条目类型
        @Override
        public int getItemViewType(int position) {
           if(position==0||position==mSdList.size()+1){
               return 0;//代表文本
           }else {
               return 1;
           }
        }

        @Override
        public int getCount() {
            return mSdList.size()+mSystemList.size()+2;
        }

        @Override
        public AppInfo getItem(int position) {
            if(position==0||position==mSdList.size()+1){
                return null;
            }else {
                if (position < mSdList.size() + 1) {
                    return mSdList.get(position-1);
                } else {
                    return mSystemList.get(position - mSdList.size()-2);
                }
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           int type =getItemViewType(position);
            if(type==0){
                ViewTitleHolder titleHolder=null;
                if(convertView==null){
                    convertView=View.inflate(getApplicationContext(),R.layout.listview_app_item_title,null);
                    titleHolder=new ViewTitleHolder();
                    titleHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
                    convertView.setTag(titleHolder);
                }else {
                    titleHolder=(ViewTitleHolder) convertView.getTag();
                }
                if(position==0){
                    titleHolder.tv_title.setText("用户应用("+mSdList.size()+")");
                }else{
                    titleHolder.tv_title.setText("系统应用("+mSystemList.size()+")");
                }
                return convertView;
            }else{
                //展示图片+文字条目
                ViewHolder holder = null;
                if(convertView == null){
                    convertView = View.inflate(getApplicationContext(), R.layout.listview_app_item, null);
                    holder = new ViewHolder();
                    holder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
                    holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
                    holder.tv_path = (TextView) convertView.findViewById(R.id.tv_path);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.iv_icon.setBackgroundDrawable(getItem(position).icon);
                holder.tv_name.setText(getItem(position).name);
                if(getItem(position).isSystem){
                    holder.tv_path.setText("系统应用");
                }else{
                    holder.tv_path.setText("用户应用");
                }
                return convertView;
            }


        }
    }
  private   static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_path;
    }
  private   static class ViewTitleHolder {
        TextView tv_title;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        initTitle();
      // List<AppInfo> appInfoList= AppInfoProider.getAppInfoList(getApplicationContext());
        initList();

    }

    private void initList() {

        tv_des = (TextView) findViewById(R.id.tv_des);
        lv_app_list = (ListView) findViewById(R.id.lv_app_list);
        new Thread(){
            @Override
            public void run() {
                super.run();
                appInfoLis= AppInfoProider.getAppInfoList(getApplicationContext());

                mSystemList = new ArrayList<AppInfo>();
                mSdList = new ArrayList<AppInfo>();
                for(AppInfo appibfo:appInfoLis){
                    if(appibfo.isSystem){
                        mSystemList.add(appibfo);
                    }else {
                        mSdList.add(appibfo);
                    }
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
        lv_app_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滚动过程中调用的方法
                //firstvi第一个课件条目
                //visible当前可见条目
                if (mSdList != null && mSystemList != null) {
                    if (firstVisibleItem > mSdList.size() + 1) {
                        tv_des.setText("系统应用(" + mSystemList.size() + ")");
                    } else {

                        tv_des.setText("用户应用(" + mSdList.size() + ")");
                    }
                }
            }
        });
        lv_app_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0||position==mSdList.size()+1){
                    return;
                }else {
                    if(position<mSdList.size()+1){
                        mAppinfo = mSdList.get(position-1);
                    }else{
                        mAppinfo=mSystemList.get(position-mSdList.size()-2);
                    }
                    showPopupWindow(view);
                }
            }
        });
    }

    private void showPopupWindow(View view1)  {
        View view=View.inflate(this,R.layout.popupwindow_layout,null);
        TextView tv_uninstall= (TextView) view.findViewById(R.id.tv_uninstall);
        TextView tv_start= (TextView) view.findViewById(R.id.tv_start);
        TextView tv_share= (TextView) view.findViewById(R.id.tv_share);

        tv_uninstall.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        tv_share.setOnClickListener(this);

        //透明动画
        AlphaAnimation alphanimation=new AlphaAnimation(0,1);
        alphanimation.setDuration(1000);
        alphanimation.setFillAfter(true);
        //缩放动画
        ScaleAnimation scaleAnimation =new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        //动画set集合
        AnimationSet abimation =new AnimationSet(true);
        abimation.addAnimation(alphanimation);
        abimation.addAnimation(scaleAnimation);

        //创建一个窗体,指定宽高

        popup = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,true);
        //设置背景
        popup.setBackgroundDrawable(new ColorDrawable());
        //指定窗体的位置
        popup.showAsDropDown(view1,120,-view1.getHeight());
        //执行动画
        view.startAnimation(abimation);
    }

    private void initTitle() {
        //获得内存可用大小
        //获得路径
        String path=Environment.getDataDirectory().getAbsolutePath();
        //外部存储的路径
        String sdPath=Environment.getExternalStorageDirectory().getAbsolutePath();

        //获得可用大小
        String memoryAvailSpace= Formatter.formatFileSize(this, getAvailSpace(path));
        String sdMmemoryAvailSpace= Formatter.formatFileSize(this, getAvailSpace(sdPath));
        TextView tv_moemory= (TextView) findViewById(R.id.tv_memory);
        TextView tv_sdmemory= (TextView) findViewById(R.id.tv_sd_memory);
        tv_moemory.setText("内存可用空间"+memoryAvailSpace);
        tv_sdmemory.setText("SD卡可用空间"+sdMmemoryAvailSpace);

    }

    private long getAvailSpace(String path) {
        //获得可用磁盘大小
       StatFs statfs=new StatFs(path);
        //获得可用区块的个数
        long count= statfs.getAvailableBlocks();
        //获得区块大小
       long size= statfs.getBlockSize();
        return count*size;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
