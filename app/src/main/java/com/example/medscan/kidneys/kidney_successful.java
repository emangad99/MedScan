package com.example.medscan.kidneys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.medscan.R;
import com.example.medscan.lungs.covid_advice;
import com.example.medscan.lungs.successful;
import com.example.medscan.lungs.successful_advice;

public class kidney_successful extends AppCompatActivity {
    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidney_successful);

        done=findViewById(R.id.btn_done_kid1);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(kidney_successful.this, kidney_successful_advice.class);
                startActivity(home);

            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(this.getResources().getColor(R.color.color8));

        }
    }
}