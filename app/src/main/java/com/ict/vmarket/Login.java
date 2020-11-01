package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {

    private Button b1, b2;
    private TextView t1;
    private TextInputLayout til_em, til_pwd;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1 = findViewById(R.id.buttonconn);
        b2 = findViewById(R.id.buttonins);
        til_em = findViewById(R.id.t_em);
        til_pwd = findViewById(R.id.t_pas);

        dialog = new ProgressDialog(this);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intent);
            }
        });

        t1 = findViewById(R.id.textlink);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                alert.setTitle("Confirmation");
                alert.setMessage("Voulez vous modifier votre mot de passe ?");
                alert.setIcon(R.drawable.ic_warning_black_24dp);
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getApplicationContext(), Reset.class);
                        startActivity(intent);


                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Annulation ...").show();
                    }
                });
                alert.create().show();


            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.pdialog);

                loginConn();

            }
        });

    }


    public void loginConn(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://market.paytickettogo.com/vmarket/LoginEnt.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("1")) {

                            //startActivity(new Intent(getApplicationContext(), Main3Activity.class));
                            CharSequence[] items = {"Un produit en promotion", "Un produit en ordinaie", "Une Annonce ou un service"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setTitle("Que voulez-vous publier ?");
                            builder.setIcon(R.drawable.ic_verified_user_black_24dp);
                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                    if (i == 0){

                                        startActivity(new Intent(getApplicationContext(), Promotion.class));

                                    }

                                    if (i == 1){

                                        startActivity(new Intent(getApplicationContext(),Pub.class));

                                    }

                                    if (i == 2){

                                        startActivity(new Intent(getApplicationContext(), Annonce.class));

                                    }

                                }
                            });
                            builder.show();

                            til_em.getEditText().setText("");
                            til_pwd.getEditText().setText("");
                            dialog.dismiss();
                        } else {
                            new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Mot de passe ou l'e-mail incorrects").show();
                            dialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){

                    new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Erreur de connexion").show();

                    dialog.dismiss();

                }else if (error instanceof VolleyError){

                    new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Une erreur s'est produit, veuillez recommencer").show();

                    dialog.dismiss();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put("Email", til_em.getEditText().getText().toString().trim());
                params.put("Password", til_pwd.getEditText().getText().toString().trim() );

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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
