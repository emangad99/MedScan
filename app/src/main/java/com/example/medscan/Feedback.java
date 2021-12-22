package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {
    String answarValue;
    Button btn_submit;
    RatingBar ratestars;
    TextView feedback;
    Animation charactanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        btn_submit=findViewById(R.id.submit);
        ratestars=findViewById(R.id.ratingBar);
        feedback=findViewById(R.id.result_feedback);
        charactanim= AnimationUtils.loadAnimation(this,R.anim.charactanim);

        btn_submit.startAnimation(charactanim);

        ratestars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                answarValue= String.valueOf((int)(ratestars.getRating()));
                if(answarValue.equals("1"))
                {
                    feedback.setText("Not Bad");
                    btn_submit.startAnimation(charactanim);

                }
                else if (answarValue.equals("2"))
                {
                    feedback.setText("Good");
                    btn_submit.startAnimation(charactanim);

                }
                else if (answarValue.equals("3"))
                {
                    feedback.setText("Very Good");
                    btn_submit.startAnimation(charactanim);

                }
                else if (answarValue.equals("4"))
                {
                    feedback.setText("Excellent");
                    btn_submit.startAnimation(charactanim);

                }
                else if (answarValue.equals("5"))
                {
                    feedback.setText("Awesome");
                    btn_submit.startAnimation(charactanim);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No Point", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}