package com.gogadon.renewal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.gogadon.fragments.Badges_Fragment;
import com.gogadon.fragments.Dashboard_Fragment;
import com.gogadon.fragments.RemindersFragment;
import com.gogadon.fragments.Stats_Fragment;
import com.gogadon.fragments.UserDetailsFragment;
import com.gogadon.roomdatabase.DatabaseRepository;
import com.gogadon.roomdatabase.Log;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends AppCompatActivity {

    String Usersname;
    ActionBarDrawerToggle toggle;
    Dashboard_Fragment dashboard_fragment;
    Badges_Fragment badges_fragment;
    RemindersFragment remindersFragment;
    Stats_Fragment stats_fragment;
    UserDetailsFragment userDetailsFragment;
    FragmentTransaction fragmentTransaction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context c;
    String currentdate;
    int whichfrag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences("Renewprefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Usersname = sharedPreferences.getString("name", "user1");



        //check the date and if it is diffrent from the last time the app was used then update the
        // days used value

        String lastdateopened = sharedPreferences.getString("lastdateopened","nodate");
        Date today = Calendar.getInstance().getTime();
        currentdate = new SimpleDateFormat("dd:MM:yyyy").format(today).toString();

        if(!currentdate.equals(lastdateopened)){

            // The date is diffrent to the last time the user opened the application add one to days used
            // and update the lastdateopenedfield

            int x = sharedPreferences.getInt("days_used",0);
            x++;
            editor.putInt("days_used", x);
            editor.putString("lastdateopened",currentdate);
            editor.commit();

        }


        final MaterialToolbar materialToolbar = findViewById(R.id.dashboard_toolbar);
        final NavigationView navigationView = findViewById(R.id.navigation);
        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout_dash);
        String open = "open";
        final String close = "close";
        c = Dashboard.this;


        // if the activity has been resotred then get the fragement else make a new one


         if(savedInstanceState == null){

             dashboard_fragment = new Dashboard_Fragment(Dashboard.this, currentdate);
             badges_fragment = new Badges_Fragment(c);
             remindersFragment = new RemindersFragment(c);
             stats_fragment = new Stats_Fragment(c);
             userDetailsFragment = new UserDetailsFragment(false,materialToolbar, c);

         }else {

             // Only one of the fragments will still be around so check that the refernce is not null for them
             // if it is null then set the new fragment.

            dashboard_fragment = (Dashboard_Fragment) getSupportFragmentManager().findFragmentByTag("frag1");
            if(dashboard_fragment !=null) dashboard_fragment.setParentactivity(this);

            badges_fragment = (Badges_Fragment) getSupportFragmentManager().findFragmentByTag("frag2");
            if(badges_fragment!= null)  badges_fragment.setContext(c);

            remindersFragment = (RemindersFragment) getSupportFragmentManager().findFragmentByTag("frag3");
            if(remindersFragment != null) remindersFragment.setContext(c);

            stats_fragment = (Stats_Fragment)getSupportFragmentManager().findFragmentByTag("frag4");
            if(stats_fragment != null) stats_fragment.setContext(c);

            userDetailsFragment = (UserDetailsFragment) getSupportFragmentManager().findFragmentByTag("frag5");
            if (userDetailsFragment != null) userDetailsFragment.setContext(c);

            if(dashboard_fragment == null)  dashboard_fragment = new Dashboard_Fragment(Dashboard.this, currentdate);
            if(badges_fragment == null)   badges_fragment = new Badges_Fragment(c);
            if(remindersFragment == null)  remindersFragment = new RemindersFragment(c);
            if(stats_fragment == null)       stats_fragment = new Stats_Fragment(c);
            if(userDetailsFragment == null) userDetailsFragment = new UserDetailsFragment(c);


         }




        setfragment(dashboard_fragment, "frag1");
        setSupportActionBar(materialToolbar);


        // create a drawertoggle and set the drawerlayouts toggle

        toggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);


        // sync the state of the toggle and set the homebuttonenabled and displayasup enabled

        getSupportActionBar().setTitle(Usersname + "'s Meals");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Change the fragment and title depending which item is clicked in the navigation drawer

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_reminders:
                        setfragment(remindersFragment,"frag3");
                        materialToolbar.setTitle(Usersname + "'s Reminders");

                        break;

                    case R.id.menu_badges:
                        setfragment(badges_fragment, "frag2");
                        materialToolbar.setTitle(Usersname + "'s Badges");
                        break;

                    case R.id.menu_home:
                        setfragment(dashboard_fragment, "frag1");
                        Usersname = getSharedPreferences("Renewprefs", MODE_PRIVATE).getString("name", "user1");
                        materialToolbar.setTitle(Usersname + "'s Meals");
                        break;

                    case R.id.menu_settings:
                        setfragment(userDetailsFragment, "frag5");
                        materialToolbar.setTitle(Usersname + "'s Settings");
                        checkforbadges();
                        break;

                    case R.id.menu_stats:
                        setfragment(stats_fragment, "frag4");
                        materialToolbar.setTitle(Usersname + "'s Stats");
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START, false);
                return false;
            }
        });


        // Check for badges as some are dependent on the number of days the user has used the application
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


