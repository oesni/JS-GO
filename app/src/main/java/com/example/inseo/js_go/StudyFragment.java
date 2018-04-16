package com.example.inseo.js_go;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by INSEO on 16. 5. 1..
 */
public class StudyFragment extends android.support.v4.app.Fragment implements OnClickListener {
    private Button study_seat;
    private Button study_reservedSeat;
    private Button study_current;
    private Button study_reserve;
    private Button study_check;
    private Button study_priorSeat;

    FragmentTransaction fragmentTransaction;


    public StudyFragment(){
        super();
    }
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_study, container, false);

        study_seat = (Button) rootView.findViewById(R.id.study_seat);
        study_reservedSeat = (Button)rootView.findViewById(R.id.study_reservedSeat);
        study_current = (Button)rootView.findViewById(R.id.study_current);
        study_reserve = (Button)rootView.findViewById(R.id.study_reserve);
        study_check = (Button)rootView.findViewById(R.id.study_check);
        study_priorSeat = (Button)rootView.findViewById(R.id.study_priorSeat);

        study_seat.setOnClickListener(this);
        study_reservedSeat.setOnClickListener(this);
        study_current.setOnClickListener(this);
        study_reserve.setOnClickListener(this);
        study_check.setOnClickListener(this);
        study_priorSeat.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        android.support.v4.app.Fragment newFragment;
        if (!JSPreference.getValue("isLogin", false)) {
            Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout), "로그인을 해주세요", Snackbar.LENGTH_LONG)
                    .setAction("로그인", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        } else {
            switch (id) {
                case R.id.study_seat:
                    openURL("http://mlib.inha.ac.kr/issuing/res_seat_g2_new.asp");
                    break;
                case R.id.study_reservedSeat:
                    openURL("http://mlib.inha.ac.kr/issuing/res_booking_val.asp");
                    break;
                case R.id.study_current:
                    openURL("http://mlib.inha.ac.kr/issuing/res_use.asp");
                    break;
                case R.id.study_reserve:
                    openURL("http://mlib.inha.ac.kr/my/res_seat_g2.asp");
                    break;
                case R.id.study_check:
                    openURL("http://mlib.inha.ac.kr/my/res_seat_g_avail.asp");
                    break;
                case R.id.study_priorSeat:
                    openURL("http://mlib.inha.ac.kr/my/mileage_t.asp");
                    break;
//
            }
        }
    }

    private void openURL(String targetURL) {
        Intent webIntent = new Intent(getActivity().getBaseContext(),WebActivity.class);
        webIntent.putExtra("url",targetURL);
        startActivity(webIntent);
    }
}//activity
