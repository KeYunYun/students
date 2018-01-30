package com.example.kcy.baidumusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kcy on 2017/4/16.
 */

public class MusicService extends Service {
    MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Mybinder();
        //返回Binder
    }

    @Override
    public void onCreate() {
        //初始化mediapler
         player =new MediaPlayer();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //用来播放音乐的
    public void playMusic(){
        System.out.println("音乐播放");
        try{
            player.reset();
            player.setDataSource("/mnt/sdcard/xpg.mp3");
            player.prepare();
            player.start();
            //更新进度条
            updatteSeekBar();

        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO
    }

    private void updatteSeekBar() {
        //获得当前歌曲的总长度
        final int duration=player.getDuration();
        //获得当前进度 一秒获得一次进度
        Timer timer =new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                int currentPosition =player.getCurrentPosition();
                Message msg=Message.obtain();
                //使用msg携带多个数据
                Bundle bundle=new Bundle();
                bundle.putInt("duration",duration);
                bundle.putInt("currentPostion",currentPosition);
                msg.setData(bundle);

                MainActivity.handler.sendMessage(msg);
            }
        };
        timer.schedule(task,300,1000);

    }
    public void seekToPosition(int position){
        player.seekTo(position);
    }
    public void pauseMusic(){
        //TODO
        System.out.println("音乐暂停");
        player.pause();
    }
    public void rePlayMusic(){
        //TODO
        System.out.println("音乐继续播放");
        player.start();
    }
    //定义一个IBinder 继承Binder
    private class Mybinder extends Binder implements Iservice{

        @Override
        public void CallPlaymusic() {
            playMusic();
        }

        @Override
        public void CallPausemusic() {
            pauseMusic();
        }

        @Override
        public void CallRePlaymusic() {
            rePlayMusic();
        }

        @Override
        public void callToPlay(int potion) {
            seekToPosition(potion);
        }
    }
}
