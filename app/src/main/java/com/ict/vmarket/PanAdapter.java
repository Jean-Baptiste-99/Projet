package com.ict.vmarket;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PanAdapter extends RecyclerView.Adapter<PanAdapter.panViewHolder>  {

    private Context mcontext;
    private List<pannier> mproduitlist;
    private static final int REQUEST_CALL = 1;
    private String number = "*145#";
    private String number2 = "*155#";
    RequestOptions option;

    public PanAdapter (Context context, List <pannier> produitlist ){
        mcontext = context;
        mproduitlist = produitlist;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.bags).error(R.drawable.bags);
    }

    @NonNull
    @Override
    public panViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.cart_row, parent, false);

        final panViewHolder viewHolder = new panViewHolder(v);
       /* viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, ProduiDetail.class);

                i.putExtra("LibelleProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_libelle());
                //i.putExtra("Description", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_desc());elle());
                //i.putExtra("CategorieProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_cat());
               // i.putExtra("NomEntreprise", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_nom_ent());
                i.putExtra("PrixNormal", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_prix_n());
                //i.putExtra("PrixPromo", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_prix_p());
                i.putExtra("ImageProduit", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_image());
               /* i.putExtra("DateDebut", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_date_deb());
                i.putExtra("DateFin", mproduitlist.get(viewHolder.getAdapterPosition()).getProduct_date_fin());*/

              /*  mcontext.startActivity(i);
            }
        });*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull panViewHolder holder, int position) {


        holder.t1.setText(mproduitlist.get(position).getProduct_libelle());
        holder.t2.setText(mproduitlist.get(position).getProduct_prix_n());
        holder.t3.setText(mproduitlist.get(position).getQte());
        holder.t4.setText(mproduitlist.get(position).getProduct_id());
        //Picasso.get().load(mproduitlist.get(position).getProduct_image()).fit().centerCrop().into(holder.imageView);
        Glide.with(mcontext).load(mproduitlist.get(position).getProduct_image()).apply(option).into(holder.imageView);

        String id = mproduitlist.get(position).getProduct_id();


    }


    @Override
    public int getItemCount() {
        return mproduitlist.size();
    }


    public class panViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView t1, t2, t3, t4;
        //public LinearLayout view_container;
        public Button b1;
        public ImageButton ib;


        public panViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image_pan_card);
            t1 = itemView.findViewById(R.id.product_name_pan_card);
            t2 = itemView.findViewById(R.id.prix_pan_card);
            t3 = itemView.findViewById(R.id.product_qt_pan_card);
            t4 = itemView.findViewById(R.id.product_id_card);
            //view_container = itemView.findViewById(R.id.container_pop);
            b1 = itemView.findViewById(R.id.btn_reserv_p);
            ib = itemView.findViewById(R.id.pannier_del_cart);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                    alert.setTitle("Confirmation");
                    alert.setMessage("Voulez vous retirer ce produit de votre pannier ?");
                    alert.setIcon(R.drawable.ic_warning_black_24dp);
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            String URL = "http://192.168.56.1:8080/VMarketphp/delete_cart.php";

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    new SweetAlertDialog(mcontext, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Le produit a été retirer du pannier").show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    new SweetAlertDialog(mcontext, SweetAlertDialog.ERROR_TYPE).setTitleText("Erreur").show();

                                }
                            }){

                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String,String> params = new HashMap<>();
                                    String id = t4.getText().toString().trim();
                                    params.put("product_id", id);


                                    return params;
                                }


                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
                            requestQueue.add(stringRequest);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new SweetAlertDialog(mcontext, SweetAlertDialog.WARNING_TYPE).setTitleText("Opération annulée ...").show();
                        }
                    });
                    alert.create().show();

                    }
            });

        }
    }

}
