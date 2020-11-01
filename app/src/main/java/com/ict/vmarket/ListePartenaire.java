package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.RequestClientOptions;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListePartenaire extends AppCompatActivity {


    private RecyclerView Lv;
    private partenaireAdapter mpartenaireAdapter;
    //private List <partenaireitem> mpartnairelist;
    private RequestQueue requestQueue;
    private List<partenaireitem> mpartnairelist;
    private ProgressDialog dialog;
    private TextView t;
    private ImageView img;
    partenaireitem mpartenaireitem;
     LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_partenaire);

        Lv = findViewById(R.id.partenairerecyclerview);
        t = findViewById(R.id.textViewcloud_pa);
        img = findViewById(R.id.imageViewcloud_pa);
        linearLayoutManager = new LinearLayoutManager(this);
        Lv.setLayoutManager(linearLayoutManager);
        mpartnairelist = new ArrayList<>();
        mpartenaireAdapter = new partenaireAdapter(this, mpartnairelist);
        Lv.setAdapter(mpartenaireAdapter);
       // requestQueue = Volley.newRequestQueue(this);
        dialog = new ProgressDialog(this);

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pdialog);
        parseJSON();


    }



    private void parseJSON() {
        String url = "http://market.paytickettogo.com/vmarket/ListePartenaire.php";
       JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {

               JSONObject jsonObject = null;

               for (int i = 0; i < response.length(); i++){

                   try {

                       jsonObject = response.getJSONObject(i);
                       String nom = jsonObject.getString("NomEntreprise");
                       String email = jsonObject.getString("Email");
                       String description = jsonObject.getString("Description");
                       String tel = jsonObject.getString("Tel");
                       String pays = jsonObject.getString("Pays");
                       String ville = jsonObject.getString("Ville");
                       String imageEntreprise = jsonObject.getString("ImageEntreprise");
                       String adresse = jsonObject.getString("Adresse");


                       String url = "http://192.168.43.189:8080/VMarketphp/Images/"+imageEntreprise;

                       mpartenaireitem = new partenaireitem(url, nom, email, description, tel, ville, pays, adresse);

                       mpartnairelist.add(mpartenaireitem);
                       mpartenaireAdapter.notifyDataSetChanged();

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }

               dialog.dismiss();

           }
       }/* {
           @Override
           public void onResponse(String response) {

               for (int i = 0; i < jsonArray.length(); i++)
               try {
                   JSONObject jsonObject = new JSONObject(response);
                   String succes = jsonObject.getString("success");
                   JSONArray jsonArray = jsonObject.getJSONArray("data");

                   if (succes.equals("1")){
                       for (int i = 0; i < jsonArray.length(); i++){
                           JSONObject object = jsonArray.getJSONObject(i);


                           String nom = object.getString("NomEntreprise");
                           String email = object.getString("Email");
                           String description = object.getString("Description");
                           String tel = object.getString("Tel");
                           String pays = object.getString("Pays");
                           String ville = object.getString("Ville");
                           String imageEntreprise = object.getString("ImageEntreprise");
                           String adresse = object.getString("Adresse");


                           String url = "http://192.168.56.1:8080/VMarketphp/Images/"+imageEntreprise;

                           mpartenaireitem = new partenaireitem(url, nom, email, description, tel, ville, pays, adresse);

                           mpartnairelist.add(mpartenaireitem);
                           mpartenaireAdapter.notifyDataSetChanged();
                       }
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       }*/ /*{
           @Override
           public void onResponse(JSONArray response) {

               JSONObject jsonObject = null;

               for (int i = 0; i < response.length(); i++){

                   try {

                       jsonObject = response.getJSONObject(i);
                       partenaireitem pt = new partenaireitem();

                       pt.setmName(jsonObject.getString("NomEntreprise"));
                       pt.setmEmail(jsonObject.getString("Email"));
                       pt.setmDesc(jsonObject.getString("Description"));
                       pt.setmTel(jsonObject.getString("Tel"));
                       pt.setmPays(jsonObject.getString("Pays"));
                       pt.setmVille(jsonObject.getString("Ville"));
                       pt.setmImageUrl(jsonObject.getString("ImageEntreprise"));
                       pt.setmAdresse(jsonObject.getString("Adresse"));

                       mpartnairelist.add(pt);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }

               stuprecyclerview(mpartnairelist);

           }

       }*/, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               t.setVisibility(View.VISIBLE);
               img.setVisibility(View.VISIBLE);
               dialog.dismiss();

           }
       });
       requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

   /* private void stuprecyclerview(List<partenaireitem> mpartnairelist) {

        mpartenaireAdapter = new partenaireAdapter(this, mpartnairelist);
        Lv.setLayoutManager(new LinearLayoutManager(this));

        Lv.setAdapter(mpartenaireAdapter);
        dialog.dismiss();

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
                mpartenaireAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}

