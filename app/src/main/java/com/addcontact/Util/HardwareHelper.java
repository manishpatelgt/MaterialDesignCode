package com.addcontact.Util;

/**
 * Created by Manish on 3/1/2015.
 */
 import android.bluetooth.BluetoothAdapter;
 import android.content.Context;
 import android.content.pm.PackageManager;
 import android.net.wifi.WifiManager;
 import android.os.Build;
 import android.provider.Settings;
 import android.telephony.TelephonyManager;
 import android.text.TextUtils;

public class HardwareHelper {

    final private static String TAG = HardwareHelper.class.getSimpleName();

    /**
     * All phones return a value for TelephonyManager.getDeviceId().
     * GSM devices (with a SIM) return a value for TelephonyManager.getSimSerialNumber().
     * All CDMA devices return null for getSimSerialNumber() (as expected).
     * All devices with a configured Google account return a value for ANDROID_ID.
     * All CDMA devices return the same value (or derivation of the same value) for both
     * ANDROID_ID and TelephonyManager.getDeviceId() - IF a Google account has been added.
     */
    public static String getUniqueId(Context context) {

        if (runsInEmulator())
            return "EMULATOR";

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String identifier = "";

        if (manager != null)
            identifier = manager.getDeviceId();

        if (manager == null || TextUtils.isEmpty(identifier))
            identifier = getAndroidId(context);

        return identifier;
    }

    /**
     * Gets the device IMEI number (GSM 02.16 standard and 3GPP2 specification).
     */
    public static String getDeviceIMEI(Context context) {

        String serial = getUniqueId(context);

        if (TextUtils.isEmpty(serial))
            serial = getWiFiMacAddress(context);

        return serial;

    }

    /**
     * Shows an Android ID which is a 64-bit hex number that is randomly generated when the user
     * first sets up the device and should remain constant for the whole lifetime of user's device.
     * Android 4.2.2 and newer versions support multiple user accounts, each one having a unique Android ID.
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Gets a a unique phone serial number
     */
    public static String getSimSerialNumber(Context context) {

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String identifier = null;

        if (manager != null)
            identifier = manager.getSimSerialNumber();

        if (manager == null || identifier.length() == 0) {
            identifier = getAndroidId(context);
        }

        return identifier;
    }

    /**
     * Retrieves the Wi-Fi adapter's MAC address. Used to uniquely identify a
     * mobile phone. On some devices, it's not available when WiFi is turned
     * off.
     */
    public static String getWiFiMacAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        return wm.getConnectionInfo().getMacAddress();
    }

    public static String getPhoneSdkVersion() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("VERSION.RELEASE {" + Build.VERSION.RELEASE + "}");
        buffer.append("\\nVERSION.SDK {" + Build.VERSION.SDK_INT + "}");
        buffer.append("\\nBOARD {" + Build.BOARD + "}");
        buffer.append("\\nBRAND {" + Build.BRAND + "}");
        buffer.append("\\nDEVICE {" + Build.DEVICE + "}");
        buffer.append("\\nFINGERPRINT {" + Build.FINGERPRINT + "}");
        buffer.append("\\nHOST {" + Build.HOST + "}");
        buffer.append("\\nID {" + Build.ID + "}");

        return buffer.toString();
    }

    /**
     * Checks whether the device has camera hardware.
     */
    public static boolean hasCameraFeature(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static boolean hasGpsFeature(Context context) {
        PackageManager manager = context.getPackageManager();
        return manager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    /**
     * Checks that the device supports Bluetooth.
     *
     * @return false when the phone has no Bluetooth adapter (e.g. emulator)
     */
    public static boolean bluetoothHardwareAvailable() {

        if (runsInEmulator())
            return false;

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter != null;
    }

    public static boolean runsInEmulator() {
        return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown");
    }

    /**
     * Build model allows for device-specific workarounds
     */
    public static String getBuildModel() {
        return Build.MODEL.toLowerCase();
    }
}
