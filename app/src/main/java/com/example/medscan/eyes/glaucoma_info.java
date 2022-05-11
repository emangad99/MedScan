package com.example.medscan.eyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medscan.R;

public class glaucoma_info extends AppCompatActivity {
    ImageView icon_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glaucoma_info);

        icon_back=findViewById(R.id.icon_back55);

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eyes=new Intent(glaucoma_info.this, eyes_menu.class);
                startActivity(eyes);
            }
        });
    }
}