package com.example.marko.zagreen;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OnMarkerClickListener,
        ButtonStateFragmentMapButtons {

    Location location;
    private static final LatLng ZAGREB = new LatLng(45.8144400, 15.9779800); // ovo je jedna lokacija

    private LatLng testnoMjesto;

    private float latitude,longitude;
    String vrsta;
    private LatLng mojaLokacija;
    private Marker mojaLokMarker;
    private GoogleMap map;
    Marker markerZG;
    SquareToggleButton prikaziLokaciju;
    GPSTracker gps;

    Context context = getActivity();
    SharedPreferences sharedPref;
    List<String> latitudeData = new ArrayList<String>();
    List<String> longitudeData = new ArrayList<String>();
    List<String> vrstaData = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


// ali ne znam kako


        // mapa nebi trebala ovisiti o markerima
        initMap();

        if(isOnline()){
        new lokacije().execute();
        }
         //tu je bilo stvaranje markera
        map.setOnMarkerClickListener(this); // sluzi za osluskivanje klika na marker


      if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.menu_container, new MenuFragment()).commit();
        }

    } //onCreate



   // klasa za dohvat podataka ovo
    public class lokacije extends AsyncTask<Void, Void, Boolean> {

        String responseBody2 = "";



        @Override
        protected Boolean doInBackground(Void... voids) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://newtobu.url.ph/zagreen.php");
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("point", "1"));


            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                HttpResponse response = httpClient.execute(httpPost);
                int responseCode = response.getStatusLine().getStatusCode();

                switch (responseCode) {
                    case 200:
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            responseBody2 = EntityUtils.toString(entity);
                            Log.e("rEE", responseBody2);
                        }
                        break;
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }





        protected void onPostExecute(Boolean result){
            //parse JSON data
            int i;
            try {
                JSONArray jArray = new JSONArray(responseBody2);
                for(i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);


                   latitude = Float.valueOf(jObject.getString("latitude"));
                   longitude = Float.valueOf(jObject.getString("longitude"));
                    vrsta = jObject.getString("vrsta");

                    latitudeData.add(Float.toString(latitude));
                    longitudeData.add(Float.toString(longitude));
                    vrstaData.add(vrsta);






                    // ovdje ce poslije testiranja za jedan podatak, biti ubacivanje u polje
                    testnoMjesto = new LatLng(latitude, longitude); //pa moze biti i gore



                    Log.e("latitude: ",Float.toString(latitude) + "longitude:" +Float.toString(longitude));

                } // End Loop



                addMyMarker(testnoMjesto, markerZG);

                //podaci su sad tu u ovaj lat i long.. i kroz for petlju se povlaće svi.. i sad tu možeš
                //napraviti da se spreme u list ili polje


            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        } // protected void onPostExecute(Void v)
    }



    public void getLocationData (float lat, float lon){


    }



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


// ovdje nije g

    private void addMyMarker(LatLng mjesto, Marker marker) {

        marker = map.addMarker(new MarkerOptions()
                .position(mjesto)
                .title("Natpis")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
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

//ova metoda prima vrijednost pritiska gumba u fragmentu i prema njemu gleda lokaciju
    @Override
    public void getLocationButtonState(boolean state) {
        if(state){

            gps = new GPSTracker(MainActivity.this);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                mojaLokacija = new LatLng(latitude, longitude);

                addMyMarker(mojaLokacija,mojaLokMarker);


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




    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null&& netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void dataFromDataBaseStorage(List<String> lat, List<String> lon, List<String> vrs){
        String naziv = "com.example.marko.zagreen";
        sharedPref = context.getSharedPreferences(
                getString(com.example.marko.zagreen.PREF_file), Context.MODE_PRIVATE);

    }

}//kraj