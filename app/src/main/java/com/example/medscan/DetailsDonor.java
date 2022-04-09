package com.example.medscan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailsDonor extends AppCompatActivity {

    EditText  medical,phone,address,time,other;
    Button submit;
     FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserHelper userHelper;
    FirebaseAuth authProfile;
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_donor);
        firebaseDatabase=FirebaseDatabase.getInstance();
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        address=findViewById(R.id.txt_address);
        phone=findViewById(R.id.txt_phone);
        time=findViewById(R.id.txt_time);
        other=findViewById(R.id.txt_other);
        submit=findViewById(R.id.btn_submit);
        medical=findViewById(R.id.txt_meical);
        dialog=new Dialog(this);






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  Address =address.getText().toString();
                String  Phone= phone.getText().toString();
                String  Time = time.getText().toString();
                String  Medical = medical.getText().toString();
                String Other = other.getText().toString();

                if (Medical.isEmpty()) {
                    medical.setError("Please enter your medical specialty");
                    medical.requestFocus();
                    return;
                }
                if (! Medical.equals("Kidney") && ! Medical.equals("eyes") && ! Medical.equals("Skin") && ! Medical.equals("Lungs")
                        && ! Medical.equals("Eyes") && ! Medical.equals("kidney") && ! Medical.equals("skin") && ! Medical.equals("lungs"))
                {
                    medical.setError("Please enter (Kidney or Lungs or eyes or skin )");
                    medical.requestFocus();
                    return;
                }
                    if (Address.isEmpty()) {
                    address.setError("Please enter your clinic address");
                    address.requestFocus();
                    return;
                }
                if (Phone.isEmpty()) {
                    phone.setError("Please enter your Phone number");
                    phone.requestFocus();
                    return;
                }
                if (Phone.length()!=11) {
                    phone.setError("Please enter a valid number");
                    phone.requestFocus();
                    return;
                }
                if (Time.isEmpty()) {
                    time.setError("Please enter your available time");
                    time.requestFocus();
                    return;
                }
                if (Other.isEmpty()) {
                    other.setError("If you have any other information ,please write it here..If not,Write Nothing");
                    other.requestFocus();
                    return;
                }
                else {

                    HashMap<String ,Object> map = new HashMap<>();
                    map.put("address",Address);
                    map.put("phone",Phone);
                    map.put("medical",Medical);
                    map.put("time",Time);
                    map.put("other",Other);
                    databaseReference.updateChildren(map);

                    openDialog();

                    Toast.makeText(DetailsDonor.this, "Data Saved", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void openDialog() {
        dialog.setContentView(R.layout.dialog_doctor);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_ok = dialog.findViewById(R.id.ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsDonor.this, menu_doctors.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }


}