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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.example.medscan.menu_doctors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class chat_activity extends AppCompatActivity {
    TextView username,lastseen, lasttime;
    FirebaseUser fuser;
    DatabaseReference reference;
    ImageView btn , back , profile_image;
    EditText edit;
    MessageAdapter messageAdapter;
    ArrayList<chatm> list;
    RecyclerView recyclerView;
    Intent intent;

    DatabaseReference databaseReference;
    FirebaseAuth authProfile;
    FirebaseUser firebaseUser ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color5));
        }

        lastseen=findViewById(R.id.lastseen);
        lasttime=findViewById(R.id.lasttime);

        recyclerView = findViewById(R.id.chatRecycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        back=findViewById(R.id.img_back2);
        username = findViewById(R.id.username);
        profile_image = findViewById(R.id.img_prof2);
        btn = findViewById(R.id.btnsend);
        edit = findViewById(R.id.edit_send);
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper userHelper = snapshot.getValue(UserHelper.class);
                upgradestates("Online");
                username.setText(userHelper.getFull_Name());
                if (userHelper.getimage().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(chat_activity.this).load(userHelper.getimage()).into(profile_image);
                }

               // lastseen.setText(userHelper.getType());
                if(userHelper.getType().equals("Offline"))
                {
                    lastseen.setText(" last seen "+ userHelper.getCurrentTime());
                    lasttime.setText(userHelper.getCurrentdate());

                }else
                {
                    lastseen.setText(userHelper.getType());
                }

                readMessage(fuser.getUid(), userid , userHelper.getimage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(chat_activity.this, chat_home.class);
                startActivity(i);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = edit.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fuser.getUid(),userid,msg);
                }else {
                    Toast.makeText(chat_activity.this,"you can't send empty message",Toast.LENGTH_SHORT).show();
                }
                edit.setText("");
            }
        });


    }

    private void sendMessage(String sender, String receiver ,String message){
        String userid = intent.getStringExtra("userid");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("chats").push().setValue(hashMap);

        DatabaseReference chatRef= FirebaseDatabase.getInstance().getReference("chatlist").child(fuser.getUid()).child(userid);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if(!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void readMessage(String myid, String userid , String image){
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chatm user = snapshot.getValue(chatm.class);
                    if(user.getReceiver().equals(myid) && user.getSender().equals(userid) || user.getReceiver().equals(userid) && user.getSender().equals(myid)){

                        list.add(user);
                    }
                    messageAdapter = new MessageAdapter(chat_activity.this, list, image);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void upgradestates(String state)
    {
        String savecurrentDate, savecurrentTime;

        Calendar calForDate =Calendar.getInstance();
        SimpleDateFormat currenrDate= new SimpleDateFormat("MMM dd, yyyy");
        savecurrentDate=currenrDate.format(calForDate.getTime());

        Calendar calFortime =Calendar.getInstance();
        SimpleDateFormat currenrtime= new SimpleDateFormat("hh:mm a");
        savecurrentTime=currenrtime.format(calFortime.getTime());

        HashMap<String ,Object> map = new HashMap<>();
        map.put("currentTime",savecurrentTime);
        map.put("currentdate",savecurrentDate);
        map.put("type",state);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.updateChildren(map);
    }

    @Override
    protected void onStop() {
        super.onStop();
        upgradestates("Offline");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        upgradestates("Offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        upgradestates("Online");
    }

    public void onBackPressed() {
        Intent donor=new Intent(chat_activity.this, chat_home.class);
        startActivity(donor);
    }
}