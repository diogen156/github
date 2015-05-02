package com.example.marko.zagreen;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Klasa koja postavlja izgled biljke
 * sadrži metode za postavljanje layouta
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class FragmentGamification extends Fragment {


    int checkInPlantCounter;
    int plantID;
    String plantImageName = "plant_";
    String resourceImageName = "drawable";
    RelativeLayout plantLayout;
    ImageView plant;
    TextViewPlus checkScore, plantDescription;
    String plantGameDescription = "Ovo je Vaša biljka kojoj trebate pomoći. " +
            "Checkin-om prilikom svakog recikliranja Vaša biljka dobit će novi listić. Broj " +
            "checkin-ova prikazan je u zelenom krugu. Checkin je ograničen u ovisnosti prema " +
            "blizini kontejnera (radijalno 25m) i vremenski (unutar jednog sata). " +
            "Pomozite Vašoj biljci i prirodi!";

    public FragmentGamification() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View gamification = inflater.inflate(R.layout.fragment_gamification, null);

        plant = (ImageView) gamification.findViewById(R.id.plant);
        checkScore = (TextViewPlus) gamification.findViewById(R.id.plant_checkin_score);
        plantDescription = (TextViewPlus) gamification.findViewById(R.id.plant_score_description);

        plantDescription.setText(plantGameDescription); //tekst za rast biljke
        checkScore.setText(String.valueOf(checkInPlantCounter)); // postavlja checkin score u zeleni krug

        MainActivity activityPlant = (MainActivity) getActivity();
        checkInPlantCounter = activityPlant.getMyPlantCheckInCounter();

        //postavljamo izgled biljke
        plantID = getActivity().getResources().getIdentifier(plantImageName + checkInPlantCounter,
                resourceImageName, getActivity().getPackageName());
        plant.setImageResource(plantID);

        return gamification;
    }


}
