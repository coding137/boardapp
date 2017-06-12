package com.hello.myapp.board_app;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BoardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {
    ArrayList<Item> myItem;
    RecyclerView recyclerView;
    BoardListAdapter boardListAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


//        for(int i = 0 ; i < 30000; i ++){
//            myItem.add(new Item("my Contents no : "+i,"mywriter","myDate","myTopic no : "+i,"myTid"));
//
//        }
//        myItem.add(new Item("my Contents","mywriter","myDate","myTopic","myTid"));

        myItem = new ArrayList<>();

        View inflate = inflater.inflate(R.layout.fragment_board, container, false);
        recyclerView = (RecyclerView)inflate.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        boardListAdapter =new BoardListAdapter();

        boardListAdapter.setItems(myItem);

        recyclerView.setAdapter(boardListAdapter);
        callBindRecycleView();

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());

                if(child!=null&&gestureDetector.onTouchEvent(e)){


                    Log.d("","getChildadapterPosition.e"+rv.getChildAdapterPosition(child));

                    Intent myIntent= new Intent(getActivity(),PopupRead.class);
               startActivity(myIntent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return inflate;
    }



    public void callBindRecycleView(){
        new BindRecycleView(getActivity()).execute();
    }


    class BindRecycleView extends AsyncTask<Void, Void, String> {
        Dialog dialog;
    Activity mContext;
        public BindRecycleView(Activity activity) {
        mContext = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPost","executdddddddde");

            dialog = ProgressDialog.show(getActivity(),"Plz wait...","Loading..");
            Log.i("","mylist on pre");

        }
        @Override
        protected String doInBackground(Void... params) {

            try{


                String link="http://172.20.10.4/"+"list.php";

                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();



            }catch (Exception e){

                return  new String("Exception :  "+e.getMessage());
            }
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject =new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count =0;
                String listTopic, listName, listDate;
                String listContents, listTid;

                while (count<jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    listName=object.getString("writer");
                    listTopic=object.getString("topic");
                    listDate=object.getString("date");
                    listContents=object.getString("contents");
                    listTid = object.getString("tid");

                    Item contents =new Item(listContents, listName,listDate,listTopic,listTid);
                    myItem.add(contents);
                    count++;
                }



            }catch (Exception e){
                e.printStackTrace();
            }
            viewList();
            dialog.dismiss();



        }


    }


    public void viewList(){
        recyclerView.setAdapter(boardListAdapter);

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
