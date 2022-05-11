package com.example.medscan.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;


public class chat_Adapter extends RecyclerView.Adapter<chat_Adapter.viewholder> {

    Context context;
    ArrayList<UserHelper> list;
    UserHelper user;

    public chat_Adapter(Context context, ArrayList<UserHelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_select_doctor,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        user= list.get(position);

        holder.fullname.setText(user.getFull_Name());
        holder.medical.setText(user.getMedical());
        Glide.with(context).load(user.getimage()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {

        TextView fullname,medical ;
        RoundedImageView img;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.doc_name);
            medical = itemView.findViewById(R.id.doc_medical);
            img=itemView.findViewById(R.id.imag_profile);


        }
    }
}
