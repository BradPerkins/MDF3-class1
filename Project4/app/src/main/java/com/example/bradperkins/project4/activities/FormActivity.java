package com.example.bradperkins.project4.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bradperkins.project4.R;
import com.example.bradperkins.project4.fragments.FormFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormActivity extends AppCompatActivity {

    Uri mImageUri;

    private static final int REQUEST_TAKE_PICTURE = 0x01001;

    FormFragment formFrag = new FormFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        setTitle("Form");
        getFragmentManager().beginTransaction().replace(R.id.form_fragment_container, FormFragment.newInstance()).commit();
    }

    public void takePic(View view) {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mImageUri = getImageUri();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);

    }

    public void savePic(View view) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PICTURE && resultCode == RESULT_OK) {
            if(mImageUri != null) {

                if (mImageUri != null) {
                    setPic(mImageUri);
                    addImageToGallery(mImageUri);
                } else {
                    formFrag.imageView.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                }
            }
        }

    }


    public Uri getImageUri() {

        System.out.println("Getting image URI");

        String imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis()));

        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appDir = new File(imageDir, "Project4");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");
        try {
            image.createNewFile();
            System.out.println("image.createNewFile();");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("return Uri.fromFile(image);");
        return Uri.fromFile(image);


    }


    public void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        sendBroadcast(scanIntent);
    }


    public void setPic(Uri imgUri){

        System.out.println("PIC set successfully");
        String pic = imgUri.getEncodedPath();

                    /* Get the size of the ImageView */
        int targetW = formFrag.imageView.getWidth();
        int targetH = formFrag.imageView.getHeight();

                    /* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pic, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

                    /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

                    /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        //mImageView.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
        Bitmap bitmap = BitmapFactory.decodeFile(pic, bmOptions);


        formFrag.imageView.setImageBitmap(bitmap);

    }

}
