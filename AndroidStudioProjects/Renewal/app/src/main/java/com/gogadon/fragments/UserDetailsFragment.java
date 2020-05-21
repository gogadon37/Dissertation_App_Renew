package com.gogadon.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gogadon.renewal.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class UserDetailsFragment extends Fragment {

    String finname;
    String finpassword;
    String finemail;
    boolean setup;
    MaterialButton updatebutton;
    Context c;
    MaterialToolbar mt;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;

    //name
    TextInputEditText textInputEditText;
    TextInputLayout namelayout;

    //password
    TextInputLayout passwordlayout;
    TextInputEditText passwordtext;

    //confirmpass
    TextInputLayout conpasswordlayout;
    TextInputEditText conpasswordtext;


    //email
    TextInputLayout emaillayout;
    TextInputEditText emailedittext;





    public String getName() {
        return finname;
    }

    public String getPassword() {
        return finpassword;
    }


    public String getEmail() {
        return finemail;
    }



   public UserDetailsFragment(Context c){

        setup = true;
        this.c = c;
    }


    public UserDetailsFragment(boolean setup, MaterialToolbar materialToolbar, Context c){

    this.setup = setup;
    mt = materialToolbar;
    this.c = c;
    }


    // Inflate the fragment

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        return inflater.inflate(R.layout.activity_user_details_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         updatebutton = getActivity().findViewById(R.id.update_button);

         passwordlayout = getActivity().findViewById(R.id.password_layout);
         passwordtext = getActivity().findViewById(R.id.password_textinput);

         textInputEditText = getActivity().findViewById(R.id.time_input);
         namelayout = getActivity().findViewById(R.id.time_layout);

         conpasswordlayout = getActivity().findViewById(R.id.conpassword_layout);
         conpasswordtext = getActivity().findViewById(R.id.conpassword_textinput);

         emailedittext = getActivity().findViewById(R.id.email_textinput);


        sharedPreferences = c.getSharedPreferences("Renewprefs", c.MODE_PRIVATE);
         editor = sharedPreferences.edit();




        if(!setup){

            // get the data from the sharedpreferences and update the textfields

           textInputEditText.setText(sharedPreferences.getString("name",""));
            passwordtext.setText(sharedPreferences.getString("password", ""));
            conpasswordtext.setText(sharedPreferences.getString("password", ""));
            emailedittext.setText(sharedPreferences.getString("email", ""));




            updatebutton.setVisibility(View.VISIBLE);
            updatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(verifypageone()){

                        savedata();
                    }
                }
            });


        }



    }

    public void savedata(){



        editor.putString("name", getName());
        editor.putString("password",getPassword());
        editor.putString("email",getEmail());
        editor.putBoolean("finishedsetup", true);
        editor.commit();


        if(!setup){

            mt.setTitle(getName() + "'s Settings");
            Toast t = Toast.makeText(getContext(), "Settings Updated" , Toast.LENGTH_SHORT);




            t.show();
        }


    }




    public boolean verifypageone(){

        // check that a name has been entered and display an error message if true

        boolean errorfound = false;


        String name = textInputEditText.getText().toString();




        if(name.trim().isEmpty()){
            namelayout.setErrorEnabled(true);
            namelayout.setError("This field is required");
            errorfound = true;
        }else{
          //  namelayout.setErrorEnabled(false);
        }

        // check if the user has entered a password


        String password = passwordtext.getText().toString().trim();

        if(password.isEmpty()){

            passwordlayout.setErrorEnabled(true);
            passwordlayout.setError("This field is required");
            errorfound = true;
        }else{

          //  passwordlayout.setErrorEnabled(false);


        }

        // check the user has confirmed the password and it matches the password entered above


        String conpassword = conpasswordtext.getText().toString().trim();

        if(conpassword.isEmpty()){
            errorfound = true;
            conpasswordlayout.setErrorEnabled(true);
            conpasswordlayout.setError("This field is required");

        }else{

            // check that the conpassword matches the password entered above

            if(!conpassword.equals(password)){
                errorfound = true;
                conpasswordlayout.setErrorEnabled(true);
                conpasswordlayout.setError("Password does not match");


            }else{

              //  conpasswordlayout.setErrorEnabled(false);

            }
        }


        //
        if(!errorfound){

            finpassword = passwordtext.getText().toString();
            finname = textInputEditText.getText().toString();
            finemail =  emailedittext.getText().toString();

            return true;


        }else {

            return false;

        }


    }


}
