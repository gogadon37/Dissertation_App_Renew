package com.gogadon.renewal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gogadon.adapters.Add_entry_fullscreen_entry;
import com.gogadon.fragments.Badges_Fragment;
import com.gogadon.fragments.UserDetailsFragment;
import com.gogadon.roomdatabase.DatabaseRepository;
import com.gogadon.roomdatabase.Log;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Dashboard extends AppCompatActivity {
    String Usersname;
    ActionBarDrawerToggle toggle;
    Dashboard_Fragment dashboard_fragment;
     FragmentTransaction fragmentTransaction;
     ActionBar actionBar;
     Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final MaterialToolbar materialToolbar = findViewById(R.id.dashboard_toolbar);
        final NavigationView navigationView = findViewById(R.id.navigation);
        final DrawerLayout drawerLayout = findViewById(R.id.drawerlayout_dash);
        String open = "open";
        final String close = "close";



        c = Dashboard.this;


        dashboard_fragment = new Dashboard_Fragment();
        final FrameLayout frameLayout = findViewById(R.id.frameLayout2);



        setfragment(dashboard_fragment);



        View header = navigationView.getHeaderView(0);



        TextView textView = header.findViewById(R.id.username_textview);

         Usersname = getSharedPreferences("Renewprefs", MODE_PRIVATE).getString("name", "user1");






        setSupportActionBar(materialToolbar);


        // create a drawertoggle and set the drawerlayouts toggle

         toggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);


        // sync the state of the toggle and set the homebuttonenabled and displayasup enabled

        getSupportActionBar().setTitle( Usersname + "'s Dashboard");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){

                    case R.id.menu_reminders : setfragment(new RemindersFragment(c));
                        materialToolbar.setTitle(Usersname + "'s Reminders");
                        break;

                    case R.id.menu_badges : setfragment(new Badges_Fragment());
                        materialToolbar.setTitle(Usersname + "'s Badges");
                        break;

                    case R.id.menu_home: setfragment(new Dashboard_Fragment());
                        Usersname = getSharedPreferences("Renewprefs", MODE_PRIVATE).getString("name", "user1");
                       materialToolbar.setTitle( Usersname + "'s Dashboard");
                    break;

                    case R.id.menu_settings: setfragment(new UserDetailsFragment(false, materialToolbar));
                        materialToolbar.setTitle(Usersname + "'s Settings");
                        break;





                }



                drawerLayout.closeDrawer(GravityCompat.START, false);





               // setfragment(new Badges_Fragment());
               // materialToolbar.setTitle( Usersname +"'s Badges");

                return false;
            }
        });





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


    public void setfragment(Fragment f){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, f);

        fragmentTransaction.commit();

    }


}
