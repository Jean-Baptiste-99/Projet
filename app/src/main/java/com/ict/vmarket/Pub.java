package com.ict.vmarket;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.ict.vmarket.MyRequest.MyRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class Pub extends AppCompatActivity {

    private Spinner spinner = null;
    private CircleImageView circleImageView;
    private Uri mainImageURI = null;
    private TextInputLayout til1, til2, til4;
    private Button b1, b2, b3;
    private ImageView imageView;
    private final int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;
    String encodedImage;

    private DatabaseVmarket mydb;

    private int nY, nM, nD, sY, sM, sD;
    private DatePickerDialog.OnDateSetListener nDateSetListener, nDateSetListener2;
    static final int DATE_A = 0;

    private RequestQueue queue;
    private MyRequest request;
    private ProgressBar leading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        til1 = findViewById(R.id.til_nom_p);
        til2 = findViewById(R.id.til_prix_p);
        til4 = findViewById(R.id.til_desc_p);
       // til5 = findViewById(R.id.til_boutiq_p);

        b1 = findViewById(R.id.btnpubprod_p);
        b2 = findViewById(R.id.btnanullprod_p);
        b3 = findViewById(R.id.button3_up);
        imageView = findViewById(R.id.Uploadimage);

        leading = findViewById(R.id.progressBar_insc_tab11);

        spinner = findViewById(R.id.spinnert_p);
        // circleImageView = v.findViewById(R.id.circleimageviewTab11_p);

        final ArrayList<String> sp1 = new ArrayList<>();
        sp1.add("Apple");
        sp1.add("Nike");
        sp1.add("Accessoir");
        sp1.add("Xiaomi");
        sp1.add("Samsung");
        sp1.add("Sony");
        sp1.add("Autres");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Pub.this, android.R.layout.simple_spinner_item, sp1);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        queue = VolleySingleton.getInstance(Pub.this).getRequestQueue();
        request = new MyRequest(Pub.this, queue);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til1.getEditText().setText("");
                til2.getEditText().setText("");
                til4.getEditText().setText("");

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Pub.this);
                View mView = View.inflate(Pub.this, R.layout.login_custon2, null);
                final TextInputLayout ti1 = (TextInputLayout) mView.findViewById(R.id.t_ps_c3);
                final TextInputLayout ti2 = (TextInputLayout) mView.findViewById(R.id.t_pas_c3);
                Button bi1 = (Button) mView.findViewById(R.id.buttonconn_c2);

                bi1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://market.paytickettogo.com/vmarket/LoginEnt.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (response.contains("1")) {

                                            leading.setVisibility(View.VISIBLE);
                                            b2.setVisibility(View.GONE);
                                            b1.setVisibility(View.GONE);

                                            final String nom = til1.getEditText().getText().toString().trim();
                                            final String prix = til2.getEditText().getText().toString().trim();
                                            final String desc = til4.getEditText().getText().toString().trim();
                                            final String btq = ti1.getEditText().getText().toString().trim();
                                            //final String img = circleImageView.toString().trim();
                                            final String cat = spinner.getSelectedItem().toString();
                                            final String imageData = imageToString(bitmap);

                                            if (nom.length() > 0 &&  til2.getEditText().getText().toString().trim().length() > 0 && desc.length() > 0 && btq.length() > 0 ) {
                                                request.Publication(nom, prix, imageData, nom, btq, cat, new MyRequest.PublicationCallback(){

                                                    @Override
                                                    public void onSuccess(String message) {

                                                        new SweetAlertDialog(Pub.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Votre produit à été publié").show();

                                                        // finish();
                                                        leading.setVisibility(View.GONE);
                                                        b2.setVisibility(View.VISIBLE);
                                                        b1.setVisibility(View.VISIBLE);

                                                        til1.getEditText().setText("");
                                                        til2.getEditText().setText("");
                                                        til4.getEditText().setText("");

                                                    }

                                                    @Override
                                                    public void inputErrors(Map<String, String> errors) {

                                                        if (errors.get("LibelleProduit") != null ){
                                                            til1.setError(errors.get("LibelleProduit"));
                                                        }else{
                                                            til1.setErrorEnabled(false);
                                                        }
                                                        if (errors.get("PrixNormal") != null ){
                                                            til2.setError(errors.get("PrixNormal"));
                                                        }else{
                                                            til2.setErrorEnabled(false);
                                                        }
                                                        if (errors.get("Description") != null ){
                                                            til4.setError(errors.get("Description"));
                                                        }else{
                                                            til4.setErrorEnabled(false);
                                                        }

                                                        leading.setVisibility(View.GONE);
                                                        b2.setVisibility(View.VISIBLE);
                                                        b1.setVisibility(View.VISIBLE);

                                                    }

                                                    @Override
                                                    public void onError(String message) {

                                                        Toast.makeText(Pub.this, message, Toast.LENGTH_SHORT).show();
                                                        leading.setVisibility(View.GONE);
                                                        b2.setVisibility(View.VISIBLE);
                                                        b1.setVisibility(View.VISIBLE);

                                                    }
                                                });

                                            }else {
                                                new SweetAlertDialog(Pub.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Veuillez remplir tout les champs").show();

                                                leading.setVisibility(View.GONE);
                                                b2.setVisibility(View.VISIBLE);
                                                b1.setVisibility(View.VISIBLE);

                                            }


                                        } else {
                                            new SweetAlertDialog(Pub.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Mot de passe ou l'e-mail incorrects").show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error instanceof NetworkError){

                                    new SweetAlertDialog(Pub.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Erreur de connexion").show();


                                }else if (error instanceof VolleyError){

                                    new SweetAlertDialog(Pub.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Une erreur s'est produit, veuillez recommencer").show();

                                }

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map <String, String> params = new HashMap<>();
                                params.put("Nom", ti1.getEditText().getText().toString().trim());
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
                        RequestQueue requestQueue= Volley.newRequestQueue(Pub.this);
                        requestQueue.add(stringRequest);

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }

        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(Pub.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST);
            }
        });



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else {
                new SweetAlertDialog(Pub.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Vous n'avez pas la permission... ").show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filepath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return  encodedImage;
    }
}
