package com.kcy.youkumean.uitl;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by kcy on 2017/6/9.
 */

public class AnimationUtils {
    public static int runningAnimationCount=0;
    public static void rotateOutAnim(RelativeLayout relativeLayout,long delay){
        int childCount =relativeLayout.getChildCount();
        for(int i=0;i<childCount;i++){
            relativeLayout.getChildAt(i).setEnabled(false);
        }
        RotateAnimation ra=new RotateAnimation(0f,-180f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,1.0f);
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setStartOffset(delay);
        ra.setAnimationListener( new MyAnimationListener());
        relativeLayout.startAnimation(ra);
    }
    public static void rotateInAnim(RelativeLayout relativeLayout,long delay){
        int childCount =relativeLayout.getChildCount();
        for(int i=0;i<childCount;i++){
            relativeLayout.getChildAt(i).setEnabled(true);
        }
        RotateAnimation ra=new RotateAnimation(-180f,0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,1.0f);
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setStartOffset(delay);
        ra.setAnimationListener( new MyAnimationListener());
        relativeLayout.startAnimation(ra);
    }

    static class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            runningAnimationCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            runningAnimationCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}



