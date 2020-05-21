package com.gogadon.roomdatabase;

import android.app.Application;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DatabaseRepository {

    private LogDAO logDAO;
    List<Log> listlog;
   static ArrayList<Log> arrayList;

    public DatabaseRepository(Application app) {

        arrayList = new ArrayList<Log>();
        DatabaseClass databaseClass = DatabaseClass.getInstance(app);
        logDAO = databaseClass.logDAO();


    }

    public void insertlog(Log log){

        Insert insert = new Insert(logDAO);

        try {
            insert.execute(log).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void updatelog(Log log){

        Edit edit = new Edit(logDAO);
        try {
            edit.execute(log).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }




    public void deletelog(Log log){


        Delete delete = new Delete(logDAO);
        try {
            delete.execute(log).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    public List<Log> getlogsfordate(String Date){


        Getfor getfor = new Getfor(logDAO, Date);
        try {
            getfor.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return arrayList;
    }





    public List<Log> getlogs(){

        Get get = new Get(logDAO);

        try {
            get.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return  arrayList;
    }




    private  static  class Edit extends  AsyncTask<Log, Void, Void>{

        LogDAO l;

        Edit(LogDAO logDAO){

            l = logDAO;

        }


        @Override
        protected Void doInBackground(Log... logs) {

            l.update(logs[0]);
            return null;
        }
    }






    private  static class Getfor extends AsyncTask<Void, Void, Void>{


        LogDAO l;
        String date;

        Getfor(LogDAO logDAO, String date){

            l = logDAO;
            this.date = date;
        }


        @Override
        protected Void doInBackground(Void... voids) {

          arrayList.addAll(l.getallfordate(date));
            return null;
        }
    }





    private  static  class Get extends  AsyncTask<Void, Void, Void>{

        LogDAO l;

        Get(LogDAO logDAO){

            l = logDAO;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            arrayList.addAll(l.getall());
            return null;
        }


    }


    private static class Insert extends AsyncTask<Log,Void, Void>{

        LogDAO l;

        Insert(LogDAO ll){


            l =ll;

        }


        @Override
        protected Void doInBackground(Log... logs) {
            l.insert(logs[0]);
            System.out.println("log added successfully");

            return null;
        }


    }



    private static class Delete extends AsyncTask<Log,Void, Void>{

        LogDAO l;

        Delete(LogDAO ll){


            l =ll;

        }


        @Override
        protected Void doInBackground(Log... logs) {
            l.delete(logs[0]);
            System.out.println("log deleted successfully");

            return null;
        }


    }
















}







