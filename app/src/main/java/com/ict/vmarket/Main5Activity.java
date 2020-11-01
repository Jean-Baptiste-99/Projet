package com.ict.vmarket;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Main5Activity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem tab1, tab2;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private DatabaseVmarket mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        tabLayout = findViewById(R.id.tab_5);
        tab1 = findViewById(R.id.t_a5);
        tab2 = findViewById(R.id.t_p5);
        viewPager = findViewById(R.id.vp_5);

        pagerAdapter = new PageAdapter4(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                }else if (tab.getPosition() == 1){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.delete_all){
            AlertDialog.Builder alert = new AlertDialog.Builder(Main5Activity.this);
            alert.setTitle("Confirmation");
            alert.setMessage("Voulez vous vider votre pannier ?");
            alert.setIcon(R.drawable.ic_warning_black_24dp);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    mydb.deleteAll();
                   /* String URL = "http://192.168.56.1:8080/VMarketphp/delete_all.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            new SweetAlertDialog(Main5Activity.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Votre pannier a été vidé").show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            new SweetAlertDialog(Main5Activity.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Erreur").show();

                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String,String> params = new HashMap<>();

                            return params;
                        }


                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Main5Activity.this);
                    requestQueue.add(stringRequest);*/

                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new SweetAlertDialog(Main5Activity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Opération annulée ...").show();
                }
            });
            alert.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

}
