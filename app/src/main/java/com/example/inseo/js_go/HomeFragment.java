package com.example.inseo.js_go;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by INSEO on 16. 5. 1..
 */
public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    public HomeFragment() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new RecyclerAdapter();
        ArrayList<String> strList = new ArrayList<>();
        strList.add("로그인을 해주세요.");
        adapter.addItem(new RecyclerAdapter.DataSet(RecyclerAdapter.TypeDefault,strList));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDataFromServer();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    public void reload() {
        adapter.notifyDataSetChanged();
    }
    public void addData(RecyclerAdapter.DataSet dataSet) {
        adapter.addItem(dataSet);
    }
    public void deleteData(int index) {
        adapter.deleteItem(index);
    }
    public void loadDataFromServer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(JSPreference.getValue(JSPreference.isLogin,false)) {
                    Document doc = null;
                    try {
                        URL url = new URL("http://mlib.inha.ac.kr/my/my_book.asp");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        String cookieString = CookieManager.getInstance().getCookie("http://mlib.inha.ac.kr");
                        Log.d("cookieString",cookieString);
                        connection.setRequestProperty("Cookie",cookieString);
                        connection.connect();
                        InputStream in = connection.getInputStream();
//                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                                String line = null;
//                                while ((line = reader.readLine()) != null) {
//                                    Log.d("server", line);
//                                }
                        Document document = Jsoup.parse(in,"UTF-8","http://mlib.inha.ac.kr/my/my_book.asp");
                        Elements liTags = document.getElementsByTag("li");
                        ArrayList<String> bookList = new ArrayList<>();
                        for(int i=4;i<liTags.size();i++) {
                            String name = liTags.eq(i).select("span.if").text();
                            String info = liTags.eq(i).select("span.ho").text();
                            name = name.substring(2,name.length()-2)+"\n";
                            info = info.replace(" 대","\n대");
                            String bookString = name + info;
                            bookString = bookString.replace("  ","");
                            bookList.add(bookString);
                            Log.d("book",bookString+"|");
                        }
                        ArrayList<ArrayList<String>> bookLists = new ArrayList<>();
                        for(String str : bookList) {
                            String[] tmp = str.split("\n");
                            ArrayList<String> tmpList = new ArrayList<>();
                            for(String tmpp : tmp) {
                                tmpList.add(tmpp);
                            }
                            bookLists.add(tmpList);
                        }
                        final ArrayList<RecyclerAdapter.DataSet> dataList = new ArrayList<>();
                        for(ArrayList<String> tmpList : bookLists) {
                            RecyclerAdapter.DataSet data = new RecyclerAdapter.DataSet(RecyclerAdapter.TypeBookCard,tmpList);
                            dataList.add(data);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final ArrayList<RecyclerAdapter.DataSet> finalDataList = dataList;
                                adapter.reLoad(finalDataList);
                            }
                        });



                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread.start();
    }
}
