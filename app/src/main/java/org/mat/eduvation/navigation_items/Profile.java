package org.mat.eduvation.navigation_items;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import org.mat.eduvation.ImageModel;
import org.mat.eduvation.LocaL_Database.DatabaseConnector;
import org.mat.eduvation.LocaL_Database.dbHelper;
import org.mat.eduvation.R;
import org.mat.eduvation.SaveSharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.mat.eduvation.SaveSharedPreference.getUserName;
import static org.mat.eduvation.Signupfrag.keyGenerator;

public class Profile extends AppCompatActivity {
    private static final int REQUEST_CODE_PICKER = 1;
    public final int READ_FROM_DATABASE_ID = 42;
    public final int SAVE_TO_DATABASE_ID = 43;
    private CircleImageView profilePhoto;
    private TextView name, company, email;
    private DatabaseConnector databaseConnector;
    private DatabaseReference images;
    private FirebaseDatabase database;
    private String userEmail;
    private String[] fields;
    private Toolbar toolbar;
    private String FirebaseChildkey;

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userEmail = SaveSharedPreference.getUserName(this);
        fields = userEmail.split("\\.");
        FirebaseChildkey = keyGenerator(fields);

        toolbar = (Toolbar) findViewById(R.id.toolbareduid);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseConnector = new DatabaseConnector(this);

        database = FirebaseDatabase.getInstance();

        images = database.getReference("images");


        name = (TextView) findViewById(R.id.username);
        company = (TextView) findViewById(R.id.CompanyProfile);
        email = (TextView) findViewById(R.id.EmailProfile);

        profilePhoto = (CircleImageView) findViewById(R.id.profile_image_edit);
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();


        getUser();

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    //Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //startActivityForResult(pickPhoto, SELECTED_PICTURE);//one can be replaced with any action code
                    ImagePicker.create(Profile.this)
                            .folderMode(true) // folder mode (false by default)
                            .folderTitle("Folder") // folder selection title
                            .imageTitle("Tap to select") // image selection title
                            .single() // single mode
                            .showCamera(true) // show camera or not (true by default)
                            .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                            .start(REQUEST_CODE_PICKER); // start image picker activity with request code
                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to the internet and try again ", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (isNetworkAvailable())

            getImageFromFB_DB();

