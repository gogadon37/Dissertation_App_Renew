package com.gogadon.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gogadon.renewal.R;
import java.util.ArrayList;
import static android.content.Context.MODE_PRIVATE;

public class Stats_Fragment extends Fragment {

    // Global Variables used to instantiate the sharedpreferences.
    SharedPreferences sharedPreferences;
    Context context;


    // Create a constructor and get the context of the parents activity.

    public  Stats_Fragment (Context c){

        context = c;
    }


    //  Inflate the relevent view

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stats_fragment , container, false);
    }

    // Get the widget references and the stats stored in the sharedPreferences and display them in the corresponding widgets.

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get views to display the stats

        TextView mealsloggedstat = getActivity().findViewById(R.id.stat_meals_logged);
        TextView daysloggedstat = getActivity().findViewById(R.id.stat_days_logged);
        TextView badgesearnedstat = getActivity().findViewById(R.id.stat_badges_earned);
        sharedPreferences = context.getSharedPreferences("Renewprefs", MODE_PRIVATE);

        // Update the textfields of the first two stats by collecting the details from the shared preferences

        mealsloggedstat.setText("" +sharedPreferences.getInt("items_logged", 0));
        daysloggedstat.setText("" +sharedPreferences.getInt("days_used", 0));


        // Get the details of the badges from the shared preferences

        int gettingstarted = sharedPreferences.getInt("badge1", 0);
        int loggingpro = sharedPreferences.getInt("badge2", 0);
        int emailer = sharedPreferences.getInt("badge3", 0);
        int secondday = sharedPreferences.getInt("badge4", 0);
        int firstweek = sharedPreferences.getInt("badge5", 0);
        int twoweeks = sharedPreferences.getInt("badge6", 0);


        ArrayList<Integer> badges = new ArrayList<Integer>();
        badges.add(gettingstarted);
        badges.add(loggingpro);
        badges.add(emailer);
        badges.add(secondday);
        badges.add(firstweek);
        badges.add(twoweeks);

        int totalbadges = 0;
        // Cycle through the array and add 1 to the total per badge that has been earned;

        for(int x: badges){
            if(x != 0){
                totalbadges ++;
            }
        }


        // Finally update the textview to reflect the count
        badgesearnedstat.setText("" + totalbadges);
    }
}
