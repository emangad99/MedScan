package com.example.medscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
TextView password;
EditText fullname;
    EditText email;
    EditText phone;
    EditText medical;
    EditText clinic;
    EditText time;
    EditText other;
Button update;
FirebaseAuth mAuth;
FirebaseUser mUser;
DatabaseReference muserRuf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);

        password = findViewById(R.id.txt_reset);
        fullname = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        medical = findViewById(R.id.editTextTextPersonName);
        clinic = findViewById(R.id.editTextTextPersonName3);
        time = findViewById(R.id.editTextTextPersonName4);
        other = findViewById(R.id.editTextTextPersonName5);
        update = findViewById(R.id.button3);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        muserRuf = FirebaseDatabase.getInstance().getReference().child("Users");




        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reset=new Intent(PatientEdit.this,ForgetPassword.class);
                startActivity(reset);
            }

            protected void onStart(){
                PatientEdit.super.onStart();
                if(mUser==null){

                    sendUserToLoginActivity();
                }

                else{
                    muserRuf.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String fullname = String.valueOf(snapshot.child("First Name").getValue());
                                String  email = String.valueOf(snapshot.child("Email").getValue());
                                String  phone = String.valueOf(snapshot.child("Phone").getValue());





                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(PatientEdit.this,"sorry, Something going Wrong ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            private void sendUserToLoginActivity() {

                Intent intent= new Intent(PatientEdit.this,Login.class);
                startActivity(intent);
                finish();
            }

        });









    }
}