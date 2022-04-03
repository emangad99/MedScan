package com.example.medscan.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medscan.R;
import com.example.medscan.SessionManager;
import com.example.medscan.login.Login;

public class SuccessResetPass extends AppCompatActivity {
    Button login;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_reset_pass);

        sessionManager=new SessionManager(getApplicationContext());

        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.setLogin(false);
                sessionManager.setUsername("");
                startActivity(new Intent(SuccessResetPass.this, Login.class));

            }
        });
    }
}