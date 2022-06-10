package com.example.medscan.menu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.example.medscan.SessionManager;
import com.example.medscan.UserHelper;
import com.example.medscan.Welcome;
import com.example.medscan.databinding.ActivityPatientEditBinding;
import com.example.medscan.eyes_image;
import com.example.medscan.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientEdit extends AppCompatActivity {
    TextView password;
    TextView fullname,emailedittext, phone, medical, clinic, time, other;
    String _EMAIL;
    CircleImageView profileimage;
    Button update ;
    ImageView input_btn;
    FirebaseDatabase database;
    FirebaseAuth authProfile;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    FirebaseUser firebaseUser ;
    ActivityPatientEditBinding binding;
    ActivityResultLauncher<String> launcher;
    SessionManager sessionManager;
    ProgressBar progressBar;
    private int STORAGE_PERMISSION_CODE = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);

        progressBar=findViewById(R.id.progrsess_edit2);

        sessionManager=new SessionManager(getApplicationContext());
        String templang = Locale.getDefault().getLanguage();
        binding = ActivityPatientEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        database.getReference("Users").child(firebaseUser.getUid()).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get().load(image).into(profileimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.profilePic.setImageURI(result);

                final StorageReference reference = storage.getReference("Users").child(firebaseUser.getUid()).child("image");
                reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {

                               database.getReference("Users").child(firebaseUser.getUid()).child("image").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {

                                   @Override
                                   public void onSuccess(Void unused) {
                                      // progressBar.setVisibility(View.GONE);
                                       if(templang == "ar")
                                       {
                                           Toast.makeText(getApplicationContext(),"تم رفع الصورة ",Toast.LENGTH_SHORT).show();
                                       }
                                       else
                                       {
                                           Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_SHORT).show();

                                          // progressBar.setVisibility(View.GONE);
                                       }
                                   }
                               });
                           }
                       }) ;
                    }
                });
            }
        });

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(PatientEdit.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)

                {
                    launcher.launch(
                            "image/*"
                    );
                 //   progressBar.setVisibility(View.VISIBLE);

                }
                else
                {
                    requesrtstoragepermission();

                }


            }
        });


        profileimage=findViewById(R.id.profile_pic);
        password = findViewById(R.id.txt_reset);

        fullname = findViewById(R.id.fullnameedit);
        emailedittext = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        medical = findViewById(R.id.editTextTextPersonName);
        clinic = findViewById(R.id.editTextTextPersonName3);
        time = findViewById(R.id.editTextTextPersonName4);
        other = findViewById(R.id.editTextTextPersonName5);
        update = findViewById(R.id.button3);
        input_btn=findViewById(R.id.camera);
        database = FirebaseDatabase.getInstance();
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper userHelper = snapshot.getValue(UserHelper.class);
                assert userHelper != null;
                fullname.setText(userHelper.getFull_Name());
                phone.setText(userHelper.getPhone());
                medical.setText(userHelper.getMedical());
                clinic.setText(userHelper.getAddress());
                time.setText(userHelper.getTime());
                other.setText(userHelper.getOther());

                if( userHelper.getMedical().equals(""))
                {
                    phone.setEnabled(false);
                    medical.setEnabled(false);
                    clinic.setEnabled(false);
                    time.setEnabled(false);
                    other.setEnabled(false);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PatientEdit.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

       showProfile(firebaseUser);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent reset = new Intent(PatientEdit.this, ForgetPassword.class);
                startActivity(reset);
            }
        });
    }

    private void requesrtstoragepermission()
    {
        String templang = Locale.getDefault().getLanguage();

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            if(templang == "ar")
            {
                new AlertDialog.Builder(this)
                        .setTitle("مطلوب إذن ")
                        .setMessage("يريد هذا الإذن الوصول إلي معرض الصور")
                        .setPositiveButton("حسنا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PatientEdit.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .create().show();

            }
            else
            {
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is needed to upload images")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PatientEdit.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .create().show();

            }

        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }


    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userIdRegistered = firebaseUser.getUid();
        DatabaseReference referenceProfile =FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userIdRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                _EMAIL = firebaseUser.getEmail();
                emailedittext.setText(_EMAIL);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String templang = Locale.getDefault().getLanguage();

                if(templang == "ar")
                {
                    Toast.makeText(PatientEdit.this, "حدث خطأ ! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PatientEdit.this, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();
                }

            }
        });


       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {



               String Address = clinic.getText().toString();
               String Phone = phone.getText().toString();
               String Time = time.getText().toString();
               String Medical = medical.getText().toString();
               String Other = other.getText().toString();
               String fName = fullname.getText().toString();
               String templang = Locale.getDefault().getLanguage();


               if( Medical.isEmpty())
               {
                   if (fName.isEmpty()) {
                       if(templang == "ar")
                       {
                           fullname.setError("من فضلك قم بكتابة اسمك ");
                           fullname.requestFocus();
                           return;
                       }
                       else
                       {
                           fullname.setError("Your Name is required");
                           fullname.requestFocus();
                           return;
                       }

                   }
                   else{

                       HashMap<String, Object> map = new HashMap<>();
                       map.put("Full_Name", fName);
                       databaseReference.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               if(templang == "ar")
                               {
                                   Toast.makeText(PatientEdit.this, "لقد تم تعديل بياناتك بنجاح  ", Toast.LENGTH_SHORT).show();
                               }
                               else
                               {
                                   Toast.makeText(PatientEdit.this, "Your Data is successfully updated ", Toast.LENGTH_SHORT).show();
                               }

                           }
                       });

                   }

               }
               else
               {
                   if (fName.isEmpty()) {

                       if(templang == "ar")
                       {
                           fullname.setError("من فضلك قم بكتابة اسمك ");
                           fullname.requestFocus();
                           return;
                       }
                       else
                       {
                           fullname.setError("Your Name is required");
                           fullname.requestFocus();
                           return;
                       }

                   }

                   if (Medical.isEmpty()) {
                       if(templang == "ar")
                       {
                           medical.setError("قم بكتابة مجالك الطبي ");
                           medical.requestFocus();
                           return;
                       }
                       else
                       {
                           medical.setError("Please enter your medical specialty");
                           medical.requestFocus();
                           return;
                       }
                   }
                   if (! Medical.equals("Kidney") && ! Medical.equals("eyes") && ! Medical.equals("Skin") && ! Medical.equals("Lungs")
                           && ! Medical.equals("Eyes") && ! Medical.equals("kidney") && ! Medical.equals("skin") && ! Medical.equals("lungs")
                           && ! Medical.equals("عيون") && ! Medical.equals("كلى") && ! Medical.equals("جلد") && ! Medical.equals("رئة")
                           &&! Medical.equals("Kidney ") && ! Medical.equals("eyes ") && ! Medical.equals("Skin ") && ! Medical.equals("Lungs ")
                           && ! Medical.equals("Eyes ") && ! Medical.equals("kidney ") && ! Medical.equals("skin ") && ! Medical.equals("lungs ")
                           && ! Medical.equals("عيون ") && ! Medical.equals("كلى ") && ! Medical.equals("جلد ") && ! Medical.equals("رئة "))
                   {
                       if(templang == "ar")
                       {
                           medical.setError("من فضلك اختار ( عيون ، كلي ، رئة ، جلد )");
                           medical.requestFocus();
                           return;
                       }
                       else
                       {
                           medical.setError("Please enter (Kidney or Lungs or eyes or skin )");
                           medical.requestFocus();
                           return;
                       }
                   }
                   if (Address.isEmpty()) {
                       if(templang == "ar")
                       {
                           clinic.setError("قم بكتابة عنوان العيادة ");
                           clinic.requestFocus();
                           return;
                       }
                       else
                       {
                           clinic.setError("Please enter your clinic address");
                           clinic.requestFocus();
                           return;
                       }
                   }
                   if (Phone.isEmpty()) {
                       if(templang == "ar")
                       {
                           phone.setError("قم بكتابة رقم الموبايل ");
                           phone.requestFocus();
                           return;
                       }
                       else
                       {
                           phone.setError("Please enter your Phone number");
                           phone.requestFocus();
                           return;
                       }
                   }
                   if (Phone.length()!=11) {
                       if(templang == "ar")
                       {
                           phone.setError("من فضلك قم بكتابة رقم صحيح");
                           phone.requestFocus();
                           return;
                       }
                       else
                       {
                           phone.setError("Please enter a valid number");
                           phone.requestFocus();
                           return;
                       }
                   }
                   if (Time.isEmpty()) {
                       if(templang == "ar")
                       {
                           time.setError("قم بكتابة وقتك المتاح");
                           time.requestFocus();
                           return;
                       }
                       else
                       {
                           time.setError("Please enter your available time");
                           time.requestFocus();
                           return;
                       }
                   }
                   if (Other.isEmpty()) {
                       if(templang == "ar")
                       {
                           other.setError("اذا كان هناك أي معلومات أخري من فضلك قم بكتابتها..");
                           other.requestFocus();
                           return;
                       }
                       else
                       {
                           other.setError("If you have any other information ,please write it here..If not,Write Nothing");
                           other.requestFocus();
                           return;
                       }
                   }
                   else{

                       HashMap<String, Object> map = new HashMap<>();
                       map.put("address", Address);
                       map.put("phone", Phone);
                       map.put("medical", Medical);
                       map.put("time", Time);
                       map.put("other", Other);
                       map.put("Full_Name", fName);
                       databaseReference.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               if(templang == "ar")
                               {
                                   Toast.makeText(PatientEdit.this, "لقد تم تعديل بياناتك بنجاح  ", Toast.LENGTH_SHORT).show();
                               }
                               else
                               {
                                   Toast.makeText(PatientEdit.this, "Your Data is successfully updated ", Toast.LENGTH_SHORT).show();
                               }

                           }
                       });

                   }

               }

           }

       });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String templang = Locale.getDefault().getLanguage();

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (templang == "ar") {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "تم أخذ الإذن ", Toast.LENGTH_SHORT).show();
                    launcher.launch(
                            "image/*"
                    );

                } else {
                    Toast.makeText(this, "تم رفض الإذن ", Toast.LENGTH_SHORT).show();


                }
            } else {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    launcher.launch(
                            "image/*"
                    );

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();


                }
            }


        }
    }

}