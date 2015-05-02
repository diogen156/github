package com.example.marko.zagreen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.logging.Handler;


/**
 * Klasa koja služi za prikaz location buttona i check in plant buttona
 * nasljeđuje klasu Fragment.
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class FragmentMapButtons extends Fragment { //implements View.OnClickListener

    SquareToggleButton locationButton;
    SquareToggleButton checkInButton;
    boolean locationButtonState = false;
    boolean checkInButtonState = false;
    ButtonStateInterface respond;
    ButtonStateInterface respondCheckIn;


    public FragmentMapButtons() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mapButton = inflater.inflate(R.layout.fragment_map_buttons, container, false);
        locationButton = (SquareToggleButton) mapButton.findViewById(R.id.pokazi_lokaciju);
        checkInButton = (SquareToggleButton) mapButton.findViewById(R.id.plant_button);

        //listener za checkInButton
        checkInButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkInButtonState = true;
                respondCheckIn.getCheckInButtonState(checkInButtonState);
                checkInButtonState = false;
                checkInToggleToButton();
            }
        });

        //listener za locationButton
        locationButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                locationButtonState = true;
                respond.getLocationButtonState(locationButtonState);
                locationButtonState = false;
                locationToggleToButton();
            }
        });


        return mapButton;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        respond = (ButtonStateInterface) getActivity();
        respondCheckIn = (ButtonStateInterface) getActivity();
    }

    /**
     * Postavlja izgled toggle locationButtona kao obični button prilikom pritiska
     */
    public void locationToggleToButton() {
        locationButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                locationButton.setChecked(false);
            }
        }, 150);
    }

    /**
     * Postavlja izgled toggle checkInButtona kao obični button prilikom pritiska
     */
    public void checkInToggleToButton() {
        checkInButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkInButton.setChecked(false);
            }
        }, 150);
    }
}
