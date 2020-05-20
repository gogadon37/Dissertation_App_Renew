package com.gogadon.renewal;


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
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gogadon.adapters.Add_entry_fullscreen_entry;
import com.gogadon.adapters.LayoutAdapter;
import com.gogadon.roomdatabase.DatabaseRepository;
import com.gogadon.roomdatabase.Log;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Dashboard_Fragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    LayoutAdapter layoutAdapter;
    static List<Log> logss;
    DatabaseRepository dbr;






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

      logss = dbr.getlogs();





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
        startActivityForResult(i, 0);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            System.out.println("worked bro");
            updateview();

        }


    }

    public void updateview(){



        logss = dbr.getlogs();
        layoutAdapter = new LayoutAdapter(getContext(), logss, this);
        recyclerView.setAdapter(layoutAdapter);


       System.out.println("finally it was done");


   }
}
