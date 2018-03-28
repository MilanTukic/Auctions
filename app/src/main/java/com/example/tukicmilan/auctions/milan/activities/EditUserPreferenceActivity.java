package com.example.tukicmilan.auctions.milan.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tukicmilan.auctions.milan.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserPreferenceActivity extends AppCompatActivity {

    EditText firstName, lastName, eMail, password, address, phone;
    ImageButton button, button2, button3;
    private CircleImageView circleImageView;
    private static final String IMAGE_DIRECTORY = "///";
    private int  CAMERA = 2;
    private static final int GALLERY = 1;
    String folder_main = "NewFolder";
    private Uri mImageUri;
    Context context;

    public static final String MyPREFERENCES = "MyPre" ;//file name
    public static final String  key = "nameKey";
    Bitmap btmap;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);

        sharedPreferences = getPreferences(MODE_PRIVATE);



        sharedPreferences =  getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key))
        {
            String u=sharedPreferences.getString(key, "");
            btmap=decodeBase64(u);
            ImageView iv = (ImageView) findViewById(R.id.userImg);
            iv.setImageBitmap(btmap);
        }

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        eMail = (EditText) findViewById(R.id.eMail);
        password = (EditText) findViewById(R.id.password);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);

        button = (ImageButton) findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fNameText = firstName.getText().toString();
                String lNameText = lastName.getText().toString();
                String emailText = eMail.getText().toString();
                String passwordText = password.getText().toString();
                String addressText = address.getText().toString();
                int phoneText = Integer.parseInt(phone.getText().toString());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("keyFName", fNameText);
                editor.putString("keyLName", lNameText);
                editor.putString("keyEmail", emailText);
                editor.putString("keyPassword", passwordText);
                editor.putString("keyAddress", addressText);
                editor.putInt("keyPhone", phoneText);
                editor.commit();

                Toast.makeText(getApplicationContext(), "Save!", Toast.LENGTH_LONG).show();
            }
        });

        button2 = (ImageButton) findViewById(R.id.btnReset);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                readPreferences();
            }
        });

        circleImageView = (CircleImageView) findViewById(R.id.userImg);


        button3 = (ImageButton) findViewById(R.id.btnEditImage);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "OEWIHFUEWHG", Toast.LENGTH_LONG).show();
                showPictureDialog();
            }
        });

        if(sharedPreferences!=null) {
            String path = sharedPreferences.getString("path", null);

            if(path!=null)
                circleImageView.setImageBitmap(BitmapFactory.decodeFile(path));
        }


        readPreferences();
    }


    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        takePhotoFromCamera();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }else{
                            Toast.makeText(getApplicationContext(), "Camera not approved!", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    public void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String [] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        choosePhotoFromGallery();
                    break;

                    case 1:
                        requestCameraPermission();
                }

            }
        });
        pictureDialog.show();
    }


    public void choosePhotoFromGallery(){
        /*Intent intentPicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentPicture,GALLERY);*/
        try {
            if (ActivityCompat.checkSelfPermission(EditUserPreferenceActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditUserPreferenceActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GALLERY);
                } else {
                    Toast.makeText(getApplicationContext(), "Gallery not approved!", Toast.LENGTH_LONG).show();
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    public void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(intent);
        //startActivityForResult(intent, CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA);
        }
    }



    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditUserPreferenceActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 40, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    //String path = saveImage(bitmap);
                    saveImage(bitmap);
                    //Toast.makeText(EditUserPreferenceActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    circleImageView.setImageBitmap(bitmap);

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    ImageView iv = (ImageView) findViewById(R.id.userImg);
                    iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    btmap=BitmapFactory.decodeFile(picturePath);//decode method called

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(key,  encodeTobase64(btmap));
                    editor.commit();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditUserPreferenceActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }else  if (resultCode != RESULT_CANCELED && data != null) {

            if(requestCode == CAMERA && resultCode == RESULT_OK){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                circleImageView.setImageBitmap(thumbnail);
                saveImage(thumbnail);

                //Picasso.with(context).load(data.getData()).into(circleImageView);

              /* final String imageOrderBy = MediaStore.Images.Media.DATE_TAKEN + "DESC" ;
                final String[] imageColumns = { MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA };

                String[] projection = new String[] {
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATE_TAKEN,
                        MediaStore.Images.ImageColumns.MIME_TYPE };

                Uri selectedImage = data.getData();

                String[] filePathColumn = { MediaStore.Images.Media.DATA};
                Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(imageColumns[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView iv = (ImageView) findViewById(R.id.userImg);
                iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                btmap=BitmapFactory.decodeFile(picturePath);//decode method called

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key,  encodeTobase64(btmap));
                editor.commit();*/
            }

        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory(), folder_main);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

   /* public void onSave(){

        String fNameText = firstName.getText().toString();
        String lNameText = lastName.getText().toString();
        String emailText = eMail.getText().toString();
        String passwordText = password.getText().toString();
        String addressText = address.getText().toString();
        int phoneText = Integer.parseInt(phone.getText().toString());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("keyFName", fNameText);
        editor.putString("keyLName", lNameText);
        editor.putString("keyEmail", emailText);
        editor.putString("keyPassword", passwordText);
        editor.putString("keyAddress", addressText);
        editor.putInt("keyPhone", phoneText);
        editor.commit();
    }*/


    public void readPreferences(){

        String s1 = sharedPreferences.getString("keyFName", "Milan");
        firstName.setText(s1);

        String s2 = sharedPreferences.getString("keyLName", "Tukic");
        lastName.setText(s2);

        String s3 = sharedPreferences.getString("keyEmail", "mil@gmail.com");
        eMail.setText(s3);

        String s4 = sharedPreferences.getString("keyPassword", "1234");
        password.setText(s4);

        String s5 = sharedPreferences.getString("keyAddress", "Boska Buhe 2");
        address.setText(s5);

        if(phone != null) {
            int i = sharedPreferences.getInt("keyPhone", 124415);
            phone.setText(String.valueOf(i));
        }
    }

}
