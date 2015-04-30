package com.example.marko.zagreen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marko on 24.4.2015..
 */
public class FragmentMapButtons extends Fragment implements View.OnClickListener {

    //SquareToggleButton plantButton;
    SquareToggleButton locationButton;
    boolean locationButtonState = false;
    ButtonStateInterface respond;


    public FragmentMapButtons() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mapButton = inflater.inflate(R.layout.fragment_map_buttons, container,false);
        locationButton = (SquareToggleButton)mapButton.findViewById(R.id.pokazi_lokaciju);
        locationButton.setOnClickListener(this);


        return mapButton;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        respond = (ButtonStateInterface) getActivity();
    }

    @Override
    public void onClick(View v) {

        locationButtonState = true;
        respond.getLocationButtonState(locationButtonState);
        locationButtonState = false;

    }
}
