package com.gogadon.renewal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gogadon.adapters.CustomviewPager;
import com.gogadon.fragments.RemindersFragment;
import com.gogadon.fragments.UserDetailsFragment;
import com.gogadon.fragments.termsandconditions_fragment;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    // create variables
    private final int numberofpages = 3;
    CustomviewPager viewPager;
    boolean setupcomplete;
    private PagerAdapter pagerAdapter;
    UserDetailsFragment frag1;
    RemindersFragment frag2;
    termsandconditions_fragment frag3;
    TextView Headingtext;
    ImageView icon1;
    ImageView icon2;
    ImageView icon3;
    SharedPreferences myprefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myprefs = getSharedPreferences("Renewprefs", MODE_PRIVATE);
        editor = myprefs.edit();

        if(myprefs.getBoolean("finishedsetup", false)){

            // Start the login activity.
            Intent i = new Intent(MainActivity.this, Login_Activity.class);
            startActivity(i);

            finish();
       }


        frag1 = new UserDetailsFragment(MainActivity.this);
        frag2 = new RemindersFragment(MainActivity.this);
        frag3 = new termsandconditions_fragment();

         viewPager = (CustomviewPager) findViewById(R.id.viewpager);
         viewPager.setAdapter(new pageradapter(getSupportFragmentManager()));
         viewPager.setPageMargin(30);

         Headingtext = findViewById(R.id.info_textview);
         icon1 = findViewById(R.id.page1_image);
         icon2 = findViewById(R.id.page2_image);
         icon3 = findViewById(R.id.page3_image);

        final MaterialButton next = findViewById(R.id.next_button);
        final MaterialButton back = findViewById(R.id.back_button);


        // when next is clicked update the item in the viewpager and update the progress dots
        // when the final time occurs save the data from the first fragment.

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Button Clicked");

                if (viewPager.getCurrentItem() == 0) {

                    if (frag1.verifypageone()) {

                        // page one is valided the button should move the viewoager to the next fragment
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        Headingtext.setText("Notification Settings");
                        icon1.setImageResource(R.mipmap.circletwo);
                        icon2.setImageResource(R.mipmap.circleone);
                        back.setVisibility(View.VISIBLE);

                    }



                }else if (viewPager.getCurrentItem() == 1){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    Headingtext.setText("Terms and Conditions");
                    next.setText("Done");
                    icon2.setImageResource(R.mipmap.circletwo);
                    icon3.setImageResource(R.mipmap.circleone);
                    System.out.println("fragtwo clicked");


                }else{

                    // the done button has been clicked.
                    // save the data from the form to sharedpreferences

                    frag1.savedata();

                    // set the reminders

                    Intent i = new Intent(MainActivity.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(viewPager.getCurrentItem() == 1){

                    viewPager.setCurrentItem(0);
                    Headingtext.setText("User Details");
                    back.setVisibility(View.INVISIBLE);
                    icon1.setImageResource(R.mipmap.circleone);
                    icon2.setImageResource(R.mipmap.circletwo);

                }else if(viewPager.getCurrentItem() == 2){
                    viewPager.setCurrentItem(1);
                    Headingtext.setText("Notification Settings");
                    icon2.setImageResource(R.mipmap.circleone);
                    icon3.setImageResource(R.mipmap.circletwo);

                    next.setText("next");

                }
            }
        });
    }

    private class pageradapter extends FragmentStatePagerAdapter {
        public pageradapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            if(position == 0){

                return frag1;

            }else if(position == 1){

                return frag2;

            }else{

                return frag3;

            }
        }

        @Override
        public int getCount() {
            return numberofpages;
        }
    }
}