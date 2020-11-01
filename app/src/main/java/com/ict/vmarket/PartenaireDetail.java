package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PartenaireDetail extends AppCompatActivity {

    private TextView t1, t2, t3, t4, t5, t6, t7, t8;
    private ImageView img;
    private View lt, cat;
    RequestOptions option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partenaire_detail);

        option = new RequestOptions().centerCrop().placeholder(R.drawable.bags).error(R.drawable.bags);

        t1 = findViewById(R.id.tnomE);
        t2 = findViewById(R.id.tad);
        t3 = findViewById(R.id.tdesc);
        t4 = findViewById(R.id.ttel);
        t5 = findViewById(R.id.tpays);
        t6 = findViewById(R.id.tville);
        t7 = findViewById(R.id.temail);
        img = findViewById(R.id.imageViewpar);

        t8 = findViewById(R.id.titin);
        lt = findViewById(R.id.it);
        cat = findViewById(R.id.ent_det);

        lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Vip.class);
                startActivity(intent);
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
                startActivity(intent);
            }
        });

        String name = getIntent().getExtras().getString("NomEntreprise");
        String ad = getIntent().getExtras().getString("Adresse");
        String desc = getIntent().getExtras().getString("Description");
        String tel = getIntent().getExtras().getString("Tel");
        String pays = getIntent().getExtras().getString("Pays");
        String ville = getIntent().getExtras().getString("Ville");
        String email = getIntent().getExtras().getString("Email");
        String image = getIntent().getExtras().getString("ImageEntreprise");


        t1.setText(name);
        t2.setText(ad);
        t3.setText(desc);
        t4.setText(tel);
        t5.setText(pays);
        t6.setText(ville);
        t7.setText(email);
        //Picasso.get().load(image).into(img);
        Glide.with(this).load(image).apply(option).into(img);

       // buynow();

        Intent i = new Intent(PartenaireDetail.this, Tabs1.class);
        String nom = t1.getText().toString().trim();
        i.putExtra("NomEntreprise", nom);

    }

    private void buynow() {

        String url = "http://192.168.43.189:8080/VMarketphp/Partdt.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(PartenaireDetail.this,response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PartenaireDetail.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params = new HashMap<>();

                String nom = t1.getText().toString().trim();

                params.put("Name", nom);

                return params;
            }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(PartenaireDetail.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirm Exit...");
        alert.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alert.setMessage("Etes vous sûr de vouloir quitter la page ?");
        alert.setCancelable(false);
        alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

}
