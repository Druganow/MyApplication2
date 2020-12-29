package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AGAdapter extends RecyclerView.Adapter<AGAdapter.AGViewHolder> {
    private ArrayList<AG> AGList;

    public AGAdapter(ArrayList<AG> AGs) {
        AGList = AGs;
    }



    @NonNull
    @Override
    public AGViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ag_item, parent, false);
        return new AGAdapter.AGViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AGViewHolder holder, final int position) {
        holder.bind(AGList.get(position));

    }

    @Override
    public int getItemCount() {
        if(AGList == null) return 0;
        else return AGList.size();
    }

    class AGViewHolder extends RecyclerView.ViewHolder
    {
        TextView Yahr1TextView;
        TextView Yahr2TextView;
        TextView GenderTextView;


        public AGViewHolder(@NonNull View itemView) {
            super(itemView);
            Yahr1TextView = (TextView) itemView.findViewById(R.id.table_yahr1);
            Yahr2TextView = (TextView) itemView.findViewById(R.id.table_yahr2);
            GenderTextView = (TextView) itemView.findViewById(R.id.table_gender1);
        }
        public void bind(AG sm) {
            Yahr1TextView.setText(sm.getYahr1());
            Yahr2TextView.setText(sm.getYahr2());
            if((Integer.parseInt(sm.getGender())==0))
                GenderTextView.setText("ж");
            else
                GenderTextView.setText("м");
        }


    }


}