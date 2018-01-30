package com.example.kcy.fragmentcommunication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kcy on 2017/4/19.
 */

public class Fragment2 extends Fragment {
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,null);
        textView= (TextView) view.findViewById(R.id.textView);
        return view;
    }
    public void setText(String content){
        textView.setText(content);
    }
}
