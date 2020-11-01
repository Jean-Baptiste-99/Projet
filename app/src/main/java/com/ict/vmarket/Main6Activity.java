package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {

    private RecyclerView Lv, Lv2;
    private ProduitAdapter mproduitAdapter;
    //private List <partenaireitem> mpartnairelist;
    private RequestQueue requestQueue;
    private List<ProduitItem> mproduitlist, mproduitlist2;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        Lv = findViewById(R.id.RecyclerPop_apple_p);
        Lv2 = findViewById(R.id.RecyclerPop_apple_promo);
        mproduitlist = new ArrayList<>();
        mproduitlist2 = new ArrayList<>();
        dialog = new ProgressDialog(Main6Activity.this);

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pdialog);

        // requestQueue = Volley.newRequestQueue(this);
        parseJSON();
        parseJSON2();
    }


    private void parseJSON() {
        String url = "http://market.paytickettogo.com/vmarket/apple.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++){

                    try {

                        jsonObject = response.getJSONObject(i);
                        ProduitItem pt = new ProduitItem();

                        String url = "http://market.paytickettogo.com/vmarket/Images/";

                        pt.setProduct_libelle(jsonObject.getString("LibelleProduit"));
                        pt.setProduct_desc(jsonObject.getString("Description"));
                        pt.setProduct_cat(jsonObject.getString("CategorieProduit"));
                        pt.setProduct_nom_ent(jsonObject.getString("NomEntreprise"));
                        pt.setProduct_prix_n(jsonObject.getString("PrixNormal"));
                        pt.setProduct_prix_p(jsonObject.getString("PrixPromo"));
                        pt.setProduct_image(url+jsonObject.getString("ImageProduit"));

                        mproduitlist.add(pt);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                stuprecyclerview(mproduitlist);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

            }
        });
        requestQueue = Volley.newRequestQueue(Main6Activity.this);
        requestQueue.add(request);


    }

    private void stuprecyclerview(List<ProduitItem> mproduitlist) {

        ProduitAdapter adapter = new ProduitAdapter(Main6Activity.this, mproduitlist);
        Lv.setLayoutManager(new LinearLayoutManager(Main6Activity.this, LinearLayoutManager.HORIZONTAL, false));

        Lv.setAdapter(adapter);
        dialog.dismiss();

    }

    private void parseJSON2() {
        String url = "http://market.paytickettogo.com/vmarket/apple2.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++){

                    try {

                        jsonObject = response.getJSONObject(i);
                        ProduitItem pt = new ProduitItem();

                        String url = "http://market.paytickettogo.com/vmarket/Images/";

                        pt.setProduct_libelle(jsonObject.getString("LibelleProduit"));
                        pt.setProduct_desc(jsonObject.getString("Description"));
                        pt.setProduct_cat(jsonObject.getString("CategorieProduit"));
                        pt.setProduct_nom_ent(jsonObject.getString("NomEntreprise"));
                        pt.setProduct_prix_n(jsonObject.getString("PrixNormal"));
                        pt.setProduct_prix_p(jsonObject.getString("PrixPromo"));
                        pt.setProduct_image(url+jsonObject.getString("ImageProduit"));
                        pt.setProduct_date_deb(jsonObject.getString("DateDebut"));
                        pt.setProduct_date_fin(jsonObject.getString("DateFin"));

                        mproduitlist2.add(pt);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                stuprecyclerview2(mproduitlist2);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

            }
        });
        requestQueue = Volley.newRequestQueue(Main6Activity.this);
        requestQueue.add(request);


    }

    private void stuprecyclerview2(List<ProduitItem> mproduitlist) {

        PromoAdapter adapter = new PromoAdapter(Main6Activity.this, mproduitlist);
        Lv2.setLayoutManager(new LinearLayoutManager(Main6Activity.this, LinearLayoutManager.HORIZONTAL, false));

        Lv2.setAdapter(adapter);
        dialog.dismiss();

    }


}
