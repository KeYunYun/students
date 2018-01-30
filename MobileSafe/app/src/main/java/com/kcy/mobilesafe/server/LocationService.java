package com.kcy.mobilesafe.server;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.kcy.mobilesafe.util.ConstantValue;
import com.kcy.mobilesafe.util.Sputils;

/**
 * Created by kcy on 2017/5/22.
 */

public class LocationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取位置管理对象
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        //用最优的方式获取坐标
        Criteria criteria = new Criteria();
        //允许花费
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //指定获得的精确度
        String bestProver = lm.getBestProvider(criteria, true);
        //在一定时间间隔，或者一定距离获得坐标
        MyLocationListener myLocationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(bestProver, 0, 0, myLocationListener);

    }
    class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            double longitub=location.getLongitude();
            double latitude=location.getLatitude();
            Log.i("longitub", "onLocationChanged: "+longitub);
            Log.i("latitude", "onLocationChanged: "+latitude);
            SmsManager smsManager=SmsManager.getDefault();

            smsManager.sendTextMessage(Sputils.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE,""),null,"longitub:"+longitub+"latitude"+latitude,null,null);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
