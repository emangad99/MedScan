package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPassword extends AppCompatActivity {
    Button reset;
    EditText oldPass , newPass,confirmPass;
    String pw = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{8,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{8,})(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        reset=findViewById(R.id.btn_reset_password);
        oldPass=findViewById(R.id.txt_old_password);
        newPass=findViewById(R.id.txt_new_password);
        confirmPass=findViewById(R.id.txt_confirm_password);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = oldPass.getText().toString();
                String newpass = newPass.getText().toString();
                String confirmpass = confirmPass.getText().toString();

                if (oldpass.isEmpty()) {
                    oldPass.setError("please fill the field ! ");
                    oldPass.requestFocus();
                    return;
                }
                if (newpass.isEmpty()) {
                    newPass.setError("please fill the field ! ");
                    newPass.requestFocus();
                    return;
                }
                if (confirmpass.isEmpty()) {
                    confirmPass.setError("please fill the field ! ");
                    confirmPass.requestFocus();
                    return;
                }
                if (!confirmpass.matches(pw)) {
                    confirmPass.setError("This must contain at least 8 (Upper case, Lower case,Numbers and signs)");
                    confirmPass.requestFocus();
                }
                if (!newpass.matches(pw)) {
                    newPass.setError("This must contain at least 8 (Upper case, Lower case,Numbers and signs)");
                    newPass.requestFocus();
                }



                Intent reset=new Intent(ForgetPassword.this,SuccessResetPass.class);
                startActivity(reset);


            }
        });
    }
}