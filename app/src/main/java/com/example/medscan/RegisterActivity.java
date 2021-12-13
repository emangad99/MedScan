package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText txtFirstName ,txtLastName , txtEmail ,txtPass;
    ImageView btnGoogle,btnFacebook,btnTwitter;
    String pw = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{8,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{8,})(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$";
    FirebaseAuth fAuth;
    TextView signin;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signin=findViewById(R.id.Sign_in);
        signup=findViewById(R.id.btn_signup);
        txtFirstName=findViewById(R.id.txt_fname);
        txtLastName = findViewById(R.id.txt_lname);
        txtEmail = findViewById(R.id.Email);
        txtPass = findViewById(R.id.Password);
        fAuth = FirebaseAuth.getInstance();
        btnFacebook = findViewById(R.id.Facebook);
        btnGoogle = findViewById(R.id.Google);
        btnTwitter = findViewById(R.id.Twitter);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstname = txtFirstName.getText().toString();
                String lastname = txtLastName.getText().toString();
                String email = txtEmail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();

                if (firstname.isEmpty()) {
                    txtFirstName.setError("First Name is required");
                    txtFirstName.requestFocus();

                }
                if (lastname.isEmpty()) {
                    txtLastName.setError("Last Name is required");
                    txtLastName.requestFocus();
                }
                if (TextUtils.isEmpty(email)) {
                    txtEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    txtPass.setError("Password is required");
                    return;
                }
                if (!password.matches(pw)) {
                    txtPass.setError("Password must contain at least 8 (Upper case, Lower case,Numbers and signs)");
                    txtPass.requestFocus();
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                        }else {
                            Toast.makeText(RegisterActivity.this,"Error" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign=new Intent(RegisterActivity.this,Login.class);
                startActivity(sign);
            }
        });


    }
}