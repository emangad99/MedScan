package com.example.medscan.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.viewholder> {
    public static  final int MSG_RECEIVE =0;
    public static  final  int MSG_SENT = 1;
    Context context;
    ArrayList<UserHelper> list;
    UserHelper user;
    String image;
    FirebaseUser fUser;

    public MessageAdapter(Context context, ArrayList<UserHelper> list , String image) {


        this.context = context;
        this.list = list;
        this.image = image;
    }

    @NonNull
    @Override
    public MessageAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_SENT) {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_send_message, parent, false);
            return new MessageAdapter.viewholder(v);
        }else {
            View v = LayoutInflater.from(context).inflate(R.layout.chat_item_recieved_message, parent, false);
            return new MessageAdapter.viewholder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull   MessageAdapter.viewholder holder, int position) {
        UserHelper  user =list.get(position);
        holder.show_message.setText(user.getMessage());
        /*if(!image.equals("default")){
            holder.img.setBackgroundResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(image).into(holder.img);
        }

         */



        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {

        TextView show_message ;
        RoundedImageView img;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.textmessage);
         //   medical = itemView.findViewById(R.id.doc_medical);
            img=itemView.findViewById(R.id.chat_img);


        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(list.get(position).getSender().equals(fUser.getUid())){
            return MSG_SENT;
        }else   return  MSG_RECEIVE;
    }
}

