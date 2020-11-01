package com.ict.vmarket;



import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {

    private RecyclerView Lv;
    private ProduitAdapter mproduitAdapter;
    //private List <partenaireitem> mpartnairelist;
    private RequestQueue requestQueue;
    private List<ProduitItem> mproduitlist;
    private ProgressDialog dialog;
    private SearchView searchView;
    private TextView t, edt;
    private ImageView img;
    ProduitItem mproduitItem;
    LinearLayoutManager linearLayoutManager;


    public Tab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);

        Lv = v.findViewById(R.id.RecyclerPop);
       // searchView = v.findViewById(R.id.svSearch);
        t = v.findViewById(R.id.textViewcloud);
       // edt = v.findViewById(R.id.edt_s);
        img = v.findViewById(R.id.imageViewcloud);
        linearLayoutManager = new LinearLayoutManager(getContext());
        Lv.setLayoutManager(linearLayoutManager);
        mproduitlist = new ArrayList<>();
        mproduitAdapter = new ProduitAdapter(getContext(), mproduitlist);
        Lv.setAdapter(mproduitAdapter);
        dialog = new ProgressDialog(getContext());

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pdialog);

       /* edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filtered(s.toString());
            }
        });*/

        // requestQueue = Volley.newRequestQueue(this);
        parseJSON();

        return v;
    }

    private void filtered(String text) {
        ArrayList<ProduitItem> filteredList = new ArrayList<>();

        for (ProduitItem item : mproduitlist) {
            if (item.getProduct_libelle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mproduitAdapter.filterList(filteredList);
    }


    private void parseJSON() {
        String url = "http://market.paytickettogo.com/vmarket/ListeProduit.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++){

                    try {

                        jsonObject = response.getJSONObject(i);
                        ProduitItem pt = new ProduitItem();
                        

                        jsonObject = response.getJSONObject(i);
                        String libelleProduit = jsonObject.getString("LibelleProduit");
                        String description = jsonObject.getString("Description");
                        String categorieProduit = jsonObject.getString("CategorieProduit");
                        String nomEntreprise = jsonObject.getString("NomEntreprise");
                        String prixNormal = jsonObject.getString("PrixNormal");
                        String promo = jsonObject.getString("PrixPromo");
                        String image = jsonObject.getString("ImageProduit");
                        String dd = jsonObject.getString("DateDebut");
                        String df = jsonObject.getString("DateFin");


                        String url = "http://market.paytickettogo.com/vmarket/Images/"+image;

                        mproduitItem = new ProduitItem(libelleProduit, categorieProduit, prixNormal, promo, description, url, nomEntreprise, dd, df);

                        mproduitlist.add(mproduitItem);
                        mproduitAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

               // stuprecyclerview(mproduitlist);
                dialog.dismiss();

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

   /* private void stuprecyclerview(List<ProduitItem> mproduitlist) {

        ProduitAdapter adapter = new ProduitAdapter(getContext(), mproduitlist);
        Lv.setLayoutManager(new LinearLayoutManager(getContext()));

        Lv.setAdapter(adapter);
        dialog.dismiss();

    }*/

   /* public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mproduitAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }*/

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mproduitAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
