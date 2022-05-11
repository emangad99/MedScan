package com.example.medscan.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import com.example.medscan.R;
import com.example.medscan.UserHelper;
import com.example.medscan.databinding.ActivityPatientEditBinding;
import com.google.android.gms.tasks.OnSuccessListener;
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
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser ;
    ActivityPatientEditBinding binding;
    ActivityResultLauncher<String> launcher;

    ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar=findViewById(R.id.progrsess_edit);

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
               // progressBar.setVisibility(View.VISIBLE);

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
                                       Toast.makeText(getApplicationContext(),"Image uploaded",Toast.LENGTH_SHORT).show();
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
                launcher.launch(
                        "image/*"
                );

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
        progressDialog=new ProgressDialog(PatientEdit.this);



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
                Toast.makeText(PatientEdit.this, "SomeThing went wrong !", Toast.LENGTH_SHORT).show();


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
                       Toast.makeText(PatientEdit.this, "Your Data is successfully updated ", Toast.LENGTH_SHORT).show();


                   }
               });

           }

       });

    }

}