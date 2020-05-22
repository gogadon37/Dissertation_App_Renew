package com.gogadon.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gogadon.renewal.R;
import static android.content.Context.MODE_PRIVATE;

public class Badges_Fragment extends Fragment {

    Context context;
    // return the inflatedview for the badgesfragment

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.badges_fragment, container, false);
    }

    // Define a constructor and get the context of the parent activity.

    public Badges_Fragment(Context c){

        context = c;
    }

    // Get the references for the widgets and change the layout accordingly.

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the badge name textviews and badgeimageviews

        ImageView badge1imageview = getView().findViewById(R.id.badge1_image);
        TextView badge1textview = getView().findViewById(R.id.badge1_text);

        ImageView badge2imageview = getView().findViewById(R.id.badge2_image);
        TextView badge2textview = getView().findViewById(R.id.badge2_text);

        ImageView badge3imageview = getView().findViewById(R.id.badge3_image);
        TextView badge3textview = getView().findViewById(R.id.badge3_text);

        ImageView badge4imageview = getView().findViewById(R.id.badge4_image);
        TextView badge4textview = getView().findViewById(R.id.badge4_text);

        ImageView badge5imageview = getView().findViewById(R.id.badge5_image);
        TextView badge5textview = getView().findViewById(R.id.badge5_text);

        ImageView badge6imageview = getView().findViewById(R.id.badge6_image);
        TextView badge6textview = getView().findViewById(R.id.badge6_text);

        // Get the values for if the user has achived the badge

      SharedPreferences sharedPreferences = context.getSharedPreferences("Renewprefs", MODE_PRIVATE);

        int gettingstarted = sharedPreferences.getInt("badge1", 0);
        int loggingpro = sharedPreferences.getInt("badge2", 0);
        int emailer = sharedPreferences.getInt("badge3", 0);
        int secondday = sharedPreferences.getInt("badge4", 0);
        int firstweek = sharedPreferences.getInt("badge5", 0);
        int twoweeks = sharedPreferences.getInt("badge6", 0);

        // Update the titles and images depending on if the badges have been earned.

        if(gettingstarted >=2) { badge1imageview.setImageResource(R.drawable.badge01); badge1textview.setText("Getting Started!");}
        if(loggingpro >=2){ badge2imageview.setImageResource(R.drawable.badge02); badge2textview.setText("Logging Pro!");}
        if(emailer >=2) {badge3imageview.setImageResource(R.drawable.badge03); badge3textview.setText("Connect!");}
        if(secondday >=2) {badge4imageview.setImageResource(R.drawable.badge4); badge4textview.setText("Second Day!");}
        if(firstweek >=2) {badge5imageview.setImageResource(R.drawable.badge05); badge5textview.setText("First Week!");}
        if(twoweeks >=2) {badge6imageview.setImageResource(R.drawable.badge06); badge6textview.setText("Two Weeks!");}

    }
}
