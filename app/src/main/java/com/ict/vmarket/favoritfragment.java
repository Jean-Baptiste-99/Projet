package com.ict.vmarket;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class favoritfragment extends Fragment {

    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        btn = v.findViewById(R.id.btnpub);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence[] items = {"Un produit en promotion", "Un produit en ordinaire", "Une Annonce ou un service"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Que voulez-vous publier ?");
                builder.setIcon(R.drawable.ic_verified_user_black_24dp);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        if (i == 0){

                            startActivity(new Intent(getContext(), Promotion.class));

                        }

                        if (i == 1){

                            startActivity(new Intent(getContext(),Pub.class));

                        }

                        if (i == 2){

                            startActivity(new Intent(getContext(), Annonce.class));

                        }

                    }
                });
                builder.show();

                //Intent intent = new Intent(getContext(), Login.class);
                //startActivity(intent);

            }
        });


        return v;
    }
}
