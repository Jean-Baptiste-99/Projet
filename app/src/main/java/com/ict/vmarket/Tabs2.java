package com.ict.vmarket;



import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Tabs2 extends Fragment {

    private RecyclerView Lv;
    private PromoAdapter mpromoAdapter;
    //private List <partenaireitem> mpartnairelist;
    private RequestQueue requestQueue;
    private List<ProduitItem> mproduitlist;
    private ProgressDialog dialog;
    private TextView t;
    private ImageView img;


    public Tabs2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tabs2, container, false);

        Lv = v.findViewById(R.id.RecyclerProd_pop);
        t = v.findViewById(R.id.textViewcloud_pop);
        img = v.findViewById(R.id.imageViewcloud_pop);
        mproduitlist = new ArrayList<>();
        dialog = new ProgressDialog(getContext());

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pdialog);

        // requestQueue = Volley.newRequestQueue(this);
        parseJSON();

        return v;
    }

    private void parseJSON() {
        String url = "http://market.paytickettogo.com/vmarket/Promotion.php";
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
                t.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
                dialog.dismiss();

            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }

    private void stuprecyclerview(List<ProduitItem> mproduitlist) {

        PromoAdapter adapter = new PromoAdapter(getContext(), mproduitlist);
        Lv.setLayoutManager(new LinearLayoutManager(getContext()));

        Lv.setAdapter(adapter);
        dialog.dismiss();

    }

}
