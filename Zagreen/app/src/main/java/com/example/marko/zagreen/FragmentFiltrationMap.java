package com.example.marko.zagreen;


import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Marko on 23.4.2015..
 */
public class FragmentFiltrationMap extends Fragment {

    GoogleMap map;
    Fragment frag;
    FragmentTransaction fragTransaction;

    public FragmentFiltrationMap(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {

        View filtration = inflater.inflate(R.layout.fragment_filtration, null);



        return filtration ;
    }





}