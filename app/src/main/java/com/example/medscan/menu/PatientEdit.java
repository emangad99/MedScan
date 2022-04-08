package com.example.medscan.menu;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
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
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientEdit extends AppCompatActivity {
    TextView password;
    TextView fullname,emailedittext, phone, medical, clinic, time, other;
    String _FULLNAME ,_EMAIL, _PHONE ,_MEDICAL ,_CLINIC ,_TIME ,_OTHER;
    CircleImageView profileimage;
    Button update ;
    ImageView input_btn;
    FirebaseDatabase database;
    FirebaseAuth authProfile;
    Uri imageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int image_request_code = 7;
    ProgressDialog progressDialog;



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
        input_btn=findViewById(R.id.camera);
        database = FirebaseDatabase.getInstance();
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        progressDialog=new ProgressDialog(PatientEdit.this);





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

        input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CropImage.activity().setAspectRatio(1,1).start(PatientEdit.this);
            }
        });




        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(this.getResources().getColor(R.color.color3));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image_request_code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileimage.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    private void uploadprofileImage() {

        if (imageUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(imageUri));
            storageReference2.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            uploadinfo imageUploadInfo = new uploadinfo(  taskSnapshot.getUploadSessionUri().toString());
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(PatientEdit.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

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
   /*    update.setOnClickListener(new View.OnClickListener() {
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




}