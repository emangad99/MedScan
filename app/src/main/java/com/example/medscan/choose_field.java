package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.medscan.eyes.eyes_menu;
import com.example.medscan.skin.skin_menu;

public class choose_field extends AppCompatActivity {
    CardView lungs , Skin , kidney , eyes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_field);


        lungs=findViewById(R.id.card_lung);
        Skin=findViewById(R.id.card_skin);
        kidney=findViewById(R.id.card_kidney);
        eyes=findViewById(R.id.card_eyes);

        lungs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lungs = new Intent(choose_field.this,RayUploaded.class);
                startActivity(lungs);
            }
        });

        kidney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kidney = new Intent(choose_field.this,kidney_report.class);
                startActivity(kidney);

            }
        });

        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eyes = new Intent(choose_field.this, eyes_menu.class);
                startActivity(eyes);

            }
        });

        Skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin = new Intent(choose_field.this, skin_menu.class);
                startActivity(skin);

            }
        });

    }
}