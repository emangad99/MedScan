package com.example.medscan.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.net.URL;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientEdit extends AppCompatActivity {
    TextView password;
    TextView fullname,emailedittext, phone, medical, clinic, time, other;
    String _FULLNAME ,_EMAIL, _PHONE ,_MEDICAL ,_CLINIC ,_TIME ,_OTHER;
    CircleImageView profileimage;
    Button update;
    FirebaseDatabase database;
    FirebaseAuth authProfile;
    Uri imageUri;
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageprofilepicsRef;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit);


        password = findViewById(R.id.txt_reset);
        fullname = findViewById(R.id.fullnameedit);
        emailedittext = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        medical = findViewById(R.id.editTextTextPersonName);
        clinic = findViewById(R.id.editTextTextPersonName3);
        time = findViewById(R.id.editTextTextPersonName4);
        other = findViewById(R.id.editTextTextPersonName5);
        update = findViewById(R.id.button3);
        database = FirebaseDatabase.getInstance();
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageprofilepicsRef = FirebaseStorage.getInstance().getReference().child("profile pc");

       showProfile(firebaseUser);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reset = new Intent(PatientEdit.this, ForgetPassword.class);
                startActivity(reset);
            }


        });

        update.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            uploadprofileImage();
        }
        });



        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(PatientEdit.this);
            }
        });

        getUserinfo();


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(this.getResources().getColor(R.color.color3));
        }

    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userIdRegistered = firebaseUser.getUid();
        DatabaseReference referenceProfile =FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userIdRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                _FULLNAME = firebaseUser.getDisplayName();
                _EMAIL = firebaseUser.getEmail();
                fullname.setText(_FULLNAME);
                emailedittext.setText(_EMAIL);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PatientEdit.this, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();


            }
        });
        DatabaseReference referenceProfile2 =FirebaseDatabase.getInstance().getReference("Donors");
        referenceProfile2.child(userIdRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper userHelper = snapshot.getValue(UserHelper.class);
                if(userHelper !=null){
                    _CLINIC = userHelper.getAddress();
                    _MEDICAL = userHelper.getMedical();
                    _PHONE = userHelper.getPhone();
                    _TIME = userHelper.getTime();
                    _OTHER = userHelper.getOther();

                    phone.setText(_PHONE);
                    time.setText(_TIME);
                    medical.setText(_MEDICAL);
                    other.setText(_OTHER);
                    clinic.setText(_CLINIC);

                }
                else{
                    Toast.makeText(PatientEdit.this, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PatientEdit.this, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();

            }
        });
      /*  update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });*/


    }

    private void updateProfile(FirebaseUser firebaseUser) {
        String Address = clinic.getText().toString();
        String Phone = phone.getText().toString();
        String Time = time.getText().toString();
        String Medical = medical.getText().toString();
        String Other = other.getText().toString();
        String fName = fullname.getText().toString();
        String email = emailedittext.getText().toString().trim();

        if (Medical.isEmpty()) {
            medical.setError("Please enter your medical specialty");
            medical.requestFocus();
            return;
        }
        if (!Medical.equals("Kidney") && !Medical.equals("eyes") && !Medical.equals("Teeth") && !Medical.equals("Lungs")
                && !Medical.equals("Eyes") && !Medical.equals("kidney") && !Medical.equals("teeth") && !Medical.equals("lungs")) {
            medical.setError("Please enter (Kidney or Lungs or eyes )");
            medical.requestFocus();
            return;
        }
        if (Address.isEmpty()) {
            clinic.setError("Please enter your clinic address");
            clinic.requestFocus();
            return;
        }
        if (Phone.isEmpty()) {
            phone.setError("Please enter your Phone number");
            phone.requestFocus();
            return;
        }
        if (Phone.length() != 11) {
            phone.setError("Please enter a valid number");
            phone.requestFocus();
            return;
        }
        if (Time.isEmpty()) {
            time.setError("Please enter your available time");
            time.requestFocus();
            return;
        }
        if (Other.isEmpty()) {
            other.setError("If you have any other information ,please write it here..If not,Write Nothing");
            other.requestFocus();
            return;
        }
        if (fName.isEmpty()) {
            fullname.setError("Full Name is required");
            fullname.requestFocus();

        }

        if (TextUtils.isEmpty(email)) {
            emailedittext.setError("Email is required");
            return;
        } else {

            Address = clinic.getText().toString();
            Phone = phone.getText().toString();
            Time = time.getText().toString();
            Medical = medical.getText().toString();
            Other = other.getText().toString();
            fName = fullname.getText().toString();
            email = emailedittext.getText().toString().trim();


        }


    }



    private void getUserinfo() {
        databaseReference.child(authProfile.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0)
                {
                    if( snapshot.hasChild("image"))
                    {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileimage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileimage.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this,"ERROR TRY AGAIN ", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadprofileImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("set your profile");
        progressDialog.setMessage( "pease wait");
        progressDialog.show();

        if(imageUri != null){
            final  StorageReference fileRef = storageprofilepicsRef.child(authProfile.getCurrentUser().getUid()+ "jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @NonNull
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task <Uri>task) {
                    if(task.isSuccessful())
                    {
                        Uri dowenoadUri =task.getResult();
                        myUri = dowenoadUri.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image",myUri);

                        databaseReference.child(authProfile.getCurrentUser().getUid()).updateChildren(userMap);

                        progressDialog.dismiss();

                    }

                }

            });
        }else
        {
            progressDialog.dismiss();
            Toast.makeText(this,"Image not Selected", Toast.LENGTH_SHORT).show();
        }
    }


}