package com.example.marko.zagreen;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.LinearLayout;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;


public class MainActivity extends Activity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            initMap();
            getFragmentManager().beginTransaction()
                    .add(R.id.menu_container, new MenuFragment()).commit();
        }

    } //onCreate


    @Override
    protected void onResume() {
        super.onResume();



    }

    /**
     * Metoda koja postavlja mapu.
     */
    public void initMap() {

        try {
            if (map == null) {
                map = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.mapView)).getMap();
            }

            //tu je bilo stvaranje markera

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}//kraj