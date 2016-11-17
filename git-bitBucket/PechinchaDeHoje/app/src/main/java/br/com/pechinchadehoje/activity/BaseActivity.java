package br.com.pechinchadehoje.activity;


import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import br.com.pechinchadehoje.R;
import br.com.pechinchadehoje.SettingsApplication;


public class BaseActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "pechinchaDeHoje_app";
    private SettingsApplication mSettingsApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingsApplication = (SettingsApplication) getApplication();


    }


    public void setUpToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }
    }

    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }

    public void log(String msg) {
        Log.d(TAG, msg);
    }

    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void alert(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}