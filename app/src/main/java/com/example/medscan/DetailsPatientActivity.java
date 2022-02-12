package com.example.medscan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailsPatientActivity extends AppCompatActivity {

    //variable

    private EditText txtblood_type ,txtphone,txtaddress;
    private  Button btnsubmit;
    TextView textView16 ,textView23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_patient);

        txtblood_type=findViewById(R.id.txt_blood_type);
        txtaddress=findViewById(R.id.txt_address);
        txtphone=findViewById(R.id.txt_phone);
        btnsubmit=findViewById(R.id.btn_submit);
        textView16=findViewById(R.id.textView16);
        textView23=findViewById(R.id.textView23);

       btnsubmit.setOnClickListener(view -> {

                   String address = txtaddress.getText().toString();
                   String phone = txtphone.getText().toString();
                   String bloodType = txtblood_type.getText().toString();

                   if (address.isEmpty()) {
                       txtaddress.setError("Please enter your Address");
                       txtaddress.requestFocus();

                   }
                   if (phone.isEmpty()) {
                       txtphone.setError("Please enter your Phone number");
                       txtphone.requestFocus();
                   }
                   if (bloodType.isEmpty()) {
                       txtblood_type.setError("Please enter your Blood Type");
                       txtblood_type.requestFocus();

                   } else {
                       Intent intentdata = new Intent(DetailsPatientActivity.this, health.class);
                       startActivity(intentdata);

                       //hashmap
                       HashMap<String, Object> usermap = new HashMap<String, Object>();

                       usermap.put("txtaddress", txtaddress.getText().toString());
                       usermap.put("txtphone", txtphone.getText().toString());
                       usermap.put("txtblood_type", txtblood_type.getText().toString());
                       FirebaseDatabase.getInstance().getReference().child("patient").push().setValue("usermap");


                       Toast.makeText(DetailsPatientActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();


                   }
               }
       );

    }
}