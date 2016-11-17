package br.com.pechinchadehoje.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class LocationUtil implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = "LocationUtil";
    private Context context;
    private CallbackLocation callbackLocation;

    public interface CallbackLocation{

        void onLocationAvaiable(Location location);

    }

    public LocationUtil(Context context, CallbackLocation callbackLocation) {
        this.context = context;
        this.callbackLocation = callbackLocation;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    public void disconnect(){
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(callbackLocation != null){
                callbackLocation.onLocationAvaiable(location);
            }
            Log.i(TAG, (location != null)? location.toString() : "vazio");

        }

        Log.i(TAG, "Conectado Google Play Services");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Conecção Suspensa");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Conecção falhou");
    }

    public Location getLastLocation() {
        if (mGoogleApiClient != null) {

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return null;
            }

            Location location =  LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            Log.i(TAG, (location != null)? location.toString() : "null");

            return  location;
        }

        Log.i(TAG, "not ready yet");
        return null;
    }

}
