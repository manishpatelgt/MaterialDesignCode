package com.addcontact.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.addcontact.R;
import com.addcontact.Util.BitmapHelper;
import com.addcontact.Util.HardwareHelper;
import com.addcontact.Util.IntentHelper;
import com.addcontact.Util.StorageHelper;
import com.addcontact.Util.Util;
import com.addcontact.application.cAapplication;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String photoPath;
    private static final int REQUEST_CODE_CAMERA_CAPTURE_PHOTO = 10082;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private String currentPhotoPath,currentPhotoName;
    private Bitmap imageBitmap;
    private ImageView imageView;
    private int UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageView = (ImageView) findViewById(R.id.capturedPhotoImageView);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Survive orientation change (happens when going to the Camera app)
        outState.putString("currentPhotoPath", currentPhotoPath);
        outState.putParcelable(BITMAP_STORAGE_KEY, imageBitmap);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentPhotoPath = savedInstanceState.getString("currentPhotoPath");
        imageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);

        if (imageBitmap != null)
            imageView.setImageBitmap(imageBitmap);
    }

    public void capturePhoto(View v) {

        if (!checkCamera())
            return;

        File file = null;

        try {
            String DateTime=Util.getDateFormatted2(Util.getCurrentTimestamp());
            String fName="Pic_" + DateTime + ".png";
            file = Util.createPhotoFile(fName, "PersonPhotos");

            currentPhotoPath = file.getAbsolutePath();
            currentPhotoName=fName;

           // cAapplication.getInstance().getDBAdapter().insertTaskPhoto(,currentPhotoName);

            cAapplication.setpersonPhotoPath(currentPhotoPath);
            // Request camera app to capture image
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Specify a path where the image has to be stored
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

            startActivityForResult(intent, REQUEST_CODE_CAMERA_CAPTURE_PHOTO);

        } catch (IOException e) {
            currentPhotoPath = null;
            currentPhotoName=null;
            file = null;
        }
    }


    private boolean checkCamera() {

        if (!StorageHelper.mediaMounted()) {
            final Toast toast = Toast.makeText(CameraActivity.this, getString(R.string.storage_is_not_mounted_external), Toast.LENGTH_LONG);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 10000);
            return false;
        }

        if (!IntentHelper.isIntentAvaliable(CameraActivity.this, MediaStore.ACTION_IMAGE_CAPTURE)) {
            final Toast toast = Toast.makeText(CameraActivity.this, "Cannot launch the camera to take a picture as no in-built camera app is available", Toast.LENGTH_LONG);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 10000);
            return false;
        }

        if (!HardwareHelper.hasCameraFeature(this)) {
            final Toast toast = Toast.makeText(CameraActivity.this, "Your phone has no camera?! Throw it away", Toast.LENGTH_LONG);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 10000);
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent  data) {
        if (requestCode != REQUEST_CODE_CAMERA_CAPTURE_PHOTO || resultCode != RESULT_OK)
            return;

        // Preview the captured photo
        Bitmap bitmap = BitmapHelper.scaleAndRotateImage(currentPhotoPath, 500, 400);
        imageBitmap = bitmap;
        imageView.setImageBitmap(bitmap);
        currentPhotoPath = null;
        currentPhotoName=null;
    }

}
