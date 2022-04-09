package com.example.medscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myviewholder> {

    Context context;
    ArrayList<UserHelper> list;
    UserHelper user;

    public MyAdapter(Context context, ArrayList<UserHelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_doctors,parent,false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        user= list.get(position);

        holder.fullname.setText(user.getFull_Name());
        holder.medical.setText(user.getMedical());
        holder.address.setText(user.getAddress());
        holder.phone.setText(user.getPhone());
        holder.time.setText(user.getTime());
        holder.info.setText(user.getOther());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder {

        TextView fullname,medical, address,phone,time,info;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            fullname=itemView.findViewById(R.id.tv_name);
            medical=itemView.findViewById(R.id.tv_medical);
            address=itemView.findViewById(R.id.tv_address);
            phone=itemView.findViewById(R.id.tv_phone);
            time=itemView.findViewById(R.id.tv_time);
            info=itemView.findViewById(R.id.tv_inf);

        }
    }
}
