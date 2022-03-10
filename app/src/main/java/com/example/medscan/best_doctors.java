package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class best_doctors extends AppCompatActivity {
CardView eyes,lungs,kidneys,teeth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_doctors);

        eyes=findViewById(R.id.doctor_eyes);
        lungs=findViewById(R.id.doctor_lungs);
        kidneys=findViewById(R.id.doctor_kidney);
        teeth=findViewById(R.id.doctor_teeth);


        kidneys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kidney = new Intent(best_doctors.this,doctors.class);
                startActivity(kidney);
            }
        });
    }
}