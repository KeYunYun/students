package com.kcy.tooglebutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kcy.tooglebutton.view.ToggleView;

public class MainActivity extends AppCompatActivity {
    private   ToggleView toggleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleview = (ToggleView) findViewById(R.id.toggleview);
  /*      toggleview.setSwitchBackgroundRource(R.drawable.switch_background);
        toggleview.setSlideButtonResource(R.drawable.slide_button);
        toggleview.setSwitchState(true);*/
        //设置开关监听
        toggleview.setOnSwitchStateUpdateListener(new ToggleView.OnSwitchStateUpdateListener() {
            @Override
            public void onStateUpdate(boolean state) {
                Toast.makeText(getApplicationContext(),state+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
