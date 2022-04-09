package com.example.medscan.skin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medscan.R;
import com.example.medscan.choose_field;
import com.example.medscan.skin_upload;

public class skin_menu extends AppCompatActivity {

    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_menu);

        ok=findViewById(R.id.con_btn);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin = new Intent(skin_menu.this, skin_upload.class);
                startActivity(skin);
            }
        });
    }
}