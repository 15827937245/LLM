package com.example.mycamrea.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycamrea.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] item;
    private Context context;
    private List<MyViewHolder> myViewHolderList = new ArrayList<>();

    public MyAdapter(String[] item, Context context) {
        this.context = context;
        this.item = item;
    }

    public void setItem(String[] item) {
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                holder.textView.setText(item[position]);
                if (position==0){
                    holder.textView.setTextColor(Color.GREEN);
                }
        myViewHolderList.add(holder);
        holder.textView.setOnClickListener((view)->{
            onClickItemListener.onClickItem(holder,position);
        });
    }
    public onClickItemListener onClickItemListener;

    public MyViewHolder getMyViewHolder(int position) {
        return myViewHolderList.get(position);
    }


    public void setOnClickItemListener(MyAdapter.onClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface  onClickItemListener{
       void onClickItem(MyViewHolder holder,int position);
    }

    @Override
    public int getItemCount() {
        return item.length;
    }


   public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                textView = itemView.findViewById(R.id.item_text);
        }
    }

}
