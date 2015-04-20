package com.example.marko.zagreen;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;


public class MainActivity extends Activity implements OnMarkerClickListener {

    private static final LatLng ZAGREB = new LatLng(45.8144400 , 15.9779800);
    private GoogleMap map;
    Marker marker;
    private static final int[] idArray = {R.id.filtrationButton,
                                          R.id.informationButton,
                                          R.id.gameficationButton,
                                          R.id.settingsButton};

    private ImageButton []  buttonArray = new ImageButton[4];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMapMarker(ZAGREB);
        map.setOnMarkerClickListener(this);

        for ( int i=0; i<idArray.length; i++) {
            buttonArray [i] = (ImageButton)findViewById(idArray[i]);
        }

        setSquareButtons(buttonArray[0]);
    }


    /**
     * metoda koja postavlja na postojecu mapu marker
     * @param mjesto - prima geografsku sirinu i duzinu
     */
    private void initMapMarker(LatLng mjesto){

        try {
            if (map == null) {
                map = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.mapView)).getMap();
             }

            marker = map.addMarker(new MarkerOptions()
                    .position(mjesto)
                    .title("Lepi Zagreb")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda koja prilikom pritiska na marker pokazuje natpis iznad markera i izbacije poruku
     * na ekranu
     * @param marker ulazni parametar je marker
     * @return vraca stavili smo true jer zelimo hendlat event
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
     *velicini ekrana
     *
     * @param firstButton prima i mjeri sirinu prvog gumba i na osnovu njega postavlja na istu duzinu
     *                    visine svih ostalih gumbova
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

    }//metoda



}//kraj