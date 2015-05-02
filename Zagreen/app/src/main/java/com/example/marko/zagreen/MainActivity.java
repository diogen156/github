package com.example.marko.zagreen;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Klasa koja sadrži podatke za klasu FragmentAdvices, prikazani su u expandable listi.
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class MainActivity extends Activity implements OnMarkerClickListener,
        ButtonStateInterface {

    boolean papirStateActivity, stakloStateActivity, plastikaStateActivity,
            tekstilStateActivity, reciklaznoDvoristeStateActivity;


    private float latitude, longitude;
    String vrsta;
    private LatLng mojaLokacija;
    private GoogleMap map;

    Marker papir, staklo, plastika, tekstil, reciklaznoDvoriste;



    String markerTitlePapir = "Papir", markerTitleStaklo = "Staklo",
            markerTitlePlastika = "Plastika", markerTitleTekstil = "Tekstil",
            markerTitleReciklažnoDvorište = "Reciklažno dvorište";
    LatLng markerPlace;
    float markerLat, markerLng;


    SquareToggleButton prikaziLokaciju;
    GPSTracker gps;


    List<Float> minDistance = new ArrayList<Float>();
    int checkInCounter = 0;
    int checkStateNubmber;
    int locationStateNubmber;
    int pozicijaNajblizegKontenjera;
    List<String> listaVremena = new ArrayList<String>();


    //Grupa podataka za latitude za zapisivanje iz bazu u aplikaciju i iz aplikaciju za daljnje operacije
    List<String> latitudeDataBase = new ArrayList<String>();// u ovo se sprema svaki clan latitude iz baze
    List<String> latitudeAppData = new ArrayList<String>(); // u ovo se sprema svaki clan latitude iz memorije aplikacije
    String nameArrayLatitude = "dataForLatitude"; // ovo je ime mjesta podataka u aplikaciji za latitude
    String nameBaseTermMemberLat = "latDataBase"; // osnovni key imena svakog clana "latDataBase_i"

    //Grupa podataka za longitude za zapisivanje iz bazu u aplikaciju i iz aplikaciju za daljnje operacije
    List<String> longitudeDataBase = new ArrayList<String>();// u ovo se sprema svaki clan longitude iz baze
    List<String> longitudeAppData = new ArrayList<String>(); // u ovo se sprema svaki clan longitude iz memorije aplikacije
    String nameArrayLongitude = "dataForLongitude"; // ovo je ime mjesta podataka u aplikaciji za longitude
    String nameBaseTermMemberLng = "lngDataBase"; // osnovni key imena svakog clana "lngDataBase_i"

    //Grupa podataka za vrstu za zapisivanje iz bazu u aplikaciju i iz aplikaciju za daljnje operacije
    List<String> vrstaDataBase = new ArrayList<String>();// u ovo se sprema svaki clan vrste iz baze
    List<String> vrstaAppData = new ArrayList<String>(); // u ovo se sprema svaki clan vrste iz memorije aplikacije
    String nameArrayVrsta = "dataForVrsta"; // ovo je ime mjesta podataka u aplikaciji za vrstu
    String nameBaseTermMemberVrs = "vrsDataBase"; // osnovni key imena svakog clana "vrsDataBase_i"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMap();

        if (isOnline()) {
            new lokacije().execute();

        } else if (!isOnline()) {

            latitudeAppData = loadArray("latDataBase", MainActivity.this, nameArrayLatitude);
            for (int n = 0; n < latitudeAppData.size(); n++) {
                Log.d("tag", latitudeAppData.get(n));
            }

        }

        map.setOnMarkerClickListener(this); // sluzi za osluskivanje klika na marker


        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.menu_container, new MenuFragment()).commit();
        }

        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");
        // set Fragmentclass Arguments
        MenuFragment fragobj = new MenuFragment();
        fragobj.setArguments(bundle);
    }

    /**
     * Klasa koja sadrži metode za dohvaćanje podataka iz online baze
     *
     * @author Collude
     * @version 2015.0502
     * @since 1.0
     */
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


        protected void onPostExecute(Boolean result) {
            //parse JSON data
            int i;
            try {
                JSONArray jArray = new JSONArray(responseBody2);
                for (i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    //spremanje pojedinog clana
                    latitude = Float.valueOf(jObject.getString("latitude"));
                    longitude = Float.valueOf(jObject.getString("longitude"));
                    vrsta = jObject.getString("vrsta");

                    // sprema pojedini clan latitude,longitude,vrsta iz baze u listu
                    latitudeDataBase.add(Float.toString(latitude));
                    longitudeDataBase.add(Float.toString(longitude));
                    vrstaDataBase.add(vrsta);



                    Log.e("latitude: ", Float.toString(latitude) + "longitude:" + Float.toString(longitude));

                }


                // spremaju se svi clanovi latitude,longitude iz liste baze podataka u aplikaciju


                saveArray(latitudeDataBase, nameBaseTermMemberLat, MainActivity.this, nameArrayLatitude);
                saveArray(longitudeDataBase, nameBaseTermMemberLng, MainActivity.this, nameArrayLongitude);
                saveArray(vrstaDataBase, nameBaseTermMemberVrs, MainActivity.this, nameArrayVrsta);


                // loadaju se svi clanovi latitude,longitude iz aplikacije za daljnje operacije
                latitudeAppData = loadArray(nameBaseTermMemberLat, MainActivity.this, nameArrayLatitude);
                longitudeAppData = loadArray(nameBaseTermMemberLng, MainActivity.this, nameArrayLongitude);
                vrstaAppData = loadArray(nameBaseTermMemberVrs, MainActivity.this, nameArrayVrsta);

                Log.d("savano", "jkgbilvjlhv");
                for (int n = 0; n < latitudeAppData.size(); n++) {
                    Log.d("tagLAT", latitudeAppData.get(n));

                }

                for (int n = 0; n < longitudeAppData.size(); n++) {
                    Log.d("tagLNG", longitudeAppData.get(n));

                }

                for (int n = 0; n < vrstaAppData.size(); n++) {
                    Log.d("tagVRS", vrstaAppData.get(n));

                }


            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        } // protected void onPostExecute(Void v)
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
     * Metoda centrira pogled na mapi sukladno lokaciji.
     *
     * @param lokacija Prima lokaciju
     */
    public void centerCameraOnLocation(final LatLng lokacija) {
        if (lokacija != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .zoom(16)
                    .target(mojaLokacija)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    /**
     * Metoda koja postavlja markere na karti.
     *
     * @param mjesto Prima lokaciju
     * @param marker Prima marker
     */
    private void addMyMarker(LatLng mjesto, Marker marker, String markerTitle, BitmapDescriptor markerIcon) {

        marker = map.addMarker(new MarkerOptions()
                .position(mjesto)
                .title(markerTitle)
                .icon(markerIcon));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow(); //pokazuje natpis iznad markera
        Toast.makeText(getApplicationContext(), "Kliknuo si na marker: "
                + marker.getTitle(), Toast.LENGTH_LONG).show(); //ispisuje poruku na ekranu
        return true; // hendlam event i ne pojavljuju se google smece ikone (koje mogu biti korisne u nekoj primjeni)
    }


    @Override
    //kada se promjeni stanje u metodi u fragmentu ovaj MainActivity implementira interface
    // i vrijednosti se prenose ovdje
    public void getCheckBoxChangeState(boolean papirState, boolean stakloState,
                                       boolean plastikaState, boolean tekstilState,
                                       boolean reciklaznoDvoristeState) {

        //ovdje se spremaju stanja u activity (boolean vrijednosti)
        papirStateActivity = papirState;
        stakloStateActivity = stakloState;
        plastikaStateActivity = plastikaState;
        tekstilStateActivity = tekstilState;
        reciklaznoDvoristeStateActivity = reciklaznoDvoristeState;

        setMarkersOnMap(papirState, stakloState, plastikaState, tekstilState,
                reciklaznoDvoristeState, vrstaAppData);

    }


    /**
     * Metoda koja provjerava da li je internet ukljucen, potrebno radi hendlanja podataka iz baze
     *
     * @return Vraca boolean true ili false
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }


    /**
     * Metoda koja sprema podatke u aplikaciju
     *
     * @param array             Prima listu podataka
     * @param arrayName         Prima osnovno ime svakog clana
     * @param mContext          Prima kontekst aplikacije
     * @param nameArrayLatitude Prima ime mjesta za podatke
     * @return Vraca commit
     */
    public boolean saveArray(List<String> array, String arrayName, Context mContext, String nameArrayLatitude) {
        SharedPreferences prefs = mContext.getSharedPreferences(nameArrayLatitude, 0); //sprema ime mjesta
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.size()); //sprema duzinu polja
        for (int i = 0; i < array.size(); i++) // sprema sve clanove
            editor.putString(arrayName + "_" + i, array.get(i));
        return editor.commit();
    }

    /**
     * Metoda koja vraća podatke spremljene u aplikaciji
     *
     * @param arrayName         Prima osnovno ime svakog clana
     * @param mContext          Prima kontekst aplikacije
     * @param nameArrayLatitude Prima ime mjesta za podatke
     * @return Vraca listu podataka
     */
    public List<String> loadArray(String arrayName, Context mContext, String nameArrayLatitude) {
        SharedPreferences prefs = mContext.getSharedPreferences(nameArrayLatitude, 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        List<String> array = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            array.add(prefs.getString(arrayName + "_" + i, null));
        }
        return array;
    }

    /**
     * Dodjeljuje ikonu, natpis svakom markeru na karti i postavlja ih na mapu, osvježava stanje karte
     * @param papirState
     * @param stakloState
     * @param plastikaState
     * @param tekstilState
     * @param reciklaznoDvoristeState
     * @param vrsta
     */
    public void setMarkersOnMap(boolean papirState, boolean stakloState,
                                boolean plastikaState, boolean tekstilState,
                                boolean reciklaznoDvoristeState, List<String> vrsta) {


        BitmapDescriptor markerIconPapir = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                markerIconStaklo = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                markerIconPlastika = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW),
                markerIconTekstil = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),
                markerIconReciklaznoDvoriste = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

        map.clear(); // brise sve stare markere
        if (papirState) {
            for (int a = 0; a < vrsta.size(); a++) {
                if (vrsta.get(a).equals("PA")) {
                    addMyMarker(prepareDataTypeForLocation(a, 0), papir, markerTitlePapir,
                            markerIconPapir);
                }
                if (vrsta.get(a).contains("PA-")) {
                    addMyMarker(prepareDataTypeForLocation(a, 1), papir, markerTitlePapir,
                            markerIconPapir);
                }
            }
        }


        if (stakloState) {
            for (int b = 0; b < vrsta.size(); b++) {
                if (vrsta.get(b).equals("ST")) {
                    addMyMarker(prepareDataTypeForLocation(b, 0), staklo, markerTitleStaklo,
                            markerIconStaklo);
                }
                if (vrsta.get(b).contains("-ST")) {
                    addMyMarker(prepareDataTypeForLocation(b, 2), staklo, markerTitleStaklo,
                            markerIconStaklo);
                }
            }

        }

        if (plastikaState) {
            for (int c = 0; c < vrsta.size(); c++) {
                if (vrsta.get(c).equals("PL")) {
                    addMyMarker(prepareDataTypeForLocation(c, 0), plastika, markerTitlePlastika,
                            markerIconPlastika);
                }
                if (vrsta.get(c).contains("-PL")) {
                    addMyMarker(prepareDataTypeForLocation(c, 3), plastika, markerTitlePlastika,
                            markerIconPlastika);
                }
            }
        }

        if (tekstilState) {
            for (int d = 0; d < vrsta.size(); d++) {
                if (vrsta.get(d).contains("TEKSTIL")) {
                    addMyMarker(prepareDataTypeForLocation(d, 4), tekstil, markerTitleTekstil,
                            markerIconTekstil);
                }
            }
        }

        if (reciklaznoDvoristeState) {
            for (int e = 0; e < vrsta.size(); e++) {
                if (vrsta.get(e).equals("Reciklažno dvorište")) {
                    addMyMarker(prepareDataTypeForLocation(e, 0), reciklaznoDvoriste,
                            markerTitleReciklažnoDvorište, markerIconReciklaznoDvoriste);
                }
            }
        }


    }

    /**
     * Razdjeljuje markere na karti zbog preklapanja
     *
     * @param i
     * @param sadrži
     * @return
     */
    public LatLng prepareDataTypeForLocation(int i, int sadrži) {
        int sad = sadrži;
        markerLat = Float.parseFloat(latitudeAppData.get(i));
        markerLng = Float.parseFloat(longitudeAppData.get(i));

        //uvećava/oduzima se radi preklapanja markera
        switch (sad) {
            case 1:
                markerLat += 0.0001;
                markerLng += 0.0001;
                break;
            case 2:
                markerLat -= 0.0001;
                markerLng -= 0.0001;
                break;
            case 3:
                markerLat += 0.00001;
                markerLng += 0.00001;
            case 4:
                markerLat -= 0.00001;
                markerLng -= 0.00001;
                break;
            default:
                break;
        }
        markerPlace = new LatLng(markerLat, markerLng);
        return markerPlace;

    }

    /**
     * Prenosi vrijednosti checkboxova u klasu FragmentFiltrationMap
     * @return
     */
    public String getMyData() {

        return Boolean.toString(papirStateActivity) + "Papir" + Boolean.toString(stakloStateActivity) + "Staklo"
                + Boolean.toString(plastikaStateActivity) + "Plastika" + Boolean.toString(tekstilStateActivity) + "Tekstil"
                + Boolean.toString(reciklaznoDvoristeStateActivity) + "Dvoriste";

    }

    /**
     * Vraća index elementa u polju, a on je najmanja udaljenost između lokacije i najbližeg markera
     * @param lat latitude
     * @param lng longitude
     * @return index elemnta
     */
    public int getNearestMarker(double lat, double lng) {


        int minIndex = 0;


        Location loc1 = new Location(""); // trenutna lokacija
        loc1.setLatitude(lat);
        loc1.setLongitude(lng);

        Location loc2 = new Location(""); //lokacija markera

        float distanceInMeters;

        for (int i = 0; i < latitudeAppData.size(); i++) {

            loc2.setLatitude(Float.parseFloat(latitudeAppData.get(i)));
            loc2.setLongitude(Float.parseFloat(longitudeAppData.get(i)));
            minDistance.add(loc1.distanceTo(loc2));
            Log.d("LOKACIJA: " + i, Float.toString(loc1.distanceTo(loc2)));

        }


        minIndex = minDistance.indexOf(Collections.min(minDistance));

        return minIndex;
    }

    /**
     * Predaje vrijednost countera u FragmentGamification klasu
     * @return broj checkinova
     */
    public int getMyPlantCheckInCounter() {

        return checkInCounter;

    }

    @Override
    public void getCheckInButtonState(boolean state) { //prilikom klika se poziva 2x vazno zbog countera
        checkStateNubmber++;



        if (checkStateNubmber == 1) {
            logicForPlantGrow();
        } else if (checkStateNubmber > 1) {
            checkStateNubmber = 0;
        }

    }


    @Override
    public void getLocationButtonState(boolean state) {
        locationStateNubmber++;
        // u ovoj metodi pronalazimo lokaciju korisnika i centriramo pogled na lokaciju
        // ukoliko GPS nije uključen pojavit će se alert dialog
        if (state) {
            gps = new GPSTracker(MainActivity.this);

            // Provjerava da li je uključen pristup lokaciji
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                mojaLokacija = new LatLng(latitude, longitude);


                if (latitude != 0 && longitude != 0) {
                    pozicijaNajblizegKontenjera = getNearestMarker(latitude, longitude);
                    Log.d("MINIMALNA: ", Integer.toString(getNearestMarker(latitude, longitude)));
                }

                centerCameraOnLocation(mojaLokacija);

                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude +
                        "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // ne može dobiti lokaciju
                // GPS ili Network pristup nisu uključeni
                // Pita korisnika za uključenje kroz postavke

                if (locationStateNubmber == 1) {
                    gps.showSettingsAlert();
                } else if (locationStateNubmber > 1) {
                    locationStateNubmber = 0;
                }
            }


        }
    }

    /**
     * Definira logiku postavljanja listića na biljci
     */
    public void logicForPlantGrow() {

        if (minDistance.size() != 0) {

            float udaljenostNajblizegKontenjara = minDistance.get(pozicijaNajblizegKontenjera);


            // uvjet za vrijeme i udaljenost za check in
            if (udaljenostNajblizegKontenjara < 25 && timeBetweenCheckIns()) {
                checkInCounter++;
                getMyPlantCheckInCounter();
            }
        } else {

            Toast.makeText(getApplicationContext(), "Molimo dohvatite svoju lokaciju pritiskom na gumb " +
                    "iznad.", Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Računa vrijeme između dva checkina
     * @return true ako je uvjet zadovoljen
     */
    public boolean timeBetweenCheckIns() {

        boolean returnValue = false;

        listaVremena.add(0, "početak");

        if ((listaVremena.get(listaVremena.size() - 1)).equalsIgnoreCase("početak")) {
            String firstEverTime = DateFormat.getDateTimeInstance().format(new Date());
            listaVremena.add(firstEverTime);

            returnValue = true;

        } else if (listaVremena.size() < 50) {


            String dateStart = listaVremena.get(listaVremena.size() - 1);


            String dateStop = DateFormat.getDateTimeInstance().format(new Date());


            SimpleDateFormat myformat = new SimpleDateFormat("dd.mm.yyyy. HH:mm:ss");
            Date d1 = null;
            Date d2 = null;

            try {

                Toast.makeText(getApplicationContext(), "try", Toast.LENGTH_LONG).show();
                d1 = myformat.parse(dateStart);
                d2 = myformat.parse(dateStop);

                //u milisekundama
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);


                if (diffDays > 0) {
                    listaVremena.add(dateStop);
                    returnValue = true;
                } else if (diffHours > 0) {

                    listaVremena.add(dateStop);
                    returnValue = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Vremenski razmak između dva checkina " +
                            "mora biti najmanje 1h.", Toast.LENGTH_LONG).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return returnValue;
    }


}