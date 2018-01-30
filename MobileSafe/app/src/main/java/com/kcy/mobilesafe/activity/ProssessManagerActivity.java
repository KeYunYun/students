package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.db.domain.ProcessInfo;
import com.kcy.mobilesafe.engine.ProcessInfoProvider;
import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kcy on 2017/6/2.
 */

public class ProssessManagerActivity extends Activity implements View.OnClickListener{
    private   TextView tv_process_count;
    private TextView tv_memoer_info;
    private     TextView tv_des;
    private   ListView lv_process_list;
    private  Button bt_setting;
    private    Button bt_clear;
    private int mProcessCount;
    String strAcailSpace;
    private   Button bt_resvert;
    private   Button bt_all;
    private long mAvailSpace;
    private ProcessInfo mProcessInfo;
    List<ProcessInfo> mProcessInfoList;
    List<ProcessInfo> mSystemList;
    List<ProcessInfo> mCustomerList;
    private   Myadapter myadapter;
    private Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myadapter=new Myadapter();
            lv_process_list.setAdapter(myadapter);
            if(tv_des!=null&&mCustomerList!=null){
                tv_des.setText("用户进程("+mCustomerList.size()+")");
            }
        }
    };
    class Myadapter extends BaseAdapter{

        //获取数据适配器中条目类型的总数，修改成两种（文本和图片）
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }
        //指向索引的条目类型
        @Override
        public int getItemViewType(int position) {
            if(position==0||position==mCustomerList.size()+1){
                return 0;//代表文本
            }else {
                return 1;
            }
        }

        @Override
        public int getCount() {
            if(Sputils.getBoolean(getApplicationContext(), ConstantValue.SHOW_SYSTEM,false)){
                return mCustomerList.size()+mSystemList.size()+2;
            }else {
                return mCustomerList.size()+1;
            }
        }

        @Override
        public ProcessInfo getItem(int position) {
            if(position==0||position==mCustomerList.size()+1){
                return null;
            }else {
                if (position < mCustomerList.size() + 1) {
                    return mCustomerList.get(position-1);
                } else {
                    return mSystemList.get(position - mCustomerList.size()-2);
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
                    titleHolder.tv_title.setText("用户进程("+mCustomerList.size()+")");
                }else{
                    titleHolder.tv_title.setText("系统进程("+mSystemList.size()+")");
                }
                return convertView;
            }else{
                //展示图片+文字条目
                ViewHolder holder = null;
                if(convertView == null){
                    convertView = View.inflate(getApplicationContext(), R.layout.listview_prosscess_item, null);
                    holder = new ViewHolder();
                    holder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
                    holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
                    holder.tv_memoey_info = (TextView) convertView.findViewById(R.id.tv_memory_info);
                    holder.cb_box= (CheckBox) convertView.findViewById(R.id.cb_box);
                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.iv_icon.setBackgroundDrawable(getItem(position).icon);
                holder.tv_name.setText(getItem(position).name);
                holder.tv_memoey_info.setText("内存占用"+Formatter.formatFileSize(getApplicationContext(),getItem(position).memsize));

               if(getItem(position).packageName.equals(getPackageName())){
                   holder.cb_box.setVisibility(View.GONE);
               }else{
                   holder.cb_box.setVisibility(View.VISIBLE);
               }
               holder.cb_box.setChecked(getItem(position).isCheck);
                return convertView;
            }
        }
    }
    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_memoey_info;
        CheckBox cb_box;
    }
    static class ViewTitleHolder {
        TextView tv_title;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prossessmanager);
        initUI();
        initTitleDate();
        initListData();
    }

    private void initListData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                mProcessInfoList=ProcessInfoProvider.getProcessInfoList(getApplicationContext());
                mSystemList=new ArrayList<ProcessInfo>();
                mCustomerList=new ArrayList<ProcessInfo>();
                for(ProcessInfo info:mProcessInfoList){
                    if(info.isSystem){
                        mSystemList.add(info);
                    }else {
                        mCustomerList.add(info);
                    }
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void initTitleDate() {
        mProcessCount=ProcessInfoProvider.getProcessCount(getApplicationContext());
        tv_process_count.setText("进程总数："+mProcessCount);
       //可用内存大小
        mAvailSpace=ProcessInfoProvider.getAvailSpace(getApplicationContext());

        strAcailSpace = Formatter.formatFileSize(this,mAvailSpace);
        //总内存大小
        long totalSpace=ProcessInfoProvider.getTotalSpace(getApplicationContext());
        String strtotalSpace=Formatter.formatFileSize(this,totalSpace);

        tv_memoer_info.setText("剩余/总数："+strAcailSpace+"/"+strtotalSpace);

    }

    private void initUI() {


        tv_des = (TextView) findViewById(R.id.tv_des);
        tv_process_count = (TextView) findViewById(R.id.tv_process_count);
        tv_memoer_info = (TextView) findViewById(R.id.tv_memoey_info);
        lv_process_list = (ListView) findViewById(R.id.lv_process_list);


        bt_all = (Button) findViewById(R.id.bt_all);
        bt_all.setOnClickListener(this);
        bt_resvert = (Button) findViewById(R.id.bt_resvert);
        bt_resvert.setOnClickListener(this);
        bt_clear = (Button) findViewById(R.id.bt_clear);
        bt_clear.setOnClickListener(this);
        bt_setting = (Button) findViewById(R.id.bt_setting);
        bt_setting.setOnClickListener(this);
        lv_process_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //滚动过程中调用方法
                //AbsListView中view就是listView对象
                //firstVisibleItem第一个可见条目索引值
                //visibleItemCount当前一个屏幕的可见条目数
                //总共条目总数
                if(mCustomerList!=null && mSystemList!=null){
                    if(firstVisibleItem>=mCustomerList.size()+1){
                        //滚动到了系统条目
                        tv_des.setText("系统进程("+mSystemList.size()+")");
                    }else{
                        //滚动到了用户应用条目
                        tv_des.setText("用户进程("+mCustomerList.size()+")");
                    }
                }

            }
        });

        lv_process_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0 || position == mCustomerList.size()+1){
                    return;
                }else{
                    if(position<mCustomerList.size()+1){
                       mProcessInfo  = mCustomerList.get(position-1);
                    }else{
                        //返回系统应用对应条目的对象
                        mProcessInfo = mSystemList.get(position - mCustomerList.size()-2);
                    }
                    if(mProcessInfo!=null){
                        if(!mProcessInfo.packageName.equals(getPackageName())){
                            //选中条目指向的对象和本应用的包名不一致,才需要去状态取反和设置单选框状态
                            //状态取反
                            mProcessInfo.isCheck = !mProcessInfo.isCheck;
                            //checkbox显示状态切换
                            //通过选中条目的view对象,findViewById找到此条目指向的cb_box,然后切换其状态
                            CheckBox cb_box = (CheckBox) view.findViewById(R.id.cb_box);
                            cb_box.setChecked(mProcessInfo.isCheck);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_all:
                selectAll();
                break;
            case R.id.bt_resvert:
                selectReverse();
                break;
            case R.id.bt_clear:
                clearAll();
                break;
            case R.id.bt_setting:
                setting();
                break;
        }

    }

    private void setting() {
        startActivity(new Intent(getApplicationContext(),ProssessSettingActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(myadapter!=null){
            myadapter.notifyDataSetChanged();
        }
    }

    /**
     * 清理选中进程
     */
    private void clearAll() {
        //1,获取选中进程
        //2,创建一个记录需要杀死的进程的集合
        List<ProcessInfo> killProcessList = new ArrayList<ProcessInfo>();
        for(ProcessInfo processInfo:mCustomerList){
            if(processInfo.getPackageName().equals(getPackageName())){
                continue;
            }
            if(processInfo.isCheck){
                //不能在集合循环过程中去移除集合中的对象
//				mCustomerList.remove(processInfo);
                //3,记录需要杀死的用户进程
                killProcessList.add(processInfo);
            }
        }

        for(ProcessInfo processInfo:mSystemList){
            if(processInfo.isCheck){
                //4,记录需要杀死的系统进程
                killProcessList.add(processInfo);
            }
        }
        //5,循环遍历killProcessList,然后去移除mCustomerList和mSystemList中的对象
        long totalReleaseSpace = 0;
        for (ProcessInfo processInfo : killProcessList) {
            //6,判断当前进程在那个集合中,从所在集合中移除
            if(mCustomerList.contains(processInfo)){
                mCustomerList.remove(processInfo);
            }

            if(mSystemList.contains(processInfo)){
                mSystemList.remove(processInfo);
            }
            //7,杀死记录在killProcessList中的进程
            ProcessInfoProvider.killProcess(this,processInfo);

            //记录释放空间的总大小
            totalReleaseSpace += processInfo.memsize;
        }
        //8,在集合改变后,需要通知数据适配器刷新
        if(myadapter!=null){
            myadapter.notifyDataSetChanged();
        }
        //9,进程总数的更新
        mProcessCount -= killProcessList.size();
        //10,更新可用剩余空间(释放空间+原有剩余空间 == 当前剩余空间)
        mAvailSpace += totalReleaseSpace;
        //11,根据进程总数和剩余空间大小
        tv_process_count.setText("进程总数:"+mProcessCount);
        tv_memoer_info.setText("剩余/总共"+Formatter.formatFileSize(this, mAvailSpace)+"/"+strAcailSpace);
        //12,通过吐司告知用户,释放了多少空间,杀死了几个进程,
        String totalRelease = Formatter.formatFileSize(this, totalReleaseSpace);
//		ToastUtil.show(getApplicationContext(), "杀死了"+killProcessList.size()+"个进程,释放了"+totalRelease+"空间");

//		jni  java--c   c---java
        //占位符指定数据%d代表整数占位符,%s代表字符串占位符
        Toast.makeText(getApplicationContext(),  String.format("杀死了%d进程,释放了%s空间", killProcessList.size(),totalRelease),Toast.LENGTH_SHORT).show();
    }

    private void selectReverse() {
        //1,将所有的集合中的对象上isCheck字段取反
        for(ProcessInfo processInfo:mCustomerList){
            if(processInfo.getPackageName().equals(getPackageName())){
                continue;
            }
            processInfo.isCheck = !processInfo.isCheck;
        }
        for(ProcessInfo processInfo:mSystemList){
            processInfo.isCheck = !processInfo.isCheck;
        }
        //2,通知数据适配器刷新
        if(myadapter!=null){
            myadapter.notifyDataSetChanged();
        }
    }

    private void selectAll() {
        //1,将所有的集合中的对象上isCheck字段设置为true,代表全选,排除当前应用
        for(ProcessInfo processInfo:mCustomerList){
            if(processInfo.getPackageName().equals(getPackageName())){
                continue;
            }
            processInfo.isCheck = true;
        }
        for(ProcessInfo processInfo:mSystemList){
            processInfo.isCheck = true;
        }
        //2,通知数据适配器刷新
        if(myadapter!=null){
            myadapter.notifyDataSetChanged();
        }
    }
}
