package com.addcontact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.renderscript.Matrix4f;
import android.util.Log;

import com.addcontact.Util.Util;
import com.addcontact.application.Consts;
import com.addcontact.application.cAapplication;
import com.addcontact.models.DataObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manish on 03/31/2015.
 */
public class DBAdapter {

    private Context context;
    private SQLiteDatabase myDataBase;

    public DBAdapter(Context mcontext)throws IOException {
                this.context=mcontext;
                DataBaseHelper dbhelper=new DataBaseHelper(mcontext);
                myDataBase=dbhelper.getWritableDatabase();
                Log.d("Contact", "DBAdapter called");
    }


    public ArrayList<DataObject> getAllContact() throws SQLException {

        ArrayList<DataObject> _list=new ArrayList<DataObject>();
        Cursor c=null;
        try{

            c = myDataBase.rawQuery("SELECT Id,FirstName,MiddleName,LastName,MobileNo,Address,Village,Taluko,District,Pincode,SlipNo,CustomerId,Category,InstallmentYear,InstallmentPrice,AuthorisePerson,InsertDateTime FROM tbl_contact ORDER BY Id DESC", null);

            if (c != null && c.moveToFirst()) {

                do {
                    int Id=getInt(c, "Id", 0);
                    String FirstName = getString(c, "FirstName", "");
                    String MiddleName = getString(c, "MiddleName", "");
                    String LastName = getString(c, "LastName", "");
                    String MobileNo = getString(c, "MobileNo", "");
                    String Address = getString(c, "Address", "");
                    String Village = getString(c, "Village", "");
                    String Taluko = getString(c, "Taluko", "");
                    String District = getString(c, "District", "");
                    String Pincode = getString(c, "Pincode", "");
                    String SlipNo = getString(c, "SlipNo", "");
                    String CustomerId = getString(c, "CustomerId", "");
                    String Category = getString(c, "Category", "");
                    String InstallmentYear = getString(c, "InstallmentYear", "");
                    String InstallmentPrice = getString(c, "InstallmentPrice", "");
                    String AuthorisePerson = getString(c, "AuthorisePerson", "");
                    String InsertDateTime=getString(c,"InsertDateTime","");

                    _list.add(new DataObject(Id,FirstName, MiddleName, LastName, MobileNo, Address,Village,Taluko,District,Pincode,SlipNo,CustomerId,Category,InstallmentYear,InstallmentPrice,AuthorisePerson,InsertDateTime));
                } while (c.moveToNext());
            }

        }catch(Exception e){
            e.printStackTrace();
            c.close();

        }finally{
            c.close();
        }
        return _list;
    }



    public ArrayList<DataObject> getAllContact(String searchText) throws SQLException {

        ArrayList<DataObject> _list=new ArrayList<DataObject>();
        Cursor c=null;
        try{

            c = myDataBase.rawQuery("SELECT Id,FirstName,MiddleName,LastName,MobileNo,Address,Village,Taluko,District,Pincode,SlipNo,CustomerId,Category,InstallmentYear,InstallmentPrice,AuthorisePerson,InsertDateTime FROM tbl_contact WHERE (FirstName LIKE '%"+searchText+"%' OR  MiddleName LIKE '%"+searchText+"%' OR LastName LIKE '%"+ searchText+"%' OR SlipNo LIKE '%"+searchText+"%') ORDER BY Id DESC ", null);

            if (c != null && c.moveToFirst()) {

                do {
                    int Id=getInt(c, "Id", 0);
                    String FirstName = getString(c, "FirstName", "");
                    String MiddleName = getString(c, "MiddleName", "");
                    String LastName = getString(c, "LastName", "");
                    String MobileNo = getString(c, "MobileNo", "");
                    String Address = getString(c, "Address", "");
                    String Village = getString(c, "Village", "");
                    String Taluko = getString(c, "Taluko", "");
                    String District = getString(c, "District", "");
                    String Pincode = getString(c, "Pincode", "");
                    String SlipNo = getString(c, "SlipNo", "");
                    String CustomerId = getString(c, "CustomerId", "");
                    String Category = getString(c, "Category", "");
                    String InstallmentYear = getString(c, "InstallmentYear", "");
                    String InstallmentPrice = getString(c, "InstallmentPrice", "");
                    String AuthorisePerson = getString(c, "AuthorisePerson", "");
                    String InsertDateTime=getString(c,"InsertDateTime","");

                    _list.add(new DataObject(Id,FirstName, MiddleName, LastName, MobileNo, Address,Village,Taluko,District,Pincode,SlipNo,CustomerId,Category,InstallmentYear,InstallmentPrice,AuthorisePerson,InsertDateTime));

                } while (c.moveToNext());
            }

        }catch(Exception e){
            e.printStackTrace();
            c.close();

        }finally{
            c.close();
        }
        return _list;
    }


