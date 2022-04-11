package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medscan.login.Login;
import com.example.medscan.lungs.covid;
import com.example.medscan.lungs.failed;
import com.example.medscan.lungs.successful;
import com.example.medscan.skin.actinic_keratosis;
import com.example.medscan.skin.basal_cell_carcinoma;
import com.example.medscan.skin.dermatofibroma;
import com.example.medscan.skin.intraepithelial_carcinoma;
import com.example.medscan.skin.melanocytic_nevi;
import com.example.medscan.skin.melanoma;
import com.example.medscan.skin.seborrheic_keratosis;

public class Welcome extends AppCompatActivity {
Button btn_start;
TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        btn_start=findViewById(R.id.btn_started);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Welcome.this, Login.class);
                startActivity(i);

            }
        });

        txt=findViewById(R.id.txt1);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Welcome.this, seborrheic_keratosis.class);
                startActivity(i);


            }
        });
    }


}