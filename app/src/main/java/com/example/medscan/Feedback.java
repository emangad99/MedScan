package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {
    Button btn_submit;
    Animation charactanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        btn_submit=findViewById(R.id.submit);
        charactanim= AnimationUtils.loadAnimation(this,R.anim.charactanim);
        btn_submit.startAnimation(charactanim);

    }
}