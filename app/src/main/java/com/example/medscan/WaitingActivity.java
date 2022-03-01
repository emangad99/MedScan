package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WaitingActivity extends AppCompatActivity {
TextView txt_advice , home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        txt_advice=findViewById(R.id.advice);
        home=findViewById(R.id.txt_home);
        txt_advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent advice =new Intent(WaitingActivity.this,health.class);
                startActivity(advice);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home =new Intent(WaitingActivity.this,HomeActivity.class);
                startActivity(home);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}