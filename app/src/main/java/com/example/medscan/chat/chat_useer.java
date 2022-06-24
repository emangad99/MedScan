package com.example.medscan.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ImageView back;
    FirebaseUser firebaseUser ;
    FirebaseAuth authProfile;
    EditText search;

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

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        search=findViewById(R.id.search);
        recyclerView=findViewById(R.id.recycle_chat);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        chat_Adapter=new chat_Adapter(this,list);
        recyclerView.setAdapter(chat_Adapter);
        progressBar=findViewById(R.id.progrsess_error);
        back=findViewById(R.id.icon_back);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    progressBar.setVisibility(View.VISIBLE);


                    userHelper = dataSnapshot.getValue(UserHelper.class);
                    assert userHelper != null;
                    assert firebaseUser !=null;
                   if( !userHelper.getMedical().equals(""))
                    {
                        if(!userHelper.getUserId().equals(firebaseUser.getUid()))
                        {
                            list.add(userHelper);
                        }

                    }



                }
                progressBar.setVisibility(View.GONE);
                chat_Adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(chat_useer.this, chat_home.class);
                startActivity(i);
            }
        });
    }
}