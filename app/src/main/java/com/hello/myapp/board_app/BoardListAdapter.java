package com.hello.myapp.board_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cloud on 2017-06-09.
 */

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder> {

    private ArrayList<Item> mitems;

    public void setItems(ArrayList<Item> itemArrayList){
        mitems = itemArrayList;
    }

    @Override
    public BoardListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);


        BoardListAdapter.ViewHolder viewHolder =new BoardListAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BoardListAdapter.ViewHolder holder, int position) {

        Item myitem = mitems.get(position);
        holder.getmTitle().setText(myitem.topic);
        holder.getmWriter().setText(myitem.writer);
        holder.getmDate().setText(myitem.date);
   //     holder.itemView.setLayoutParams(new LinearLayout.LayoutParams());
    }

    @Override
    public int getItemCount() {
        return mitems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle;
        TextView mWriter;
        TextView mDate;

        public TextView getmTitle() {
            return mTitle;
        }

        public TextView getmWriter() {
            return mWriter;
        }

        public TextView getmDate() {
            return mDate;
        }

        public ViewHolder(View itemView){
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.t_title);
            mWriter = (TextView) itemView.findViewById(R.id.t_writer);
            mDate = (TextView) itemView.findViewById(R.id.t_date);


        }

    }
}
