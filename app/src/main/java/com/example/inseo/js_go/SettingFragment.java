package com.example.inseo.js_go;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by INSEO on 16. 5. 1..
 */
public class SettingFragment extends android.support.v4.app.Fragment{

    public SettingFragment(){
        super();
    }
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_setting, container, false);
        Bundle args = getArguments();
        return rootView;
    }
}
