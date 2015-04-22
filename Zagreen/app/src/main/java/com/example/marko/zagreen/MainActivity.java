package com.example.marko.zagreen;


import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;
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


public class MainActivity extends Activity implements OnMarkerClickListener {

    Location location;
    private static final LatLng ZAGREB = new LatLng(45.8144400, 15.9779800);
    private LatLng mojaLokacija;
    private GoogleMap map;
    Marker marker;
    private static final int[] idArray = {R.id.filtrationButton,
            R.id.informationButton,
            R.id.gameficationButton,
            R.id.settingsButton};

    private ImageButton[] buttonArray = new ImageButton[4];

    GPSTracker gps;
    Button prikaziLokaciju;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap(); // dodaje mapu na kartu
        addMyMarker(ZAGREB, marker);
        map.setOnMarkerClickListener(this);

        for (int i = 0; i < idArray.length; i++) {
            buttonArray[i] = (ImageButton) findViewById(idArray[i]);
        }

        setSquareButtons(buttonArray[0]);


        //testni gumb, prikazuje i povlaci kameru na lokaciju
        prikaziLokaciju = (Button) findViewById(R.id.pokazi_lokaciju);

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
        });



    } //onCreate


    @Override
    public void onResume() {
        super.onResume();
        map.setMyLocationEnabled(true); // blue Google dot enabled

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





    /**
     *Metoda pretvara 4 buttona rasirena horizontalno po ekranu u oblik kvadrata neovisno o
     *velicini ekrana.
     *
     * @param firstButton Prima i mjeri sirinu prvog gumba i na osnovu njega postavlja na istu duzinu
     *                    visine svih ostalih gumbova.
     */
    public void setSquareButtons (final ImageButton firstButton ){
        firstButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                // Ensure you call it only once :
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    firstButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else {
                    firstButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                // Here you can get the size :)
                int size = firstButton.getMeasuredWidth();
                for ( int i=0; i<idArray.length; i++) {


                    buttonArray [i].setLayoutParams(new LinearLayout.LayoutParams(size,size));
                }


            }
        });

    }



}//kraj