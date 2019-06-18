package com.example.misha.myapplication.util;


import android.Manifest;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.misha.myapplication.ScheduleApp;
import com.example.misha.myapplication.common.RationaleDialog;

/**
 * Utility class for access to runtime permissions.
 */
public final class PermissionUtils {
    /**
     * Singleton constructor.
     */
    private PermissionUtils() {
    }

    /**
     * Requests the fine location permission. If a rationale with an additional explanation
     * should be shown to the user, displays a dialog that triggers the request.
     *
     * @param activity   activity
     * @param requestId  request id
     * @param permission permission
     * @param message    message
     */
    public static void requestPermission(AppCompatActivity activity, int requestId,
                                         String permission, String message) {
        if (shouldRequestRationale(activity, permission)) {
            RationaleDialog.newInstance(requestId, permission, message).show(
                    activity.getSupportFragmentManager(),
                    RationaleDialog.class.getSimpleName());
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
        }
    }

    /**
     * Is location granted.
     *
     * @return location granted
     */
    public static boolean isLocationGranted() {
        return ContextCompat.checkSelfPermission(ScheduleApp.getAppContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Is permission granted.
     *
     * @param grantPermissions grant permissions
     * @param grantResults     grant results
     * @param permission       permission
     * @return true if granted
     */
    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                              String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }


    /**
     * Should request rationale dialog.
     *
     * @param activity   activity
     * @param permission permission
     * @return true if should
     */
    public static boolean shouldRequestRationale(AppCompatActivity activity,
                                                 String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * Is granted.
     *
     * @param activity   activity
     * @param permission permission
     * @return true if granted
     */
    public static boolean isGranted(AppCompatActivity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
