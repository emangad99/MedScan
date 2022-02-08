package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailsPatientActivity extends AppCompatActivity {

    //variable

    EditText txtname ,txtblood_type ,txtphone,txtaddress;
    Button btnsubmit;
    TextView textView16 ,textView23;

    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference reference=db.getReference().child("patient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_patient);

        txtname=findViewById(R.id.txt_name);
        txtblood_type=findViewById(R.id.txt_blood_type);
        txtaddress=findViewById(R.id.txt_address);
        txtphone=findViewById(R.id.txt_phone);
        btnsubmit=findViewById(R.id.btn_submit);
        textView16=findViewById(R.id.textView16);
        textView23=findViewById(R.id.textView23);

       btnsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               //Get  all the values

               String name = txtname.getText().toString();
               String address = txtaddress.getText().toString();
               String phone = txtphone.getText().toString();
               String bloodtype = txtblood_type.getText().toString();

               //hashmap
               HashMap<String,String> usermap=new HashMap<>();
               usermap.put("txtname",name);
               usermap.put("txtaddress",address);
               usermap.put("txtphone",phone);
               usermap.put("txtblood_type",bloodtype);


               reference.push().setValue(usermap);
               Toast.makeText(DetailsPatientActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();

                                            
                                        }
                                    }

       );

    }
}