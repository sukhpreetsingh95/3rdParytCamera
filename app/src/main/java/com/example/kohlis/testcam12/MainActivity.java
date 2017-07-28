package com.example.kohlis.testcam12;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button button, buttonCompress;
    private String encoded_string, image_name;
    private Bitmap bitmap;
    private ImageView img;
    private File file;
    private Uri file_uri;
    private ProgressDialog mProgressDialog;
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);//For writing into internal storage.

        img = (ImageView) findViewById(R.id.imageView);
         button = (Button) findViewById(R.id.btnCam);
        buttonCompress = (Button) findViewById(R.id.btnComp);
        button.setOnClickListener(this);
        buttonCompress.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCam:
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(intentCamera, 10);
                break;
            case R.id.btnComp:
                Bitmap bitmapsave = null;
                //get image bitmap from imageview
                bitmapsave = ((BitmapDrawable) img.getDrawable()).getBitmap();


                //create folder where you want to store compressed image
                File file = new File(Environment.getExternalStorageDirectory().getPath(), "/Pictures");
                if (!file.exists())
                {
                    file.mkdirs();
                }
                // name that image
                String filename = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";

                FileOutputStream out = null;
                try
                {
                    out = new FileOutputStream(filename);
                    bitmapsave.compress(Bitmap.CompressFormat.JPEG, 40, out);
                    out.close();

                    Toast.makeText(getApplicationContext(), "saved successfully", Toast.LENGTH_SHORT).show();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK) {
            img.setImageURI(file_uri);
            Log.d("path is", file.getPath());
            Toast.makeText(MainActivity.this, "Successfully Written !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getFileUri() {
        image_name = "Sukhi.jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name
        );
        file_uri = Uri.fromFile(file);
        Log.d("uri is", file_uri.toString());
    }


//    private byte[] getImageBytes(@NonNull ImageView imageView) {
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
//        return bos.toByteArray();
//    }

//    private void storeImage(Bitmap image) {
//        if(image == null){
//            return;
//        }
//        try {
//            File pictureFile = new File ("storage/emulated/0/Pictures/");
//
//            FileOutputStream fos = new FileOutputStream(pictureFile);
//            image.compress(Bitmap.CompressFormat.PNG,100, fos);
//            fos.close();
//            Log.e(TAG,"FILE created");
//        } catch (FileNotFoundException e) {
//            Log.d(TAG, "File not found: " + e.getMessage());
//        } catch (IOException e) {
//            Log.d(TAG, "Error accessing file: " + e.getMessage());
//        } catch (Exception e){
//            Log.e(TAG,"Excetion in image storage "+e.toString());
//        }
//    }





}



