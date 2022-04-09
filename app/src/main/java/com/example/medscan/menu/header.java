package com.example.medscan.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
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
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class header extends AppCompatActivity {

    CircleImageView profileimage;
    String _EMAIL;
    DatabaseReference databaseReference;
    TextView txtname, email;
    FirebaseAuth authProfile;
    FirebaseUser firebaseUser ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        authProfile = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        profileimage = findViewById(R.id.pic);
        txtname = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);

        showProfile(firebaseUser);

        }
    private void showProfile(FirebaseUser firebaseUser) {
        String userIdRegistered = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userIdRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                _EMAIL = firebaseUser.getEmail();
                email.setText(_EMAIL);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(header.this, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();


            }
        });
    }

}