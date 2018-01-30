package com.example.kcy.fragmentcommunication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by kcy on 2017/4/19.
 */

public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,null);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Fragment2 f2= (Fragment2) getActivity().getFragmentManager().findFragmentByTag("f2");
                f2.setText("haiahi");
                Toast.makeText(getActivity(),"tos",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