//    @Override
//    protected void onResume() {
//        super.onResume();
//        dashboard_fragment.updateview(currentdate);
//
//    }


    // Replace the fragment currently inside of the activty

    public void setfragment(Fragment f ,String tag) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, f, tag);
        fragmentTransaction.commit();

    }


    // Check if any badges have been earned and not shown to the user.

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
                makeText("Getting Started Badge Earned!");
                // set the badge to 3 to prevent it being reshown to the user
                editor.putInt("badge1", 3);
                changes = true;
            }
        }


        // Badge 2 : Pro Logger (The user logs 6 meals)

        if (loggingpro != 3) {

            if (numberofitemslogged >= 6) {
                // congrats the user has earned this reward!!
                makeText("Pro Logger Badge Earned!");
                // set the badge to 3 to prevent it being reshown to the user
                editor.putInt("badge2", 3);
                changes = true;
            }


        }

        // Badge 3 : Emailer (The user sends an email log to their clinician)

        if (emailer == 2) {


            makeText("Connected Badge Earned!");
            // set the badge to 3 to prevent it being reshown to the user
            editor.putInt("badge3", 3);
            changes = true;
        }


        // Badge 4 : Second Day (The user opens the app for a second day)

        if (secondday != 3) {

            makeText("Second Day Badge Earned!");
            // set the badge to 3 to prevent it being reshown to the user
            if(numberofdaysused >1){
                editor.putInt("badge4", 3);
                changes = true;

            }

        }


        // Badge 5 : First Week (The user opens the app for 7 days)

        if (firstweek != 3) {

            if(numberofdaysused > 6){
                makeText("First Week Badge Earned!");
                // set the badge to 3 to prevent it being reshown to the user
                editor.putInt("badge5", 3);
                changes = true;

            }

        }



        // Badge 6 : Two Week Pro (Two Week Pro)

        if (twoweeks !=3) {

            if(numberofdaysused > 13){
                makeText("Two Weeks Badge Earned!");
                // set the badge to 3 to prevent it being reshown to the user
                editor.putInt("badge6", 3);
                changes = true;


            }

        }

        if(changes) editor.commit();

    }





public void makeText(String message){

    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


}


public void writeemail(String Date){




    DatabaseRepository dbr = new DatabaseRepository(getApplication());
    ArrayList<Log> logs = new ArrayList<Log>();
    logs.addAll( dbr.getlogsfordate(Date));

    // Create the email intent

    String message = "Please take a look at my meal logs for " + Date;

    for(Log log : logs){



        message = message
                          + "\n"
                          + "\n" + log.getMeal() + ":" +"\n "
                         + "\n" + " Date: " + log.getDate()
                         + "\n" + " Time: " + log.getTime()
                + "\n" + " Location: " + log.getLocation()
        + "\n" + " Binge: " + log.getB() + "\n" + " Vomit: " + log.getV() + "\n" + " Laxative: " + log.getL() + "\n" + " Situation / Thoughts / Feelings: " + "\n" + log.getThoughts()+ "\n"+ "\n";


    }



    //intent.resolve activity

    Intent email = new Intent(Intent.ACTION_SENDTO);


    email.setData(Uri.parse("mailto:"));
    email.putExtra(Intent.EXTRA_SUBJECT, "Meal Logs");
    email.putExtra(Intent.EXTRA_TEXT, message);



    // check and app on the users device can recive the intent

    if(email.resolveActivity(getPackageManager())!= null) {

        startActivity(email);
        if(  sharedPreferences.getInt("badge3", 0) == 0){

            editor.putInt("badge3",2);
            editor.commit();

        }



    }else{

        makeText("No Email Applications Found");

    }
}


}
