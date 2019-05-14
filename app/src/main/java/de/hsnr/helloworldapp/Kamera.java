package de.hsnr.helloworldapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class Kamera extends AppCompatActivity {


private static final String TITLE = "Foto_";
private static final String DESCRIPTION = "Foto_";

private static final int IMAGE_CAPTURE = 1;
private static final int GALLERY_PICK =22;
private static final int PERMISSIO_REQUEST = 123;

private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

private Uri imageUri;
private Button foto, video, gallery;
private ImageView imageView;
private boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera);

        imageView = findViewById(R.id.view);
        Log.w("ASDF", "Anfang");

        //Button für Foto
        foto = findViewById(R.id.foto);
        foto.setText("Foto aufnehmen");
        foto.setOnClickListener((v) -> {
          //  Intent intent = new Intent(
           // MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            //startActivity(intent);
                    startCamera();
        });

        video = findViewById(R.id.video);
        video.setText("Video aufnehmen");
        video.setOnClickListener((v) -> {
          //Intent intent = new Intent(
            //        MediaStore.INTENT_ACTION_VIDEO_CAMERA);
            //startActivity(intent);
            startCamera();
        });

        gallery = findViewById(R.id.gallery);
        gallery.setText("Gallery");
        gallery.setOnClickListener((V) -> {
            Intent intent = new Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_PICK);
        });
        }



    private void startCamera() {
        //   if (this.permissionGranted) {
        Log.w("ASDF", "StartingCamera");
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, TITLE);
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,  DESCRIPTION);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentC.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentC, IMAGE_CAPTURE);

        //}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE){
            if (resultCode == RESULT_OK){
                try{
                    Log.w("ASDF", "OnActivityResult");
                    Bitmap b1 = MediaStore.Images.Media
                            .getBitmap(
                                    getContentResolver(), imageUri);
                    // Größe des aufgenommenen Bildes
                    float w1 = b1.getWidth();
                    float h1 = b1.getHeight();
                    // auf eine Höhe von maximal 300 Pixel skalieren
                    int h2 = (int) h1 > 300 ? 300 : (int) h1;
                    int w2 = (int) (w1 / h1 * (float) h2);
                    Bitmap b2 = Bitmap.createScaledBitmap(b1,
                            w2, h2, false);
                    imageView.setImageBitmap(b2);
                }
                catch(Exception e){
                    Log.e(MainActivity.class.getSimpleName(), "setBitmap()", e);
                }
            }
            else{
                int rowsDeleted = getContentResolver().delete(imageUri,
                        null, null);
                Log.d(MainActivity.class.getSimpleName(), rowsDeleted + " rows deleted");
            }
        }

    }

    private Bitmap getAndScaleBitmap(Uri uri, int dstWidth, int dstHeigth){
        try{
            Bitmap src = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), uri);
            float srcWidth = src.getWidth();
            float srcHeight = src.getHeight();

            if (dstWidth < 1) {
                dstWidth = (int) (srcWidth / srcHeight * dstHeigth);
            }
            Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeigth, false);
            return dst;

        }
        catch(IOException e){
            Log.e(Activity.class.getSimpleName(), "setBitmap", e);

        }
        return null;
    }
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();
        if (checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            foto.setEnabled(false);
        } else {
            foto.setEnabled(true);
        }
    }

}
