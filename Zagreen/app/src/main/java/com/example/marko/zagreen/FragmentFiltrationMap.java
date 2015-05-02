package com.example.marko.zagreen;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.List;


/**
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class FragmentFiltrationMap extends Fragment {

    CheckBox papir, staklo, plastika, tekstil, reciklaznoDvoriste;
    boolean papirState, stakloState, plastikaState, // ovdje spremam vrijednosti
            tekstilState, reciklaznoDvoristeState;  // selekcije
    //boolean pap, st, pl,tek, rec;
    ButtonStateInterface respondCheckBoxState;
    Bundle savedState;


    public FragmentFiltrationMap() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View filtration = inflater.inflate(R.layout.fragment_filtration,container,false);
        papir = (CheckBox) filtration.findViewById(R.id.papir_check);
        staklo = (CheckBox) filtration.findViewById(R.id.staklo_check);
        plastika = (CheckBox) filtration.findViewById(R.id.plastika_check);
        tekstil = (CheckBox) filtration.findViewById(R.id.tekstil_check);
        reciklaznoDvoriste = (CheckBox) filtration.findViewById(R.id.dvoriste_check);

        MainActivity activity = (MainActivity) getActivity();
        String proba = activity.getMyData();

        
        if(proba.contains("truePapir")){
           papir.setChecked(true);
           papirState = true;//
       }else {

       }

        if(proba.contains("trueStaklo")){
            staklo.setChecked(true);
           stakloState= true;
        }else {
            staklo.setChecked(false);
        }
        if(proba.contains("truePlastika")){
            plastika.setChecked(true);
           plastikaState= true;
        }else {
            plastika.setChecked(false);
        }
        if(proba.contains("trueTekstil")){
            tekstil.setChecked(true);
           tekstilState= true;
        }else {
            tekstil.setChecked(false);
        }
        if(proba.contains("trueDvoriste")){
            reciklaznoDvoriste.setChecked(true);
           reciklaznoDvoristeState= true;
        }else {
            reciklaznoDvoriste.setChecked(false);
        }






        papir.setOnCheckedChangeListener(myClickListener);
        staklo.setOnCheckedChangeListener(myClickListener);
        plastika.setOnCheckedChangeListener(myClickListener);
        tekstil.setOnCheckedChangeListener(myClickListener);
        reciklaznoDvoriste.setOnCheckedChangeListener(myClickListener);
        return filtration;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        respondCheckBoxState = (ButtonStateInterface) activity;
    }


     /**
     *Metoda koja predaje stanja checkBoxova MainActivityu preko interfacea
     * @param papirCheck
     * @param stakloCheck
     * @param plastikaCheck
     * @param tekstilCheck
     * @param reciklaznoDvoristeCheck
     */
    public void checkBoxStates(boolean papirCheck, boolean stakloCheck,
                               boolean plastikaCheck, boolean tekstilCheck,
                               boolean reciklaznoDvoristeCheck) {

        respondCheckBoxState.getCheckBoxChangeState(papirCheck, stakloCheck, plastikaCheck,
                tekstilCheck, reciklaznoDvoristeCheck);
    }



    CompoundButton.OnCheckedChangeListener myClickListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView == papir) {
                if (isChecked) {
                    papirState = true; //kada je oznacen checkbox sprema vrijednost
                } else if (!isChecked) {
                    papirState = false;
                }
            } else if (buttonView == staklo) {
                if (isChecked) {
                    stakloState = true;
                } else if (!isChecked) {
                    stakloState = false;
                }

            } else if (buttonView == plastika) {
                if (isChecked) {
                    plastikaState = true;
                } else if (!isChecked) {
                    plastikaState = false;
                }

            } else if (buttonView == tekstil) {
                if (isChecked) {
                    tekstilState = true;
                } else if (!isChecked) {
                    tekstilState = false;
                }

            } else if (buttonView == reciklaznoDvoriste) {
                if (isChecked) {
                    reciklaznoDvoristeState = true;
                } else if (!isChecked) {
                    reciklaznoDvoristeState = false;
                }
            }
            checkBoxStates(papirState, stakloState, plastikaState, tekstilState, reciklaznoDvoristeState);
        }
    };

    // dobije mapa nazad sve i bilo je dobro na pocetku, sada kada smo sredili kucice sad markeri jebu

   public void updateCheckBoxStates(List<String> updateState){


        papir.setChecked(Boolean.valueOf(updateState.get(0)));
     /*   staklo.setChecked(Boolean.valueOf(updateState.get(1)));
        plastika.setChecked(Boolean.valueOf(updateState.get(2)));
        tekstil.setChecked(Boolean.valueOf(updateState.get(3)));
        reciklaznoDvoriste.setChecked(Boolean.valueOf(updateState.get(4)));*/
    }

    /*public void updateCheckBoxStates(boolean papi){
        papir.setChecked(papi);
    }*/

}