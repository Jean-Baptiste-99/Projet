package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginClient extends AppCompatActivity {

    private Button b1, b2;
    private TextView t1;
    private TextInputLayout til_em, til_pwd;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);

        b1 = findViewById(R.id.buttonconn_c);
        b2 = findViewById(R.id.buttonins_c);
        til_em = findViewById(R.id.t_em_c);
        til_pwd = findViewById(R.id.t_pas_c);


        dialog = new ProgressDialog(this);

        new SweetAlertDialog(LoginClient.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Pour plus de sécurité veuillez vous identifier.").show();

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InscriptionClient.class);
                startActivity(intent);
            }
        });

        t1 = findViewById(R.id.textlink_c);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(LoginClient.this);
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

                        new SweetAlertDialog(LoginClient.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Annulation ...").show();
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://market.paytickettogo.com/vmarket/LoginClient.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.contains("1")) {
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                            til_em.getEditText().setText("");
                            til_pwd.getEditText().setText("");
                            dialog.dismiss();
                        } else {
                            new SweetAlertDialog(LoginClient.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Mot de passe ou pseudo incorrects").show();
                            dialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){

                    new SweetAlertDialog(LoginClient.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Erreur de connexion").show();
                    dialog.dismiss();


                }else if (error instanceof VolleyError){

                    new SweetAlertDialog(LoginClient.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Une erreur s'est produit, veuillez recommencer").show();
                    dialog.dismiss();

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put("Pseudo", til_em.getEditText().getText().toString().trim());
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
