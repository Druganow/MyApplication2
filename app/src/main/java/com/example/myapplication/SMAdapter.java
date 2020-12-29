package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SMAdapter extends RecyclerView.Adapter<SMAdapter.SMViewHolder> {
   ArrayList<SM> SMList;

    public SMAdapter(ArrayList<SM> SMs) {
        SMList = SMs;
    }

    @NonNull
    @Override
    public SMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sm_item, parent, false);
        return new SMAdapter.SMViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SMViewHolder holder, final int position) {
        holder.bind(SMList.get(position));
    }

    @Override
    public int getItemCount() {
        if(SMList == null) return 0;
        else return SMList.size();
    }

    class SMViewHolder extends RecyclerView.ViewHolder
    {
        TextView NameTextView;
        TextView AgeTextView;
        TextView GenderTextView;


        public SMViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTextView = (TextView) itemView.findViewById(R.id.table_name);
            AgeTextView = (TextView) itemView.findViewById(R.id.table_yahr);
            GenderTextView = (TextView) itemView.findViewById(R.id.table_gender);
        }
        public void bind(SM sm) {
            NameTextView.setText(sm.getName());
            AgeTextView.setText(sm.getAge());
            if((Integer.parseInt(sm.getGender())==0))
                GenderTextView.setText("ж");
            else
                GenderTextView.setText("м");
        }


    }


}
