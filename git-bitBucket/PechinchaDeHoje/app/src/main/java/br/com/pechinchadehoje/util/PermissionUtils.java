package br.com.pechinchadehoje.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Sistemas de permissão do Android 6.0
 * <p/>
 * http://developer.android.com/preview/features/runtime-permissions.html
 */
public class PermissionUtils {

    /**
     * Solicita as permissões
     */
    public static boolean validate(AppCompatActivity activity, int requestCode, String... permissions) {
        List<String> list = new ArrayList<String>();
        for (String permission : permissions) {
            // Valida permissão
            boolean ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if (!ok) {
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            // Tudo ok, retorna true
            return true;
        }

        // Lista de permissões que falta acesso.
        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        // Solicita permissão
        ActivityCompat.requestPermissions(activity, newPermissions, requestCode);

        return false;
    }

    public static boolean hasPermission(Context context, int requestCode, String... permissions) {
        List<String> list = new ArrayList<String>();
        for (String permission : permissions) {
            // Valida permissão
            boolean ok = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            if (!ok) {
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            // Tudo ok, retorna true
            return true;
        }

        return false;
    }

}