package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    Button btn_submit;
    Animation charactanim;
    EditText txtFeedback;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        btn_submit=findViewById(R.id.submit);
        charactanim= AnimationUtils.loadAnimation(this,R.anim.charactanim);
        btn_submit.startAnimation(charactanim);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Feedbacks");
        userHelper = new UserHelper();
        txtFeedback = findViewById(R.id.txt_feedback);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = txtFeedback.getText().toString();

                if (feedback.isEmpty()) {
                    txtFeedback.setError("Please fill it..Your Opinion matters");
                    txtFeedback.requestFocus();
                    return;
                }else{

                    userHelper.setFeedback(feedback);
                    databaseReference.push().setValue(userHelper);


                    Toast.makeText(Feedback.this, " Thanks..Your Opinion matters..We hope we helped you.", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}