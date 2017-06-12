package com.hello.myapp.board_app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean isMenubarOpen=false;
    ImageButton pencilButton;
    ImageButton settingButton;
    ImageView boardButton;
    FrameLayout frageLayout;
    ConstraintLayout menubar;
    InputMethodManager imm;
    ImageButton navbtn_right;
    FloatingActionButton mActionBtn;

    private Handler handler;
    ConstraintLayout.LayoutParams params ;
    private List<Item> mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setup();
    }




    void setup() {

        handler = new Handler();

        Snackbar.make(findViewById(R.id.board_main), "Rabbit blog",Snackbar.LENGTH_LONG).show();
        mActionBtn =(FloatingActionButton) findViewById(R.id.floatingActionButton);
        navbtn_right = (ImageButton) findViewById(R.id.navbarhidebtn);
        menubar = (ConstraintLayout) findViewById(R.id.constraintLayout);
//        menubar.setAlpha((float)0.8);
        pencilButton = (ImageButton) findViewById(R.id.pencil);
        settingButton = (ImageButton) findViewById(R.id.setup);
        boardButton = (ImageView) findViewById(R.id.boardlist);
        frageLayout = (FrameLayout) findViewById(R.id.frag);
        frageLayout.setAlpha((float) 0.8);
        boardButton.setOnClickListener(btnlistenr);
        pencilButton.setOnClickListener(btnlistenr);
        settingButton.setOnClickListener(btnlistenr);
        mActionBtn.setOnClickListener(btnlistenr);
        navbtn_right.setOnClickListener(btnlistenr);
        boardButton.setSelected(true);

        // boardClicked();
        final SoftKeyboardDectectorView softKeyboardDectectorView = new SoftKeyboardDectectorView(this);
        addContentView(softKeyboardDectectorView, new FrameLayout.LayoutParams(-1, -1));
        softKeyboardDectectorView.setOnShownKeyboard(new SoftKeyboardDectectorView.OnShownKeyboardListener() {
            @Override
            public void onShowSoftKeyboard() {

                mActionBtn.hide();
                menubar.setVisibility(View.GONE);
            }
        });
        softKeyboardDectectorView.setOnHiddenKeyboard(new SoftKeyboardDectectorView.OnHiddenKeyboardListener() {
            @Override
            public void onHiddenSoftKeyboard() {
                mActionBtn.show();
          menubar.setVisibility(View.GONE);
      //          keyboardDown();
            }
        });

    }

    public  void  setActionBtnClicked(){
        mActionBtn.hide();
        menubar.setVisibility(View.VISIBLE);
        Animation anim  = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tran_left);
        menubar.startAnimation(anim);
        handler.postDelayed(runnable, 5000);
    }
    public  void setNavbtn_rightClicked(){
        mActionBtn.show();
        menubar.setVisibility(View.GONE);

        Animation anima = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tran_right);
        menubar.startAnimation(anima);
        handler.removeMessages(0);
    }

    public void zebal(){
        mActionBtn.hide();
        menubar.setVisibility(View.GONE);
    }
    public void keyboardUp(){
        mActionBtn.hide();
        menubar.setVisibility(View.GONE);

        Animation anima = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tran_right);
        menubar.startAnimation(anima);
    }
    public  void  keyboardDown(){
        mActionBtn.show();
    }
    void  show_actionbar(){

    }
    void  hide_actionbar(){

    }
    void hideKeyboard() {
//        imm.hideSoftInputFromInputMethod();
    }

    public void boardClicked() {
        BoardFragment boardFragment = new BoardFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.layout_leftin, R.animator.layout_leftout, R.animator.layout_leftin, R.animator.layout_leftout);
        fragmentTransaction.replace(R.id.frag, boardFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void writeClicked() {
        Writepage writepage = new Writepage();
        //menubar.setVisibility(View.GONE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.layout_leftin, R.animator.layout_leftout, R.animator.layout_leftin, R.animator.layout_leftout);

        fragmentTransaction.replace(R.id.frag, writepage);
        fragmentTransaction.setCustomAnimations(R.animator.layout_leftin, R.animator.layout_leftout);

//        fragmentTransaction.setCustomAnimations(R.anim.layout_leftin,)
        fragmentTransaction.commit();
    }

    public void settingClicked() {
        SettingFragment settingFragment = new SettingFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.layout_leftin, R.animator.layout_leftout, R.animator.layout_leftin, R.animator.layout_leftout);

        fragmentTransaction.replace(R.id.frag, settingFragment);
        fragmentTransaction.setCustomAnimations(R.animator.layout_leftin, R.animator.layout_leftout);


        fragmentTransaction.commit();
    }


    Runnable runnable =new Runnable() {
        @Override
        public void run() {
            setNavbtn_rightClicked();
        }
    };

    View.OnClickListener btnlistenr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pencilButton.setSelected(false);
            settingButton.setSelected(false);
            boardButton.setSelected(false);
            navbtn_right.setSelected(false);
            mActionBtn.setSelected(false);



            switch (v.getId()) {
                case R.id.boardlist:
                    boardButton.setSelected(true);
                    boardClicked();
                    setNavbtn_rightClicked();
                    break;
                case R.id.pencil:
                    pencilButton.setSelected(true);
                    writeClicked();
                    setNavbtn_rightClicked();
                    break;
                case R.id.setup:
                    settingButton.setSelected(true);
                    settingClicked();
                    setNavbtn_rightClicked();
                    break;
                case  R.id.floatingActionButton:
                    mActionBtn.setSelected(true);
                    setActionBtnClicked();
                    break;
                case  R.id.navbarhidebtn:
                    navbtn_right.setSelected(true);
                    setNavbtn_rightClicked();
                    break;

            }
        }
    };


    class ConnecttoBoardList extends AsyncTask<String, Void, String>{
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
            try {
                JSONObject jsonObject =new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count =0;
                String listTopic, listName, listDate;
                String listContents;
                String tid;
                while (count<jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    listName=object.getString("writer");
                    listTopic=object.getString("topic");
                    listDate=object.getString("date");
                    listContents=object.getString("contents");
                    tid = object.getString("tid");

                    Item contents =new Item(listContents, listName,listDate,listTopic,tid);
                    mylist.add(contents);
                    count++;
                }



            }catch (Exception e){
                e.printStackTrace();
            }



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
                String link = "http://172.20.10.4/" + "list.php";// 요청하는 url 설정 ex)192.168.0.1/httpOnlineTest.php
                URL url = new URL(link);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");//post방식으로 설정
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
               httpURLConnection.setConnectTimeout(300000);

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

}
