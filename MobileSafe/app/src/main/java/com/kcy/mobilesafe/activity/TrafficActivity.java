package com.kcy.mobilesafe.activity;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kcy.mobilesafe.R;

/**
 * Created by kcy on 2017/6/5.
 */

public class TrafficActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        //获得流量 手机下载流量
        long mobileRxBytes= TrafficStats.getMobileRxBytes();
        //手机的总流量 上传 与下载的
      long mobileTxBytes=  TrafficStats.getMobileTxBytes();

        //下载流量的总和 手机+wifi的
        long totalRxBytes= TrafficStats.getTotalRxBytes();
        //总的上传下载流量 手机+WiFi
        long totalTxBytes=TrafficStats.getTotalTxBytes();


    }
}
