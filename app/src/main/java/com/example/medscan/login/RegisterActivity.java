package com.example.medscan.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medscan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    EditText txtFirstName , txtEmail ,txtPass, txtpassconfirm;
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
        txtEmail = findViewById(R.id.Email);
        txtPass = findViewById(R.id.Password);
        txtpassconfirm=findViewById(R.id.confirm_Password);
        fAuth = FirebaseAuth.getInstance();
        btnFacebook = findViewById(R.id.Facebook);
        btnGoogle = findViewById(R.id.Google);
        btnTwitter = findViewById(R.id.Twitter);
        String templang = Locale.getDefault().getLanguage();

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
                String email = txtEmail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();
                String confirm_password = txtpassconfirm.getText().toString().trim();


                if (firstname.isEmpty()) {

                    if(templang == "ar")
                    {
                        txtFirstName.setError("يجب عليك كتابة الاسم ");
                        txtFirstName.requestFocus();
                    }
                    else
                    {
                        txtFirstName.setError("Full Name is required");
                        txtFirstName.requestFocus();
                    }

                }

                if (TextUtils.isEmpty(email)) {

                    if(templang == "ar")
                    {
                        txtEmail.setError("يجب عليك كتابة البريد الالكتروني");
                        return;
                    }
                    else
                    {
                        txtEmail.setError("Email is required");
                        return;
                    }
                }
                if (TextUtils.isEmpty(password)) {

                    if(templang == "ar")
                    {
                        txtPass.setError("كلمة المرور مطلوبة");
                        return;
                    }
                    else
                    {
                        txtPass.setError("Password is required");
                        return;
                    }

                }
                if (confirm_password.isEmpty()){
                    if(templang == "ar")
                    {
                        txtpassconfirm.setError("تأكيد كلمة المرور");
                        txtpassconfirm.requestFocus();
                        return;
                    }
                    else
                    {
                        txtpassconfirm.setError("confirm password is empty");
                        txtpassconfirm.requestFocus();
                        return;
                    }

                }
                if(! password.equals(confirm_password)){

                    if(templang == "ar")
                    {
                        txtpassconfirm.setError("Confirm the password does not match the password");
                        txtpassconfirm.setError("كلمة المرور ليست متطابقة");
                        txtpassconfirm.requestFocus();
                        return;
                    }
                    else
                    {
                        txtpassconfirm.setError("Confirm the password does not match the password");
                        txtpassconfirm.requestFocus();
                        return;
                    }

                }
                if (!password.matches(pw)) {

                    if(templang == "ar")
                    {
                        txtPass.setError("يجب أن تحتوي كلمة المرور على 8 أحرف على الأقل ( الحروف الكبيرة ، الصغيرة ، والارقام والرموز )");
                        txtPass.requestFocus();
                    }
                    else
                    {
                        txtPass.setError("Password must contain at least 8 (Upper case, Lower case,Numbers and signs)");
                        txtPass.requestFocus();
                    }

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
                                hashMap.put("Full_Name",firstname);
                                hashMap.put("Email",email);
                                hashMap.put("Password",password);
                                hashMap.put("userId",userId);
                                hashMap.put("medical","");
                                hashMap.put("image","https://firebasestorage.googleapis.com/v0/b/medscan-36621.appspot.com/o/Users%2FZuSrNSz6SaNcpBmUJQ7aJoMMc6l1%2Fimage?alt=media&token=58528708-03b5-4a29-94ee-2127a6bc1baa");

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
                                                        if(templang == "ar")
                                                        {
                                                            Toast.makeText(RegisterActivity.this, "لقد تم التسجيل بنجاح . من فضلك قم بمراجعة بريدك الالكتروني للمصادقة ", Toast.LENGTH_SHORT).show();

                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(RegisterActivity.this, "User created Successfully..Please check you email for Verification ", Toast.LENGTH_SHORT).show();

                                                        }
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