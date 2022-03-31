package com.example.medscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    TextView password;
    EditText fullname;
    //  TextView emailedittext;
    // EditText phone;
    // EditText medical;
     // EditText clinic;
      //EditText time;
     //EditText other;
    ImageView profileimage;
    Button update;
   /* FirebaseDatabase database;
    DatabaseReference muserRuf;
    FirebaseAuth fAuth;
    FirebaseUser rUser;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);


        password = findViewById(R.id.txt_reset);
        fullname = findViewById(R.id.editTextTextPersonName2);
        // emailedittext = findViewById(R.id.editTextTextEmailAddress);
        //  phone = findViewById(R.id.editTextPhone);
        //  medical = findViewById(R.id.editTextTextPersonName);
        //  clinic = findViewById(R.id.editTextTextPersonName3);
        //  time = findViewById(R.id.editTextTextPersonName4);
        //  other = findViewById(R.id.editTextTextPersonName5);
        update = findViewById(R.id.button3);

       /* database = FirebaseDatabase.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser rUser = fAuth.getCurrentUser();
        assert rUser != null;
        String userId =rUser.getUid();
        muserRuf = database.getReference().child("Users").child(userId).child("First Name");


        muserRuf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String  firstname=snapshot.getValue(String.class);
                System.out.println(firstname);
                //   String email= snapshot.child("Email").getValue(String.class);

                fullname.setText(firstname);
                //  emailedittext.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });*/





        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reset=new Intent(PatientEdit.this,ForgetPassword.class);
                startActivity(reset);
            }


        });


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(this.getResources().getColor(R.color.color3));
        }









    }
}