package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailsDonor extends AppCompatActivity {
    private EditText blood_type ,phone,address,medical_history;
    private Button submit;
    TextView textView1,textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_donor);

        address=findViewById(R.id.txt_address);
        phone=findViewById(R.id.txt_phone);
        blood_type=findViewById(R.id.txt_blood_type);
        medical_history=findViewById(R.id.txt_blood_type2);
        submit=findViewById(R.id.btn_submit);
        textView1=findViewById(R.id.textView16);
        textView4=findViewById(R.id.textView23);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentdonor = new Intent(DetailsDonor.this,health.class);
                startActivity(intentdonor);

                //hashmap
                HashMap<String,Object> usermap=new HashMap<String, Object>();

                usermap.put("Address", address.getText().toString());
                usermap.put("Phone",phone.getText().toString());
                usermap.put("Blood_Type",blood_type.getText().toString());
                usermap.put("Medical_History",medical_history.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Donor").push().setValue("usermap");


                Toast.makeText(DetailsDonor.this, "Data Saved", Toast.LENGTH_SHORT).show();


            }
        });
    }


}