package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class best_doctors extends AppCompatActivity {
    CardView eyes,lungs,kidneys,skin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_doctors);

        eyes=findViewById(R.id.doctor_eyes);
        lungs=findViewById(R.id.doctor_lungs);
        kidneys=findViewById(R.id.doctor_kidney);
        skin=findViewById(R.id.doctor_skin);


        kidneys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kidney = new Intent(best_doctors.this,doctors.class);
                startActivity(kidney);
            }
        });

        lungs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lungs = new Intent(best_doctors.this,lung_doctors.class);
                startActivity(lungs);
            }
        });

        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin = new Intent(best_doctors.this,skin_doctors.class);
                startActivity(skin);
            }
        });

        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eyes = new Intent(best_doctors.this,eyes_doctors.class);
                startActivity(eyes);
            }
        });
    }
}