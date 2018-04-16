package com.example.inseo.js_go;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by INSEO on 16. 5. 3..
 */
public class FPagerAdapter extends FragmentPagerAdapter {

    public FPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new StudyFragment();
                break;
            case 2:
                fragment = new BookFragment();
                break;
            case 3:
                fragment = new WebFragment();
                break;
//            case 4:
//                fragment = new SettingFragment();
//                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch(position) {
            case 0:
                title = "홈화면";
                break;
            case 1:
                title = "열람실";
                break;
            case 2:
                title = "도서";
                break;
            case 3:
                title = "모바일 웹";
                break;
        }
        return title;
    }


    @Override
    public int getCount() {
        return 4;
    }


}
