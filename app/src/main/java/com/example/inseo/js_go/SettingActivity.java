package com.example.inseo.js_go;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends Activity implements View.OnClickListener{
    Button setting_logOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting_logOutButton = (Button)findViewById(R.id.setting_logOutButton);
        setting_logOutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.setting_logOutButton:
                Thread logoutThread = LoginManager.getLogoutThread();
                logoutThread.start();
                MainActivity.setDrawer(true);
                try {
                    logoutThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                break;
        }
    }
}
