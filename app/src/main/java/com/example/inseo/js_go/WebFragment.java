package com.example.inseo.js_go;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by INSEO on 16. 5. 1..
 */
public class WebFragment extends android.support.v4.app.Fragment{
    private WebView webView;
    public WebFragment(){
        super();
    }
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_web, container, false);
        try {
            webView = (WebView) rootView.findViewById(R.id.webView);
            webView.loadUrl("http://mlib.inha.ac.kr");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());

        }catch (Exception e){
            e.toString();
        }
        return rootView;
    } //onCreateView()

    public void reloadHome() {
        if(!webView.getUrl().equals("http://mlib.inha.ac.kr"))
        webView.loadUrl("http://mlib.inha.ac.kr");
    }

}
