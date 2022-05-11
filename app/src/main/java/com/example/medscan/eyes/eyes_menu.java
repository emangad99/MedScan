package com.example.medscan.eyes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.example.medscan.eyes_image;
import com.example.medscan.skin.skin_menu;
import com.example.medscan.skin_upload;

public class eyes_menu extends AppCompatActivity {

    Button ok;
    TextView eyes1, eyes2 , eyes3 , eyes4 , eyes5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes_menu);

        ok=findViewById(R.id.con_btn2);
        eyes1=findViewById(R.id.bulging_eyes);
        eyes2=findViewById(R.id.cataract);
        eyes3=findViewById(R.id.crossed_eyes);
        eyes4=findViewById(R.id.uveitis);
        eyes5=findViewById(R.id.glaucoma);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin = new Intent(eyes_menu.this, eyes_image.class);
                startActivity(skin);
            }
        });

        eyes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent skin1 = new Intent(eyes_menu.this, bulging_eyes_info.class);
                startActivity(skin1);

            }
        });

        eyes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent skin2 = new Intent(eyes_menu.this, cataract_info.class);
                startActivity(skin2);

            }
        });

        eyes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent skin3 = new Intent(eyes_menu.this, crossed_eyes_info.class);
                startActivity(skin3);

            }
        });

        eyes4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent skin4 = new Intent(eyes_menu.this, uveitis_info.class);
                startActivity(skin4);

            }
        });

        eyes5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent skin5 = new Intent(eyes_menu.this, glaucoma_info.class);
                startActivity(skin5);

            }
        });
    }
    public void onBackPressed() {
        Intent donor=new Intent(eyes_menu.this, HomeActivity.class);
        startActivity(donor);
    }
}