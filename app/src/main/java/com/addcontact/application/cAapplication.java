package com.addcontact.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.addcontact.BuildConfig;
import com.addcontact.database.DBAdapter;
import com.addcontact.models.DataObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Manish on 2/28/2015.
 */
public class cAapplication extends Application {

    private static final String TAG = "Contact";
    private static cAapplication applicationInstance;
    private static final Object lockObject = new Object();
    private static Context applicationContext = null;
    private DBAdapter dba;
    private static DataObject dataObject=null;
    private static  String personPhotoPath=null;

    public static void setDataObject(DataObject dObject){
        cAapplication.dataObject=dObject;
    }

    public static DataObject getDataObject(){
        return dataObject;
    }

    public static void setpersonPhotoPath(String personPhotoPath){
        cAapplication.personPhotoPath=personPhotoPath;
    }

    public static String getpersonPhotoPath(){
        return personPhotoPath;
    }


    @Override
    public void onCreate() {
        super.onCreate();

       // Initialize the Application singleton
        applicationInstance = this;
        applicationContext = getApplicationContext();
        preferences = applicationContext.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);

        try {
            dba = new DBAdapter(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (BuildConfig.DEBUG)
            Log.d(TAG, "onTerminate");
            //LocationLoggingService.StopLogging();
        if (dba != null)
            dba.getDB().close();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public DBAdapter getDBAdapter() {
        return dba;
    }

    public static synchronized cAapplication getInstance() {
        return applicationInstance;
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static void setContext(Context newContext) {
        applicationContext = newContext;
    }

    private static SharedPreferences preferences;
    private static SharedPreferences worldPreferences;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static SharedPreferences getPublicPreferences() {
        return worldPreferences;
    }
}
