package com.example.marko.zagreen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marko on 24.4.2015..
 */
public class FragmentMapButtons extends Fragment {


    public FragmentMapButtons() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mapButton = inflater.inflate(R.layout.fragment_map_buttons, null);


        return mapButton;
    }

}
