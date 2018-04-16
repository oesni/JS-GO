package com.example.inseo.js_go;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {



    Button loginButton = null;
    TextInputEditText login_id = null;
    TextInputEditText login_pswd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = (TextInputEditText) findViewById(R.id.login_id);
        login_pswd = (TextInputEditText) findViewById(R.id.login_pswd);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String id = login_id.getText().toString();
        String pwd = login_pswd.getText().toString();

        Thread loginThread = LoginManager.getLoginThread(id,pwd,false);
        loginThread.start();
        try {
            loginThread.join();
        } catch (InterruptedException e) {
            Log.d("LoginActivity","error");
            e.printStackTrace();
        }
        if(JSPreference.getValue(JSPreference.isLogin,false)) {
            ///
            JSPreference.put(JSPreference.id,id);
            MainActivity.setDrawer(false);
            ///
        }

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        finish();
//        startActivity(intent);
    }

//    http://mlib.inha.ac.kr/my/my_book.asp?




}//Activity

//    function encode64(input) {
//        var output = "";
//        var chr1, chr2, chr3;
//        var enc1, enc2, enc3, enc4;
//        var i = 0;
//
//        do {
//            chr1 = input.charCodeAt(i++);
//            chr2 = input.charCodeAt(i++);
//            chr3 = input.charCodeAt(i++);
//
//            enc1 = chr1 >> 2;
//            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
//            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
//            enc4 = chr3 & 63;
//
//            if (isNaN(chr2)) {
//                enc3 = enc4 = 64;
//            } else if (isNaN(chr3)) {
//                enc4 = 64;
//            }
//
//            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) +
//                    keyStr.charAt(enc3) + keyStr.charAt(enc4);
//        }
//        while (i < input.length);
//
//        return output;
//    }

//https://mlib.inha.ac.kr/login_p.asp
