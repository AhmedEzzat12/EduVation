package org.mat.eduvation;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.mat.eduvation.LocaL_Database.DatabaseConnector;
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

public class navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment = null;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private String title="";
    private CircleImageView circleImageView;
    private DatabaseConnector databaseConnector;
    private TextView userNameTxtView, userCompanyTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");

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
                Profile profile = new Profile();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, profile);
                fragmentTransaction.commit();
            }
        });

        userNameTxtView = (TextView) hView.findViewById(R.id.userHeaderName);

        userNameTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = new Profile();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, profile);
                fragmentTransaction.commit();
            }
        });


        userCompanyTxtView = (TextView) hView.findViewById(R.id.userHeaderName);

        userCompanyTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = new Profile();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, profile);
                fragmentTransaction.commit();
            }
        });


        navigationView.setNavigationItemSelectedListener(this);

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

            fragment = new FindUs();

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


    private void getUser() {

        Cursor cursor = databaseConnector.getUserByEmail(SaveSharedPreference.getUserName(this));

        if (cursor != null && cursor.moveToFirst()) {

            String temp;
            TextView[] array = {userNameTxtView, userCompanyTxtView};
            for (int i = 0; i < 2; ++i) {
                temp = cursor.getString(i);
                array[i].setText(temp);
            }

        }

    }


}
