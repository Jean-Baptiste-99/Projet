package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.ict.vmarket.MyRequest.MyRequest;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Annonce extends AppCompatActivity {

    private TextInputLayout t1, t2, t3;
    private Button b1, b2;
    private RequestQueue queue;
    private MyRequest request;
    private ProgressBar leading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);

        t1 = findViewById(R.id.titre_ann);
        t2 = findViewById(R.id.msg_ann);
        t3 = findViewById(R.id.dt_ann);
       // t4 = findViewById(R.id.nomEt_ann);

        b1 = findViewById(R.id.btnup1);
        b2 = findViewById(R.id.btnall1);

        leading = findViewById(R.id.progressBar_insc_tab21);

        queue = VolleySingleton.getInstance(Annonce.this).getRequestQueue();
        request = new MyRequest(Annonce.this, queue);

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

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Annonce.this);
                View mView = View.inflate(Annonce.this, R.layout.login_custon2, null);
                final TextInputLayout ti1 = (TextInputLayout) mView.findViewById(R.id.t_ps_c3);
                final TextInputLayout ti2 = (TextInputLayout) mView.findViewById(R.id.t_pas_c3);
                Button bi1 = (Button) mView.findViewById(R.id.buttonconn_c2);

                bi1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://market.paytickettogo.com/vmarket/LoginEnt.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (response.contains("1")) {

                leading.setVisibility(View.VISIBLE);
                b2.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);

                final String titre = t1.getEditText().getText().toString().trim();
                final String msg = t2.getEditText().getText().toString().trim();
                final String nom = ti1.getEditText().getText().toString().trim();
                final String dt = t3.getEditText().getText().toString().trim();

                if (titre.length() > 0  && msg.length() > 0 ) {
                    request.Annonce(titre, msg, nom, dt, new MyRequest.AnnonceCallback(){

                        @Override
                        public void onSuccess(String message) {

                            new SweetAlertDialog(Annonce.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Votre annonce à été publié").show();

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

                            Toast.makeText(Annonce.this, message, Toast.LENGTH_SHORT).show();
                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);

                        }
                    });

                }else {
                    new SweetAlertDialog(Annonce.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Veuillez remplir tout les champs").show();

                    leading.setVisibility(View.GONE);
                    b2.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);

                }

            } else {
                new SweetAlertDialog(Annonce.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Mot de passe ou l'e-mail incorrects").show();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            if (error instanceof NetworkError){

                new SweetAlertDialog(Annonce.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Erreur de connexion").show();


            }else if (error instanceof VolleyError){

                new SweetAlertDialog(Annonce.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Une erreur s'est produit, veuillez recommencer").show();

            }

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map <String, String> params = new HashMap<>();
            params.put("Nom", ti1.getEditText().getText().toString().trim());
            params.put("Password", ti2.getEditText().getText().toString().trim() );

            return params;
        }
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String,String> params = new HashMap<String, String>();
            // Removed this line if you dont need it or Use application/json
            // params.put("Content-Type", "application/x-www-form-urlencoded");
            return params;
        }
    };
    // Volley.newRequestQueue(this).add(request);
    RequestQueue requestQueue= Volley.newRequestQueue(Annonce.this);
                        requestQueue.add(stringRequest);

}
                });

                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();
            }

        });

        //
        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        t3.getEditText().setText(currentdate);

    }
}
