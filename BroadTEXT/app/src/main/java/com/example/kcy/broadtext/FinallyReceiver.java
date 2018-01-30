package com.example.kcy.broadtext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/13.
 */

public class FinallyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String content=getResultData();
        Toast.makeText(context,"报告"+content,Toast.LENGTH_SHORT).show();
    }
}
