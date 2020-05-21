package com.gogadon.fragments;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gogadon.adapters.Add_entry_fullscreen_entry;
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
    Dashboard parentactivity;

    TextView datetextview;

    String currentdate;

    String datecursor;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    SimpleDateFormat simpleDateFormat;
    Date dateselected;


    public Dashboard_Fragment(Dashboard dash, String currentdate) {

        parentactivity = dash;
        this.currentdate = currentdate;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard__fragment, container, false);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         dbr = new DatabaseRepository(getActivity().getApplication());
          logss = dbr.getlogsfordate(currentdate);


        sharedPreferences = getActivity().getSharedPreferences("Renewprefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();




        // check the dates and update the

        recyclerView = view.findViewById(R.id.dash_recycler);
        layoutAdapter = new LayoutAdapter(getContext(), logss, this);
        recyclerView.setAdapter(layoutAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab = view.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getContext(), AddItemActivity.class);
                startActivityForResult(i, 0);


            }
        });

        datetextview = getActivity().findViewById(R.id.datetv);


       simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy");
        try {
            dateselected = simpleDateFormat.parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final MaterialButton next = getActivity().findViewById(R.id.nextday);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    System.out.println(dateselected);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateselected);
                    calendar.add(Calendar.DATE, 1);

                    dateselected = calendar.getTime();
                    datecursor = simpleDateFormat.format(calendar.getTime());
                    System.out.println(datecursor);
                updateview(datecursor);




            }
        });



        MaterialButton previous = getActivity().findViewById(R.id.previousday);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                System.out.println(dateselected);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateselected);
                calendar.add(Calendar.DATE, -1);

                dateselected = calendar.getTime();
                datecursor = simpleDateFormat.format(calendar.getTime());
                System.out.println(datecursor);

                updateview(datecursor);





            }
        });




    }


    public void delete(Log l){

        System.out.println("Delete was clicked");
        DatabaseRepository  dbc = new DatabaseRepository(getActivity().getApplication());
        dbc.deletelog(l);
        logss.remove(l);
        layoutAdapter.notifyDataSetChanged();


    }


    public void update(Log l){

        // Launch the additem activty and bundle the data contained inside of the itemview

        System.out.println(l.getId_key() + " Is the key");

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

   }











}
