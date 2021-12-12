package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEmail , mPassword;
    TextView forgotPassword,signup;
    Button login;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotPassword=findViewById(R.id.txt_forgot_password);
        signup=findViewById(R.id.txt_sign_up);
        login=findViewById(R.id.btn_login3);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password);



        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent password=new Intent(Login.this,ForgetPassword.class);
                startActivity(password);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign=new Intent(Login.this,RegisterActivity.class);
                startActivity(sign);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(Login.this,HomeActivity.class);
                startActivity(login);



            }
        });
    }
}