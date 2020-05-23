package com.gogadon.fragments;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogadon.adapters.LayoutAdapter;
import com.gogadon.renewal.AddItemActivity;
import com.gogadon.renewal.Dashboard;
import com.gogadon.renewal.R;
import com.gogadon.roomdatabase.DatabaseRepository;
import com.gogadon.roomdatabase.Log;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Dashboard_Fragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    LayoutAdapter layoutAdapter;
    static List<Log> logss;
    DatabaseRepository dbr;

    public void setParentactivity(Dashboard parentactivity) {
        this.parentactivity = parentactivity;
    }

    Dashboard parentactivity;
    TextView datetextview;
    String currentdate;
    String datecursor;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SimpleDateFormat simpleDateFormat;
    Date dateselected;



    // Create the constructor and get the parent activity (Dashboard.class) and the current date


    public Dashboard_Fragment(){

        Date d = new Date();
        currentdate = new SimpleDateFormat("dd:MM:yyyy").format(d).toString();


    }



    public Dashboard_Fragment(Dashboard dash, String currentdate) {

        parentactivity = dash;
        this.currentdate = currentdate;
        datecursor = currentdate;
    }

    // Inflate the fragment for the dashboard fragment layout

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard__fragment, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        datetextview = getActivity().findViewById(R.id.datetv);

        // Get the list of logs from the database from the given current date.

         dbr = new DatabaseRepository(getActivity().getApplication());
         logss = dbr.getlogsfordate(currentdate);


         // Set the sharedPreferences reference and editor reference
        sharedPreferences = getActivity().getSharedPreferences("Renewprefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        // get the recycler reference and populate it using the Layout Adapter

        recyclerView = view.findViewById(R.id.dash_recycler);
        layoutAdapter = new LayoutAdapter(getContext(), logss, this);
        recyclerView.setAdapter(layoutAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        // get a reference to the fab and launch the add item activity when it is clicked.

        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), AddItemActivity.class);
                startActivityForResult(i, 0);


            }
        });




        // parse the date passed by the parent so it can be used by the calander class later.

       simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy");
        try {
            dateselected = simpleDateFormat.parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // When the next button is clicked create a calander set it to the given date add one day to it then parse that date to a string
        // Call the updateview method to update the recyclerview passing the new date.

        final MaterialButton next = getActivity().findViewById(R.id.nextday);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateselected);
                    calendar.add(Calendar.DATE, 1);
                    dateselected = calendar.getTime();
                    datecursor = simpleDateFormat.format(calendar.getTime());
                    updateview(datecursor);

            }
        });


        // Same as above but the opposite

        MaterialButton previous = getActivity().findViewById(R.id.previousday);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateselected);
                calendar.add(Calendar.DATE, -1);
                dateselected = calendar.getTime();
                datecursor = simpleDateFormat.format(calendar.getTime());
                updateview(datecursor);

            }
        });


        // When the send logs button is clicked call the write email method passing in the current date the cursor is set to.

        MaterialButton sendlogs = getActivity().findViewById(R.id.sendlogs);
        sendlogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentactivity.writeemail(datecursor);
                datecursor = currentdate;
            }
        });



    }




    //-------------------------------------------------------------------------------------------------------//

    // Class Methods

    // Call the delete method of the database repository and pass in the log
    // This method is called by the itemview and it passess in the id of the log it holds
    // The adapter is then updated to reflect this change.

    public void delete(Log l){

        DatabaseRepository  dbc = new DatabaseRepository(getActivity().getApplication());
        dbc.deletelog(l);
        logss.remove(l);
        layoutAdapter.notifyDataSetChanged();


    }


    // The update method launches the add item activty but passess some extras
    // The extras contian the values of the log object
    // This method is also called by the itemview and the data passed corresponds to the data the itemview contains.
    // The activity is launched for result which this class then waits for.
    public void update(Log l){

        // Launch the additem activty and bundle the data contained inside of the itemview

        Intent i = new Intent(getContext(), AddItemActivity.class);
        i.putExtra("Spinner1", l.getMeal());
        i.putExtra("Id", l.getId_key());
        i.putExtra("Spinner2", l.getMood());

        i.putExtra("Time", l.getTime());
        i.putExtra("Location", l.getLocation());
        i.putExtra("FoodDrink", l.getFooddrink());
        i.putExtra("Situation", l.getThoughts());

        i.putExtra("b", l.getB());
        i.putExtra("v", l.getV());
        i.putExtra("l", l.getL());
        i.putExtra("date", l.getDate());
        startActivityForResult(i, 0);

    }


    // When a result is returned the class must check it was successful
    // If an item has been added or edited succesfully then set the date back to todays date, update the view
    // Finally check for badges (Best time to check as most badges are related to adding items.)

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            System.out.println("worked bro");
            datecursor = currentdate;

            try {
                dateselected = simpleDateFormat.parse(currentdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            updateview(currentdate);
            parentactivity.checkforbadges();


        }


    }


    // reset the logs list, get a new list from the database and change the adapter
    // set the date selected back to the current date.


    public void updateview(String date){

        logss.clear();
        logss = dbr.getlogsfordate(date);
        layoutAdapter = new LayoutAdapter(getContext(), logss, this);
        recyclerView.setAdapter(layoutAdapter);
        System.out.println("finally it was done");

        if(date.equals( currentdate) ){

            datetextview.setText("Today");

        }else{

            datetextview.setText(date);


        }

        datecursor = date;

        try {
            dateselected = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
