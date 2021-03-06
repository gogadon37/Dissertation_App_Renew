package com.gogadon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gogadon.fragments.Dashboard_Fragment;
import com.gogadon.renewal.R;
import com.gogadon.roomdatabase.Log;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.customholder> {

    Context context;
    Dashboard_Fragment d;
    List<Log> logs;


    // Define the constructor to get the context, the parent class and the list of logs

    public LayoutAdapter(Context c , List<Log> logList, Dashboard_Fragment dbf) {

        d =dbf;
        context = c;
        logs =logList;

    }


    // Inflate the view and pass it to the returned customviewholder

    @NonNull
    @Override
    public customholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitem, parent, false);
       return new customholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final customholder holder, int position) {

        // Set the text for the current item by calling the references defined in the custom viewholder class.

        final Log l =logs.get(position);
        holder.title.setText(l.getMeal());
        holder.timeeaten.setText(l.getTime());
        holder.location.setText(l.getLocation());
        holder.binge.setText(booleantoyesno(l.getB()));
        holder.vomit.setText(booleantoyesno(l.getV()));
        holder.laxative.setText(booleantoyesno(l.getL()));
        holder.feelings.setText(l.getThoughts());
        holder.foodanddrink.setText(l.getFooddrink());


         // Display a colour based on the mood of the user for that meal.

        switch (l.getMood()){

            case "Good": holder.moodimage.setImageResource(R.mipmap.goodmeal);
                break;
            case "Ok": holder.moodimage.setImageResource(R.mipmap.okmeal);
                break;

            default: holder.moodimage.setImageResource(R.mipmap.badmeal);

        }


        // set the edit and delete buttons, calling the parent activities methods

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.delete(l);

            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.update(l);

            }
        });


        // Make the top of the card clickable and toggle the show/hide of the bottom of the card.

        holder.toplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the holder has been hidden already then make its visable and change
                // the imageview to the hide key.

                if(holder.hidden){

                    holder.layout.setVisibility(View.VISIBLE);
                    holder.showhide.setImageResource(R.drawable.ic_action_less);
                    holder.hidden = false;

                }else{

                    holder.layout.setVisibility(View.GONE);
                    holder.showhide.setImageResource(R.drawable.ic_action_more);
                    holder.hidden = true;
                }

            }
        });





    }


    //======================================================================================================//w


    // Simple method to convert a boolean to yes or no string to be more userfriendly.w

    public String booleantoyesno (Boolean b){

        if(b){

            return "Yes";

        }else{

            return "No";
        }

    }

    // The number of items is equal to the size of the log

    @Override
    public int getItemCount() {
        return logs.size();
    }


    // Define a customviewholder class to hold references to the widgets.

    public class customholder extends  RecyclerView.ViewHolder{

        ImageView showhide;
        LinearLayout layout;
        boolean hidden;
        TextView title;
        LinearLayout toplayout;
        MaterialButton edit;
        MaterialButton delete;
        TextView timeeaten;
        TextView location;
        TextView binge;
        TextView vomit;
        TextView laxative;
        TextView feelings;
        TextView foodanddrink;
        ImageView moodimage;

        //Define a customviewholder constructor and set the references to each widget inside

        public customholder(@NonNull View itemView) {
            super(itemView);

            edit = itemView.findViewById(R.id.log_edit);
            delete = itemView.findViewById(R.id.log_delete);
            moodimage = itemView.findViewById(R.id.list_item_meal_image);
            timeeaten = itemView.findViewById(R.id.list_item_time);
            foodanddrink = itemView.findViewById(R.id.list_item_subtitle);
             location = itemView.findViewById(R.id.list_item_location);
             binge = itemView.findViewById(R.id.list_item_b);
             vomit = itemView.findViewById(R.id.list_item_v);
             laxative = itemView.findViewById(R.id.list_item_l);
             feelings = itemView.findViewById(R.id.list_item_situation);
             showhide = itemView.findViewById(R.id.hide_show_listitem);
             layout = itemView.findViewById(R.id.showhidelayout);
             title = itemView.findViewById(R.id.listitemtitle);
             toplayout = itemView.findViewById(R.id.toplayout);
             hidden = true;
        }




    }
}
