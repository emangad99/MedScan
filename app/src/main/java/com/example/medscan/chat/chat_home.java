package com.example.medscan.chat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;

public class chat_home extends AppCompatActivity {

    RoundedImageView profile;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_home);

        profile=findViewById(R.id.img_prof);
        name=findViewById(R.id.text_name);

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