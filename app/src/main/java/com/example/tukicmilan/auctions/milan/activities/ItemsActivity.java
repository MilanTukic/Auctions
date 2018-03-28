package com.example.tukicmilan.auctions.milan.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.data.DatabaseHelper;
import com.example.tukicmilan.auctions.milan.fragments.AuctionsFragment;
import com.example.tukicmilan.auctions.milan.fragments.ItemsFragment;
import com.example.tukicmilan.auctions.milan.fragments.SettingsFragment;
import com.example.tukicmilan.auctions.milan.fragments.SettingsUserFragment;
import com.example.tukicmilan.auctions.milan.model.Item;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tukicmilan.auctions.milan.activities.EditUserPreferenceActivity.decodeBase64;
import static com.example.tukicmilan.auctions.milan.activities.EditUserPreferenceActivity.encodeTobase64;

public class ItemsActivity extends AppCompatActivity implements ItemsFragment.MenuClickedListener, AuctionsFragment.MenuClickedListenerA{

    private String[] drawerListNames = new String[]{};
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
    private TypedArray img;
    public static Context contextOfApplication;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    private ImageView ivMenu;
    private String menuclicked = "no";
    private ImageButton btnEditClick;
    CircleImageView circleImageView;
    private static final int GALLERY = 1;
    String folder_main = "NewFolder";



    SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPre" ;//file name
    public static final String  key = "nameKey";
    Bitmap btmap;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        contextOfApplication = getApplicationContext();

        Log.i("itemsa", "onCreateItemsA");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //loadFragment(70);



        drawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        //drawerListView = (ListView) findViewById(R.id.main_activity_drawer_list_view);
        // drawerListNames = getResources().getStringArray(R.array.drawerItemNames);
        //drawerListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerListNames));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_activity_content_frame, new ItemsFragment());
        fragmentTransaction.commit();
        //getSupportActionBar().setTitle("Items");
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_items_fragment:
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_activity_content_frame, new ItemsFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //getSupportActionBar().setTitle("Items");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_auctions_fragment:
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_activity_content_frame, new AuctionsFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //getSupportActionBar().setTitle("Auctions");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_settings_fragment:
                        fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_activity_content_frame, new SettingsFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //getSupportActionBar().setTitle("Settings");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });
        /*
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerListView.setItemChecked(position, true);
                loadFragment(position);
                drawerLayout.closeDrawer(drawerListView);
            }
        });
        */
        try {
            // Open database
            DatabaseHelper mDbHelper =  DatabaseHelper.getInstance(this);
            SQLiteDatabase database = mDbHelper.getReadableDatabase();
        } catch (Exception e) {
            Toast.makeText(this, "A bug!", Toast.LENGTH_SHORT).show();
        }
        // Lookup navigation view
        // NavigationView navigationView = (NavigationView) findViewById(R.id);
        //Inflate the header view at runtime
        //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        // We can now look up items within the header if needed
        //ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.items_toolbar);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
        }else{
            //no connection
            Toast toast = Toast.makeText(getApplicationContext(), "Turn on internet, run app again!",
                    Toast.LENGTH_LONG);
            toast.show();
        }

        btnEditClick = (ImageButton) findViewById(R.id.btnEditImage);
        btnEditClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Click!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ItemsActivity.this, EditUserPreferenceActivity.class);
                startActivity(intent);

                /*fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_activity_content_frame, new SettingsUserFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                drawerLayout.closeDrawers();

            }
        });

    }

    /*
    private void loadFragment(int index) {
        android.app.Fragment fragment = new ItemsFragment();

        //android.app.Fragment fragment;
        //pogledaj ovde je mozda greska, treba da ide new ItemsFragment
        switch (index) {
            case 1:
                getSupportActionBar().setTitle("Auctions");
                fragment = new AuctionsFragment();
                break;
            case 2:
                getSupportActionBar().setTitle("Settings");
                fragment = new SettingsFragment();
                break;
            default:
                getSupportActionBar().setTitle("Items");
                fragment = new ItemsFragment();
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    */




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        }else if(getFragmentManager().getBackStackEntryCount() > 0){

            getFragmentManager().popBackStack();
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit").setIcon(R.drawable.esplash)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            //super.finishAffinity();
        }
    }

    @Override
    public void onClickedMenu(String clicked) {
        menuclicked = clicked;
        Log.i("Menu clicked", "onclicked menu acitivty: "+menuclicked);
        if(menuclicked.equals("yes")){
            drawerLayout.openDrawer(Gravity.START);
        }
    }

    @Override
    public void onClickedMenuA(String clicked) {
        menuclicked = clicked;
        Log.i("Menu clicked", "onclicked menu acitivty: "+menuclicked);
        if(menuclicked.equals("yes")){
            drawerLayout.openDrawer(Gravity.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("resum", "onResumStart");
        sharedPreferences = getPreferences(MODE_PRIVATE);



        sharedPreferences =  getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key))
        {
            String u=sharedPreferences.getString(key, "");
            btmap=decodeBase64(u);
            ImageView iv = (ImageView) findViewById(R.id.userImg);
            iv.setImageBitmap(btmap);
        }

        circleImageView = (CircleImageView) findViewById(R.id.userImg);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Toast.makeText(ItemsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }else  {
            Toast.makeText(getApplicationContext(), "Neki Tekst", Toast.LENGTH_LONG).show();
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

}

