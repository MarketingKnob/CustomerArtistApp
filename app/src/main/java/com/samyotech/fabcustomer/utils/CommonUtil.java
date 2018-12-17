package com.samyotech.fabcustomer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * Developer: Alisha
 */

public final class CommonUtil {

    /**
     * Prevent instantiation
     */

    private CommonUtil() {
    }

    /**
     * Method to check if internet is connected
     *
     * @param context context of calling class
     * @return true if connected to any network else return false
     */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) (context.getApplicationContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Method to show toast
     *
     * @param message  that display in the Toast
     * @param mContext context of calling activity or fragment
     */
    public static void showToast(final Context mContext, final String message) {
        if (mContext == null
                || message == null
                || message.isEmpty()) {
            return;
        }
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * @param context of calling activity or fragment
     * @return pixel scale factor
     */
    private static float getPixelScaleFactor(final Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT;
    }


    /**
     * Method to convert dp into pixel
     *
     * @param context of calling activity or fragment
     * @param dp      dp value that need to convert into px
     * @return px converted dp into px
     */
    public static int dpToPx(final Context context, final int dp) {
        return Math.round(dp * getPixelScaleFactor(context));
    }

    /**
     * Method to convert pixel into dp
     *
     * @param context of calling activity or fragment
     * @param px      pixel value that need to convert into dp
     * @return dp converted px into dp
     */
    public static int pxToDp(final Context context, final int px) {
        return Math.round(px / getPixelScaleFactor(context));
    }

    /**
     * Get the app version code
     *
     * @param context calling context
     * @return the app version code
     */
    public static int getAppVersionCode(final Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * Get base 64 encoded string
     *
     * @param file the file
     * @return base64 encoded string
     */
    public static String getBase64EncodedString(final File file) {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        byte[] bytes;
        byte[] buffer = new byte[(int) file.length()];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * For Email Validation
     */

    public static boolean isValidEmail(String email) {
//        For Mandatory Email Validation
//        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//        For optional Email Validation
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * For Phone Validation
     */
    public static boolean isValidnumber(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
                 if(phone.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }

    /**
     * For Hide Soft Keyboard
     */

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
