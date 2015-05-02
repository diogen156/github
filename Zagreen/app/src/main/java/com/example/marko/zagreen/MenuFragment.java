package com.example.marko.zagreen;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;


/**
 * Klasa koja sadrži podatke buttone za navigacijski menu i postavlja layoute
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class MenuFragment extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;
    boolean[] array = new boolean[4];



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        view.setBackgroundColor(Color.TRANSPARENT);

        frag = new FragmentMapButtons();
        fragTransaction = getFragmentManager().beginTransaction()
                .add(R.id.container, frag, "MAP_BUTTONS");
        fragTransaction.commit();

        final SquareToggleButton filtration = (SquareToggleButton) view.findViewById(R.id.filtrationButton);
        final SquareToggleButton information = (SquareToggleButton) view.findViewById(R.id.informationButton);
        final SquareToggleButton gamification = (SquareToggleButton) view.findViewById(R.id.gameficationButton);
        final SquareToggleButton settings = (SquareToggleButton) view.findViewById(R.id.settingsButton);


        filtration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentFiltrationMap();
                    fragTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.container, frag, "FILTRACIJA");
                    fragTransaction.commit();
                    array[0] = true;
                    stateButtonsOff(information, gamification, settings);

                } else if (!isChecked) {
                    array[0] = false;

                    stateMapOn();
                }
            }
        });

        information.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentAdvices();
                    fragTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.container, frag, "ADVICES");
                    fragTransaction.commit();
                    array[1] = true;
                    stateButtonsOff(gamification, settings, filtration);
                } else if (!isChecked) {
                    array[1] = false;

                    stateMapOn();
                }
            }
        });

        gamification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentGamification();
                    fragTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.container, frag, "GAME");
                    fragTransaction.commit();
                    array[2] = true;
                    stateButtonsOff(settings, filtration, information);
                } else if (!isChecked) {
                    array[2] = false;

                    stateMapOn();
                }
            }
        });


        settings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentAbout();
                    fragTransaction = getFragmentManager().beginTransaction()
                            .replace(R.id.container, frag, "ABOUT");
                    fragTransaction.commit();
                    array[3] = true;
                    stateButtonsOff(filtration, information, gamification);
                } else if (!isChecked) {
                    array[3] = false;

                    stateMapOn();
                }
            }
        });


        return view;
    }


    /**
     * Postavalja buttone u off stanje
     *
     * @param bt2 Button koji želimo off
     * @param bt3 Button koji želimo off
     * @param bt4 Button koji želimo off
     */
    public void stateButtonsOff(SquareToggleButton bt2, SquareToggleButton bt3, SquareToggleButton bt4) {
        bt2.setChecked(false);
        bt3.setChecked(false);
        bt4.setChecked(false);
    }

    /**
     * Postavlja fragment FragmentMapButtons kada nije uključen nijedan
     * od menu buttona
     */
    public void stateMapOn() {

        int counter = 0;


        for (int i = 0; i < 4; i++) {
            if (array[i] == false) {
                counter++;
            }
        }

        if (counter == 4) {
            frag = new FragmentMapButtons();
            fragTransaction = getFragmentManager().beginTransaction()
                    .replace(R.id.container, frag, "MAP");
            fragTransaction.commit();
        }

    }


}
