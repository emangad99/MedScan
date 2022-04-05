package com.example.medscan.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.example.medscan.databinding.ActivityMainBinding;
import com.example.medscan.menu_doctors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class chat_home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);

        final FloatingActionButton button = findViewById(R.id.chat_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat=new Intent(chat_home.this, chat_user.class);
                startActivity(chat);

            }
        });
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color5));

        }
    }
    public void onBackPressed() {
        Intent donor=new Intent(chat_home.this, HomeActivity.class);
        startActivity(donor);
    }
}