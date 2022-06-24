package com.example.medscan.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class chat_home extends AppCompatActivity {

    TextView name;
    String _NAME , photo;
    ProgressBar progressBar;
    //StorageReference mstorageReference;
    RoundedImageView img;
    RecyclerView recyclerView;
    chat_Adapter chat_adapter2;
    ArrayList<UserHelper>list;
    FirebaseAuth authProfile;
    FirebaseUser firebaseUser ;
    DatabaseReference databaseReference;
    List<chatlist>userlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);

        recyclerView=findViewById(R.id.conversation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        name=findViewById(R.id.text_name);
        progressBar=findViewById(R.id.progress_bar);
        img=findViewById(R.id.img_prof);
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        userlist = new ArrayList<>();
       databaseReference = FirebaseDatabase.getInstance().getReference("chatlist").child(firebaseUser.getUid());
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               progressBar.setVisibility(View.VISIBLE);
               userlist.clear();
               for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   chatlist chatlist = snapshot.getValue(chatlist.class);
                   userlist.add(chatlist);
               }

               Chatlist();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


        final FloatingActionButton button = findViewById(R.id.chat_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat=new Intent(chat_home.this, chat_useer.class);
                startActivity(chat);

            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color5));

        }


    }


    private void Chatlist(){
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // progressBar.setVisibility(View.VISIBLE);
                list.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserHelper user = snapshot.getValue(UserHelper.class);
                    for (chatlist chatllist :userlist){
                        if(user.getUserId().equals(chatllist.getId())){
                            list.add(user);
                        }
                    }
                }
                progressBar.setVisibility(View.GONE);
                chat_adapter2 = new chat_Adapter(chat_home.this,list);
                recyclerView.setAdapter(chat_adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    photo=snapshot.child("image").getValue().toString();
                    _NAME=snapshot.child("Full_Name").getValue().toString();


                    Picasso.get().load(photo).into(img);
                    name.setText(_NAME);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chat_home.this, "sorry", Toast.LENGTH_SHORT).show();

            }
        });
    }





    public void onBackPressed() {
        Intent donor=new Intent(chat_home.this, HomeActivity.class);
        startActivity(donor);
    }
}