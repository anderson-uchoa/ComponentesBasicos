package br.com.pechinchadehoje.util.constantes;

import android.Manifest;


public class Permissoes {
    public static final String[] permissoes = new String[]{
            //Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    public static final int REQUEST_CODE = 100;
}
