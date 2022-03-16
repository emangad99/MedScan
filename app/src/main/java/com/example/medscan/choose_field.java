package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class choose_field extends AppCompatActivity {
    CardView lungs , teeth , kidney , eyes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_field);

        lungs=findViewById(R.id.card_lung);
        teeth=findViewById(R.id.card_skin);
        kidney=findViewById(R.id.card_kidney);
        eyes=findViewById(R.id.card_eyes);

        lungs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lungs = new Intent(choose_field.this,RayUploaded.class);
                startActivity(lungs);
            }
        });
    }
}