package com.example.medscan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsPatientActivity extends AppCompatActivity {

    EditText txttime ,txtphone,txtaddress,txtmedical;
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

        txttime=findViewById(R.id.txt_time);
        txtaddress=findViewById(R.id.txt_address);
        txtphone=findViewById(R.id.txt_phone);
        btnsubmit=findViewById(R.id.btn_submit);
        txtmedical=findViewById(R.id.txt_meical);

       btnsubmit.setOnClickListener(view -> {


           String  address = txtaddress.getText().toString();
           String  phone= txtphone.getText().toString();
           String  time= txttime.getText().toString();
           String  medical= txtmedical.getText().toString();

           if (time.isEmpty()) {
               txttime.setError("Please enter Full Name");
               txttime.requestFocus();
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
           if (phone.length()!=11) {
               txtphone.setError("Please enter a valid number");
               txtphone.requestFocus();
               return;
           }
           if (medical.isEmpty()) {
               txtmedical.setError("Please enter your Blood Type");
               txtmedical.requestFocus();
               return;
           }

           else {

               userHelper.setAddress(address);
               userHelper.setPhone(phone);
               userHelper.setTime(time);
               userHelper.setMedical(medical);
               databaseReference.push().setValue(userHelper);

               Intent intentdata = new Intent(DetailsPatientActivity.this, WaitingActivity.class);
               startActivity(intentdata);


               Toast.makeText(DetailsPatientActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();

           }
       }

       );

    }
}