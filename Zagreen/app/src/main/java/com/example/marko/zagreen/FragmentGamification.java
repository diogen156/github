package com.example.marko.zagreen;


import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentGamification extends Fragment {

    public FragmentGamification() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View gamification = inflater.inflate(R.layout.fragment_gamification, null);


        return gamification;
    }
}
