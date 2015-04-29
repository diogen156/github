package com.example.marko.zagreen;


import android.app.ListFragment;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



/**
 * Created by Marko on 23.4.2015..
 */
public class FragmentFiltrationMap extends Fragment {

    ListView filtrationList;
    String[] vrste = new String[] { "Papir", "Staklo","Plastika","Reciklažno dvorište" };

    public FragmentFiltrationMap(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {

        View filtration = inflater.inflate(R.layout.fragment_filtration, null);







        return filtration ;
    }





}