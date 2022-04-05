package com.example.medscan.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medscan.R;

public class header extends AppCompatActivity {

    ImageView img;
    TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        img=findViewById(R.id.pic);
        name=findViewById(R.id.user_name);
        email=findViewById(R.id.user_email);
    }
}