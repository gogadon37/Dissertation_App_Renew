package com.gogadon.renewal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Retrieve the users password. When the login button is clicked, compare the two and launch
        // The home activity if a match is found else return an error message via the helper text.

        SharedPreferences myprefs = getSharedPreferences("Renewprefs", MODE_PRIVATE);
        final String password = myprefs.getString("password","");

        final TextInputEditText passwordinput = findViewById(R.id.passinputloginet);
        final TextInputLayout passwordlayout = findViewById(R.id.passinputloginlayout);
        MaterialButton login = findViewById(R.id.loginbutton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.equals(passwordinput.getText().toString())){

                    Intent i = new Intent(Login_Activity.this, Home.class);
                    startActivity(i);
                    finish();

                }else{
                    passwordlayout.setHelperText("Incorrect Password");
                }

            }
        });




    }
}
