package com.example.marko.zagreen;


import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;


public class MainActivity extends Activity implements OnMarkerClickListener,
        ButtonStateFragmentMapButtons {

    Location location;
    private static final LatLng ZAGREB = new LatLng(45.8144400, 15.9779800);
    private LatLng mojaLokacija;
    private GoogleMap map;
    Marker markerZG;
    SquareToggleButton prikaziLokaciju;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMap();
        addMyMarker(ZAGREB, markerZG); //tu je bilo stvaranje markera
        map.setOnMarkerClickListener(this); // sluzi za osluskivanje klika na marker



        /*prikaziLokaciju = (SquareButton) findViewById(R.id.pokazi_lokaciju);

        prikaziLokaciju.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    mojaLokacija = new LatLng(latitude, longitude);



                    centerCameraOnLocation(mojaLokacija);

                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude +
                            "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });*/




      if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.menu_container, new MenuFragment()).commit();
        }

    } //onCreate



    @Override
    public void onResume() {
        super.onResume();
        map.setMyLocationEnabled(true); //  Google blue radius dot enabled
        map.getUiSettings().setMyLocationButtonEnabled(false); // Google location button disable

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



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Metoda centrira pogled na mapi sukladno lokaciji
     *
     * @param lokacija Prima lokaciju
     */
    public void centerCameraOnLocation(final LatLng lokacija) {
        if(lokacija != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .zoom(15)
                    .target(mojaLokacija)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    private void addMyMarker(LatLng mjesto, Marker marker) {

        marker = map.addMarker(new MarkerOptions()
                .position(mjesto)
                .title("Natpis")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    /**
     * Metoda koja prilikom pritiska na marker pokazuje natpis iznad markera i izbacuje poruku
     * na ekranu.
     *
     * @param marker ulazni parametar je marker
     * @return True - želim hendlat event.
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow(); //pokazuje natpis iznad markera
        Toast.makeText(getApplicationContext(), "Kliknuo si na marker: "
                + marker.getTitle(), Toast.LENGTH_LONG).show(); //ispisuje poruku na ekranu
        return true; // hendlam event i ne pojavljuju se google smece ikone (koje mogu biti korisne u nekoj primjeni)
    }


    @Override
    public void getLocationButtonState(boolean state) {
        if(state){

            gps = new GPSTracker(MainActivity.this);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                mojaLokacija = new LatLng(latitude, longitude);



                centerCameraOnLocation(mojaLokacija);

                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude +
                        "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }


        }
    }
}//kraj