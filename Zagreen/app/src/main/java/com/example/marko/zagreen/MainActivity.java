package com.example.marko.zagreen;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
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
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OnMarkerClickListener,
        ButtonStateInterface {

    boolean papirStateActivity, stakloStateActivity, plastikaStateActivity,
            tekstilStateActivity, reciklaznoDvoristeStateActivity;

    Location location;
    private static final LatLng ZAGREB = new LatLng(45.8144400, 15.9779800); // ovo je jedna lokacija

    private LatLng testnoMjesto;

    private float latitude, longitude;
    String vrsta;
    private LatLng mojaLokacija;
    private Marker mojaLokMarker;
    private GoogleMap map;

    Marker papir, staklo, plastika, tekstil, reciklaznoDvoriste;
    Marker markerZG;

    List<String> oznakaVrste = new ArrayList<String>();
    String markerTitlePapir = "Papir", markerTitleStaklo = "Staklo", markerTitlePlastika = "Plastika", markerTitleTekstil = "Tekstil", markerTitleReciklažnoDvorište = "Reciklažno dvorište";
    LatLng markerPlace;
    float markerLat, markerLng;


    SquareToggleButton prikaziLokaciju;
    GPSTracker gps;

    List<Float> latitudeDataTest = new ArrayList<Float>();
    List<Float> longitudeDataTest = new ArrayList<Float>();
    List<String> myFragmentFiltratonStates = null;


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


//        myFragmentFiltratonStates.add("ss");
        // mapa nebi trebala ovisiti o markerima
        initMap();

        if (isOnline()) {
            new lokacije().execute();

        } else if (!isOnline()) {
            Log.d("testno mjesto", "OVOje izvrsilo else");
            latitudeAppData = loadArray("latDataBase", MainActivity.this, nameArrayLatitude);
            for (int n = 0; n < latitudeAppData.size(); n++) {
                Log.d("tag", latitudeAppData.get(n));
            }

        }


        Location loc1 = new Location("");
        loc1.setLatitude(45.8080);
        loc1.setLongitude(15.1584);

        Location loc2 = new Location("");
        loc2.setLatitude(45.8777);
        loc2.setLongitude(15.4263);

        float distanceInMeters = loc1.distanceTo(loc2);

        Log.d("UDALJENOSTI JE OVA : ", Float.toString(distanceInMeters));


        //tu je bilo stvaranje markera
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

                    latitudeDataTest.add(latitude);
                    longitudeDataTest.add(longitude);


                    Log.e("latitude: ", Float.toString(latitude) + "longitude:" + Float.toString(longitude));

                } // End Loop


                pokaziOdredeneMarker(latitudeDataTest, longitudeDataTest);

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


    public void getLocationData(float lat, float lon) {


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

    //ova metoda prima vrijednost pritiska gumba u FragmentMapButtons i prema njemu trazimo zahtjev za lokacijom
    @Override
    public void getLocationButtonState(boolean state) {
        if (state) {

            gps = new GPSTracker(MainActivity.this);

            // Provjerava da li je uključen pristup lokaciji
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                mojaLokacija = new LatLng(latitude, longitude);

                //addMyMarker(mojaLokacija,mojaLokMarker);

//sto trazis ?
                centerCameraOnLocation(mojaLokacija);

                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude +
                        "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // ne može dobiti lokaciju
                // GPS ili Network pristup nisu uključeni
                // Pita korisnika za uključenje kroz postavke
                gps.showSettingsAlert();
            }


        }
    }

    @Override //kada se promjeni stanje u metodi u fragmentu ovaj MainActivity implementira interface
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
        //ček da istestiram još

     /*   myFragmentFiltratonStates.add(Boolean.toString(stakloStateActivity));
        myFragmentFiltratonStates.add(Boolean.toString(plastikaStateActivity));
        myFragmentFiltratonStates.add(Boolean.toString(tekstilStateActivity));
        myFragmentFiltratonStates.add(Boolean.toString(reciklaznoDvoristeStateActivity));*/

               /* Toast.makeText(getApplicationContext(),"checkPapir:"+papirStateActivity+"\nstaklo: "
                        + stakloStateActivity+"\nplastika: "+plastikaStateActivity+
                        "\ntekstil: "+tekstilStateActivity+"\ndvoriste: " +reciklaznoDvoristeStateActivity
                        , Toast.LENGTH_LONG).show();*/

//nećemo se tak zajebavat :) napravit ćemo par funkcija da vidimo jel sve radi




    }


    public void pokaziOdredeneMarker(List<Float> lat, List<Float> lng) {
        for (int i = 0; i < lat.size(); i++) {
            testnoMjesto = new LatLng(lat.get(i).floatValue(), lng.get(i).floatValue());
            //addMyMarker(testnoMjesto, markerZG);
        }
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

    public void setMarkersOnMap(boolean papirState, boolean stakloState,
                                boolean plastikaState, boolean tekstilState,
                                boolean reciklaznoDvoristeState, List<String> vrsta) {

        /*oznakaVrste.add("PA");
        oznakaVrste.add("PA-PL");
        oznakaVrste.add("PA-ST");
        oznakaVrste.add("PA-ST-PL");
        oznakaVrste.add("PA-ST-PL-TEKSTIL");
        oznakaVrste.add("PL");
        oznakaVrste.add("Reciklažno dvorište");
        oznakaVrste.add("ST");*/
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

    @Override
    public void getFiltrationButtonState(boolean state) {

        /*if(state){
        Bundle bundle = new Bundle();
        bundle.putBoolean("papirStanje",papirStateActivity);
        // set Fragmentclass Arguments
        FragmentFiltrationMap fragobj = new FragmentFiltrationMap();
        fragobj.setArguments(bundle);}
        FragmentManager manager = getFragmentManager();
        Fragment f = new FragmentFiltrationMap();
        f.getFragmentManager().findFragmentByTag("FILTRACIJA");
        f.setArguments();
        //Fragment f= (FragmentFiltrationMap) manager.findFragmentByTag("FILTRACIJA");
        f.updateCheckBoxStates(papirStateActivity,stakloStateActivity,plastikaStateActivity,
                tekstilStateActivity,reciklaznoDvoristeStateActivity);
        //Toast.makeText(getApplicationContext(), "papir: " + papirStateActivity,
        // Toast.LENGTH_LONG).show();*/
    }

    public String getMyData() {

        return Boolean.toString(papirStateActivity)+"Papir"+Boolean.toString(stakloStateActivity)+"Staklo"
                +Boolean.toString(plastikaStateActivity)+"Plastika"+Boolean.toString(tekstilStateActivity)+"Tekstil"
                +Boolean.toString(reciklaznoDvoristeStateActivity)+"Dvoriste";

    } // mogu provjravat string truePrvi ili falsePrvi itd..da

}//kraj