package com.addcontact.data;

import android.content.SharedPreferences;

import com.addcontact.application.cAapplication;


/**
 * Created by Manish on 3/1/2015.
 */
public class PreferencesHelper {

    private static final String IS_LOGIN_PREFS = "Is_Login";


    public static Boolean getBoolean(String key, Boolean defValue) {
        return cAapplication.getPreferences().getBoolean(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = cAapplication.getPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(String key, String defValue) {
        return cAapplication.getPreferences().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = cAapplication.getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defValue) {
        return cAapplication.getPreferences().getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = cAapplication.getPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getLoginCheck() {
        return getBoolean(IS_LOGIN_PREFS, false);
    }

    public static void setLoginCheck(boolean tested) {
        putBoolean(IS_LOGIN_PREFS, tested);
    }
}
