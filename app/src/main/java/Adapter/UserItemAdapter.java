package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.example.medscan.chat.MessageActivity;


import java.util.List;

public class UserItemAdapter  extends RecyclerView.Adapter<UserItemAdapter.ViewHolder> {

     Context context;
     List<UserHelper> mUserHelper;

    public UserItemAdapter(Context context, List<UserHelper> mUserHelper){

        this.context = context;
        this.mUserHelper = mUserHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_doctor, parent,false);
        return new UserItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserHelper user = mUserHelper.get(position);
        holder.doc_name.setText(user.getFull_Name());
        holder.doc_medical.setText(user.getMedical());
        if(user.getImageURI().equals("defult")){

            holder.imag_profile.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(user.getImageURI()).into(holder.imag_profile);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserHelper.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView doc_name;
        public  TextView doc_medical;
        public ImageView imag_profile;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doc_name = itemView.findViewById(R.id.doc_name);
            imag_profile = itemView.findViewById(R.id.imag_profile);
            doc_medical = itemView.findViewById(R.id.doc_medical);


        }
    }
}
