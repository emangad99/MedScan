package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class skin_image extends AppCompatActivity {
    ImageView btn_choose , image;
    Button upload ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_image);


        btn_choose = findViewById(R.id.choose3);
        image=findViewById(R.id.ray);
        upload=findViewById(R.id.upload);


        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }


}
