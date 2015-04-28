package com.example.marko.zagreen;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentSettings extends Fragment {


    SpannableString aboutText = new SpannableString( "Aplikacija koja građanima Zagreba na jednostavan i osvještavajući" +
            " način pomaže da pronađu mjesta za recikliranje i odlaganje različitih vrsta" +
            " otpada.\n\nUkoliko primjetite reciklažno odlagalište koje nije na karti bili bismo " +
            "Vam zahvalni da nas obavjestite putem\n\n e-maila: zagreen@gmail.com");

    TextView text;
    float spacing = 3f, mult = 1.2f;
    public FragmentSettings(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {

        View settings = inflater.inflate(R.layout.fragment_settings, container, false);

        text = (TextView) settings.findViewById(R.id.about);
        aboutText.setSpan(new ForegroundColorSpan(Color.rgb(76,175,80)),272,289,0);
        text.setText(aboutText, TextView.BufferType.SPANNABLE);
        text.setLineSpacing(spacing,mult);




        return settings ;
    }


}
