package org.mat.eduvation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mat.eduvation.LocaL_Database.DatabaseConnector;
import org.mat.eduvation.LocaL_Database.dbHelper;
import org.mat.eduvation.navigation_items.Announcements;
import org.mat.eduvation.navigation_items.Attendance;
import org.mat.eduvation.navigation_items.ContactUs;
import org.mat.eduvation.navigation_items.FindUs;
import org.mat.eduvation.navigation_items.Logout;
import org.mat.eduvation.navigation_items.Profile;
import org.mat.eduvation.navigation_items.Schedule;
import org.mat.eduvation.navigation_items.Speakers;
import org.mat.eduvation.navigation_items.SponsorsAndPartners;
import org.mat.eduvation.navigation_items.home;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.mat.eduvation.SaveSharedPreference.getUserName;
import static org.mat.eduvation.Signupfrag.keyGenerator;
import static org.mat.eduvation.navigation_items.Profile.decodeBase64;

public class navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public final int READ_FROM_DATABASE_ID = 42;
    public final int SAVE_TO_DATABASE_ID = 43;
    private Fragment fragment = null;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private String title="";
    private CircleImageView circleImageView;
    private TextView userNameTxtView, userCompanyTxtView;
    private DatabaseConnector databaseConnector;
    private DatabaseReference images;
    private FirebaseDatabase database;
    private String FirebaseChildkey;
    private String userEmail;
    private String[] fields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        toolbar = (Toolbar) findViewById(R.id.toolbarNav);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");
        userEmail = SaveSharedPreference.getUserName(this);
        fields = userEmail.split("\\.");
        FirebaseChildkey = keyGenerator(fields);

        //Set the fragment initially
        fragment = new home();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description */
                R.string.navigation_drawer_close  /* "close drawer" description */
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_36dp);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        circleImageView = (CircleImageView) hView.findViewById(R.id.profile_image);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigation.this, Profile.class));
            }
        });

        userNameTxtView = (TextView) hView.findViewById(R.id.userHeaderName);

        userNameTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigation.this, Profile.class));
            }
        });


        userCompanyTxtView = (TextView) hView.findViewById(R.id.userHeaderCompany);

        userCompanyTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(navigation.this, Profile.class));
            }
        });


        navigationView.setNavigationItemSelectedListener(this);

        databaseConnector = new DatabaseConnector(this);

        database = FirebaseDatabase.getInstance();

        images = database.getReference("images");

        getUser();

        if (isNetworkAvailable())

            getImageFromFB_DB();

        else
            loadDataFromDatabase();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.Home) {

            title="Home";

            fragment = new home();

        } else if (id == R.id.Attendance) {

            title="Attendance";
            fragment = new Attendance();

        } else if (id == R.id.Schedule) {

            title="Schedule";

            fragment = new Schedule();

        } else if (id == R.id.Speakers) {

            title="Speakers";

            fragment = new Speakers();

        } else if (id == R.id.Sponsors) {

            title="Sponsors And Partners";

            fragment = new SponsorsAndPartners();

        } else if (id == R.id.Location) {

            title="Find Us";


            fragment=new FindUs();

        }else if (id == R.id.ContactUs) {

            title="Contact Us";

            fragment = new ContactUs();

        }else if (id == R.id.Logout) {
            title="Logout";
            fragment = new Logout();

        }else if (id == R.id.announcements) {

            title="Announcements";

            fragment = new Announcements();

        }

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadDataFromDatabase() {

        try {
            android.support.v4.content.Loader<String> loader =
                    getSupportLoaderManager()
                            .initLoader(READ_FROM_DATABASE_ID, null, new LoaderManager.LoaderCallbacks<String>() {
                                @Override
                                public Loader<String> onCreateLoader(int id, Bundle args) {

                                    return new navigation.ReadDataLoader(navigation.this);
                                }

                                @Override
                                public void onLoadFinished(Loader<String> loader, String data) {
                                    if (data != null) {
                                        try {
                                            Bitmap myBitmapAgain = decodeBase64(data);
                                            Drawable d = new BitmapDrawable(getResources(), myBitmapAgain);
                                            circleImageView.setImageDrawable(d);

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
            databaseConnector.close();
            loadDataFromDatabase();
        } else {
            images.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {

                        ImageModel imageModel = dataSnapshot.child(String.valueOf(FirebaseChildkey)).getValue(ImageModel.class);
                        if (imageModel != null) {
                            Bitmap myBitmapAgain = decodeBase64(imageModel.getImagestr());
                            //Log.d("getDataFB", imageMap.get("imageStr"));
                            Drawable d = new BitmapDrawable(getResources(), myBitmapAgain);
                            circleImageView.setImageDrawable(d);
                            saveToDatabase(imageModel.getImagestr());
                        } else {
                            //do nothing
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

    private void saveToDatabase(final String imageString) {
        try {
            Loader<Void> loader = getSupportLoaderManager()
                    .initLoader(
                            SAVE_TO_DATABASE_ID,
                            null,
                            new LoaderManager.LoaderCallbacks<Void>() {
                                @Override
                                public Loader<Void> onCreateLoader(int id, Bundle args) {
                                    return new navigation.WriteDataLoader(navigation.this, imageString
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void getUser() {
        databaseConnector.open();
        Cursor cursor = databaseConnector.getUserByEmail(getUserName(this));

        if (cursor != null && cursor.moveToFirst()) {

            String temp;
            TextView[] array = {userNameTxtView, userCompanyTxtView};
            for (int i = 0; i < 2; ++i) {
                temp = cursor.getString(i);
                array[i].setText(temp);
            }

        }
        databaseConnector.close();
    }

    static class ReadDataLoader extends AsyncTaskLoader<String> {
        DatabaseConnector databaseConnector = new DatabaseConnector(getContext());
        private String imageString;

        public ReadDataLoader(Context context) {
            super(context);
            databaseConnector.open();

        }


        @Override
        public String loadInBackground() {

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

    static class WriteDataLoader extends AsyncTaskLoader<Void> {

        public DatabaseConnector databaseConnector;
        private String imageStr;

        public WriteDataLoader(Context context, String imageStr) {
            super(context);
            this.imageStr = imageStr;
            databaseConnector = new DatabaseConnector(getContext());
            databaseConnector.open();
        }

        @Override
        public Void loadInBackground() {
            // save to the local database

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

}