        else
            loadDataFromDatabase();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);

            Bitmap myBitmap = BitmapFactory.decodeFile(images.get(0).getPath());
            String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.PNG, 100);
            saveToFireBase(myBase64Image);

            Picasso.with(this).load(new File(images.get(0).getPath())).placeholder(R.mipmap.ic_launcher).into(profilePhoto);


        }
    }

    private void saveToDatabase(final String imageString) {
        try {
            Loader<Void> loader = getSupportLoaderManager()
                    .initLoader(
                            SAVE_TO_DATABASE_ID,
                            null,
                            new LoaderManager.LoaderCallbacks<Void>() {
                                @Override
                                public Loader<Void> onCreateLoader(int id, Bundle args) {
                                    return new WriteDataLoader(Profile.this, imageString
                                    );
                                }

                                @Override
                                public void onLoadFinished(Loader<Void> loader, Void data) {
                                    Toast.makeText(getApplicationContext(), "saved successfully", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onLoaderReset(Loader<Void> loader) {
                                    // do nothing
                                }

                            });
            loader.forceLoad();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void saveToFireBase(String imageFile) {

        // Map<String, String> imageMap = new HashMap<>();
        ImageModel imageModel = new ImageModel(imageFile, getUserName(Profile.this));
        //imageMap.put("imageStr", imageFile);

        images.child(FirebaseChildkey).setValue(imageModel);
        Toast.makeText(getApplicationContext(), "Saving", Toast.LENGTH_LONG).show();
        saveToDatabase(imageFile);

    }

    private void loadDataFromDatabase() {

        try {
            android.support.v4.content.Loader<String> loader =
                    getSupportLoaderManager()
                            .initLoader(READ_FROM_DATABASE_ID, null, new LoaderManager.LoaderCallbacks<String>() {
                                @Override
                                public Loader<String> onCreateLoader(int id, Bundle args) {

                                    return new ReadDataLoader(Profile.this);
                                }

                                @Override
                                public void onLoadFinished(Loader<String> loader, String data) {
                                    if (data != null) {
                                        try {
                                            Bitmap myBitmapAgain = decodeBase64(data);
                                            Drawable d = new BitmapDrawable(getResources(), myBitmapAgain);
                                            profilePhoto.setImageDrawable(d);

                                        } catch (Exception e) {
                                            Log.d("loadDatafromdb", e.getMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onLoaderReset(Loader<String> loader) {

                                }
                            });
            loader.forceLoad();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void getImageFromFB_DB() {
        databaseConnector.open();
        if (databaseConnector.isImageExist(SaveSharedPreference.getUserName(getApplicationContext()).toLowerCase())) {
            loadDataFromDatabase();
        } else {
            images.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        ImageModel imageModel = dataSnapshot.child(String.valueOf(FirebaseChildkey)).getValue(ImageModel.class);
                        //  Map<String, String> imageMap = (Map) dataSnapshot.child(String.valueOf(FirebaseChildkey)).getValue();
                        if (imageModel != null) {
                            Bitmap myBitmapAgain = decodeBase64(imageModel.getImagestr());
                            //Log.d("getDataFB", imageMap.get("imageStr"));
                            Drawable d = new BitmapDrawable(getResources(), myBitmapAgain);
                            profilePhoto.setImageDrawable(d);
                            saveToDatabase(imageModel.getImagestr());
                        } else {
                            Toast.makeText(getApplicationContext(), "Please set a profile picture", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Please wait while loading your profile picture ", Toast.LENGTH_LONG).show();
                    Log.e("onCancelled image", databaseError.getMessage());
                }
            });
        }
    }

    private void getUser() {
        databaseConnector.open();
        Cursor cursor = databaseConnector.getUserByEmail(getUserName(this));

        if (cursor != null && cursor.moveToFirst()) {

            String temp;
            TextView[] array = {name, company, email};
            for (int i = 0; i < 3; ++i) {
                temp = cursor.getString(i);
                array[i].setText(temp);
            }

        }
        databaseConnector.close();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    static class WriteDataLoader extends AsyncTaskLoader<Void> {

        public DatabaseConnector databaseConnector;
        private String imageStr;

        public WriteDataLoader(Context context, String imageStr) {
            super(context);
            this.imageStr = imageStr;
            databaseConnector = new DatabaseConnector(getContext());
        }

        @Override
        public Void loadInBackground() {
            // save to the local database
            databaseConnector.open();
            if (!databaseConnector.isImageExist(SaveSharedPreference.getUserName(getContext()).toLowerCase())) {
                databaseConnector.insertImageString(imageStr,
                        String.valueOf(SaveSharedPreference.getUserName(getContext()).toLowerCase()));
            } else if (databaseConnector.isImageExist(SaveSharedPreference.getUserName(getContext()).toLowerCase())) {
                databaseConnector.updateImage(SaveSharedPreference.getUserName(getContext()).toLowerCase(), imageStr);
            }
            databaseConnector.close();
            return null;
        }
    }

    static class ReadDataLoader extends AsyncTaskLoader<String> {
        DatabaseConnector databaseConnector = new DatabaseConnector(getContext());
        private String imageString;

        public ReadDataLoader(Context context) {
            super(context);
        }


        @Override
        public String loadInBackground() {

            databaseConnector.open();
            if (databaseConnector.isEmailExist(SaveSharedPreference.getUserName(getContext()).toLowerCase())) {

                Cursor cursor = databaseConnector.getImageByEmail(SaveSharedPreference.getUserName(getContext()).toLowerCase());

                // parse data from the cursor
                if (cursor.moveToFirst())
                    do {
                        imageString = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_IMAGE_STRING));
                    } while (cursor.moveToNext());
                databaseConnector.close();
                return imageString;
            } else
                return null;
        }
    }


}