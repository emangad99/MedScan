package com.example.medscan.skin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medscan.R;

public class intraepithelial_carcinoma_info extends AppCompatActivity {

    ImageView icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intraepithelial_carcinoma_info);

        icon_back=findViewById(R.id.icon_back2);

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin=new Intent(intraepithelial_carcinoma_info.this, skin_menu.class);
                startActivity(skin);
            }
        });
    }
}