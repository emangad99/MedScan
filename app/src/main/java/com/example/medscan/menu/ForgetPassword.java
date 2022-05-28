package com.example.medscan.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medscan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class ForgetPassword extends AppCompatActivity {
    Button reset , auth;
    EditText oldPass , newPass,confirmPass;
    FirebaseAuth authProfile;

    boolean passvisible;
    String pw = "^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{8,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{8,})(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        reset=findViewById(R.id.btn_reset_password);
        oldPass=findViewById(R.id.txt_old_password);
        newPass=findViewById(R.id.txt_new_password);
        confirmPass=findViewById(R.id.txt_confirm_password);
        auth=findViewById(R.id.authentication);
        String templang = Locale.getDefault().getLanguage();
        newPass.setEnabled(false);
        confirmPass.setEnabled(false);
        reset.setEnabled(false);
        auth.setBackgroundTintList(ContextCompat.getColorStateList(
                ForgetPassword.this,R.color.color2
        ));
        reset.setBackgroundTintList(ContextCompat.getColorStateList(
                ForgetPassword.this,R.color.gray
        ));

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =authProfile.getCurrentUser();
        if (firebaseUser.equals((""))){
            if(templang == "ar")
            {
                Toast.makeText(ForgetPassword.this, "هناك خطأ .. ", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(ForgetPassword.this, "Something went wrong ! User's details not available", Toast.LENGTH_SHORT).show();
            }
            Intent nnn = new Intent(ForgetPassword.this, PatientEdit.class);
            startActivity(nnn);
            finish();

        }else {

            reAuthenticateUser(firebaseUser);

        }


        newPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if(event.getRawX()>= newPass.getRight()-newPass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = newPass.getSelectionEnd();
                        if(passvisible) {
                            newPass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off_icon,0);
                            newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        }
                        else {
                            newPass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                        }
                        newPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        confirmPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if(event.getRawX()>= confirmPass.getRight()-confirmPass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = confirmPass.getSelectionEnd();
                        if(passvisible) {
                            confirmPass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off_icon,0);
                            confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        }
                        else {
                            confirmPass.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            confirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                        }
                        confirmPass.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = oldPass.getText().toString();
                String templang = Locale.getDefault().getLanguage();


                if (oldpass.isEmpty()) {
                    if(templang == "ar")
                    {
                        oldPass.setError("من فضلك قم بكتابة الباسورد الحالي للمصادقة ");
                        oldPass.requestFocus();
                        return;
                    }
                    else
                    {
                        oldPass.setError("please enter your current password to authenticate ! ");
                        oldPass.requestFocus();
                        return;
                    }
                } else {
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldpass);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                oldPass.setEnabled(false);
                                newPass.setEnabled(true);
                                confirmPass.setEnabled(true);
                                auth.setEnabled(false);
                                auth.setBackgroundTintList(ContextCompat.getColorStateList(
                                        ForgetPassword.this,R.color.gray
                                ));

                                reset.setEnabled(true);
                                reset.setBackgroundTintList(ContextCompat.getColorStateList(
                                        ForgetPassword.this,R.color.color2
                                ));
                                if(templang == "ar")
                                {
                                    Toast.makeText(ForgetPassword.this, "تمت المصادقة بنجاح ." + " يمكنك تغيير كلمة السر الأن .. ", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(ForgetPassword.this, "Password has been verified." + " Change your password now", Toast.LENGTH_SHORT).show();

                                }

                                reset.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        changePwd(firebaseUser);
                                    }
                                });
                            } else {
                                try {
                                    throw task.getException();

                                } catch (Exception e) {
                                    Toast.makeText(ForgetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        private void changePwd(FirebaseUser firebaseUser) {

                            String newpass = newPass.getText().toString();
                            String confirmpass = confirmPass.getText().toString();

                            if (newpass.isEmpty()) {
                                if(templang == "ar")
                                {
                                    newPass.setError("كلمة المرور مطلوبة");
                                    newPass.requestFocus();
                                    return;
                                }
                                else
                                {
                                    newPass.setError("Password is required");
                                    newPass.requestFocus();
                                    return;
                                }
                            }
                            if (confirmpass.isEmpty()) {
                                if(templang == "ar")
                                {
                                    confirmPass.setError("تأكيد كلمة المرور");
                                    confirmPass.requestFocus();
                                    return;
                                }
                                else
                                {
                                    confirmPass.setError("confirm password is empty");
                                    confirmPass.requestFocus();
                                    return;
                                }

                            }
                            if(! newpass.equals(confirmpass)){

                                if(templang == "ar")
                                {
                                    confirmPass.setError("من فضلك يرجى إدخال نفس كلمة المرور");
                                    confirmPass.requestFocus();
                                    return;
                                }
                                else
                                {
                                    confirmPass.setError("Please re-enter the same password");
                                    confirmPass.requestFocus();
                                    return;
                                }
                            }
                            if(oldpass.equals(newpass)){
                                if(templang == "ar")
                                {
                                    newPass.setError("لا يكن إعادة كتابة نفس كلمة المرور القديمة");
                                    newPass.requestFocus();
                                    return;
                                }
                                else
                                {
                                    newPass.setError("new password cannot be the same as old password");
                                    newPass.requestFocus();
                                    return;
                                }
                            }
                            if (!newpass.matches(pw)) {
                                if(templang == "ar")
                                {
                                    newPass.setError("يجب أن تحتوي كلمة المرور على 8 أحرف على الأقل ( الأحرف الكبيرة ، الصغيرة ، والارقام والرموز )");
                                    newPass.requestFocus();
                                }
                                else
                                {
                                    newPass.setError("Password must contain at least 8 (Upper case, Lower case,Numbers and signs)");
                                    newPass.requestFocus();
                                }

                            }else{
                                firebaseUser.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Intent reset=new Intent(ForgetPassword.this,SuccessResetPass.class);
                                            startActivity(reset);
                                        }else {
                                            try {
                                                {
                                                    throw task.getException();

                                                }
                                            }catch (Exception e){
                                                Toast.makeText(ForgetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
    }

}