package com.gogadon.renewal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gogadon.fragments.Badges_Fragment;
import com.gogadon.fragments.Dashboard_Fragment;
import com.gogadon.fragments.RemindersFragment;
import com.gogadon.fragments.Stats_Fragment;
import com.gogadon.fragments.UserDetailsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends AppCompatActivity {
    String Usersname;
    ActionBarDrawerToggle toggle;
    Dashboard_Fragment dashboard_fragment;
    FragmentTransaction fragmentTransaction;
    ActionBar actionBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context c;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences("Renewprefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Usersname = sharedPreferences.getString("name", "user1");



        //check the date

        String lastdateopened = sharedPreferences.getString("lastdateopened","nodate");
        Date today = Calendar.getInstance().getTime();
        final String currentdate = new SimpleDateFormat("dd:MM:yyyy").format(today).toString();

        if(!currentdate.equals(lastdateopened)){

            // The date is diffrent to the last time the user opened the application add one to days used
            // and update the lastdateopenedfield

            int x = sharedPreferences.getInt("days_used",0);
            x++;
            editor.putInt("days_used", x);
            editor.putString("lastdateopened",currentdate);
            editor.commit();
            System.out.println("Its a new day!");

        }



        System.out.println("Todays date is " + currentdate);

        final MaterialToolbar materialToolbar = findViewById(R.id.dashboard_toolbar);
        final NavigationView navigationView = findViewById(R.id.navigation);
        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout_dash);
        String open = "open";
        final String close = "close";


        c = Dashboard.this;


        dashboard_fragment = new Dashboard_Fragment(Dashboard.this, currentdate);
        final FrameLayout frameLayout = findViewById(R.id.frameLayout2);


        setfragment(dashboard_fragment);


        View header = navigationView.getHeaderView(0);
        TextView textView = header.findViewById(R.id.username_textview);













        setSupportActionBar(materialToolbar);


        // create a drawertoggle and set the drawerlayouts toggle

        toggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);


        // sync the state of the toggle and set the homebuttonenabled and displayasup enabled

        getSupportActionBar().setTitle(Usersname + "'s Meals");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_reminders:
                        setfragment(new RemindersFragment(c));
                        materialToolbar.setTitle(Usersname + "'s Reminders");

                        break;

                    case R.id.menu_badges:
                        setfragment(new Badges_Fragment());
                        materialToolbar.setTitle(Usersname + "'s Badges");
                        break;

                    case R.id.menu_home:
                        setfragment(new Dashboard_Fragment(Dashboard.this, currentdate));
                        Usersname = getSharedPreferences("Renewprefs", MODE_PRIVATE).getString("name", "user1");
                        materialToolbar.setTitle(Usersname + "'s Meals");
                        break;

                    case R.id.menu_settings:
                        setfragment(new UserDetailsFragment(false, materialToolbar, Dashboard.this));
                        materialToolbar.setTitle(Usersname + "'s Settings");
                        checkforbadges();
                        break;

                    case R.id.menu_stats:
                        setfragment(new Stats_Fragment());
                        materialToolbar.setTitle(Usersname + "'s Stats");
                        break;


                }


                drawerLayout.closeDrawer(GravityCompat.START, false);


                return false;
            }
        });


        checkforbadges();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // call to sync the state of the actiontoggle
        toggle.syncState();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //dashboard_fragment.updateview();

    }


    public void setfragment(Fragment f) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, f);

        fragmentTransaction.commit();

    }


    public void checkforbadges() {

        // Check for each of the 6 badge conditions

        int numberofdaysused = sharedPreferences.getInt("days_used", 0);
        int numberofitemslogged = sharedPreferences.getInt("items_logged", 0);


        System.out.println(numberofdaysused + "NUMBER OF DAYS");


        // 0 = badge not earned
        // 1 = badge earned but not shown
        // 2 = new badge dialog shown


        int gettingstarted = sharedPreferences.getInt("badge1", 0);
        int loggingpro = sharedPreferences.getInt("badge2", 0);
        int emailer = sharedPreferences.getInt("badge3", 0);
        int secondday = sharedPreferences.getInt("badge4", 0);
        int firstweek = sharedPreferences.getInt("badge5", 0);
        int twoweeks = sharedPreferences.getInt("badge6", 0);


        Boolean changes = false;

        // Badge 1 : Getting Started (The user logs their first meal)

        if (gettingstarted != 3) {

            if (numberofitemslogged > 0) {
                // congrats the user has earned this reward!!
                makeText();
                // set the badge to 3 to prevent it being reshown to the user
                editor.putInt("badge1", 3);
                changes = true;
            }
        }


        // Badge 2 : Pro Logger (The user logs 6 meals)

        if (loggingpro != 3) {

            if (numberofitemslogged >= 6) {
                // congrats the user has earned this reward!!
                makeText();
                // set the badge to 3 to prevent it being reshown to the user
                editor.putInt("badge2", 3);
                changes = true;
            }


        }

        // Badge 3 : Emailer (The user sends an email log to their clinician)

        if (emailer == 2) {


            makeText();
            // set the badge to 3 to prevent it being reshown to the user
            editor.putInt("badge3", 3);
            changes = true;
        }


        // Badge 4 : Second Day (The user opens the app for a second day)

        if (secondday == 2) {

            makeText();
            // set the badge to 3 to prevent it being reshown to the user
            editor.putInt("badge4", 3);
            changes = true;
        }


        // Badge 5 : First Week (The user opens the app for 7 days)

        if (firstweek != 3) {

            if(numberofdaysused == 1){
                makeText();
                // set the badge to 3 to prevent it being reshown to the user
                editor.putInt("badge5", 3);
                changes = true;

            }

        }



        // Badge 6 : Two Week Pro (Two Week Pro)

        if (twoweeks == 2) {

            makeText();
            // set the badge to 3 to prevent it being reshown to the user
            editor.putInt("badge6", 3);
            changes = true;
        }






        if(changes) editor.commit();

    }





public void makeText(){

    Toast.makeText(this, "Getting Started Badge Earned", Toast.LENGTH_SHORT).show();


}

}
