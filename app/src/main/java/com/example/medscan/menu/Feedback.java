package com.example.medscan.menu;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    Button btn_submit,btn_ok;
    Animation charactanim;
    EditText txtFeedback;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth authProfile;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        btn_submit=findViewById(R.id.submit);
        btn_ok=findViewById(R.id.send_feedback);
        charactanim= AnimationUtils.loadAnimation(this,R.anim.charactanim);
        btn_submit.startAnimation(charactanim);
        firebaseDatabase=FirebaseDatabase.getInstance();
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        txtFeedback = findViewById(R.id.txt_feedback);
        dialog=new Dialog(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = txtFeedback.getText().toString();

                if (feedback.isEmpty()) {
                    txtFeedback.setError("Please fill it..Your Opinion matters");
                    txtFeedback.requestFocus();
                    return;
                }else{

                    HashMap<String ,Object> map = new HashMap<>();
                    map.put("Feedback",feedback);

                    databaseReference.updateChildren(map);


                    openDialog();


                }
            }
        });

    }

    private void openDialog() {
        dialog.setContentView(R.layout.layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_ok = dialog.findViewById(R.id.send_feedback);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

}