package com.example.cubestop;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Inner_hist_Adapter extends RecyclerView.Adapter<Inner_hist_Adapter.ExampleViewHolder> {
    private List<Inner_hist_item> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.product_image);
            mTextView1 = itemView.findViewById(R.id.product_name);
            mTextView2 = itemView.findViewById(R.id.product_shop);
            mTextView3=itemView.findViewById(R.id.product_credits);
        }
    }
    public Inner_hist_Adapter(List<Inner_hist_item> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Inner_hist_item currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        if(currentItem.getText3().toCharArray()[0]=='-') {
            holder.mTextView3.setTextColor(Color.RED);
        }
        holder.mTextView3.setText(currentItem.getText3());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }



}