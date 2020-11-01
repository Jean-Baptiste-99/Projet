package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ProduitAchat extends AppCompatActivity {

    private TextView t1, t2, t3, t4, t5;
    private ImageView img;
    private Button b1;
    private ImageButton i1, i2;
    private int clicks = 0;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_achat);

        t1 = findViewById(R.id.detail_quantite_p);

        b1 = findViewById(R.id.add_cart_p);

        i1 = findViewById(R.id.plus_p);
        i2 = findViewById(R.id.moin_p);
        dialog = new ProgressDialog(this);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                t1.setText(""+clicks);

            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks--;
                t1.setText(""+clicks);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.pdialog);
                buynow();
            }
        });

    }

    private void buynow() {

        String url = "https://paygateglobal.com/api/v1/pay";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(ProduitAchat.this,response, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(ProduitAchat.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> params = new HashMap<>();

                    params.put("Nom", "");
                    params.put("auth_token", " bd3625e9-8b7c-41e0-9477-c48786d06e06");
                    params.put("phone_number", "");
                    params.put("amount", "");
                    params.put("identifier", "");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ProduitAchat.this);
            requestQueue.add(stringRequest);

    }

}
