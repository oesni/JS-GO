package com.example.inseo.js_go;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by INSEO on 16. 5. 4..
 */
public class SeatFragment extends android.support.v4.app.Fragment {

    public SeatFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_seat, container, false);
        return rootView;
    }

}
