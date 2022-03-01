package com.example.medscan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailsPatientActivity extends AppCompatActivity {

    EditText txtblood_type ,txtphone,txtaddress,full_name;
    Button btnsubmit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_patient);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Patients");
        userHelper = new UserHelper();

        txtblood_type=findViewById(R.id.txt_blood_type);
        txtaddress=findViewById(R.id.txt_address);
        txtphone=findViewById(R.id.txt_phone);
        btnsubmit=findViewById(R.id.btn_submit);
        full_name=findViewById(R.id.txt_name);

       btnsubmit.setOnClickListener(view -> {


           String  address = txtaddress.getText().toString();
           String  phone= txtphone.getText().toString();
           String  blood_type = txtblood_type.getText().toString();
           String  Full_name = full_name.getText().toString();

           if (Full_name.isEmpty()) {
               full_name.setError("Please enter Full Name");
               full_name.requestFocus();
               return;
           }
           if (address.isEmpty()) {
               txtaddress.setError("Please enter your Address");
               txtaddress.requestFocus();
               return;
           }
           if (phone.isEmpty()) {
               txtphone.setError("Please enter your Phone number");
               txtphone.requestFocus();
               return;
           }
           if (blood_type.isEmpty()) {
               txtblood_type.setError("Please enter your Blood Type");
               txtblood_type.requestFocus();
               return;
           }

           else {

               userHelper.setAddress(address);
               userHelper.setPhone(phone);
               userHelper.setBlood_type(blood_type);
               userHelper.setFull_name(Full_name);
               databaseReference.push().setValue(userHelper);

               Intent intentdata = new Intent(DetailsPatientActivity.this, WaitingActivity.class);
               startActivity(intentdata);


               Toast.makeText(DetailsPatientActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();

           }
       }

       );

    }
}