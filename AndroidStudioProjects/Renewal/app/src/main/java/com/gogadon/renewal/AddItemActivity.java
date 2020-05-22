package com.gogadon.renewal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import com.gogadon.adapters.UserFriendlyTimeConverter;
import com.gogadon.roomdatabase.DatabaseRepository;
import com.gogadon.roomdatabase.Log;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    UserFriendlyTimeConverter userFriendlyTimeConverter;
    TextInputLayout timelayout;
    String timeselected;
    TextInputLayout locationlayout;
    TextInputLayout foodlayout;
    TextInputLayout situationlayout;
    Spinner meals;
    Spinner thoughtspinner;
    int numberoflogs;
    TextInputEditText time;
    TextInputEditText location;
    TextInputEditText foodanddrink;
    TextInputEditText situation;
    SwitchMaterial bswitch;
    SwitchMaterial lswitch;
    SwitchMaterial vswitch;
    DatabaseRepository dbc;
    Boolean edit;
    int id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String todaysdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_log_dialog);

        sharedPreferences = getSharedPreferences("Renewprefs", MODE_PRIVATE);
        numberoflogs = sharedPreferences.getInt("items_logged",0);
        editor = sharedPreferences.edit();



        // get the date


        Date today = Calendar.getInstance().getTime();
        todaysdate = new SimpleDateFormat("dd:MM:yyyy").format(today).toString();




        edit =false;
        //get the layouts holding the edittextviews

        MaterialToolbar toolbar = findViewById(R.id.dashboard_toolbar);
         dbc = new DatabaseRepository(getApplication());

        setSupportActionBar(toolbar);

         timelayout = findViewById(R.id.time_layout);
         locationlayout = findViewById(R.id.location_layout);
         foodlayout = findViewById(R.id.fooddrink_layout);
         situationlayout = findViewById(R.id.situation_layout);

        // edittexts and switches

         time = findViewById(R.id.time_input);
         location = findViewById(R.id.location);
         foodanddrink = findViewById(R.id.fooddrink);
         situation = findViewById(R.id.situation);

         bswitch = findViewById(R.id.switch_b);
         lswitch = findViewById(R.id.switch_l);
         vswitch = findViewById(R.id.switch_v);

         MaterialButton submit = findViewById(R.id.save_button);

        LinearLayout ll = findViewById(R.id.timell);

         meals = findViewById(R.id.spinner2);
         thoughtspinner = findViewById(R.id.spinner3);

        // Populate the dropdownpickers

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mealsarray, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.feelings, R.layout.support_simple_spinner_dropdown_item);

        meals.setAdapter(adapter);
        thoughtspinner.setAdapter(adapter1);

        // auto set the time to the current time
         userFriendlyTimeConverter = new UserFriendlyTimeConverter();


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddItemActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                          timeselected = userFriendlyTimeConverter.getUserFriendlyTime(hourOfDay, minute) ;
                          time.setText( userFriendlyTimeConverter.getUserFriendlyTime(hourOfDay, minute));


                    }
                }, 12, 0, false);

                timePickerDialog.setTitle("What time did you eat?");
                timePickerDialog.setIcon(R.drawable.ic_action_reminder);
                timePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkvalues();
            }
        });

        // check if anything was bundled with the call if it has been then the activity has been
        // launched with the intention of editing an activity so retrive the values from inside the bundle
        // and set the widgets text etc to match.

        Bundle b = getIntent().getExtras();
        if(b == null) System.out.println("BundleEmpty");
        else {

            // if something has been bundled then the activty has been launched as an edit request
            getSupportActionBar().setTitle("Edit Log");
          meals.setSelection(getmealposition(b.getString("Spinner1")));
          thoughtspinner.setSelection(getmoodposition(b.getString("Spinner2")));
          timeselected = b.getString("Time");
          time.setText(b.getString("Time"));
          location.setText(b.getString("Location"));
          foodanddrink.setText(b.getString("FoodDrink"));
          situation.setText(b.getString("Situation"));
          todaysdate = b.getString("date");
          bswitch.setChecked(b.getBoolean("b"));
          vswitch.setChecked(b.getBoolean("v"));
          lswitch.setChecked(b.getBoolean("l"));
          id = b.getInt("Id");
          edit = true;
        }
    }


    // Verify the values entered into the form

    public Boolean checkvalues(){

        Boolean errorfound = false;

        // Check that a meal has been selected from the dropdownlist
        // Check that a mood positivity has been selected


        // Check that a time has been entered into the box
        if(time.getText().toString().trim().isEmpty()){
            timelayout.setError("This field is required");
            errorfound = true;
        }

        // Check that a location has been added
        if(location.getText().toString().trim().isEmpty()){
            locationlayout.setError("This field is required");
            errorfound = true;
        }

        // Check the user has entered what they have eaten and drank for the meal
        if(foodanddrink.getText().toString().trim().isEmpty()){
            foodlayout.setError("This field is required");
            errorfound = true;
        }

        // Check the user has entered details of their thoughts into the field
        if(situation.getText().toString().trim().isEmpty()){
            situationlayout.setError("This field is required");
            errorfound = true;
        }

        return errorfound;
    }



    // When the save button is clicked... save the log if a new log or save over the old log if an edited log
    // return result is ok! and close this activity.

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.save_setting:

                if(!checkvalues()){

                    Log l = new Log(todaysdate
                            ,meals.getSelectedItem().toString(),
                            thoughtspinner.getSelectedItem().toString(),
                            timeselected, location.getText().toString().trim(),
                            foodanddrink.getText().toString().trim(),
                            situation.getText().toString().trim(),
                            bswitch.isChecked(), lswitch.isChecked(),
                            vswitch.isChecked()
                            );


                    System.out.println("testme " + bswitch.isChecked());


                    if(edit){
                        l.setId_key(id);
                        dbc.updatelog(l);
                        System.out.println("Is log Updated");

                    }else{

                        dbc.insertlog(l);
                        System.out.println("log added");
                        numberoflogs ++;
                        System.out.println("Total logs = " + numberoflogs);
                        editor.putInt("items_logged", numberoflogs);
                        editor.commit();

                    }

                    setResult(RESULT_OK, getIntent());
                    finish();
                }

            break;
            default: finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.test,menu);
        return true;
    }


    // Get the positions for the meal

    public int getmealposition (String mealstring ){

        switch (mealstring){

            case "Unscheduled":  return 0;
            case "Breakfast" : return  1;
            case "Morning Snack":  return 2;
            case "Lunch" : return  3;
            case "Afternoon Snack" : return  4;
            case "Dinner":  return 5;
            case "Evening Snack" : return  6;
        }


        return 1;
    }

    // return the positon for the thoughts

    public int getmoodposition (String mealstring ){

        switch (mealstring.trim().toLowerCase()){

            case "good":  return 0;
            case "ok" : return  1;
            case "bad":  return 2;

        }


        return 1;
    }
}
