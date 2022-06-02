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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
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
import com.example.medscan.skin.SkinApiInterface;
import com.example.medscan.skin.SkinResult;
import com.example.medscan.skin.actinic_keratosis;
import com.example.medscan.skin.basal_cell_carcinoma;
import com.example.medscan.skin.dermatofibroma;
import com.example.medscan.skin.intraepithelial_carcinoma;
import com.example.medscan.skin.melanocytic_nevi;
import com.example.medscan.skin.melanoma;
import com.example.medscan.skin.seborrheic_keratosis;
import com.example.medscan.skin.successful_skin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class skin_upload extends AppCompatActivity {

    int SELECT_PHOTO=2;
    private int STORAGE_PERMISSION_CODE = 1 ;
    ImageView btn_choose , image , btn_camera;
    Button upload ;
    ProgressBar prog_bar;
    private Uri imageuri;
    String currentPhotoPath;
    private static final int MY_CAMERA_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST = 1888;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("image_skin");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    String templang = Locale.getDefault().getLanguage();
    TextView text;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_upload);

        text=findViewById(R.id.text_skin);
        btn_choose = findViewById(R.id.choose);
        image=findViewById(R.id.image_ray);
        upload=findViewById(R.id.btn_upload_rays);
        btn_camera=findViewById(R.id.camera);
        prog_bar = findViewById(R.id.prog_bar2);
        prog_bar.setVisibility(View.INVISIBLE);

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                prog_bar.setVisibility(View.INVISIBLE);

                if(ContextCompat.checkSelfPermission(skin_upload.this,
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
                    btn_choose.setVisibility(View.GONE);
                    btn_camera.setVisibility(View.GONE);

                    result();

                }else{
                    if(templang == "ar")
                    {
                        Toast.makeText(skin_upload.this, "من فضلك قم بإختيار الصورة ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(skin_upload.this, "Please select image ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                prog_bar.setVisibility(View.INVISIBLE);

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
            Context c = skin_upload.this;
            path = RealPathUtil.getRealPath(c, imageuri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            image.setImageBitmap(bitmap);
            text.setVisibility(View.GONE);
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            File f = new File (path);
            image.setImageURI(Uri.fromFile(f));
            Log.d("tag","ABsolute Url of Image is "+ Uri.fromFile(f));

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            imageuri = Uri.fromFile(f);
            mediaScanIntent.setData(imageuri);
            this.sendBroadcast(mediaScanIntent);

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
                                ActivityCompat.requestPermissions(skin_upload.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

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
                                ActivityCompat.requestPermissions(skin_upload.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

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
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        path = image.getAbsolutePath();
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
                    Toast.makeText(this, "تم أخذ إذن الكاميرا ", Toast.LENGTH_LONG).show();
                    dispatchTakePictureIntent();
                }
                else
                {
                    Toast.makeText(this, "تم رفض إذن الكاميرا", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED )
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
                        if(templang == "ar")
                        {
                            Toast.makeText(skin_upload.this, "تم الرفع بنجاح ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(skin_upload.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(templang == "ar")
                {
                    Toast.makeText(skin_upload.this, "حدث خطأ ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(skin_upload.this, "Uploading failed", Toast.LENGTH_SHORT).show();
                }
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

        SkinApiInterface skinapiInterface = retrofit.create(SkinApiInterface.class);
        Call<SkinResult> call = skinapiInterface.addImage(body);
        call.enqueue(new Callback<SkinResult>() {
            @Override
            public void onResponse(@NonNull Call<SkinResult> call, @NonNull Response<SkinResult> response) {
                if (response.isSuccessful()){

                    if (response.body() != null) {
                        if (response.body().getData().toString().equals("akiec")){

                            Intent akiec = new Intent(skin_upload.this, intraepithelial_carcinoma.class);
                            startActivity(akiec);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Actinic Keratosis And Intraepithelial Carcinoma", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("bcc")){

                            Intent bcc = new Intent(skin_upload.this, basal_cell_carcinoma.class);
                            startActivity(bcc);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Basal Cell Carcinoma", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("bkl")){

                            Intent bkl = new Intent(skin_upload.this, seborrheic_keratosis.class);
                            startActivity(bkl);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Benign Lesions Of The Keratosis", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("df")){

                            Intent df = new Intent(skin_upload.this, dermatofibroma.class);
                            startActivity(df);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Dermatofibroma", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("mel")){

                            Intent mel = new Intent(skin_upload.this, melanoma.class);
                            startActivity(mel);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Melanoma", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("nv")){

                            Intent nv = new Intent(skin_upload.this, melanocytic_nevi.class);
                            startActivity(nv);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Melanocytic nevi", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("vasc")){

                            Intent vasc = new Intent(skin_upload.this, actinic_keratosis.class);
                            startActivity(vasc);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Melanocytic nevi", Toast.LENGTH_LONG).show();
                        }
                        else if (response.body().getData().toString().equals("normal")){

                            Intent normal = new Intent(skin_upload.this, successful_skin.class);
                            startActivity(normal);

                            //Toast.makeText(getApplicationContext(), "Your Skin Is Healthy", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Something went wrong! Please try Again", Toast.LENGTH_LONG).show();

                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<SkinResult> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}



class  Model4{
    private String imageUri;
    public Model4(){

    }
    public  Model4(String imageUri){
        this.imageUri = imageUri;
    }
    public  String getImageUri(){
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}