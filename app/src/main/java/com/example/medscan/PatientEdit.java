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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientEdit extends AppCompatActivity {
    TextView passwordt;
     TextView fullname;
     TextView emailedittext;
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
        fullname = findViewById(R.id.editTextTextPersonName2);
        emailedittext = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        medical = findViewById(R.id.editTextTextPersonName);
        clinic = findViewById(R.id.editTextTextPersonName3);
        time = findViewById(R.id.editTextTextPersonName4);
        other = findViewById(R.id.editTextTextPersonName5);
        update = findViewById(R.id.button3);

        database = FirebaseDatabase.getInstance();
        muserRuf = database.getReference().child("Users");


        muserRuf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstname=snapshot.child("First Name").getValue(String.class);
                String email= snapshot.child("Email").getValue(String.class);

                fullname.setText(firstname);
                emailedittext.setText(email);

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