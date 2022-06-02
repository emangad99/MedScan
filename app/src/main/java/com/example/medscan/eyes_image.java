package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class eyes_image extends AppCompatActivity {

    int SELECT_PHOTO=2;
    private int STORAGE_PERMISSION_CODE = 1 ;
    ImageView btn_choose , image , btn_camera;
    Button upload ;
    private Uri imageuri;
    String currentPhotoPath;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("image_eyes");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes_image);

        text=findViewById(R.id.text_eyes);
        btn_choose = findViewById(R.id.choose);
        image=findViewById(R.id.image_ray);
        upload=findViewById(R.id.btn_upload_rays);
        btn_camera=findViewById(R.id.camera);
        String templang = Locale.getDefault().getLanguage();

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(eyes_image.this,
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
                        Toast.makeText(eyes_image.this, "قم بإختيار الصورة", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(eyes_image.this, "Please select image ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    dispatchTakePictureIntent();
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK && data != null )
        {
            imageuri = data.getData();
            image.setImageURI(imageuri);
            text.setVisibility(View.GONE);

        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            File f = new File (currentPhotoPath);
            image.setImageURI(Uri.fromFile(f));
            Log.d("tag","ABsolute Url of Image is "+ Uri.fromFile(f));

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            imageuri = Uri.fromFile(f);
            mediaScanIntent.setData(imageuri);
            this.sendBroadcast(mediaScanIntent);

            text.setVisibility(View.GONE);




        }

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
                                ActivityCompat.requestPermissions(eyes_image.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

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
                                ActivityCompat.requestPermissions(eyes_image.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

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



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String templang = Locale.getDefault().getLanguage();

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
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if(templang == "ar")
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "تم منح إذن الكاميرا", Toast.LENGTH_LONG).show();
                    dispatchTakePictureIntent();

                }
                else
                {
                    Toast.makeText(this, "تم رفض إذن الكاميرا", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                    dispatchTakePictureIntent();

                }
                else
                {
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
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

                        Model4 model = new Model4(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
                        String templang = Locale.getDefault().getLanguage();
                        if(templang == "ar")
                        {
                            Toast.makeText(eyes_image.this, "تم الرفع بنجاح", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(eyes_image.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String templang = Locale.getDefault().getLanguage();

                if(templang == "ar")
                {
                    Toast.makeText(eyes_image.this, "حدث خطأ", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(eyes_image.this, "Uploading failed", Toast.LENGTH_SHORT).show();

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
class  Model3{
    private String imageUri;
    public Model3(){

    }
    public  Model3(String imageUri){
        this.imageUri = imageUri;
    }
    public  String getImageUri(){
        return imageUri;

    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

}


