package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.Objects;

public class ListeAnnonce extends AppCompatActivity {

    String url ="http://market.paytickettogo.com/vmarket/ListeAnnonce.php";
    ProgressDialog pDialog;
    ArrayList  <HashMap<String, String>> listAnn = new ArrayList<>();
    ListView Lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_annonce);

        Lv = (ListView)findViewById(R.id.lv);

        new Webservice().execute(url);

    }

    class Webservice extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute () {
            pDialog = new ProgressDialog(ListeAnnonce.this);
            pDialog.setMessage("connexion au serveur");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result  = new StringBuilder();


            try {
                HttpURLConnection conn;
                URL url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset", "UTF_8");
                conn.setConnectTimeout(1000);
                conn.connect();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String Line;

                if ((Line = reader.readLine()) != null)
                {
                    result.append(Line);
                }
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toString();
        }


        @Override
        protected void onPostExecute (String JSON_Reponse){

            pDialog.dismiss();
            JSONObject jObj = null;
            JSONArray jArr = null;

            try {
                jArr = new JSONArray(JSON_Reponse.toString());

                for (int x=0; x<jArr.length(); x++ ){
                    jObj = jArr.getJSONObject(x);
                   // String sID = jObj.getString("id");
                    String title = jObj.getString("LibelleAnnonce");
                    String desc = jObj.getString("Description");
                    String nom = jObj.getString("NomEntreprise");
                    String dt = jObj.getString("dte");
                    HashMap <String, String> map = new HashMap<>();
                   // map.put("id",sID);
                    map.put("LibelleAnnonce",title);
                    map.put("Description",desc);
                    map.put("NomEntreprise",nom);
                    map.put("dte",dt);

                    listAnn.add(map);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ListAdapter adapter = new SimpleAdapter(
                    ListeAnnonce.this, listAnn,
                    R.layout.itemannonce, new String[]{"LibelleAnnonce", "Description", "NomEntreprise", "dte"},
                    new int[]{R.id.textViewtitle, R.id.textViewann, R.id.textViewnomE, R.id.textViewdt}
            );
            Lv.setAdapter(adapter);

            Lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    SparseBooleanArray positionchecker =Lv.getCheckedItemPositions();
                    int count = Lv.getCount();

                    for (int item = count = 1; item >= 0; item--){

                        if (positionchecker.get(item)){

                        }
                    }
                    return false;
                }
            });

        }
    }


}
