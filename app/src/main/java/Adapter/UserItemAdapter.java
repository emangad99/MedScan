package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.medscan.R;
import com.example.medscan.login.User;


import java.util.List;

public class UserItemAdapter  extends RecyclerView.Adapter<UserItemAdapter.ViewHolder> {

     Context mcontext;
     List<User> mUsers;

    public UserItemAdapter(Context mcontext, List<User> mUsers){

        this.mcontext = mcontext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_select_doctor, parent,false);
        return new UserItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = mUsers.get(position);
        holder.doc_name.setText(user.getDoc_name());
        if(user.getImageURl().equals("defult")){

            holder.imag_profile.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mcontext).load(user.getImageURl()).into(holder.imag_profile);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView doc_name;
        public ImageView imag_profile;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doc_name = itemView.findViewById(R.id.doc_name);
            imag_profile = itemView.findViewById(R.id.imag_profile);


        }
    }
}
