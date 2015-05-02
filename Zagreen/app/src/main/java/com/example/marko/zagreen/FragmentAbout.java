package com.example.marko.zagreen;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Klasa koja prikazuje opis aplikacije, logo aplikacije.
 *
 * @author Collude
 * @version 2015.0502
 * @since 1.0
 */
public class FragmentAbout extends Fragment {


    SpannableString aboutText = new SpannableString("Aplikacija koja građanima Zagreba na" +
            " jednostavan i osvještavajući način pomaže da pronađu mjesta za recikliranje i " +
            "odlaganje različitih vrsta otpada.\n\nUkoliko primijetite reciklažno odlagalište" +
            " koje nije na karti bili bismo Vam zahvalni da nas obavijestite putem\n\n " +
            "e-maila: zagreenapp@gmail.com\n\nNove dodane lokacije bit će dostupne u aplikaciji" +
            " bez potrebne reinstalacije u vrlo brzom roku.\n\nHvala Vam što reciklirate!");

    TextView text;

    public FragmentAbout() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View settings = inflater.inflate(R.layout.fragment_about, container, false);

        text = (TextView) settings.findViewById(R.id.about);
        aboutText.setSpan(new ForegroundColorSpan(Color.rgb(76, 175, 80)), 273, 294, 0);
        text.setText(aboutText, TextView.BufferType.SPANNABLE);


        return settings;
    }


}
