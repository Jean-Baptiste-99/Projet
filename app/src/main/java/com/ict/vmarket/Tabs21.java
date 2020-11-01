package com.ict.vmarket;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tabs21 extends Fragment {

    private ListView lv, lv2;
    public static DatabaseVmarket mydb;
    private pannierAdapter2 adapter = null;
    private ArrayList<pannier2> list;
    private TextView textView;
    private ImageView imageView;
    private static final int REQUEST_CALL = 1;
    private String number = "*145#";
    private String number2 = "*445#";

    /*private RecyclerView Lv;
    private PanAdapter2 mproduitAdapter;
    private RequestQueue requestQueue;
    private List<pannier2> mproduitlist;
    private ProgressDialog dialog;
    private SearchView searchView;*/


    public Tabs21() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tabs21, container, false);

       /* Lv = v.findViewById(R.id.Recyclertabs21);
        mproduitlist = new ArrayList<>();
        dialog = new ProgressDialog(getContext());

        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pdialog);*/

        // requestQueue = Volley.newRequestQueue(this);
        //parseJSON();

        lv = v.findViewById(R.id.list_pannier7);
       // lv2 = v.findViewById(R.id.list_pannier17);
        mydb = new DatabaseVmarket(getContext());
        list = new ArrayList<>();
        adapter = new pannierAdapter2(getContext(), list, R.layout.cart_row2);
        lv.setAdapter(adapter);

        //imageView = v.findViewById(R.id.imageView3_7);

       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Confirmation");
                alert.setMessage("Voulez vous vider cet pannier ?");
                alert.setIcon(R.drawable.ic_warning_black_24dp);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Opération annulée ...").show();
                    }
                });
                alert.create().show();

            }
        });*/
        list1();
       // list2();

            return v;

    }

   /* private void parseJSON() {
        String url = "http://192.168.56.1:8080/VMarketphp/ListePannier2.php";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++){

                    try {

                        jsonObject = response.getJSONObject(i);
                        pannier2 pt = new pannier2();

                        pt.setP_id(jsonObject.getString("id"));
                        pt.setProduct_libelle(jsonObject.getString("libelle"));
                        pt.setQte(jsonObject.getString("qte"));
                        pt.setProduct_prix_n(jsonObject.getString("prix"));
                        pt.setProduct_image(jsonObject.getString("image"));
                        pt.setProduct_prix_p(jsonObject.getString("PrixPromo"));

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
                // t.setVisibility(View.VISIBLE);
                //img.setVisibility(View.VISIBLE);
                dialog.dismiss();

            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }

    private void stuprecyclerview(List<pannier2> mproduitlist) {

        PanAdapter2 adapter = new PanAdapter2(getContext(), mproduitlist);
        Lv.setLayoutManager(new LinearLayoutManager(getContext()));

        Lv.setAdapter(adapter);
        dialog.dismiss();

    }*/

    public void list1(){
        /*Cursor res2 = mydb.pannier2();
        ArrayList<String> Ls = new ArrayList<>();*/

        Cursor res = mydb.viewpannier2();
        list.clear();

        if (res.getCount() == 0){
           // new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Base de donnée vide ...").show();

        }else {

            while (res.moveToNext()) {
                String id = res.getString(0);
                String img = res.getString(1);
                String libelle = res.getString(2);
                String ent = res.getString(3);
                String prix = res.getString(4);
                String promo = res.getString(5);
                String qte = res.getString(6);

                list.add(new pannier2(ent, img, libelle, prix, promo, qte, id));

            }
            adapter.notifyDataSetChanged();

            /*while (res2.moveToNext()) {

                Ls.add(res2.getString(0));

                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Ls);
                lv2.setAdapter(listAdapter);
            }*/

        }

    }

   /* public void list2(){
        Cursor res2 = mydb.pannier2();
        ArrayList<String> Ls = new ArrayList<>();

        if (res2.getCount() == 0){
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Base de donnée vide ...").show();

        }else {

            while (res2.moveToNext()) {

                Ls.add(res2.getString(0));

                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Ls);
                lv2.setAdapter(listAdapter);
            }
        }

    }*/

}
