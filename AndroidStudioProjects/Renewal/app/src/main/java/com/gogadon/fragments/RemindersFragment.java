package com.gogadon.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TimePicker;
import com.gogadon.renewal.R;

public class RemindersFragment extends Fragment {

    Context appcontext;
    String breakfasttime = "8:0";
    String morningsnacktime = "11:0";
    String lunchsnacktime = "13:0";
    String afternoonsnacktime = "15:0";
    String dinnertime = "18:0";
    String eveningsnacktime = "20:0";
    CheckBox breakfastcheckbox;
    CheckBox morningsnackcheckbox;
    CheckBox lunchcheckbox;
    CheckBox afternoonsnackcheckbox;
    CheckBox eveningsnackCheckBox;
    CheckBox dinnercheckbox;


    // Create a constructor and get the parent activities context

    public RemindersFragment(Context context) {
        appcontext = context;
    }


    // Inflate the view for the reminders fragment layout

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_reminders_fragment, container, false);
    }


    // Get the references for the widgets and call the gettime method when an edit icon is clicked.
    // Change the time selected by the user in the dialog.


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView breakfastedit = getActivity().findViewById(R.id.imageView_Breakfast);
        ImageView morningsnackedit = getActivity().findViewById(R.id.imageViewMorningSnack);
        ImageView lunchedit = getActivity().findViewById(R.id.imageView_lunch);
        ImageView afternoonsnackedit = getActivity().findViewById(R.id.imageView_AfternoonSnack);
        ImageView dinneredit = getActivity().findViewById(R.id.imageView4_Dinner);
        ImageView eveningsnackedit = getActivity().findViewById(R.id.imageView_EveningSnack);

        breakfastcheckbox = getActivity().findViewById(R.id.checkBox_Breakfast1);
        morningsnackcheckbox = getActivity().findViewById(R.id.checkBox_MorningSnack);
        lunchcheckbox = getActivity().findViewById(R.id.checkBox_Lunch);
        afternoonsnackcheckbox = getActivity().findViewById(R.id.checkBox_AfternoonSnack);
        dinnercheckbox  = getActivity().findViewById(R.id.checkBox_Dinner);
        eveningsnackCheckBox = getActivity().findViewById(R.id.checkBox_EveningSnack);

        breakfastedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettime(0, "Breakfast ");
            }
        });

        morningsnackedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettime(1, "Morning Snack ");
            }
        });


        lunchedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettime(2, "Lunch ");
            }
        });

        afternoonsnackedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettime(3, "Afternoon Snack ");
            }
        });

        dinneredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettime(4, "Dinner ");
            }
        });

        eveningsnackedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettime(5, "Evening Snack ");
            }
        });
    }



    // Create a timepickerdialog and set the text of the corresponding timer for the edit which was clicked.
    // the userfriendlytime method converts the time returned into an am or pm value.

    public void gettime(final int position, String title) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(appcontext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                switch (position){

                    case 0: breakfasttime = hourOfDay + ":" + minute ;
                    breakfastcheckbox.setText("Breakfast : " + getUserFriendlyTime(hourOfDay, minute));
                    break;

                    case 1: morningsnacktime = hourOfDay + ":" + minute;
                    morningsnackcheckbox.setText("Morning Snack : " + getUserFriendlyTime(hourOfDay, minute));
                    break;

                    case 2: lunchsnacktime = hourOfDay + ":" + minute;
                    lunchcheckbox.setText("Lunch : " + getUserFriendlyTime(hourOfDay, minute));
                    break;

                    case 3: afternoonsnacktime = hourOfDay + ":" + minute;
                    afternoonsnackcheckbox.setText("Afternoon Snack : " + getUserFriendlyTime(hourOfDay, minute));
                    break;

                    case 4: dinnertime = hourOfDay + ":" +minute;
                    dinnercheckbox.setText("Dinner : " + getUserFriendlyTime(hourOfDay, minute));
                    break;

                    case 5: eveningsnacktime = hourOfDay + ":" +minute;
                    eveningsnackCheckBox.setText("Evening Snack : " + getUserFriendlyTime(hourOfDay,minute));
                    break;

                }

            }
        }, 12, 0, false);

        timePickerDialog.setTitle(title);
        timePickerDialog.setIcon(R.drawable.ic_action_reminder);
        timePickerDialog.show();
    }


    // Given the hour of the day and the minuets calculate if the item is am or pm and return the
    // Value to the user in a userfriendly string.

    public String getUserFriendlyTime(int hourOfDay, int minute){

        String userfriendlytime;

        if(hourOfDay < 12){

            System.out.println("AM");
            if(hourOfDay == 0){

                if(minute < 10){

                    userfriendlytime = 12 + ":" + "0" + minute + " am";

                }else{

                    userfriendlytime = 12 + ":" + minute + " am";
                }



            }else {


                if(minute < 10){

                    userfriendlytime = hourOfDay + ":" + "0" + minute + " am";

                }else{

                    userfriendlytime = hourOfDay + ":" + minute + " am";
                }
            }

        }else {

            int hoursedited = hourOfDay;

            if(hourOfDay == 12){

                hoursedited = hoursedited + 12;

            }

            if(minute < 10){

                userfriendlytime = (hoursedited - 12) + ":" + "0" + minute + " pm";

            }else{

                userfriendlytime = (hoursedited-12) + ":" + minute + " pm";
            }


            System.out.println("PM");

        }
            return  userfriendlytime;
    }

}
