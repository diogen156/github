package com.example.marko.zagreen;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

/**
 * Klasa koja sadrži metode za dohvat korisnikove lokacije
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    // zastavica za GPS status
    boolean isGPSEnabled = false;

    // zastavica za network status
    boolean isNetworkEnabled = false;

    // zastavice za lokaciju
    boolean canGetLocation = false;

    Location location; // lokacija
    double latitude; // latitude
    double longitude; // longitude

    // minimalna udaljenost pri kojoj će raditi update
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 metara

    // minimalno vrijeme izmedju dva updatea
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minuta

    //  deklaracija Location Managera
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * Vraća kordinate trenutne lokacije korisnika
     *
     * @return
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // dohvaćanje GPS statusa
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // dohvaćanje network statusa
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // ukoliko nije dostupna lokacija od providera
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider //
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // ukoliko se koristi GPS
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Prestanak korištenja GPS listenera
     * Pozivanje ove metode prestat ce koristiti GPS u korisnikovoj aplikaciji
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Metoda za dohvaćanje geografske širine
     *
     * @return latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    /**
     * Metoda za dohvaćanje geografske dužine
     *
     * @return longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    /**
     * Metoda za provjeravanje GPS/wifi uključenosti
     *
     * @return true ako je dostupna lokacija
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Metoda pokazuje settings upozoravajući prozor,
     * na pritisak Settings buttona prebacit će korisnika na Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Naslov dialog prozora
        alertDialog.setTitle("Postavke lokacije");


        alertDialog.setMessage("Želite li uključiti pristup Vašoj lokaciji u postavkama?" +
                " (Potrebno zbog svih funkcionalnosti aplikacije)");

        // pritisak na button Postavke
        alertDialog.setPositiveButton("Postavke", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // pritisak na button Ne želim
        alertDialog.setNegativeButton("Ne želim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // pokazivanje alert dialoga
        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}


