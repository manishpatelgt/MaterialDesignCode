package com.addcontact.Util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

public class IntentHelper {

    /**
     * Verifies that the specified action can be used as an intent by querying Package
     * Manager for installed packages that can respond to an intent with the specified action.
     *
     * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
     *
     * @param context Application's environment
     * @param action  Intent to check for availability
     * @return False, if no suitable package is found
     */
    public static boolean isIntentAvaliable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> info = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return info.size() > 0;
    }

}
