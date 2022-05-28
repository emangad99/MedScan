package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;

public class RayUploaded extends AppCompatActivity {

    ImageView btn_choose , image;
    Button upload ;
    private Uri imageuri;
    int SELECT_PHOTO=2;
    private int STORAGE_PERMISSION_CODE = 1 ;
    String templang = Locale.getDefault().getLanguage();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("image_lung");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ray_uploaded);

        btn_choose = findViewById(R.id.choose);
        image=findViewById(R.id.image_ray);
        upload=findViewById(R.id.btn_upload_rays);


        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(RayUploaded.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    Intent gallery = new Intent();
                    gallery.setAction(Intent.ACTION_PICK);
                    gallery.setType("image/*");
                    startActivityForResult(gallery,SELECT_PHOTO);


                }
                else
                {
                    requesrtstoragepermission();

                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( (imageuri != null)){
                    uplaodToFirebase(imageuri);

                }else{
                    if(templang == "ar")
                    {
                        Toast.makeText(RayUploaded.this, "من فضلك قم بإختيار الصورة ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(RayUploaded.this, "Please select image ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null )
        {
            imageuri = data.getData();
            image.setImageURI(imageuri);

        }


    }
    private void requesrtstoragepermission()
    {
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
                                ActivityCompat.requestPermissions(RayUploaded.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

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
                                ActivityCompat.requestPermissions(RayUploaded.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if(templang == "ar")
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "تم أخذ الإذن ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "تم رفض الإذن ", Toast.LENGTH_SHORT).show();


                }
            }
            else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();


                }
            }


        }

    }
    private  void  uplaodToFirebase(Uri uri){
        StorageReference fileRef =  reference.child(System.currentTimeMillis() + "." + getFileExtention(uri));
        fileRef.putFile((uri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Model model = new Model(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
                        if(templang == "ar")
                        {
                            Toast.makeText(RayUploaded.this, "تم الرفع بنجاح ", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(RayUploaded.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(templang == "ar")
                {
                    Toast.makeText(RayUploaded.this, "حدث خطأ ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RayUploaded.this, "Uploading failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private  String getFileExtention(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}
class  Model{
    private String imageUri;
    public Model(){

    }
    public  Model(String imageUri){
        this.imageUri = imageUri;
    }
    public  String getImageUri(){
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}