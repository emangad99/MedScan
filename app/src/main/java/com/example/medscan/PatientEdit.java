package com.example.medscan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private EditText fullname;
    private EditText emailedittext;
    private EditText phone;
    EditText medical;
    EditText clinic;
    EditText time;
    EditText other;
    ImageView profileimage;
    Button update;
    private FirebaseDatabase database;
    private DatabaseReference muserRuf;
    private String email,password;
    private static  final  String USERS ="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);

        Intent intent = getIntent();
         email = intent.getStringExtra("email");

         DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
         DatabaseReference UserRef = rootRef.child(USERS);

         Log.v("EMAILADD", UserRef.orderByChild("email").equalTo(email).toString());



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
        muserRuf = database.getReference(USERS);


        muserRuf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    if(ds.child("Email").getValue().equals(email)){
                        fullname.setText(ds.child("First Name").getValue(String.class));
                        phone.setText(ds.child("phone").getValue(String.class));
                        emailedittext.setText(email);
                    }
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