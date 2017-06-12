package com.hello.myapp.board_app;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cloud on 2017-06-09.
 */

public class BoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private ArrayList<Item> mitems;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;


    public interface OnLoadMoreListener{
        void onLoadMore();
    }


    public BoardListAdapter(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener=onLoadMoreListener;
        mitems =new ArrayList<>();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager){
        this.mLinearLayoutManager=linearLayoutManager;

    }

    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mitems.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    public void addAll(List<Item> lst){
        mitems.clear();
        mitems.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<Item> lst){
        mitems.addAll(lst);
        notifyItemRangeChanged(0,mitems.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == VIEW_ITEM){
            return  new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
        }else  {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress,parent,false));
        }



    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof StudentViewHolder){
            Item myItem = mitems.get(position);
            ( (StudentViewHolder) holder).mTitle.setText(myItem.getTopic());
            ( (StudentViewHolder) holder).mWriter.setText(myItem.getWriter());
            ( (StudentViewHolder) holder).mDate.setText(myItem.getDate());


        }
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mitems.add(null);
                    notifyItemInserted(mitems.size() - 1);
                }
            });
        } else {
            mitems.remove(mitems.size() - 1);
            notifyItemRemoved(mitems.size());
        }
    }



    @Override
    public int getItemCount() {
        return mitems.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public  TextView mWriter;
        public TextView mDate;

        public StudentViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.t_title);
            mWriter = (TextView) v.findViewById(R.id.t_writer);
            mDate = (TextView) v.findViewById(R.id.t_date);
        }
    }
    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;
        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.pBar);
        }
    }
}
