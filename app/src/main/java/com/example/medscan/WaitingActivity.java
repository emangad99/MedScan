package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WaitingActivity extends AppCompatActivity {
TextView txt_advice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        txt_advice=findViewById(R.id.advice);
        txt_advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent advice =new Intent(WaitingActivity.this,health.class);
                startActivity(advice);
            }
        });
    }
}