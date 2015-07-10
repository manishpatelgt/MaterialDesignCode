package com.addcontact.Util;

/**
 * Created by Manish on 12/06/2015.
 */
public class Validation {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static final boolean isValidPinCode(CharSequence target) {
        if (target.length()!=6) {
            return false;
        } else {
            return true;
            //return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static final boolean isValidSlipNo(CharSequence target) {
        if (target.length()!=7) {
            return false;
        } else {
            return true;
            //return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static final boolean isValidCustomerNo(CharSequence target) {
        if (target.length()!=6) {
            return false;
        } else {
            return true;
            //return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static final boolean isValidPrice(CharSequence target) {
        if (target.length()!=3) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }
}
