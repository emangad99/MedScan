package com.example.medscan.eyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medscan.R;

public class uveitis_info extends AppCompatActivity {
    ImageView icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uveitis_info);

        icon_back=findViewById(R.id.icon_back44);

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eyes=new Intent(uveitis_info.this, eyes_menu.class);
                startActivity(eyes);
            }
        });
    }
}