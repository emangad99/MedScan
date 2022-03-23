package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class eyes_doctors extends AppCompatActivity {

    private RelativeLayout mlayout ,mlayout2 ,mlayout3 ,mlayout4 ,mlayout5 , mlayout6;
    private ImageView doc1 , doc2 , doc3 , doc4 , doc5 , doc6;
    private TextView namdoc1, namdoc2 ,namdoc3 , namdoc4 ,namdoc5 , namdoc6;
    private TextView desc1, desc2 ,desc3 , desc4 ,desc5 , desc6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes_doctors);

        final FloatingActionButton button = findViewById(R.id.fab4);

        mlayout=(RelativeLayout) findViewById(R.id.eyes_relative1);
        mlayout2=(RelativeLayout) findViewById(R.id.eyes_relative2);

        doc1=(ImageView) findViewById(R.id.eyes_doctor1);
        namdoc1=(TextView) findViewById(R.id.eyes_name_doc1);
        desc1=(TextView) findViewById(R.id.eyes_des1);

        doc2=(ImageView)findViewById(R.id.eyes_doctor2);
        namdoc2=(TextView) findViewById(R.id.eyes_name_doc2);
        desc2=(TextView) findViewById(R.id.eyes_des2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(eyes_doctors.this,eyes_hospitals.class));

            }
        });
    }
}