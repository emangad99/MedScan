package com.example.medscan.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.medscan.MyAdapter;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.example.medscan.login.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.UserItemAdapter;
import okio.Options;

public class chat_user extends AppCompatActivity {
    ProgressBar progressBar;
    TextView texterror;
    ImageView icon_back;
   // DatabaseReference databaseReference;
     RecyclerView recyclerView;
     UserItemAdapter userItemAdapter;
     List<UserHelper> mUserHelper;
     UserHelper userHelper;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color5));
        }
            progressBar = findViewById(R.id.progrsess_error);
            texterror=findViewById(R.id.texterrormessage);
            icon_back=findViewById(R.id.icon_back);
            recyclerView = findViewById(R.id.recycle_chat);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mUserHelper = new ArrayList<>();

        ReadDoctors();





      /*  databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:  snapshot.getChildren()){
                    userHelper = dataSnapshot.getValue(UserHelper.class);
                    mUserHelper.add(userHelper);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/





        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat=new Intent(chat_user.this, chat_home.class);
                startActivity(chat);
            }
        });
    }

    private void ReadDoctors() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUserHelper.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserHelper user = dataSnapshot.getValue(UserHelper.class);
                    mUserHelper.add(user);
                }

                userItemAdapter = new UserItemAdapter(this,mUserHelper);
                recyclerView.setAdapter(userItemAdapter);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

