package com.example.inseo.js_go;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.htmlparser.jericho.Source;

/**
 * Created by INSEO on 2015. 11. 25..
 */
/*
    DATA LIST

    String eid
    String epwd
    boolean isLogin
    String id

 */
public class JSPreference {
    //data list
    public static final String eid = "eid"; //암호화된 학번
    public static final String epwd = "epwd"; //암호화된 비밀번호
    public static final String id = "id"; //학번
    public static final String idCount = "idCount"; //재발급횟수(로 추정 ...) -> 삭제
                                                    // -> 모바일 학생증 바코드 생성에 필요
                                                    //format : 학번+idCount
    public static final String name = "name"; //이름
    public static final String dept = "dept"; //학과
    public static final String email = "email"; //이메일
    public static final String inhaid = "inhaid"; //뭔지 모르겠음... 일단저장
    //setting list
    public static final String isLogin = "isLogin"; //로그인여부



    static final String PREF_NAME = "JSPreference";

    static Context mContext;

    static public void setContext(Context c){
        mContext=c;
    }

    static public void put(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.commit();
    }

    static public void put(String key, int value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    static public void put(String key,boolean value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key,value);
        editor.commit();
    }

    static public String getValue(String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    static public int getValue(String key, int dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    static public boolean getValue(String key, boolean dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    static public void clearAll() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_NAME,Activity.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }



}
