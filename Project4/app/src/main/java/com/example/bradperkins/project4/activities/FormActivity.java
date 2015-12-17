package com.example.bradperkins.project4.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.bradperkins.project4.DataBaseUtils.DBOpenHelper;
import com.example.bradperkins.project4.DataBaseUtils.PhotoProvider;
import com.example.bradperkins.project4.R;
import com.example.bradperkins.project4.fragments.FormFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormActivity extends AppCompatActivity implements LocationListener {

    Uri mImageUri;

    LocationManager mManager;

    double mLatitude;
    double mLongitude;

    private static final int REQUEST_TAKE_PICTURE = 0x01001;
    private static final int REQUEST_ENABLE_GPS = 0x02001;

    FormFragment formFrag = new FormFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        setTitle("Form");
        getFragmentManager().beginTransaction().replace(R.id.form_fragment_container, FormFragment.newInstance()).commit();
    }

    public void takePic(View view) {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mImageUri = getImageUri();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);

    }

    //Save PhotoData
    public void savePic(View view) {

        enableGps();

        String pName = formFrag.nameET.getText().toString().trim();
        String pTitle = formFrag.titleET.getText().toString().trim();
        String pImageUri = mImageUri.toString();
        double pLat = mLatitude;
        double pLng = mLongitude;

        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.PHOTO_NAME, pName);
        values.put(DBOpenHelper.PHOTO_TITLE, pTitle);
        values.put(DBOpenHelper.PHOTO_URI, pImageUri);
        values.put(DBOpenHelper.PHOTO_LATITUDE, pLat);
        values.put(DBOpenHelper.PHOTO_LONGITUDE, pLng);

        getContentResolver().insert(PhotoProvider.CONTENT_URI, values);
        setResult(RESULT_OK);

        finish();

        Log.i("Check", " !!!! ");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PICTURE && resultCode == RESULT_OK) {
            if (mImageUri != null) {

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
        } catch (Exception e) {
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


    public void setPic(Uri imgUri) {

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
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

                    /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        //mImageView.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
        Bitmap bitmap = BitmapFactory.decodeFile(pic, bmOptions);


        formFrag.imageView.setImageBitmap(bitmap);

    }


    public void enableGps() {
        if (mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, (LocationListener) this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            Location loc = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc != null) {

                mLatitude = loc.getLatitude();
                mLongitude = loc.getLongitude();

                Log.i("Location", " " + loc.getLatitude() + "    " +loc.getLongitude());
            }

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("GPS Unavailable")
                    .setMessage("Please enable GPS in the system settings.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }

                    })
                    .show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



}
