package com.kcy.mobilesafe.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.kcy.mobilesafe.R;
import com.kcy.mobilesafe.engine.CommounmDao;

import java.util.List;

/**
 * Created by kcy on 2017/6/3.
 */

public class CommonNumberQueryActivity extends Activity {
    private ExpandableListView elv_common_number;
    private List<CommounmDao.Grop> mGroplist;
    private Myadapter myadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonnumber_query);
        initUI();
        initData();
    }


    private void initData() {
        CommounmDao common = new CommounmDao();

        mGroplist = common.getGrou();

        myadapter = new Myadapter();
        elv_common_number.setAdapter(myadapter);

        elv_common_number.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                startCall(myadapter.getChild(groupPosition, childPosition).number);

                return false;
            }
        });

    }

    private void startCall(String phone) {
        //开启系统的打电话界面
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CommonNumberQueryActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);

        }else {
            startActivity(intent);
        }
    }

    class Myadapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return mGroplist.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mGroplist.get(groupPosition).childList.size();
        }

        @Override
        public CommounmDao.Grop getGroup(int groupPosition) {
            return mGroplist.get(groupPosition);
        }

        //组当中的孩子节点的索引
        @Override
        public CommounmDao.Child getChild(int groupPosition, int childPosition) {
            return mGroplist.get(groupPosition).childList.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
          TextView textView= new TextView(getApplicationContext());
            textView.setText("             "+getGroup(groupPosition).name);
            textView.setTextColor(Color.BLUE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
            return textView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
          View view=  View.inflate(getApplicationContext(),R.layout.elv_child_item,null);
           TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
            TextView tv_number= (TextView) view.findViewById(R.id.tv_number);
            tv_name.setText(getChild(groupPosition,childPosition).name);
            tv_number.setText(getChild(groupPosition,childPosition).number);
            return view;
        }

        //孩子节点是否响应事件
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private void initUI() {

        elv_common_number = (ExpandableListView) findViewById(R.id.elv_common_number);
    }
}
