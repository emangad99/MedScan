package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.media.ThumbnailUtils;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medscan.eyes.bulging_eyes;
import com.example.medscan.eyes.cataract;
import com.example.medscan.eyes.crossed_eyes;
import com.example.medscan.eyes.glaucoma;
import com.example.medscan.eyes.successful_eyes;
import com.example.medscan.eyes.uveitis;
import com.example.medscan.kidneys.kidney_successful;
import com.example.medscan.lungs.RealPathUtil;
import com.example.medscan.ml.Eyes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class eyes_image extends AppCompatActivity {


    ImageView btn_choose , img , btn_camera;
    TextView text;
    Button upload ;
    ProgressBar prog;
    Bitmap image = null;
    int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyes_image);

        text=findViewById(R.id.text_eyes);
        btn_choose = findViewById(R.id.choose);
        img=findViewById(R.id.image_ray);
        upload=findViewById(R.id.btn_upload_rays);
        btn_camera=findViewById(R.id.camera);
        prog = findViewById(R.id.eyes_prog);
        prog.setVisibility(View.INVISIBLE);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
                prog.setVisibility(View.VISIBLE);
                btn_camera.setVisibility(View.GONE);
                btn_choose.setVisibility(View.GONE);
                text.setVisibility(View.GONE);

            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cam, 3);
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        btn_choose.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                Intent cam = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cam, 1);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 3){
                 image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                img.setImageBitmap(image);


            }
            else {
                Uri dat = data.getData();

                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                img.setImageBitmap(image);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void classifyImage(Bitmap image) {
        try {
            Eyes model = Eyes.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i <imageSize; i++){
                for (int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; //RGB
                    byteBuffer.putFloat(((val >> 16 & 0xFF) * (1.f / 255.f)));
                    byteBuffer.putFloat(((val >> 8 & 0xFF) * (1.f / 255.f)));
                    byteBuffer.putFloat(((val & 0xFF) * (1.f / 255.f)));
                }
            }


            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Eyes.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidences = 0;
            for (int i = 0; i < confidences.length; i++ ){
                if (confidences[i] > maxConfidences){
                    maxConfidences = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"healthy", "cataract", "glaucoma", "crossed", "uveitis", "bulging"};

            if (maxPos == 76728) {
                Intent cdk = new Intent(eyes_image.this, successful_eyes.class);
                startActivity(cdk);
                prog.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "healthy", Toast.LENGTH_LONG).show();
            }
            else if (maxPos == 78601){
                Intent cdk = new Intent(eyes_image.this, cataract.class);
                startActivity(cdk);
                prog.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "cataract", Toast.LENGTH_LONG).show();
            }
            else if (maxPos == 79146){
                Intent cdk = new Intent(eyes_image.this, glaucoma.class);
                startActivity(cdk);
                prog.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "glaucoma", Toast.LENGTH_LONG).show();
            }
            else if (maxPos == 77274){
                Intent cdk = new Intent(eyes_image.this, crossed_eyes.class);
                startActivity(cdk);
                prog.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "crossed", Toast.LENGTH_LONG).show();
            }
            else if (maxPos == 78444){
                Intent cdk = new Intent(eyes_image.this, uveitis.class);
                startActivity(cdk);
                prog.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "uveitis", Toast.LENGTH_LONG).show();
            }
            else if (maxPos == 78133){
                Intent cdk = new Intent(eyes_image.this, bulging_eyes.class);
                startActivity(cdk);
                prog.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "bulging", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_LONG).show();
                prog.setVisibility(View.INVISIBLE);
            }

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }


}

//}
/*class  Model3{
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

}*/


