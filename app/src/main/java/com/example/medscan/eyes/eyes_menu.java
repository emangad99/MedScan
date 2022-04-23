package com.example.medscan.eyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medscan.R;
import com.example.medscan.eyes_image;
import com.example.medscan.skin.skin_menu;
import com.example.medscan.skin_upload;

public class eyes_menu extends AppCompatActivity {

    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes_menu);

        ok=findViewById(R.id.con_btn2);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin = new Intent(eyes_menu.this, eyes_image.class);
                startActivity(skin);
            }
        });
    }
}