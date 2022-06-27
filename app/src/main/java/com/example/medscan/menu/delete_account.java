package com.example.medscan.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medscan.R;
import com.example.medscan.SessionManager;
import com.example.medscan.Welcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class delete_account extends AppCompatActivity {

    EditText password;
    Button delete;
    FirebaseAuth authProfile;
    String templang = Locale.getDefault().getLanguage();
    ProgressDialog progressDialog;
    SessionManager sessionManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        password=findViewById(R.id.txt_old_password2);
        delete=findViewById(R.id.delete_account);
        progressDialog=new ProgressDialog(delete_account.this);
        sessionManager=new SessionManager(getApplicationContext());

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =authProfile.getCurrentUser();
        if (firebaseUser.equals((""))){
            if(templang == "ar")
            {
                Toast.makeText(delete_account.this, "هناك خطأ .. ", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(delete_account.this, "Something went wrong ! User's details not available", Toast.LENGTH_SHORT).show();
            }
            Intent nnn = new Intent(delete_account.this, Welcome.class);
            startActivity(nnn);
            finish();

        }else {

            reAuthenticateUser(firebaseUser);

        }

    }


    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = password.getText().toString();


                if (oldpass.isEmpty())
                {
                    if(templang == "ar")
                    {
                        password.setError("من فضلك قم بكتابة الباسورد الحالي للمصادقة ");
                        password.requestFocus();
                        return;
                    }
                    else
                    {
                        password.setError("please enter your current password to authenticate ! ");
                        password.requestFocus();
                        return;
                    }
                }
                else{
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), oldpass);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                if(templang == "ar")
                                {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(delete_account.this);
                                    dialog.setTitle("هل انت متأكد ؟ ");
                                    dialog.setMessage("سيؤدي حذف هذا الحساب إلى إزالة حسابك تمامًا ولن تتمكن من الوصول إلى التطبيق.");
                                    dialog.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            delete_current_user();

                                        }
                                    });
                                    dialog.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = dialog.create();
                                    alertDialog.show();
                                }
                                else
                                {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(delete_account.this);
                                    dialog.setTitle("Are you sure ?");
                                    dialog.setMessage("Deleting this account will result in completely removing your account from the system and you won't be able to access the app.");
                                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            delete_current_user();

                                        }
                                    });
                                    dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alertDialog = dialog.create();
                                    alertDialog.show();
                                }







                            } else {
                                try {
                                    throw task.getException();

                                } catch (Exception e) {
                                    Toast.makeText(delete_account.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

                }





            }
        });
    }



    private void delete_current_user() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait ... ");
        progressDialog.setMessage("We are deleting your account.");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            sessionManager.setLogin(false);
                            sessionManager.setUsername("");
                            Intent intent = new Intent(delete_account.this, Welcome.class);
                            startActivity(intent);
                        }
                        else{

                            Toast.makeText(delete_account.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
}