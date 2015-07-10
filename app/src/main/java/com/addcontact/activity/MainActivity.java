package com.addcontact.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.addcontact.R;
import com.addcontact.Util.FileChooser;
import com.addcontact.Util.Util;
import com.addcontact.application.cAapplication;
import com.addcontact.data.PreferencesHelper;
import com.addcontact.models.DataObject;
import com.addcontact.widgets.SlidingTabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


public class MainActivity extends AppCompatActivity {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Add Person","Person List"};
    int Numboftabs =2;
    private ArrayList<DataObject> _list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
       /* tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);*/

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.dismiss();
                                MainActivity.this.finish();
                            }
                        });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setMessage("Are you sure want to exit?");
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }


     class ExportDatabaseCSVTask extends AsyncTask<Void, Void, Boolean>{

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
         private String filePath=null;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... args){

             File exportDir= Util.getPhotoDirectory();
             try {

                 File file= Util.createFile(exportDir.getAbsolutePath(),"PersonCSV.csv");
                 filePath=file.getAbsolutePath();

                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

                _list= cAapplication.getInstance().getDBAdapter().getAllContact();
                DataObject dataObject=null;
                // this is the Column of the table and same for Header of CSV file
                String arrStr1[] ={"FirstName", "MiddleName", "LastName","MobileNo","Address","Village","Taluko","District","Pincode","SlipNo","CustomerId","Category","InstallmentYear","InstallmentPrice","AuthorisePerson","InsertDateTime"};
                csvWrite.writeNext(arrStr1);

                if(_list.size() > 0)
                {
                    for(int index=0; index < _list.size(); index++)
                    {
                        dataObject=_list.get(index);
                        String addres= dataObject.getAddress().toString().replaceAll(","," ");
                        String arrStr[] ={dataObject.getFirstName(),
                                dataObject.getMiddleName(),
                                dataObject.getLastName(),
                                dataObject.getMobileNo(),
                                addres,
                                dataObject.getVillage(),
                                dataObject.getTaluko(),
                                dataObject.getDistrict(),
                                dataObject.getPincode(),
                                dataObject.getSlipNo(),
                                dataObject.getCustomerId(),
                                dataObject.getCategory(),
                                dataObject.getInstallmentYear(),
                                dataObject.getIInstallmentPrice(),
                                dataObject.getAuthorisePerson(),
                                dataObject.getDateTime()};
                        csvWrite.writeNext(arrStr);
                    }
                }

                csvWrite.close();
                return true;
            }
            catch (IOException e){
                Log.e("MainActivity", e.getMessage(), e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (this.dialog.isShowing()){
                this.dialog.dismiss();
            }
            if (success){
                Toast.makeText(MainActivity.this, "Export successful! "+filePath, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Export failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint("Enter Text");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_export) {
            if(cAapplication.getInstance().getDBAdapter().CheckDataCount()!=0){
                new ExportDatabaseCSVTask().execute();
            }else{
                Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        if (id == R.id.action_import) {
            showdialog();
            return true;
        }

        if (id == R.id.action_share) {
            shareFile();
            return true;
        }
       /* if (id == R.id.action_clear) {
            cAapplication.getInstance().getDBAdapter().deletetbl_contact();
            return true;
        }*/

        if (id == R.id.action_about) {
            Intent i = new Intent(getApplicationContext(), Aboutus.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
            return true;
        }

        if(id==R.id.action_logout){
            resetSystem();
            return true;
        }

        /*if (id == R.id.action_search) {
            SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

            SearchView searchView = null;
            if (item != null) {
                searchView = (SearchView) item.getActionView();
            }
            if (searchView != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            }
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void shareFile(){
        try{
            File exportDir= Util.getPhotoDirectory();
            File file = new File(exportDir, "PersonCSV.csv");

            if(file.exists()){
                Uri u1  =   null;
                u1  =   Uri.fromFile(file);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Person Detail CSV");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, Exported person details from Balprakash android app. Thanx for using Balprakash..");
                sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                sendIntent.setType("text/html");
                startActivity(sendIntent);

            }else{

                final Toast toast = Toast.makeText(MainActivity.this, "File not found for attachment.", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 3000);


            }

        }catch(Exception e){
            e.printStackTrace();

        }

    }


    private void resetSystem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                resetsystem();
                                dialog.dismiss();
                                startActivity(new Intent(MainActivity.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                                overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
                            }
                        });

        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setMessage("Are you sure want to logout?");
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }

    private void resetsystem(){
        PreferencesHelper.setLoginCheck(false);

    }

    private void showdialog(){

        new FileChooser(MainActivity.this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                // do something with the file
                if(file.getAbsolutePath().endsWith(".csv") || file.getAbsolutePath().endsWith(".CSV")){
                    //Toast.makeText(MainActivity.this, "Selected File Path: "+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    ImportCSVtoDBTask saveTask=new ImportCSVtoDBTask(file.getAbsolutePath());
                    saveTask.execute();
                }else{

                    Toast.makeText(MainActivity.this, "You must select file  with .csv extention", Toast.LENGTH_SHORT).show();
                }

              }
        }).showDialog();

        }

   /* private void importCSVtoDB(){
        File exportDir= Util.getPhotoDirectory();

        File file = new File(exportDir, "PersonCSV.csv");
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String [] nextLine;
            try {
                while ((nextLine = reader.readNext()) != null) {

                    // nextLine[] is an array of values from the line

                    String FirstName=nextLine[0];
                    String MiddleName=nextLine[1];
                    String LastName=nextLine[2];
                    String MobileNo=nextLine[3];
                    String Email=nextLine[4];
                    String Address=nextLine[5];

                    if(FirstName.equalsIgnoreCase("FirstName"))
                    {

                    }
                    else
                    {

                        if(cAapplication.getInstance().getDBAdapter().insertContact(FirstName,
                                MiddleName,
                                LastName,
                                Email,
                                MobileNo,
                                Address))
                        {
                            Toast.makeText(getApplicationContext(), "Data inerted into table", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/


    class ImportCSVtoDBTask extends AsyncTask<Void, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        private String filePath;

        public ImportCSVtoDBTask(String filePath) {
            this.filePath = filePath;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Importing CSV...");
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... args) {
            try {
                File file = new File(filePath);
                CSVReader reader = new CSVReader(new FileReader(file));
                String[] nextLine;

                    while ((nextLine = reader.readNext()) != null) {

                        // nextLine[] is an array of values from the line

                        String FirstName = nextLine[0];
                        String MiddleName = nextLine[1];
                        String LastName = nextLine[2];
                        String MobileNo = nextLine[3];
                        String Address = nextLine[4];
                        String village=nextLine[5];
                        String taluko=nextLine[6];
                        String district=nextLine[7];
                        String pincode=nextLine[8];
                        String slipNo=nextLine[9];
                        String cusId=nextLine[10];
                        String category=nextLine[11];
                        String year=nextLine[12];
                        String price=nextLine[13];
                        String Aname=nextLine[14];
                        String currntTime=nextLine[15];

                        if (FirstName.equalsIgnoreCase("FirstName")) {

                        } else {

                            if (cAapplication.getInstance().getDBAdapter().insertContact(FirstName,
                                    MiddleName,
                                    LastName,
                                    MobileNo,
                                    Address,
                                    village,
                                    taluko,
                                    district,
                                    pincode,
                                    slipNo,
                                    cusId,
                                    category,
                                    year,
                                    price,
                                    Aname,
                                    currntTime)) {
                            }
                        }

                    }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (this.dialog.isShowing()){
                this.dialog.dismiss();
            }
            if (success){

                final Toast toast = Toast.makeText(MainActivity.this, "Data inserted into table", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 3000);


                //Toast.makeText(getApplicationContext(), "Data inserted into table", Toast.LENGTH_LONG).show();
            }
            else {

                final Toast toast = Toast.makeText(MainActivity.this, "Import failed!", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 3000);

                //Toast.makeText(MainActivity.this, "Import failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}