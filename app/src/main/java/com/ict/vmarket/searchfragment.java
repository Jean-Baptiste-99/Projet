package com.ict.vmarket;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class searchfragment extends Fragment {

    private CardView C1, C2, C3, C4, C5, C6, C7, C8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_search, container, false);

        C1 = v.findViewById(R.id.c1);
        C2 = v.findViewById(R.id.c2);
        C3 = v.findViewById(R.id.c3);
        C4 = v.findViewById(R.id.c4);
        C5 = v.findViewById(R.id.c5);
        C6 = v.findViewById(R.id.c6);
        C7 = v.findViewById(R.id.c7);
        C8 = v.findViewById(R.id.c8);

        C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Historique.class);
                startActivity(intent);
            }
        });

        C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Favori.class);
                startActivity(intent);
            }
        });

        C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Aide.class);
                startActivity(intent);
            }
        });

        C4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Propos.class);
                startActivity(intent);
            }
        });

        C7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Parametre.class);
                startActivity(intent);
            }
        });

        C8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Profil.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
