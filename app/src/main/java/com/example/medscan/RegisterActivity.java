package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText txtFirstName ,txtLastName , txtEmail ,txtPass, txtpassconfirm;
    ImageView btnGoogle,btnFacebook,btnTwitter;
    String pw = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{8,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{8,})(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$";
    FirebaseAuth fAuth;
    TextView signin ;
    Button signup;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://medscan-36621-default-rtdb.firebaseio.com/");
    boolean passvisible;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        signin=findViewById(R.id.Sign_in);
        signup=findViewById(R.id.btn_signup);
        txtFirstName=findViewById(R.id.txt_fname);
        txtLastName = findViewById(R.id.txt_lname);
        txtEmail = findViewById(R.id.Email);
        txtPass = findViewById(R.id.Password);
        txtpassconfirm=findViewById(R.id.confirm_Password);
        fAuth = FirebaseAuth.getInstance();
        btnFacebook = findViewById(R.id.Facebook);
        btnGoogle = findViewById(R.id.Google);
        btnTwitter = findViewById(R.id.Twitter);

        txtPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if(event.getRawX()>= txtPass.getRight()-txtPass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = txtPass.getSelectionEnd();
                        if(passvisible) {
                            txtPass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off_icon,0);
                            txtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        }
                        else {
                            txtPass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            txtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                        }
                        txtPass.setSelection(selection);
                        return true;
                }
                }
                return false;
            }
        });

        txtpassconfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if(event.getRawX()>= txtpassconfirm.getRight()-txtpassconfirm.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = txtpassconfirm.getSelectionEnd();
                        if(passvisible) {
                            txtpassconfirm.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off_icon,0);
                            txtpassconfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        }
                        else {
                            txtpassconfirm.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            txtpassconfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                        }
                        txtpassconfirm.setSelection(selection);
                        return true;
                        }
                }
                return false;
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentgoogle = new Intent(RegisterActivity.this,GoogleAuth.class);
                intentgoogle.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intentgoogle);
            }
        });
        
        btnFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentfacebook = new Intent(RegisterActivity.this,FacebookAuth.class);
                    intentfacebook.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intentfacebook);

                }
        });

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttwitter = new Intent(RegisterActivity.this,TwitterAuth.class);
                intenttwitter.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intenttwitter);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstname = txtFirstName.getText().toString();
                String lastname = txtLastName.getText().toString();
                String email = txtEmail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();
                String confirm_password = txtpassconfirm.getText().toString().trim();


                if (firstname.isEmpty()) {
                    txtFirstName.setError("First Name is required");
                    txtFirstName.requestFocus();

                }
                if (lastname.isEmpty()) {
                    txtLastName.setError("Last Name is required");
                    txtLastName.requestFocus();
                }
                if (TextUtils.isEmpty(email)) {
                    txtEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    txtPass.setError("Password is required");
                    return;
                }
                if (confirm_password.isEmpty()){
                    txtpassconfirm.setError("confirm password is empty");
                    txtpassconfirm.requestFocus();
                    return;
                }
                if(! password.equals(confirm_password)){
                    txtpassconfirm.setError("Confirm the password does not match the password");
                    txtpassconfirm.requestFocus();
                    return;
                }
                if (!password.matches(pw)) {
                    txtPass.setError("Password must contain at least 8 (Upper case, Lower case,Numbers and signs)");
                    txtPass.requestFocus();
                }
                else{

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser rUser = fAuth.getCurrentUser();
                                assert rUser != null;
                                String userId =rUser.getUid();
                                databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(userId);
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("First Name",firstname);
                                hashMap.put("Last Name",lastname);
                                hashMap.put("Email",email);
                                hashMap.put("Password",password);

                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                       Intent intent = new Intent(RegisterActivity.this,Login.class);
                                                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                                                        startActivity(intent);
                                                        Toast.makeText(RegisterActivity.this, "User created Successfully..Please check you email for Verification ", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                                                    }

                                                }
                                            });
                                        }else {
                                            Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                            }else {
                                Toast.makeText(RegisterActivity.this,Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign=new Intent(RegisterActivity.this,Login.class);
                startActivity(sign);
            }
        });


    }
}