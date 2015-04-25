package com.example.marko.zagreen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentAdvices extends Fragment {

    public FragmentAdvices(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View advices = inflater.inflate(R.layout.fragment_advices, null);


        return advices ;
    }

}
