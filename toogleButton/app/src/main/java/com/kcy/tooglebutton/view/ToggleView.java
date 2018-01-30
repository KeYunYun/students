package com.kcy.tooglebutton.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by kcy on 2017/6/9.
 */

public class ToggleView extends View {
    private  Bitmap switchBackgroundBitmap;
    private Bitmap slideBackgroundBitmap;
    private Paint paint;
    private boolean mSwitchState=false;//开关状态
    private       float currentX;
    private boolean isTouchState=true;
    private OnSwitchStateUpdateListener onSwitchStateUpdateListener;

    /**
     * 用于代码创建
     * @param context
     */
    public ToggleView(Context context) {
        super(context);


    }

    private void initUI() {

        paint = new Paint();
    }

    /**
     * 在xml中使用，属性集合
     * @param context
     * @param attrs
     */
    public ToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        String nameSoace="http://schemas.android.com/apk/res/com.kcy.tooglebutton";

        setSwitchBackgroundRource(attrs.getAttributeResourceValue(nameSoace,"switch_background",-1));
        setSlideButtonResource(attrs.getAttributeResourceValue(nameSoace,"slide_button",-1));
        setSwitchState(attrs.getAttributeBooleanValue(nameSoace,"switch_state",false));
    }

    /**
     *  在xml中使用，指定样式
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    /**
     * 设置背景图片
     * @param switch_background
     */
    public void setSwitchBackgroundRource(int switch_background) {

        switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(),switch_background);
    }

    /**
     * 设置滑块的图片
     * @param slide_button
     */
    public void setSlideButtonResource(int slide_button) {
        slideBackgroundBitmap = BitmapFactory.decodeResource(getResources(),slide_button);
    }

    /**
     * 设置状态
     * @param b
     */
    public void setSwitchState(boolean b) {
        mSwitchState=b;
    }

    //绘制大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       setMeasuredDimension(switchBackgroundBitmap.getWidth(),switchBackgroundBitmap.getHeight());
    }


    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
       //绘制背景
        canvas.drawBitmap(switchBackgroundBitmap,0,0,paint);

        if(isTouchState){
            float newLeft = (float) (currentX-(slideBackgroundBitmap.getWidth()/2.0));
            if(newLeft<0){
                newLeft=0;
            }else if(newLeft>(newLeft = switchBackgroundBitmap.getWidth() - slideBackgroundBitmap.getWidth())){
                newLeft = switchBackgroundBitmap.getWidth() - slideBackgroundBitmap.getWidth();
            }
            canvas.drawBitmap(slideBackgroundBitmap, newLeft, 0, paint);
        }else {
            if (mSwitchState) {
                int newLeft = switchBackgroundBitmap.getWidth() - slideBackgroundBitmap.getWidth();
                canvas.drawBitmap(slideBackgroundBitmap, newLeft, 0, paint);
            } else {
                canvas.drawBitmap(slideBackgroundBitmap, 0, 0, paint);
            }
        }

        //绘制前景
    }
    //重写触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouchState=true;
                currentX =  event.getX();
                //重画
                break;
            case MotionEvent.ACTION_UP:
                isTouchState=false;
                float center= (float) (switchBackgroundBitmap.getWidth()/2.0);
                currentX=event.getX();
              boolean  state=currentX >center;

                //若果开关状态变化了，通知界面，状态更新了
                if(state!=mSwitchState&&onSwitchStateUpdateListener!=null){
                    onSwitchStateUpdateListener.onStateUpdate(state);
                    Log.i("onTouchEvent", "onTouchEvent: "+state);
                }
                mSwitchState=state;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX=event.getX();

                break;
            default:break;
        }
        invalidate();//应发 onDraw方法被调用，界面更新
        return true;//true 才能收到其他事件
    }
    //设置开关更新监听
    public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
        this.onSwitchStateUpdateListener=onSwitchStateUpdateListener;

    }

    public interface OnSwitchStateUpdateListener{
        void onStateUpdate(boolean state);
    }
}
