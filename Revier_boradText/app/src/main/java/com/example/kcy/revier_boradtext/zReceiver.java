package com.example.kcy.revier_boradtext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/13.
 */

public class zReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String content=getResultData();
        System.out.println("zzz"+content);
        Toast.makeText(context,"zzzp"+content,Toast.LENGTH_SHORT).show();
        System.out.println(content);
    }
}
