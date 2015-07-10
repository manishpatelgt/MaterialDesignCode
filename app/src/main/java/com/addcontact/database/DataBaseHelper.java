package com.addcontact.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.addcontact.Util.Util;

public class DataBaseHelper extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    private static String DB_PATH = Util.DB_PATH+"contact";
    private static String DB_NAME = "contact";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
 // Data Base Version.
    private static final int DATABASE_VERSION = 1;
    /**
      * Constructor
      * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
      * @param context
      */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
            this.myContext = context;
            createDataBase();
    }

    /**
     * This method opens the data base connection.
     * First it create the path up till data base of the device.
     * Then create connection with data base.
     */
    public void open() throws SQLException{      
        //Open the database
          String myPath = DB_PATH;
          if(myDataBase!=null || !myDataBase.isOpen()){
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }
    
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * By calling this method and empty database will be created into the default system path
     * of your application so we are gonna be able to overwrite that database with our database.
     * */
    public void createDataBase() {
        //check if the database exist
        boolean databaseExist=checkDataBase();
       
        if(databaseExist){
            // By calling this method here onUpgrade will be called on a
            // writable database, but only if the version number has been increased

        	 // Do Nothing.
      	 this.getWritableDatabase();
      	//onUpgrade(myDataBase,1,2);
      	//open();
      	//String upgradeQuery = "ALTER TABLE tbl_item ADD COLUMN Testing TEXT";
   	    //myDataBase.execSQL(upgradeQuery);
   	    //System.out.println("Column added");
   	    //close();
      	System.out.println("do something over here");
        }
        
        databaseExist=checkDataBase();
        if(!databaseExist){
        	
        	System.out.println("create brand new DB");
            this.getReadableDatabase(); 
            
            try {
                copyDataBase();
            } catch (IOException e) {

                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }// end if else dbExist
    } // end createDataBase().

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH);
        return databaseFile.exists();        
    }

    /**
      * Copies your database from your local assets-folder to the just created empty database in the
      * system folder, from where it can be accessed and handled.
      * This is done by transfering bytestream.
      * */
    private void copyDataBase() throws IOException{

            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH ;
            File databaseFile = new File(DB_PATH);
             // check if databases folder exists, if not create one and its subfolders
            if (!databaseFile.exists()){
                databaseFile.mkdir();
            }

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
    }
	
    @Override
    public synchronized void close() {
        if(myDataBase != null)
        myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	   System.out.println("onCreate called");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    
    	   System.out.println("onUpgrade called");


    	  /* String upgradeQuery = "ALTER TABLE tbl_item ADD COLUMN Testing2 TEXT";
    	    if (oldVersion == 1 && newVersion == 2)
    	         db.execSQL(upgradeQuery);
    	         System.out.println("Column added");
    	         onCreate(db);*/
    }
}