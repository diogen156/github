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
 * Created by Marko on 23.4.2015..
 */
public class MenuFragment extends Fragment {

    Fragment frag;
    FragmentTransaction fragTransaction;
    boolean[] array = new boolean[4];
    int stBt = 0, counter = 0;

    boolean newSt=false, oldSt=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        view.setBackgroundColor(Color.TRANSPARENT);

        frag = new FragmentMapButtons();
        fragTransaction = getFragmentManager().beginTransaction().add(R.id.container, frag, "FILTRACIJA");
        fragTransaction.commit();


        final SquareButton filtration = (SquareButton) view.findViewById(R.id.filtrationButton);
        final SquareButton information = (SquareButton) view.findViewById(R.id.informationButton);
        final SquareButton gamification = (SquareButton) view.findViewById(R.id.gameficationButton);
        final SquareButton settings = (SquareButton) view.findViewById(R.id.settingsButton);

        //setSquareButtons(filtration,information, gamification,settings); //namjestanje gumbova u layoutu

        filtration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentFiltrationMap();
                    fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag, "FILTRACIJA");
                    fragTransaction.commit();
                    array[0]=true;
                    stateButtonsOff(information,gamification,settings);
                } else if (!isChecked) {
                    array[0]=false;
                   // stateButtonsOn(information,gamification,settings);
                    stateMapOn();
                }
            }
        });

        information.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentAdvices();
                    fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag, "FILTRACIJA");
                    fragTransaction.commit();
                    array[1]=true;
                    stateButtonsOff(gamification,settings,filtration);
                } else if (!isChecked) {
                    array[1]=false;
                   // stateButtonsOn(gamification,settings,filtration);
                    stateMapOn();
                }
            }
        });

        gamification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentGamification();
                    fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag, "FILTRACIJA");
                    fragTransaction.commit();
                    array[2]=true;
                    stateButtonsOff(settings,filtration,information);
                } else if (!isChecked) {
                    array[2]=false;
                   // stateButtonsOn(settings,filtration,information);
                    stateMapOn();
                }
            }
        });

        settings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frag = new FragmentSettings();
                    fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag, "FILTRACIJA");
                    fragTransaction.commit();
                    array[3] = true;
                    stateButtonsOff(filtration,information,gamification);
                } else if (!isChecked) {
                    array[3] = false;
                    //stateButtonsOn(filtration,information,gamification);
                    stateMapOn();
                }
            }
        });



        return view;
    }


    public void stateButtonsOff( SquareButton bt2, SquareButton bt3, SquareButton bt4) {
         bt2.setChecked(false);
         bt3.setChecked(false);
         bt4.setChecked(false);

    }

    public void stateMapOn() {

        int counter = 0;


        for (int i = 0; i<4; i++){
            if(array[i]==false){
                counter++;
            }
        }

        if(counter == 4){
            frag = new FragmentMapButtons();
            fragTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag, "MAP");
            fragTransaction.commit();
        }

    }



}//kraj
