package com.addcontact.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.addcontact.application.Consts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manish on 2/28/2015.
 */
public class Util {

    public static String DB_NAME="DB.db";
    public static String DB_PATH="/data/data/com.addcontact/databases/";
    public static final String DATE_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_yyyy_MM_dd= "yyyy-MM-dd";
    public static final String DATE_dd_MM_yyyy = "dd-MM-yyyy";
    public static final String DATE_dd_MM_yyyy_hh_mm_ss = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_yyyy_MM_dd_hh_mm_ss2 = "yyyy-MM-ddhh:mm:ss";
    public static final String DATE_YYYY_MM_DD = "yyyy-MlM-dd"; //1970-01-01
    public static final String DATE_yyyy_MM_dd_HH_mm_ss  = "yyyy-MM-dd HH:mm:ss";  // 1970-01-01 00:00
    public static final String DATE_yyyy_MM_dd_HH_mmZ  = "yyyy-MM-dd HH:mmZ";  //  1970-01-01 00:00+0000
    public static final String DATE_yyyy_MM_dd_HH_mm_ss_SSSZ  = "yyyy-MM-dd HH:mm:ss.SSSZ"; // 1970-01-01 00:00:00.000+0000
    public static final String DATE_yyyy_MM_dd_T_HH_mm_ss_SSSZ  = "yyyy-MM-dd'T'HH:mm:ss.SSS"; // 1970-01-01T00:00:00.000+0000

    public static final String TIMESTAMP_FORMAT_LONG_DATE = "yyyyMMddHHmmss";
    private static final SimpleDateFormat longDateFormatter = new SimpleDateFormat(TIMESTAMP_FORMAT_LONG_DATE);

    public static final int INTERVAL_ONE_SECOND = 1000;
    public static final int INTERVAL_TWENTY_SECOND = 10000;
    public static final int INTERVAL_TWENTY_SECOND_NEW = 30*1000;
    public static final int INTERVAL_ONE_MINUTE = INTERVAL_ONE_SECOND * 60;
    public static final int INTERVAL_FIFTEEN_SECONDS = INTERVAL_ONE_SECOND * 15;
    public static final int INTERVAL_THIRTY_SECONDS = INTERVAL_ONE_SECOND * 30;

    // How often to request location updates
    public static final int UPDATE_INTERVAL_IN_SECONDS = 30;
    // A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 10;
    // The rate in milliseconds at which your app prefers to receive location updates
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = INTERVAL_ONE_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = INTERVAL_ONE_SECOND * FAST_CEILING_IN_SECONDS;

    public static String PATH = Consts.APPLICATION_PATH+"/BalParkash/";
    //public static String PATH_NEW =Environment.getExternalStorageDirectory().getAbsolutePath()+"/eKawach/";
    public static String location = PATH + "images/";


    public static final String TIMESTAMP_SHORT_DATE = "dd-MM-yyyy";
    private static final SimpleDateFormat shortDateFormatter = new SimpleDateFormat(TIMESTAMP_SHORT_DATE);

    public static String getTodayShortDateString() {
        return shortDateFormatter.format(new Date());
    }