    public List<String> getPersonDetail(int PersonId) throws SQLException {

        List<String> _list=new ArrayList<String>();
        Cursor c=null;
        try{

            c = myDataBase.rawQuery("SELECT FirstName,MiddleName,LastName,MobileNo,Address,Village,Taluko,District,Pincode,SlipNo,CustomerId,Category,InstallmentYear,InstallmentPrice,AuthorisePerson,InsertDateTime FROM tbl_contact WHERE Id="+PersonId, null);

            if (c != null && c.moveToFirst()) {

                String FirstName = getString(c, "FirstName", "");
                String MiddleName = getString(c, "MiddleName", "");
                String LastName = getString(c, "LastName", "");
                String MobileNo = getString(c, "MobileNo", "");
                String Address = getString(c, "Address", "");
                String Village = getString(c, "Village", "");
                String Taluko = getString(c, "Taluko", "");
                String District = getString(c, "District", "");
                String Pincode = getString(c, "Pincode", "");
                String SlipNo = getString(c, "SlipNo", "");
                String CustomerId = getString(c, "CustomerId", "");
                String Category = getString(c, "Category", "");
                String InstallmentYear = getString(c, "InstallmentYear", "");
                String InstallmentPrice = getString(c, "InstallmentPrice", "");
                String AuthorisePerson = getString(c, "AuthorisePerson", "");
                String DateTime = getString(c, "InsertDateTime", "");

                _list.add(FirstName);
                _list.add(MiddleName);
                _list.add(LastName);
                _list.add(MobileNo);
                _list.add(Address);
                _list.add(Village);
                _list.add(Taluko);
                _list.add(District);
                _list.add(Pincode);
                _list.add(DateTime);
                _list.add(SlipNo);
                _list.add(CustomerId);
                _list.add(Category);
                _list.add(InstallmentYear);
                _list.add(InstallmentPrice);
                _list.add(AuthorisePerson);
            }

        }catch(Exception e){
            e.printStackTrace();
            c.close();

        }finally{
            c.close();
        }
        return _list;
    }

    public int CheckDataCount(){
        int taskCount=0;

        Cursor c=null;
        try{


            c = myDataBase.rawQuery("SELECT Id from tbl_contact",null);

            if (c != null && c.moveToFirst()) {
                taskCount=c.getCount();
            }
            System.out.println("getCount: "+taskCount);

        }catch(Exception e){
            e.printStackTrace();
            c.close();

        }finally{
            c.close();
        }

        return taskCount;
    }

    public boolean insertContact(String FirstName,
                               String MiddleName,
                               String LastName,
                               String Mobile,
                               String Address,String Village,String Taluko,String District,String Pincode,
                                 String SlipNo,String CustomerId,String Category,String InYear,String InPrice,
                                 String AthoPerson,String dateTime){

        ContentValues values = new ContentValues();
        values.put("FirstName", FirstName);
        values.put("MiddleName", MiddleName);
        values.put("LastName", LastName);
        values.put("MobileNo", Mobile);
        values.put("Address", Address);
        values.put("Village", Village);
        values.put("Taluko", Taluko);
        values.put("District", District);
        values.put("Pincode", Pincode);
        values.put("SlipNo", SlipNo);
        values.put("CustomerId", CustomerId);
        values.put("Category", Category);
        values.put("InstallmentYear", InYear);
        values.put("InstallmentPrice", InPrice);
        values.put("AuthorisePerson", AthoPerson);
        values.put("InsertDateTime", dateTime);
        System.out.println("records inserted in tbl_contact");
        boolean b=myDataBase.insert("tbl_contact", null, values)>0;
        return b;
    }

