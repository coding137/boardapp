package com.hello.myapp.board_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Cloud on 2017-06-06.
 */

public class DbConnection extends AsyncTask<String, Void, String> {
    ProgressDialog dialog;
    int cnt = 0;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();


//            Toast.makeText(getApplicationContext(),"i got a msg from server :"+s,Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d("onProgress update", "" + cnt++);

    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;

        try {
            String tmsg = params[0];
            String tmsg2 = params[1];
            String data = URLEncoder.encode("tmsg", "UTF-8") + "=" + URLEncoder.encode(tmsg, "UTF-8");
            data += "&" + URLEncoder.encode("tmsg2", "UTF-8") + "=" + URLEncoder.encode(tmsg2, "UTF-8");


//                String data2 = "tmsg="+testMsg+"&tmsg2="+testMsg2;
            String link = "http://172.20.10.4/" + "test.php";// 요청하는 url 설정 ex)192.168.0.1/httpOnlineTest.php
            URL url = new URL(link);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");//post방식으로 설정
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
//                httpURLConnection.setConnectTimeout(30);

            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }
            httpURLConnection.disconnect();
            return sb.toString();

        } catch (Exception e) {
            httpURLConnection.disconnect();
            return new String("Exception Occure" + e.getMessage());

        }//try catch end

    }//doInbackground end
}
