package com.example.medscan.skin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medscan.R;
import com.example.medscan.chat.chat_home;
import com.example.medscan.chat.chat_user;

public class actinic_keratosis_info extends AppCompatActivity {

    ImageView icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actinic_keratosis_info);

        icon_back=findViewById(R.id.icon_back1);

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin=new Intent(actinic_keratosis_info.this, skin_menu.class);
                startActivity(skin);
            }
        });
    }
}