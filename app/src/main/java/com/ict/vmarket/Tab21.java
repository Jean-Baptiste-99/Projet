package com.ict.vmarket;



import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputLayout;
import com.ict.vmarket.MyRequest.MyRequest;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab21 extends Fragment {


    public Tab21() {
        // Required empty public constructor
    }

    private TextInputLayout t1, t2, t3, t4;
    private Button b1, b2;
    private RequestQueue queue;
    private MyRequest request;
    private ProgressBar leading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tab21, container, false);

        t1 = v.findViewById(R.id.titre_ann);
        t2 = v.findViewById(R.id.msg_ann);
        t3 = v.findViewById(R.id.dt_ann);
        t4 = v.findViewById(R.id.nomEt_ann);

        b1 = v.findViewById(R.id.btnup1);
        b2 = v.findViewById(R.id.btnall1);

        leading = v.findViewById(R.id.progressBar_insc_tab21);

        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        request = new MyRequest(getContext(), queue);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.getEditText().setText("");
                t2.getEditText().setText("");
                t3.getEditText().setText("");
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leading.setVisibility(View.VISIBLE);
                b2.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);

                final String titre = t1.getEditText().getText().toString().trim();
                final String msg = t2.getEditText().getText().toString().trim();
                final String nom = t4.getEditText().getText().toString().trim();
                final String dt = t3.getEditText().getText().toString().trim();

                if (titre.length() > 0  && msg.length() > 0 ) {
                    request.Annonce(titre, msg, nom, dt, new MyRequest.AnnonceCallback(){

                        @Override
                        public void onSuccess(String message) {

                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Votre annonce à été publié").show();

                           // finish();
                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);

                            t1.getEditText().setText("");
                            t2.getEditText().setText("");
                            t3.getEditText().setText("");



                        }

                        @Override
                        public void inputErrors(Map<String, String> errors) {

                            if (errors.get("Title") != null ){
                                t1.setError(errors.get("Title"));
                            }else{
                                t1.setErrorEnabled(false);
                            }
                            if (errors.get("Msg") != null ){
                                t2.setError(errors.get("Msg"));
                            }else{
                                t2.setErrorEnabled(false);
                            }
                            if (errors.get("NomEntreprise") != null ){
                                t2.setError(errors.get("NomEntreprise"));
                            }else{
                                t2.setErrorEnabled(false);
                            }

                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onError(String message) {

                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);

                        }
                    });

                }else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Veuillez remplir tout les champs").show();

                    leading.setVisibility(View.GONE);
                    b2.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);

                }
            }

        });

        //
        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        t3.getEditText().setText(currentdate);

        return v;
    }

}
