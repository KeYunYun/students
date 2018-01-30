package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.db.dao.BlackNumberDao;
import com.kcy.mobilesafe.db.domain.BlackNumberInfo;

import java.util.List;

/**
 * Created by kcy on 2017/5/31.
 */

public class BlackNumberActivity extends Activity{
    Button bt_add;
    ListView lv_baclkNumber;
    BlackNumberDao mDao;
    private int mCount;
    List<BlackNumberInfo> mListBlackNum;
    MyAdapet myadapet;
    private  boolean mTsLoad=false;
    private int mode=1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //告知listView可以使用数据适配器
            if(myadapet==null){
                myadapet = new MyAdapet();
                lv_baclkNumber.setAdapter(myadapet);
            }else{
                myadapet.notifyDataSetChanged();
            }



        }
    };
    class ViewHolder{
        TextView tv_phone;
        TextView tv_mode;
        ImageView iv_delete;

    }
    class MyAdapet extends BaseAdapter{

        @Override
        public int getCount() {
            return mListBlackNum.size();
        }

        @Override
        public Object getItem(int position) {
            return mListBlackNum.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
           // View view =null;
            //复用convertView;
            ViewHolder viewholder=null;
            if(convertView==null){
                convertView= View.inflate(getApplicationContext(),R.layout.listview_blacknumber,null);
                viewholder=new ViewHolder();
                viewholder.tv_phone=(TextView) convertView.findViewById(R.id.tv_phone);
                viewholder.tv_mode=(TextView) convertView.findViewById(R.id.tv_mode);
                viewholder.iv_delete=(ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewholder);

            }else{

                viewholder=(ViewHolder) convertView.getTag();
            }
            //对findviewbyid的优化 使用

          viewholder.tv_phone.setText(mListBlackNum.get(position).phone);

            viewholder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //数据库中删除
                    mDao.delete(mListBlackNum.get(position).phone);
                    //集合中删除
                    mListBlackNum.remove(position);
                    if(myadapet!=null){
                        myadapet.notifyDataSetChanged();
                    }

                }
            });
            int mode=Integer.parseInt(mListBlackNum.get(position).mode);
            switch (mode){
                case 1: viewholder.tv_mode.setText("短信");break;
                case 2:viewholder.tv_mode.setText("电话");break;
                case 3:viewholder.tv_mode.setText("所有");break;
                default:break;
            }
            return convertView;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacknumber);
        initUI();
        initData();
    }

    private void initData() {
        //获得电话号码，耗时操作，放入线程中运行
        new Thread(){
            @Override
            public void run() {
                super.run();
                //获得操作黑名单数据库对象；
                 mDao=BlackNumberDao.getInstance(getApplicationContext());
                mCount=mDao.getCount();
               // 查询所以数据
                mListBlackNum=mDao.find(0);
                //通过消息机制，告知可以使用
                mHandler.sendEmptyMessage(0);

            }
        }.start();
    }

    private void initUI() {
        bt_add= (Button) findViewById(R.id.bt_add);
        lv_baclkNumber= (ListView) findViewById(R.id.lv_blacknumber);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
        //监听listview的滚动状态
        lv_baclkNumber.setOnScrollListener(new AbsListView.OnScrollListener() {
            //滚动过程中调用的方法
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
              //  AbsListView.OnScrollListener.SCROLL_STATE_FLING 飞速滚动
              //  AbsListView.OnScrollListener.SCROLL_STATE_IDLE 空闲状态
              //  AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL 拿手触摸后的滚动状态
                if(mListBlackNum!=null) {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                            && lv_baclkNumber.getLastVisiblePosition() >= mListBlackNum.size() - 1
                            && !mTsLoad) {
                        if(mCount>mListBlackNum.size()){
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    //获得操作黑名单数据库对象；
                                    mDao=BlackNumberDao.getInstance(getApplicationContext());
                                    // 查询所以数据
                                    List<BlackNumberInfo> moreData=mDao.find(mListBlackNum.size());
                                    mListBlackNum.addAll(moreData);
                                    //通过消息机制，告知可以使用
                                    mHandler.sendEmptyMessage(0);

                                }
                            }.start();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void showdialog() {
        AlertDialog.Builder builde=new AlertDialog.Builder(this);
        final AlertDialog alertdialog=builde.create();
        View view=View.inflate(getApplicationContext(),R.layout.dialog_add_blacknumber,null);
        alertdialog.setView(view,0,0,0,0);
        final EditText et_phone= (EditText) view.findViewById(R.id.et_phone);
        Button bt_submit= (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel= (Button) view.findViewById(R.id.bt_cancel);
        RadioGroup rg_grop= (RadioGroup) view.findViewById(R.id.rg_grop);
        rg_grop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_sms:
                        mode=1;
                        break;
                    case R.id.rb_phone:
                        mode=2;
                        break;
                    case R.id.rb_all:
                        mode=3;
                        break;
                    default:break;
                }
            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.获得电话号码
                String phone=et_phone.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                    mDao.insert(phone,mode+"");
                    //向集合中添加数据
                  BlackNumberInfo blackInfo=  new BlackNumberInfo();
                    blackInfo.phone=phone;
                    blackInfo.mode=mode+"";
                    mListBlackNum.add(0,blackInfo);
                    //通知数据适配器刷新
                    if(myadapet!=null){
                        myadapet.notifyDataSetChanged();
                    }
                    alertdialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(),"请输入号码",Toast.LENGTH_SHORT).show();
;                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });
        alertdialog.show();
    }
}
