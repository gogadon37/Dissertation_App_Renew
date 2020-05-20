package com.gogadon.adapters;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gogadon.renewal.R;

public class Add_entry_fullscreen_entry extends DialogFragment {


    public Add_entry_fullscreen_entry() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_log_dialog, container, false);




    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog d = super.onCreateDialog(savedInstanceState);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return d;
    }


    public void ShowDialog(FragmentManager fm){

    Add_entry_fullscreen_entry dialogfrag = new Add_entry_fullscreen_entry();

    FragmentTransaction ft = fm.beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    ft.add(android.R.id.content, dialogfrag).addToBackStack(null).commit();


    }



}
