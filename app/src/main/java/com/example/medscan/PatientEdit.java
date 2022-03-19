package com.example.medscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientEdit extends AppCompatActivity {
    TextView passwordt;
     TextView fullnametxtview;
     TextView emailtxtview;
     EditText phone;
    EditText medical;
    EditText clinic;
    EditText time;
    EditText other;
    ImageView profileimage;
    Button update;
     FirebaseDatabase database;
     DatabaseReference muserRuf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);


        passwordt = findViewById(R.id.txt_reset);
        fullnametxtview = findViewById(R.id.editTextTextPersonName2);
        emailtxtview = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        medical = findViewById(R.id.editTextTextPersonName);
        clinic = findViewById(R.id.editTextTextPersonName3);
        time = findViewById(R.id.editTextTextPersonName4);
        other = findViewById(R.id.editTextTextPersonName5);
        update = findViewById(R.id.button3);

        database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser rUser = firebaseAuth.getCurrentUser();
        assert rUser != null;
        String userId =rUser.getUid();
        muserRuf = database.getReference().child("Users").child(userId);


        muserRuf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    fullnametxtview.setText(ds.child("First Name").getValue(String.class));
                    fullnametxtview.setText(ds.child("Email").getValue(String.class));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        passwordt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reset=new Intent(PatientEdit.this,ForgetPassword.class);
                startActivity(reset);
            }


        });









    }
}