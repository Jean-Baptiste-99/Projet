package com.ict.vmarket;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class pannierAdapter2 extends BaseAdapter {

    private Context context;
    private ArrayList<pannier2> pannierlist;
    private int layout;
    private DatabaseVmarket mydb;
    private static final int REQUEST_CALL = 1;
    private String number = "*145#";
    private String number2 = "*155#";
    RequestOptions option;

    public pannierAdapter2(Context context, ArrayList<pannier2> pannierlist, int layout) {
        this.context = context;
        this.pannierlist = pannierlist;
        this.layout = layout;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.bags).error(R.drawable.bags);
    }

    @Override
    public int getCount() {
        return pannierlist.size();
    }

    @Override
    public Object getItem(int position) {
        return pannierlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView t1, t2, t3, t4, t5, t6;
        Button b1;
        ProgressBar leading;
        ImageButton i1;
    }

    private void showDialogDelete(final int idpannier ) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Confirmation");
        alert.setMessage("Etes vous sûr de vouloir le supprimer ???");
        alert.setIcon(R.drawable.ic_warning_black_24dp);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                try {

                    mydb.deletePannier(idpannier);
                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Data was delete ...").show();

                }catch (Exception e){

                }

            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();
        if (row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.t1 = (TextView) row.findViewById(R.id.product_name_pan_card7);
            holder.t2 = (TextView) row.findViewById(R.id.prix_pan_card7);
            holder.t4 = (TextView) row.findViewById(R.id.prix_promo_pr_cart2);
            holder.t3 = (TextView) row.findViewById(R.id.product_qt_pan_card2);
            holder.t5 = (TextView) row.findViewById(R.id.prix_id_pr_cart2);
            holder.t6 = (TextView) row.findViewById(R.id.click_ent_cart2);
            holder.b1 = (Button) row.findViewById(R.id.btn_reserv2);
            holder.imageView = (ImageView) row.findViewById(R.id.product_image_pan_card7);
            holder.i1 = (ImageButton) row.findViewById(R.id.pannier_del_row2);
            holder.leading = (ProgressBar) row.findViewById(R.id.progressBar2_p2);
            row.setTag(holder);
        }else{
            holder = (ViewHolder) row.getTag();
        }

        ViewHolder finalHolder = holder;
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = View.inflate(context, R.layout.login_custom, null);
                final TextInputLayout ti1 = (TextInputLayout) mView.findViewById(R.id.t_ps_cl);
                final TextInputLayout ti2 = (TextInputLayout) mView.findViewById(R.id.t_pas_cl);
                Button bi1 = (Button) mView.findViewById(R.id.buttonconn_cl);

                bi1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.189:8080/VMarketphp/LoginClient.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        if (response.contains("1")) {
                                            //ti1.getEditText().setText("");
                                            //ti2.getEditText().setText("");

                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setTitle("Confirmation");
                                    alert.setMessage("Voulez vous effectuer une réservation ?");
                                    alert.setIcon(R.drawable.ic_warning_black_24dp);
                                    alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {

                                            finalHolder.leading.setVisibility(View.VISIBLE);
                                            finalHolder.b1.setVisibility(View.GONE);

                                            String url = "http://192.168.43.189:8080/VMarketphp/ReserverP.php";

                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                        //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Votre réservation est en cours de validation, vous recevrez bientôt une notification ...").show();
                                        finalHolder.leading.setVisibility(View.GONE);
                                        finalHolder.b1.setVisibility(View.VISIBLE);

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                                finalHolder.leading.setVisibility(View.GONE);
                                finalHolder.b1.setVisibility(View.VISIBLE);

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() {
                                HashMap<String,String> params = new HashMap<>();

                                String nom = finalHolder.t1.getText().toString().trim();
                                String prix = finalHolder.t2.getText().toString().trim();
                                String qt = finalHolder.t3.getText().toString().trim();
                                String id = finalHolder.t5.getText().toString().trim();
                                String promo = finalHolder.t4.getText().toString().trim();
                                String img = finalHolder.imageView.toString().trim();
                                String ent = finalHolder.t6.getText().toString().trim();

                                params.put("nom", nom);
                                params.put("prix", prix);
                                params.put("qt", qt);
                                params.put("id", id);
                                params.put("img", img);
                                params.put("promo", promo);
                                params.put("ent", ent);
                                params.put("Email", ti1.getEditText().getText().toString().trim());

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);


                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Votre réservation a été annulé annulée ...").show();
                    }
                });
                alert.create().show();
                                        } else {
                                            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Mot de passe ou pseudo incorrects").show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error instanceof NetworkError){

                                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Erreur de connexion").show();


                                }else if (error instanceof VolleyError){

                                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Une erreur s'est produit, veuillez recommencer").show();

                                }

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map <String, String> params = new HashMap<>();
                                params.put("Pseudo", ti1.getEditText().getText().toString().trim());
                                params.put("Password", ti2.getEditText().getText().toString().trim() );

                                return params;
                            }
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                // Removed this line if you dont need it or Use application/json
                                // params.put("Content-Type", "application/x-www-form-urlencoded");
                                return params;
                            }
                        };
                        // Volley.newRequestQueue(this).add(request);
                        RequestQueue requestQueue= Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            }
        });

        holder.i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Confirmation");
                alert.setMessage("Voulez vous Effectuer cette opération ?");
                alert.setIcon(R.drawable.ic_warning_black_24dp);
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        Cursor c = mydb.getdata("select idpannier from T_pannier");
                        ArrayList<Integer> ls = new ArrayList<Integer>();

                        while (c.moveToNext()){
                            ls.add(c.getInt(0));
                        }
                        showDialogDelete(ls.get(i));

                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText("Opération annulée ...").show();
                    }
                });
                alert.create().show();

            }
        });

        pannier2 p = pannierlist.get(position);

        Glide.with(context).load(p.getProduct_image()).apply(option).into(holder.imageView);
        holder.t1.setText(p.getProduct_libelle());
        holder.t2.setText(p.getProduct_prix_n());
        holder.t4.setText(p.getProduct_prix_p());
        holder.t3.setText(p.getQte());
        holder.t5.setText(p.getP_id());
        holder.t6.setText(p.getEnt());
        //Picasso.get().load(p.getProduct_image()).fit().centerCrop().into(holder.imageView);


        return row;
    }

}
