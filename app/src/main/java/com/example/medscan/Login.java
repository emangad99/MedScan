package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEmail , mPassword;
    TextView forgotPassword,signup ;
    Button login;
    FirebaseAuth fAuth;
    ImageView btn_google,btn_facebook, btn_twitter;
    boolean passvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotPassword=findViewById(R.id.txt_forgot_password);
        signup=findViewById(R.id.txt_sign_up);
        login=findViewById(R.id.btn_login3);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password);
        fAuth= FirebaseAuth.getInstance();
        btn_google=findViewById(R.id.google);
        btn_facebook=findViewById(R.id.facebook);
        btn_twitter=findViewById(R.id.twitter);

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if(event.getRawX()>= mPassword.getRight()-mPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = mPassword.getSelectionEnd();
                        if(passvisible) {
                            mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off_icon,0);
                            mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        }
                        else {
                            mPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                        }
                        mPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });


        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfacebook = new Intent(Login.this,FacebookAuth.class);
                intentfacebook.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intentfacebook);

            }
        });

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentgoogle = new Intent(Login.this,GoogleAuth.class);
                intentgoogle.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intentgoogle);

            }
        });

        btn_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttwitter = new Intent(Login.this,TwitterAuth.class);
                intenttwitter.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intenttwitter);


            }
        });



        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle(" Reset Password ?");
                passwordResetDialog.setIcon(R.drawable.icon2);
                passwordResetDialog.setMessage(" Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton(" Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void avoid) {
                                Toast.makeText(Login.this,"Reset Link To Your Email",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Error ! Reset Link is not sent" + e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                passwordResetDialog.setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();


            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign=new Intent(Login.this,RegisterActivity.class);
                startActivity(sign);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if( fAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

                            }else{
                                Toast.makeText(Login.this,"Please verify your email address" ,Toast.LENGTH_SHORT).show();

                            }

                        }else {
                            Toast.makeText(Login.this,"Error" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}