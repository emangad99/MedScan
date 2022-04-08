package com.example.medscan.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medscan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chat_user extends AppCompatActivity {
    ProgressBar progressBar;
    TextView texterror;
    ImageView icon_back;
    FirebaseDatabase database;
    FirebaseAuth fAuth;
    String _TEXTERROR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color5));

        }

        progressBar=findViewById(R.id.progrsess_error);
        texterror=findViewById(R.id.texterrormessage);
        icon_back=findViewById(R.id.icon_back);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(chat_user.this,"something is wrong ",Toast.LENGTH_LONG).show();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            showChatuser(firebaseUser);
        }


        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat=new Intent(chat_user.this, chat_home.class);
                startActivity(chat);
            }
        });

    }

    private void showChatuser(FirebaseUser firebaseUser) {

        String userIdRegistered = firebaseUser.getUid();
        DatabaseReference referenceuser = FirebaseDatabase.getInstance().getReference("Users");
        referenceuser.child(userIdRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                _TEXTERROR = firebaseUser.getDisplayName();
                texterror.setText(_TEXTERROR);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chat_user.this,"something is wrong",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getUsers()
    {
        loading(true);
    }

    private void showErrorMessage()
    {
        texterror.setText(String.format("%s","No user available"));
        texterror.setVisibility(View.VISIBLE);
    }

    private void loading (Boolean isloading)
    {
        if(isloading)
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}