    public void DeleteTaskLocalRecord(String[] TaskIds,int UserId) {
        if (TaskIds.length > 0 ) {
            // to handle the ' character directly into SQL, you will probably
            // need to name.replaceAll("'", "\'");

            StringBuilder nameBuilder = new StringBuilder();

            for (String n : TaskIds) {
                nameBuilder.append("'").append(n).append("',");
            }

            nameBuilder.deleteCharAt(nameBuilder.length() - 1);

            //Delete all the Eng related to Tasks
            //for(int i=0;i<TaskIds.length;i++){
                String query="DELETE from tbl_SupportCallEngineers WHERE TransId NOT IN(" + nameBuilder.toString() + ")"+" AND UserId="+UserId;
                Cursor c=myDataBase.rawQuery(query, null);
                if(c!=null && c.moveToFirst()){
                    System.out.println("Deleted SupportEng: "+c.getCount());
                }
            //}

            //Delete all the Complains related to Tasks
            //for(int i=0;i<TaskIds.length;i++){
                String query2="DELETE from tbl_SupportCallComplains WHERE TransId NOT IN(" + nameBuilder.toString() + ")"+" AND UserId="+UserId;
                Cursor c2=myDataBase.rawQuery(query2, null);
                if(c2!=null && c2.moveToFirst()){
                    System.out.println("Deleted tbl_SupportCallComplains: "+c2.getCount());
                }
            //}

            //Delete all the Photos related to Tasks
            String query4="DELETE from tbl_SupportCallPhotos WHERE TransId NOT IN(" + nameBuilder.toString() + ")"+" AND UserId="+UserId;
            Cursor c4=myDataBase.rawQuery(query4, null);
            if(c4!=null && c4.moveToFirst()){
                System.out.println("Deleted SupportPhotos: "+c4.getCount());
            }

            //Delete all the Tasks
            String query3="DELETE from tbl_SupportCallList WHERE TransId NOT IN(" + nameBuilder.toString() + ")"+" AND UserId="+UserId;
            System.out.println("Delete query: "+query3);
            Cursor c3=myDataBase.rawQuery(query3, null);
            if(c3!=null && c3.moveToFirst()){
                System.out.println("Deleted Task: "+c3.getCount());
            }
        }
    }

    public Boolean updatePersonDetails(int Id,String FirstName,String MiddleName,String LastName,String Mobile,
                                     String Address,String Village,String Taluko,String District,String Pincode) {

        ContentValues values = new ContentValues();
        values.put("FirstName", FirstName);
        values.put("MiddleName", MiddleName);
        values.put("LastName", LastName);
        values.put("MobileNo", Mobile);
        values.put("Address", Address);
        values.put("Village", Village);
        values.put("Taluko", Taluko);
        values.put("District", District);
        values.put("Pincode", Pincode);
        System.out.println("Updation of tbl_contact table");

        return myDataBase.update("tbl_contact", values, "Id" + "='" + Id+"'", null) > 0;
    }


    public void removeContact(int Id){
        System.out.println("contact removed");
        myDataBase.execSQL("DELETE FROM tbl_contact WHERE Id=" + Id);
    }


    public boolean deletetbl_contact() {

        return myDataBase.delete("tbl_contact", null, null) > 0;
    }

    public static String getString(Cursor cursor, String fieldName, String nullValue) {
        int column = cursor.getColumnIndex(fieldName);

        if (cursor.isNull(column))
            return nullValue;
        else
            return cursor.getString(column);
    }

    public static int getInt(Cursor cursor, String fieldName, int nullValue) {
        int column = cursor.getColumnIndex(fieldName);

        if (cursor.isNull(column))
            return nullValue;
        else
            return cursor.getInt(column);
    }

    public SQLiteDatabase getDB() {
        return myDataBase;
    }

    public boolean exportDatabaseToExternalStorage(String fileName) throws IOException {

        File fromDatabase = new File(Consts.APPLICATION_DATABASES_PATH+"contact");
        if (!fromDatabase.exists())
            return false;

        File destinationFile = Util.createFile(Util.getStorageCardFolderBackup(), fileName);
        Util.copyFile(fromDatabase, destinationFile);
        return true;
    }

    public static void DumpMYDb(String fname){
        //Dump Database to SDCard
        String PATH_NEW = Environment.getExternalStorageDirectory().getPath()+"/contact/";
        File file = new File(PATH_NEW);
        if(!file.exists())
        file.mkdirs();

        File f=new File(Consts.APPLICATION_DATABASES_PATH+"contact",fname);
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try
        {
            fis=new FileInputStream(f);
            fos=new FileOutputStream(file);
            while(true)
            {
                int i=fis.read();
                if(i!=-1)
                {fos.write(i);}
                else
                {break;}
            }
            fos.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fos.close();
                fis.close();
            }
            catch(Exception ioe)
            {
                ioe.printStackTrace();;
            }
        }
    }
}
