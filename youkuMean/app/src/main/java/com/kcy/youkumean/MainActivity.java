package com.kcy.youkumean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import  com.kcy.youkumean.uitl.AnimationUtils;

public class MainActivity extends AppCompatActivity {
   private RelativeLayout rl_level1;
    private RelativeLayout rl_level2;
    private RelativeLayout rl_level3;
    boolean isLeveisShow1=true;
    boolean isLeveisShow2=true;
    boolean isLeveisShow3=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        rl_level1  = (RelativeLayout) findViewById(R.id.rl_level1);
        rl_level2  = (RelativeLayout) findViewById(R.id.rl_level2);
        rl_level3  = (RelativeLayout) findViewById(R.id.rl_level3);
        findViewById(R.id.ib_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AnimationUtils.runningAnimationCount>0){
                    return;
                }
                if(isLeveisShow2){
                    if(isLeveisShow3){
                        AnimationUtils.rotateOutAnim(rl_level3,0);
                        isLeveisShow3=false;
                        AnimationUtils.rotateOutAnim(rl_level2,500);
                    }else {
                        AnimationUtils.rotateOutAnim(rl_level2, 0);
                    }
                }else {
                    AnimationUtils.rotateInAnim(rl_level2,0);
                }
                isLeveisShow2=!isLeveisShow2;

            }
        });
        findViewById(R.id.ib_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AnimationUtils.runningAnimationCount>0){
                    return;
                }
                if(isLeveisShow3){
                    AnimationUtils.rotateOutAnim(rl_level3,0);
                }else {
                    AnimationUtils.rotateInAnim(rl_level3,0);
                }
                isLeveisShow3=!isLeveisShow3;
            }
        });
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(AnimationUtils.runningAnimationCount>0){
            return true;
        }
       if(keyCode==KeyEvent.KEYCODE_MENU){
           if(isLeveisShow1){
               if(isLeveisShow3){
                   AnimationUtils.rotateOutAnim(rl_level3,0);
                   isLeveisShow3=false;
                   AnimationUtils.rotateOutAnim(rl_level2,200);
                   AnimationUtils.rotateOutAnim(rl_level1,400);
               }else if(isLeveisShow2){
                   AnimationUtils.rotateOutAnim(rl_level2,0);
                   AnimationUtils.rotateOutAnim(rl_level1,200);
                   isLeveisShow2=false;
               }else {
                   AnimationUtils.rotateOutAnim(rl_level1,0);
               }
           }else{
                AnimationUtils.rotateInAnim(rl_level1,0);
               AnimationUtils.rotateInAnim(rl_level2,200);
               AnimationUtils.rotateInAnim(rl_level3,400);
               isLeveisShow2=true;
               isLeveisShow3=true;
           }
           isLeveisShow1=!isLeveisShow1;
           return true;
       }
        return super.onKeyDown(keyCode, event);
    }
}
