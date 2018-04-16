package com.example.inseo.js_go;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

/**
 * Created by INSEO on 16. 5. 20..
 */
public class LoginManager {

    public static String loginURL = "https://mlib.inha.ac.kr/login_p.asp";

    public static Thread getLoginThread(final String id, final String pwd ,final boolean isLogin) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(loginURL);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

                    //SSL
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null,null,null);
                    connection.setSSLSocketFactory(context.getSocketFactory());


                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);

                    //encode id,pwd
                    String id_e,pwd_e;
                    if(!isLogin) {
                        id_e = Base64.encodeToString(id.getBytes(), Base64.NO_WRAP);
                        pwd_e = Base64.encodeToString(pwd.getBytes(), Base64.NO_WRAP);
                    } else {
                        id_e = id;
                        pwd_e = pwd;
                    }
                    /*
                     encoded url에 new line
                     Base64기본옵션 => newline 추가
                      해결 ㅠㅠ
                     */

                    Log.d("Base64 encoded",id_e+pwd_e);
//                    Log.d("pwd_e",pwd_e);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("id_e",id_e)
                            .appendQueryParameter("pwd_e",pwd_e);

                            ///hard-coded test
//                            .appendQueryParameter("id_e","MTIxNTE0MDQ=")//MTIxNTE0MDQ=
//                            .appendQueryParameter("pwd_e","SW5zZW8wODIzQEA=");//SW5zZW8wODIzQEA=
                            ///

                    String query = builder.build().getEncodedQuery();
                    query.replaceAll("%0A","");
//                    String query = "id_e="+id_e+"&pwd_e="+pwd_e; /*!!!*/
                    Log.d("query",query);
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();

                    connection.connect();
                    connection.setInstanceFollowRedirects(true);

                    //Cookie
                    String COOKIES_HEADER = "Set-Cookie";
                    String inhauser = null;
                    Map<String, List<String>> headerFields = connection.getHeaderFields();
                    List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
                    if(cookiesHeader != null) {
                        for(String cookie: cookiesHeader) {
                            String cookieName = HttpCookie.parse(cookie).get(0).getName();
                            String cookieValue = HttpCookie.parse(cookie).get(0).getValue();
                            String cookieString = cookieName + "=" + cookieValue;
                            Log.d("Cookie",cookieString);
                            android.webkit.CookieManager.getInstance().setCookie("http://mlib.inha.ac.kr",cookieString);

                            if(cookieName.equals("inhauser"))
                                inhauser=cookieString;
                        }
                    }

                    if(inhauser != null) {
                        //login success
                        String decoded =null;
                        decoded = URLDecoder.decode(inhauser,"UTF-8");
                        String[] values = decoded.split("&");
                        String[] nameValues = values[0].split("=");
                        String[] deptValues = values[1].split("=");
                        String[] emailValues = values[2].split("=");
                        String[] inhaidValues = values[3].split("=");
                        String name = nameValues[2];
                        String dept = deptValues[1];
                        String email = emailValues[1];
                        String inhaid = inhaidValues[1];
                        JSPreference.put(JSPreference.name,name);
                        JSPreference.put(JSPreference.dept,dept);
                        JSPreference.put(JSPreference.email,email);
                        JSPreference.put(JSPreference.inhaid,inhaid);
                        JSPreference.put(JSPreference.isLogin, true);
                        Log.d("saved data",name+" "+dept+" "+email);
                        Log.d("Decoded",decoded);
                        Log.d("Log in Success", "Log in Success");
                    } else {
                        //inhauser -> null
                        //login failed
                        Log.d("login","failed");
                    }

                    //Cookie

                    InputStream in = connection.getInputStream();
                    //학번파싱 => 삭제
//                    LoginManager.printInputStream(in);
//                    String line =
//                    Document document = Jsoup.parse(in, "UTF-8", "https://mlib.inha.ac.kr/login_p.asp");
//                    Elements element = document.getElementsByTag("input");
//                    String row_id ="";
//                    row_id = element.attr("value");
//                    Log.d("login",row_id);
//                    String id = "";
//                    String count = "";
//                    String[] results = row_id.split(";");
//                    id = results[0];
//                    count = results[1];

//                    JSPreference.put(JSPreference.id, id);
//                    JSPreference.put(JSPreference.idCount, count);
                    //삭제예정




                }catch (Exception e){
                    JSPreference.put(JSPreference.isLogin,false);
                    Log.d("error","loginError");
                    e.printStackTrace();
                }finally {
                    Log.d("server","login end");
                }

//                try{
//                    String book = "http://mlib.inha.ac.kr/my/my_book.asp?";
//                    URL urlBook = new URL(book);
//                    HttpURLConnection bookConnection= (HttpURLConnection)urlBook.openConnection();
//                    bookConnection.connect();
//                    InputStream in = bookConnection.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    String line = null;
//                    while ((line = reader.readLine()) != null) {
//                        Log.d("server",line);
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }//run
        });  //Thread
        return thread;
    }//getLoginThread

    public static Thread getLogoutThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://mlib.inha.ac.kr/logout.asp");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ( (line = reader.readLine()) != null) {
                        Log.d("log out",line);
                    }

                    //delete Cookies
                    clearCookies();

                    //delete info(preference)
                    JSPreference.clearAll();

                    //set DrawerHeader default
//                    MainActivity.setDrawerHeader(true);




                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return thread;
    } //get

    private static String encode64(String input) {
        //Base64.encode()
        String keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        String output="";
        char chr1,chr2,chr3;
        int enc1,enc2,enc3,enc4;
        int temp;
        int i = 0;
        do {
            chr1 = input.charAt(i++);
            chr2 = input.charAt(i++);
            chr3 = input.charAt(i++);

            enc1 = /*(char)*/ (chr1 >> 2);//(char)temp;
            enc2 = /*(char)*/ (((chr1 & 3) << 4) | (chr2 >> 4));
            enc3 = /*(char)*/ (((chr2 & 15) << 2) | (chr3 >>6));
            enc4 = /*(char)*/ (chr3 & 64);
            if(isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) + keyStr.charAt(enc4);
        }while (i<input.length());
        return output;
    }

    private static boolean isNaN(char chr) {
        try {
            Integer.parseInt(""+chr);
            return false;
        }catch (Exception e) {
            return true;
        }
    }



    @SuppressWarnings("deprecation")
    public static void clearCookies()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            Log.d(C.TAG, "Using clearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
//            Log.d(C.TAG, "Using clearCookies code for API <" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(MainActivity.getApplicationContextFromMain());
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    private static void printInputStream(InputStream in)  {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        try {
            while((line = reader.readLine()) != null) {
                Log.d("printFromWriter",line);
            }
        } catch (IOException e) {
            Log.d("printFromWriter","IOException");
        }
    }

}//LoginManager
