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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medscan.lungs.ApiInterface;
import com.example.medscan.lungs.RealPathUtil;
import com.example.medscan.lungs.Result;
import com.example.medscan.lungs.covid;
import com.example.medscan.lungs.covid_advice;
import com.example.medscan.lungs.failed;
import com.example.medscan.lungs.successful;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RayUploaded extends AppCompatActivity {

    ImageView btn_choose , image;
    Button upload ;
    ProgressBar prog_bar;
    private Uri imageuri;
    int SELECT_PHOTO=2;
    private int STORAGE_PERMISSION_CODE = 1 ;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("image_lung");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    String path;
    String templang = Locale.getDefault().getLanguage();
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ray_uploaded);

        btn_choose = findViewById(R.id.choose);
        image=findViewById(R.id.image_ray);
        upload=findViewById(R.id.btn_upload_rays);
        prog_bar = findViewById(R.id.prog_bar);
        prog_bar.setVisibility(View.INVISIBLE);
        text=findViewById(R.id.txt_upload);


        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prog_bar.setVisibility(View.INVISIBLE);

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

                    prog_bar.setVisibility(View.VISIBLE);

                    result();

                }else{
                    if(templang == "ar")
                    {
                        Toast.makeText(RayUploaded.this, "قم بإختيار الصورة", Toast.LENGTH_SHORT).show();
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
            Context c = RayUploaded.this;
            path = RealPathUtil.getRealPath(c, imageuri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            image.setImageBitmap(bitmap);
            text.setVisibility(View.GONE);

        }
    }
    private void requesrtstoragepermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            if(templang == "ar")
            {
                new AlertDialog.Builder(this)
                        .setTitle("مطلوب إذن")
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
                    Toast.makeText(this, "تم أخذ الإذن", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "تم رفض الإذن", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else
                {
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
                        Toast.makeText(RayUploaded.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RayUploaded.this, "Uploading failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  String getFileExtention(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    public void result(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ai-team.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Result> call = apiInterface.addImage(body);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getData().toString().equals("Covid-19")){


                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent covid = new Intent(RayUploaded.this, covid.class);
                                    startActivity(covid);
                                }
                            }, 10000);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with covid-19", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("PNEUMONIA")){
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent pneumonia = new Intent(RayUploaded.this, failed.class);
                                    startActivity(pneumonia);
                                }
                            }, 10000);
                        }
                        else if (response.body().getData().toString().equals("NORMAL")){
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent normal = new Intent(RayUploaded.this, successful.class);
                                    startActivity(normal);
                                }
                            }, 10000);
                        }
                        else {
                            if(templang == "ar")
                            {
                                Toast.makeText(getApplicationContext(), "هناك خطأ ! من فضلك قم بالمحاولة مرة اخرى .. ", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Something went wrong! Please Retry Again", Toast.LENGTH_LONG).show();
                            }
                        }
                    }



                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
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