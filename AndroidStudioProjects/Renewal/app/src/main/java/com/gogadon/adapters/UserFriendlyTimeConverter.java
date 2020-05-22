package com.gogadon.adapters;

public class UserFriendlyTimeConverter {

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







