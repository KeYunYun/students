package com.kcy.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kcy.mobilesafe.db.domain.ProcessInfo;
import com.kcy.mobilesafe.engine.ProcessInfoProvider;

/**
 * Created by kcy on 2017/6/3.
 */

public class KillProcessReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //杀死进程
        ProcessInfoProvider.killAllProcess(context);

    }
}
