package com.addcontact.Util;

import android.os.Environment;

public class StorageHelper {

    public static boolean mediaMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean mediaReadable() {
        String state = Environment.getExternalStorageState();

        // Check if external storage is available to at least the READ
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;

        return false;
    }

    public static boolean mediaReady() {
        String cardState = Environment.getExternalStorageState();

        if (cardState.equals(Environment.MEDIA_REMOVED)
                || cardState.equals(Environment.MEDIA_UNMOUNTABLE)
                || cardState.equals(Environment.MEDIA_UNMOUNTED)
                || cardState.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
            return false;
        else
            return true;
    }

    private StorageHelper() {}
}