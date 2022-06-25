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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;


public class chat_Adapter extends RecyclerView.Adapter<chat_Adapter.viewholder> {

    Context context;
    ArrayList<UserHelper> list;
  //  UserHelper user;
   String thelastMessage;
    public chat_Adapter(Context context, ArrayList<UserHelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_select_doctor,parent,false);
        return new chat_Adapter.viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        final UserHelper user= list.get(position);

        holder.fullname.setText(user.getFull_Name());
        holder.medical.setText(user.getMedical());
        Glide.with(context).load(user.getimage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,chat_activity.class);
                intent.putExtra("userid",user.getUserId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        public TextView fullname,medical ;
        public RoundedImageView img;
        public  TextView last_msg;


        public viewholder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.doc_name);
            medical = itemView.findViewById(R.id.doc_medical);
            img=itemView.findViewById(R.id.imag_profile);
            last_msg=itemView.findViewById(R.id.doc_lastmessage);


        }
    }

    private void lastMessage(String userid ,TextView last_msg){


        thelastMessage = "defult";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //medical.setVisibility(View.GONE);
                    chatm chat = snapshot.getValue(chatm.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                    chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid()))
                    {
                        thelastMessage = chat.getMessage();

                    }
                }

                switch (thelastMessage){
                    case "defult":
                        last_msg.setText("NO Message");
                        break;
                    default:
                        last_msg.setText(thelastMessage);
                        break;
                }
                thelastMessage = "defult";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
