package br.com.pechinchadehoje.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.util.List;

import br.com.pechinchadehoje.SettingsApplication;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class DispositivoUtil {

    public static String getIdDispositivo(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getMac(Activity activity){
        SettingsApplication application = (SettingsApplication) activity.getApplication();

        if(application.getMac()!=null)
            return application.getMac();

        WifiManager wimanager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wimanager.getConnectionInfo().getMacAddress();

        application.setMac(macAddress);

        return macAddress;
    }

    public static boolean isMyApplicationTaskOnTop(Context context) {
        String packageName = context.getPackageName();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if(recentTasks != null && recentTasks.size() > 0) {
            ActivityManager.RunningTaskInfo t = recentTasks.get(0);
            String pack = t.baseActivity.getPackageName();
            if(pack.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    public static boolean verifyConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
