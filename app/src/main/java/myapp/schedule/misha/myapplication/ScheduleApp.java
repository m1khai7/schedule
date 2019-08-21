package myapp.schedule.misha.myapplication;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

//Todo Application и final/static
public class ScheduleApp extends Application {

    private static Context appContext;

    public static void showToast(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@StringRes int message) {
        Toast.makeText(appContext, getStr(message), Toast.LENGTH_SHORT).show();
    }

    public static Drawable getDrwbl(@DrawableRes int drawRes) {
        return ContextCompat.getDrawable(appContext, drawRes);
    }


    public static String getStr(int strId, Object... param) {
        return appContext.getString(strId, param);
    }

    public static int getClr(@ColorRes int colorRes) {
        return ContextCompat.getColor(appContext, colorRes);
    }

    public static int getDimen(@DimenRes int dimenRes) {
        return appContext.getResources().getDimensionPixelSize(dimenRes);
    }

    public static String getStr(@StringRes int stringId) {
        return appContext.getResources().getString(stringId);
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static boolean isConnectionAvailable() {
        ConnectivityManager connectManager = (ConnectivityManager) appContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        Network[] networks = connectManager.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connectManager.getNetworkInfo(mNetwork);
            if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        appContext = getApplicationContext();
    }

}

