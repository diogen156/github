package com.example.marko.zagreen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentSettings extends Fragment {


    public FragmentSettings(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {

        View settings = inflater.inflate(R.layout.fragment_settings, container, false);



        return settings ;
    }


}
