package com.example.inseo.js_go;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by INSEO on 16. 5. 1..
 */
public class BookFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    Button book_current;
    Button book_search;
    Button book_want;
    Button book_love;
    Button book_new;
    Button book_mileage;
    public BookFragment(){
        super();
    }
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_book, container, false);
        book_current = (Button) rootView.findViewById(R.id.book_current);
        book_search = (Button) rootView.findViewById(R.id.book_search);
        book_want = (Button) rootView.findViewById(R.id.book_want);
        book_love = (Button) rootView.findViewById(R.id.book_love);
        book_new = (Button) rootView.findViewById(R.id.book_new);
        book_mileage = (Button) rootView.findViewById(R.id.book_mileage);

        book_current.setOnClickListener(this);
        book_search.setOnClickListener(this);
        book_want.setOnClickListener(this);
        book_love.setOnClickListener(this);
        book_new.setOnClickListener(this);
        book_mileage.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        android.support.v4.app.Fragment newFragment;
        if (!JSPreference.getValue("isLogin", false)) {
            Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout), "로그인을 해주세요", Snackbar.LENGTH_LONG)
                    .setAction("로그인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        } else {
            switch (id) {
                case R.id.book_current:
                    openURL("http://mlib.inha.ac.kr/my/my_book.asp?");
                    break;
                case R.id.book_search:
                    openURL("http://mlib.inha.ac.kr/search/opac_search_result.asp?");
                    break;
                case R.id.book_want:
                    openURL("http://mlib.inha.ac.kr/service/acq_newbook_search.asp");
                    break;
                case R.id.book_love:
                    openURL("http://mlib.inha.ac.kr/book/review_multiline.asp");
                    break;
                case R.id.book_new:
                    openURL("http://mlib.inha.ac.kr/book/new_book_thumb.asp");
                    break;
                case R.id.book_mileage:
                    openURL("http://mlib.inha.ac.kr/book/mileage_mall.asp");
                    break;
            }
        }
    }

    private void openURL(String targetURL) {
        Intent webIntent = new Intent(getActivity().getBaseContext(),WebActivity.class);
        webIntent.putExtra("url",targetURL);
        startActivity(webIntent);
    }
}
