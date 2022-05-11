package com.example.medscan.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.medscan.MyAdapter;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat_useer extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    chat_Adapter chat_Adapter;
    ArrayList<UserHelper> list;
    UserHelper userHelper;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_useer);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color5));
        }


        recyclerView=findViewById(R.id.recycle_chat);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        chat_Adapter=new chat_Adapter(this,list);
        recyclerView.setAdapter(chat_Adapter);
        progressBar=findViewById(R.id.progrsess_error);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    progressBar.setVisibility(View.VISIBLE);

                    userHelper = dataSnapshot.getValue(UserHelper.class);
                    list.add(userHelper);


                }
                progressBar.setVisibility(View.GONE);
                chat_Adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}