    public static String getDateFormatted(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getDateFormatted2(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getNow() {
        return longDateFormatter.format(new Date());
    }

    public static String getYesterdayDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_yyyy_MM_dd);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    public static String last_monthDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_dd_MM_yyyy_hh_mm_ss);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        return dateFormat.format(cal.getTime());
    }

    public static String getDateFormatted3(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_dd_MM_yyyy_hh_mm_ss);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getStorageCardFolderBackup() {
        File dir = createOrOpenFolder(Environment.getExternalStorageDirectory().getAbsolutePath()+"/BalParkash/"+"Backup");
        return dir.getAbsolutePath();
    }

    public static String getStorageCardFolderPhotos() {
        File dir = createOrOpenFolder(Environment.getExternalStorageDirectory().getAbsolutePath()+"/BalParkash/"+"Photos");
        return dir.getAbsolutePath();
    }

    public static File getPhotoDirectory() {
        return createOrOpenFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BalParkash/");
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static File createOrOpenFolder(String dirPath) {
        File dir = new File(dirPath);

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }

        return dir;
    }

    public static File createOrOpenFolder(File baseDir, String dirPath) {
        return createOrOpenFolder(baseDir.getAbsolutePath(), dirPath);
    }

    public static File createOrOpenFolder(String baseDirPath, String dirPath) {
        return createOrOpenFolder(baseDirPath + "/" + dirPath);
    }

    public static File createFile(String dirPath, String fileName) throws IOException {
        File file = new File(dirPath, fileName);

        if (file.exists())
            deleteFile(dirPath, fileName);

        File dir = new File(dirPath);
        dir.mkdirs();
        file.createNewFile();

        return file;
    }

    public static boolean deleteFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        return file.delete();
    }

    public static void copyFile(File fromFile, File toFile) throws IOException {

        FileChannel fromChannel = null;
        FileChannel toChannel = null;

        try {
            fromChannel = new FileInputStream(fromFile).getChannel();
            toChannel = new FileOutputStream(toFile).getChannel();

            fromChannel.transferTo(0, fromChannel.size(), toChannel);

        } finally {

            if (fromChannel != null) {
                fromChannel.close();
            }

            if (toChannel != null) {
                toChannel.close();
            }
        }
    }

    public static String getDateFormatted(long miliseconds){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(miliseconds);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_HH_mm_ss, new Date(miliseconds)));
    }

    private static final SimpleDateFormat timeAndDateFormatter = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
    public static String getString(Timestamp timestamp) {
        return timeAndDateFormatter.format(timestamp.getTime());
    }


    public static Timestamp getCurrentTimestamp() {
        Date now = Calendar.getInstance().getTime();
        return new Timestamp(now.getTime());
    }

	   public static String getHumanReadableDifference(long startTime, long endTime) {
        long timeDiff = endTime - startTime;

        return String.format("%02d:%02d:%02d",
                TimeUnit.MICROSECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
                TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff)));
    }


    static public void setActivityAnimation(Activity activity, int in, int out) {
        try {

            Method method = Activity.class.getMethod(
                    "overridePendingTransition", new Class[] { int.class,
                            int.class });
            method.invoke(activity, in, out);

        } catch (Exception e) {
            // Can't change animation, so do nothing
        }
    }

    public static String getApplicationVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Should never happen
            e.printStackTrace();
            throw new RuntimeException("Could not get the app version name: " + e);
        }
    }

    // ---alert for showing phone number list---
    public static void show_alert(final Context mCon,String phon) {

        ArrayList<String> phone = new ArrayList<String>();

        if (!phon.equalsIgnoreCase("")) {
            phone.add(phon);
        }

        final CharSequence[] cs = phone.toArray(new CharSequence[phone.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(mCon);

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.setTitle("Would You Like to Call ");
        builder.setItems(cs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                call(mCon, cs[item].toString());

            }
        }).show();
    }

    public static void call(Context ctx, String number) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            ctx.startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Dailing", "Call failed",
                    activityException);
        }
    }

    public static void storeImage(Bitmap image,String fname) {
        try {
        String PATH_NEW =Environment.getExternalStorageDirectory().getPath()+"/BalPrakash/PersonPhotos";
        File file = new File(PATH_NEW);
        if(!file.exists())
        file.mkdirs();
        //File file = new File(PATH_NEW);
        System.out.println("photo path is: "+file.getPath());
        file.mkdirs();
        File outputFile = new File(file, fname);
         FileOutputStream fOut = new FileOutputStream(outputFile);
        image.compress(Bitmap.CompressFormat.PNG, 90, fOut);
        fOut.flush();
        fOut.close();
        System.out.println("photo saved at is: "+outputFile.getPath()+" size: "+outputFile.length());
        } catch (Exception e) {
        Log.d("eKawach",  e.getMessage());
       }
    }

    public static File createPhotoFile(String fileName, String dirPath) throws IOException {
        File photosDir = createOrOpenFolder(getPhotoDirectory(), dirPath);
        File photoFile = new File(photosDir,fileName);
        return photoFile;
    }

    public static Bitmap ImgBitFromFile(String file_name) {
     try{
     float scale;
     String PATH_NEW =Environment.getExternalStorageDirectory().getPath()+"/BalPrakash/PersonPhotos/";
     String file_path = PATH_NEW+file_name;
     File imgFile = new File(file_path);
     System.out.println("photo file found PATH:::" + imgFile.getPath().toString());
     if (imgFile.exists()) {
         Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
         System.out.println("photo found:::");
         return myBitmap;
     }
     }catch(Exception e){
     e.printStackTrace();
      }
        return null;
    }


    public static void deleteImgFile(String file_path) {
        try{
            System.out.println("photo file delete und PATH:::" + file_path);
            File file=new File(file_path);
            if(file.exists()){
                file.delete();
                System.out.println("photo deleted:::");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
