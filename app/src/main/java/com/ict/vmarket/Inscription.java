package com.ict.vmarket;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ict.vmarket.MyRequest.MyRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class Inscription extends AppCompatActivity {

    private TextInputEditText til_Nom, til_Email, til_Pwd, til_Pwd2;
    private TextInputLayout t_n, t_p, t_p2, t_e, t_tel, t_pay, t_vil, t_ad, t_desc;
    private Button btnEnreg;
    private ProgressBar leading;
    private RequestQueue queue;
    private MyRequest request;
    private CircleImageView circleImageView;
    private Uri mainImageURL = null;
    private ImageView imageView;
    private Button b3;
    private final int CODE_GALLERY_REQUEST = 999;
    private Bitmap bitmap;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        leading = findViewById(R.id.progressBar_insc);
        //circleImageView = findViewById(R.id.circleimageviewIns);
        til_Nom = findViewById(R.id.editTextNom);
        til_Email = findViewById(R.id.editTextEmail);
        til_Pwd = findViewById(R.id.editTextpass);
        til_Pwd2 = findViewById(R.id.edtConfpass);
        btnEnreg = findViewById(R.id.buttonInsb);
        t_n = findViewById(R.id.til_nom);
        t_e = findViewById(R.id.til_email);
        t_p = findViewById(R.id.til_pass);
        t_p2 = findViewById(R.id.til_pass2);
        t_ad = findViewById(R.id.til_Adresse_b);
        t_vil = findViewById(R.id.til_Ville_b);
        t_pay = findViewById(R.id.til_Pays_b);
        t_tel = findViewById(R.id.til_Tel_b);
        t_desc = findViewById(R.id.til_desc_b);
        b3 = findViewById(R.id.button3_upIns);
        imageView = findViewById(R.id.UploadimageIns);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);


        btnEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnEnreg.setVisibility(v.GONE);
                leading.setVisibility(v.VISIBLE);



                final String name = t_n.getEditText().getText().toString().trim();
                final String tel = t_tel.getEditText().getText().toString().trim();
                final String email = t_e.getEditText().getText().toString().trim();
                final String pwd = t_p.getEditText().getText().toString().trim();
                final String cpwd = t_p2.getEditText().getText().toString().trim();
                final String ville = t_vil.getEditText().getText().toString().trim();
                final String adr = t_ad.getEditText().getText().toString().trim();
                final String pay = t_pay.getEditText().getText().toString().trim();
                final String desc = t_desc.getEditText().getText().toString().trim();
               // final String img = circleImageView.toString().trim();
                final String imageData = imageToString(bitmap);

                if (name.length() > 0  && email.length() > 0 && tel.length() > 0 && ville.length() > 0 && pay.length() > 0  && adr.length() > 0 && desc.length() > 0 && pwd.length() > 0 && cpwd.length() > 0) {
                    request.Inscription(imageData, name, email, pwd, cpwd, desc, tel, ville, pay, adr, new MyRequest.InscriptionCallback() {

                        @Override
                        public void onSuccess(String message) {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.putExtra("INSCRIPTION", message);
                             new SweetAlertDialog(Inscription.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Votre compte est en cours de validation ...").show();
                            startActivity(intent);
                            btnEnreg.setVisibility(v.VISIBLE);
                            leading.setVisibility(v.GONE);
                            finish();



                        }

                        @Override
                        public void inputErrors(Map<String, String> errors) {
                            btnEnreg.setVisibility(v.VISIBLE);
                            leading.setVisibility(v.GONE);

                            if (errors.get("NomEntreprise") != null ){
                                t_n.setError(errors.get("NomEntreprise"));
                            }else{
                                t_n.setErrorEnabled(false);
                            }
                            if (errors.get("Email") != null ){
                                t_e.setError(errors.get("Email"));
                            }else{
                                t_e.setErrorEnabled(false);
                            }
                            if (errors.get("Password") != null ){
                                t_p.setError(errors.get("Password"));
                            }else{
                                t_p.setErrorEnabled(false);
                            }

                        }

                        @Override
                        public void onError(String message) {

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            btnEnreg.setVisibility(v.VISIBLE);
                            leading.setVisibility(v.GONE);

                        }
                    });

                }else {
                    new SweetAlertDialog(Inscription.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Veuillez remplir tout les champs").show();
                    btnEnreg.setVisibility(v.VISIBLE);
                    leading.setVisibility(v.GONE);


                }
            }
        });


       /* circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(Inscription.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        new SweetAlertDialog(Inscription.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Vous n'avez pas la permission... ").show();

                        ActivityCompat.requestPermissions(Inscription.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    }else {

                        CropImage.activity()
                                .setGuidelines ( CropImageView.Guidelines.ON )
                                .setAspectRatio(1,1)
                                .start (Inscription.this);
                    }
                }
            }
        });*/

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(Inscription.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST);
            }
        });
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode ==  CropImage . CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {
            CropImage.ActivityResult result =  CropImage.getActivityResult (data);
            if (resultCode ==  RESULT_OK ) {
                mainImageURL = result.getUri();
                circleImageView.setImageURI(mainImageURL);
            } else  if (resultCode ==  CropImage . CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE ) {
                Exception error = result.getError();
            }
        }
    }*/
   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

       if (requestCode == CODE_GALLERY_REQUEST){
           if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
               Intent intent = new Intent(Intent.ACTION_PICK);
               intent.setType("image/*");
               startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
           }else {
               new SweetAlertDialog(Inscription.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Vous n'avez pas la permission... ").show();
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
