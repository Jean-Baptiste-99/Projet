package com.ict.vmarket;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class ProduiDetail extends AppCompatActivity {

    private TextView t1, t2, t3, t4, t5, t6, t7;
    private ImageView img;
    private Button b1, b2;
    private ImageButton i1, i2;
    private int clicks = 0;
    private ProgressBar leading;
    private DatabaseVmarket mydb;
    private static final int REQUEST_CALL = 1;
    private String number = "*145*1*1";
    private String number2 = "*155";
    RequestOptions option;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produi_detail);

        mydb = new DatabaseVmarket(this);
        leading = findViewById(R.id.progressBar_insc_prod);


        option = new RequestOptions().centerCrop().placeholder(R.drawable.bags).error(R.drawable.bags);

        t1 = findViewById(R.id.click_ent);
        t2 = findViewById(R.id.detail_pname);
        t3 = findViewById(R.id.detail_prix);
        t4 = findViewById(R.id.detail_desc);
        t5 = findViewById(R.id.detail_cat);
        img = findViewById(R.id.image_detail);

        t6 = findViewById(R.id.detail_quantite_p1);
        t7 = findViewById(R.id.mt);
        i1 = findViewById(R.id.plus_p1);
        i2 = findViewById(R.id.moin_p1);

        b1 = findViewById(R.id.add_cart);
        b2 = findViewById(R.id.buy);

        String nameEt = getIntent().getExtras().getString("NomEntreprise");
        String libelle_p = getIntent().getExtras().getString("LibelleProduit");
        String desc = getIntent().getExtras().getString("Description");
        String prix = getIntent().getExtras().getString("PrixNormal");
        String cat = getIntent().getExtras().getString("CategorieProduit");
        String image = getIntent().getExtras().getString("ImageProduit");


        t1.setText(nameEt);
        t2.setText(libelle_p);
        t4.setText(desc);
        t3.setText(prix);
        t5.setText("Catégorie  :  " + cat);
        //Picasso.get().load(image).into(img);
        Glide.with(this).load(image).apply(option).into(img);

        int mnt = Integer.parseInt(prix);
        int qt = Integer.parseInt(t6.getText().toString().trim());
        final int sommes = mnt * qt;

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProduiDetail.this, PromoDetail.class);
                String nom = t1.getText().toString().trim();
                i.putExtra("nom", nom);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProduiDetail.this);
                View mView = getLayoutInflater().inflate(R.layout.login_custom, null);
                final TextInputLayout ti1 = (TextInputLayout) mView.findViewById(R.id.t_ps_cl);
                final TextInputLayout ti2 = (TextInputLayout) mView.findViewById(R.id.t_pas_cl);
                final ProgressBar pb = (ProgressBar) mView.findViewById(R.id.progressB);
                Button bi1 = (Button) mView.findViewById(R.id.buttonconn_cl);

                bi1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pb.setVisibility(View.VISIBLE);
                        bi1.setVisibility(View.GONE);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://market.paytickettogo.com/vmarket/LoginClient.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        pb.setVisibility(View.GONE);
                                        bi1.setVisibility(View.VISIBLE);
                                        if (response.contains("1")) {
                                            makephonecall();
                                           // ti1.getEditText().setText("");
                                           // ti2.getEditText().setText("");

                                            String url = "http://market.paytickettogo.com/vmarket/Achat.php";

                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            pb.setVisibility(View.GONE);
                                                            bi1.setVisibility(View.VISIBLE);

                                                            new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Votre Achat est en cours de validation, vous recevrez bientôt une notification ...").show();

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    pb.setVisibility(View.GONE);
                                                    bi1.setVisibility(View.VISIBLE);

                                                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();


                                                }
                                            }){

                                                @Override
                                                protected Map<String, String> getParams() {
                                                    HashMap<String,String> params = new HashMap<>();

                                                    String nom = t2.getText().toString().trim();
                                                    String prix = t7.getText().toString().trim();
                                                    String qt = t6.getText().toString().trim();
                                                    String img = image.toString().trim();
                                                    String ent = t1.getText().toString().trim();

                                                    params.put("nom", nom);
                                                    params.put("prix", prix);
                                                    params.put("qt", qt);
                                                    params.put("img", img);
                                                    params.put("ent", ent);
                                                    params.put("Email", ti1.getEditText().getText().toString().trim());

                                                    return params;
                                                }
                                            };

                                            RequestQueue requestQueue = Volley.newRequestQueue(ProduiDetail.this);
                                            requestQueue.add(stringRequest);
                                        } else {
                                            pb.setVisibility(View.GONE);
                                            bi1.setVisibility(View.VISIBLE);
                                            new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Mot de passe ou pseudo incorrects").show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pb.setVisibility(View.GONE);
                                bi1.setVisibility(View.VISIBLE);

                                if (error instanceof NetworkError){
                                    pb.setVisibility(View.GONE);
                                    bi1.setVisibility(View.VISIBLE);

                                    new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Erreur de connexion").show();


                                }else if (error instanceof VolleyError){

                                    pb.setVisibility(View.GONE);
                                    bi1.setVisibility(View.VISIBLE);
                                    new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Une erreur s'est produit, veuillez recommencer").show();

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
                        RequestQueue requestQueue= Volley.newRequestQueue(ProduiDetail.this);
                        requestQueue.add(stringRequest);

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });


        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clicks++;
                t6.setText("" + clicks);
                Integer q= Integer.valueOf(t6.getText().toString().trim());
                t7.setText(""+mnt * q);

            }
        });

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clicks <= 0) clicks = 0;
                else clicks--;

                t6.setText("" + clicks);
                Integer q= Integer.valueOf(t6.getText().toString().trim());
                t7.setText(""+mnt * q);


            }
        });


        b1.setOnClickListener(new View.OnClickListener() {

           /* String prix = t7.getText().toString().trim();
            String libelle = t2.getText().toString().trim();
            String desc = t4.getText().toString().trim();
            String non = t1.getText().toString().trim();*/

            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProduiDetail.this);
                View mView = View.inflate(ProduiDetail.this, R.layout.login_custom, null);
                final TextInputLayout ti1 = (TextInputLayout) mView.findViewById(R.id.t_ps_cl);
                final TextInputLayout ti2 = (TextInputLayout) mView.findViewById(R.id.t_pas_cl);
                final ProgressBar pb = (ProgressBar) mView.findViewById(R.id.progressB);
                Button bi1 = (Button) mView.findViewById(R.id.buttonconn_cl);

                bi1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://market.paytickettogo.com/vmarket/LoginClient.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pb.setVisibility(View.GONE);
                                        bi1.setVisibility(View.VISIBLE);


                                        if (response.contains("1")) {
                                            //ti1.getEditText().setText("");
                                            // ti2.getEditText().setText("");


                                            AlertDialog.Builder alert = new AlertDialog.Builder(ProduiDetail.this);
                                            alert.setTitle("Confirmation");
                                            alert.setMessage("Voulez vous effectuer une réservation ?");
                                            alert.setIcon(R.drawable.ic_warning_black_24dp);
                                            alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int i) {

                                                    String url = "http://market.paytickettogo.com/vmarket/Reserver.php";

                                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    pb.setVisibility(View.GONE);
                                                                    bi1.setVisibility(View.VISIBLE);

                                                                    //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                                                                    new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Votre réservation est en cours de validation, vous recevrez bientôt une notification ...").show();
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            pb.setVisibility(View.GONE);
                                                            bi1.setVisibility(View.VISIBLE);

                                                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                                                        }
                                                    }){

                                                        @Override
                                                        protected Map<String, String> getParams() {
                                                            HashMap<String,String> params = new HashMap<>();

                                                            String nom = t2.getText().toString().trim();
                                                            String prix = t7.getText().toString().trim();
                                                            String qt = t6.getText().toString().trim();
                                                            String img = image.toString().trim();
                                                            String ent = t1.getText().toString().trim();


                                                            params.put("nom", nom);
                                                            params.put("prix", prix);
                                                            params.put("qt", qt);
                                                            params.put("img", img);
                                                            params.put("ent", ent);
                                                            params.put("Email", ti1.getEditText().getText().toString().trim());

                                                            return params;
                                                        }
                                                    };

                                                    RequestQueue requestQueue = Volley.newRequestQueue(ProduiDetail.this);
                                                    requestQueue.add(stringRequest);
                                                }
                                            }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    pb.setVisibility(View.GONE);
                                                    bi1.setVisibility(View.VISIBLE);
                                                    new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Votre réservation a été annulé annulée ...").show();
                                                }
                                            });
                                            alert.create().show();

                                        } else {
                                            pb.setVisibility(View.GONE);
                                            bi1.setVisibility(View.VISIBLE);
                                            new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Mot de passe ou pseudo incorrects").show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error instanceof NetworkError){
                                    pb.setVisibility(View.GONE);
                                    bi1.setVisibility(View.VISIBLE);

                                    new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Erreur de connexion").show();


                                }else if (error instanceof VolleyError){
                                    pb.setVisibility(View.GONE);
                                    bi1.setVisibility(View.VISIBLE);

                                    new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Une erreur s'est produit, veuillez recommencer").show();

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
                        RequestQueue requestQueue= Volley.newRequestQueue(ProduiDetail.this);
                        requestQueue.add(stringRequest);

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

               /* leading.setVisibility(View.VISIBLE);
                b2.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);

                AlertDialog.Builder alert = new AlertDialog.Builder(ProduiDetail.this);
                alert.setTitle("Confirmation");
                alert.setMessage("Voulez vous ajouter ce produit au pannier ?");
                alert.setIcon(R.drawable.ic_warning_black_24dp);
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        boolean isInserted = mydb.Insertpannier(
                                image.toString().trim(),
                                t2.getText().toString().trim(),
                                t1.getText().toString().trim(),
                                Integer.parseInt(t7.getText().toString().trim()),
                                Integer.parseInt(t6.getText().toString().trim()));

                        if (isInserted == true){
                            // Intent serviceIntent = new Intent(ProduiDetail.this, ExampleService.class);
                            // serviceIntent.putExtra("inputExtra", libelle);

                            // ContextCompat.startForegroundService(ProduiDetail.this, serviceIntent);
                            new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Le produit a été ajouté à votre pannier ...").show();

                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);
                        }else {
                            new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Erreur").show();
                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);
                        }


                    }
                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Opération annulée ...").show();
                        leading.setVisibility(View.GONE);
                        b2.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.VISIBLE);
                    }
                });
                alert.create().show();*/


            }
        });
    }


    private void makephonecall(){

        String num = number.toString();
        String num2 = number2.toString();
        String prix = t7.getText().toString().trim();
        String qt = t6.getText().toString().trim();
        String telph = "92132146";
        String conf = "1";

            CharSequence[] items = {"T-Money", "Flooz"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ProduiDetail.this);
            builder.setTitle("Choisissez votre moyen de payement");
            builder.setIcon(R.drawable.ic_verified_user_black_24dp);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    if (i == 0){

                        if (ContextCompat.checkSelfPermission(ProduiDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                            ActivityCompat.requestPermissions(ProduiDetail.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }else {
                            String dial = "tel:" +number+ Uri.encode("*")+prix+ Uri.encode("*")+telph+Uri.encode("#");
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                            new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Votre Achat est en cours de validation, vous recevrez bientôt une notification ...").show();
                        }

                    }

                    if (i == 1){

                        if (ContextCompat.checkSelfPermission(ProduiDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                            ActivityCompat.requestPermissions(ProduiDetail.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }else {
                            String dial = "tel:"+ number2+ Uri.encode("#");
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                        }

                    }

                }
            });
            builder.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makephonecall();
            }else {
                new SweetAlertDialog(ProduiDetail.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Access refuser").show();
            }
        }
    }

}
