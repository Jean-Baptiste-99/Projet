package com.ict.vmarket;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ict.vmarket.MyRequest.MyRequest;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class InscriptionClient extends AppCompatActivity {

    private TextInputEditText til_Nom, til_Email, til_Pwd, til_Pwd2;
    private TextInputLayout t_n, t_p, t_p2, t_e, t_t;
    private Button btnEnreg;
    private ProgressBar leading;
    private RequestQueue queue;
    private MyRequest request;
    private CircleImageView circleImageView;
    private Uri mainImageURL = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_client);



        t_n = findViewById(R.id.til_pseudo_c);
        t_t = findViewById(R.id.til_tel_c);
        t_e = findViewById(R.id.til_email_c);
        t_p = findViewById(R.id.til_pass_c);
        t_p2 = findViewById(R.id.til_pass2_c);

        btnEnreg = findViewById(R.id.buttonInsb_c);

        leading = findViewById(R.id.progressBar_insc_cl);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        btnEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leading.setVisibility(v.VISIBLE);
                btnEnreg.setVisibility(v.GONE);

                final String pseudo = t_n.getEditText().getText().toString().trim();
                final String tel = t_t.getEditText().getText().toString().trim();
                final String email = t_e.getEditText().getText().toString().trim();
                final String pwd = t_p.getEditText().getText().toString().trim();
                final String cpwd = t_p2.getEditText().getText().toString().trim();


                if (pseudo.length() > 0  && email.length() > 0 && pwd.length() > 0 && cpwd.length() > 0) {
                    request.Client( pseudo, email, tel, pwd, cpwd, new MyRequest.ClientCallback() {

                        @Override
                        public void onSuccess(String message) {
                            Intent intent = new Intent(getApplicationContext(), LoginClient.class);
                            intent.putExtra("INSCRIPTION", message);
                            new SweetAlertDialog(InscriptionClient.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Votre inscription s'est bien déroulé ...").show();
                            startActivity(intent);

                            t_n.getEditText().setText("");
                            t_t.getEditText().setText("");
                            t_e.getEditText().setText("");
                            t_p.getEditText().setText("");
                            t_p2.getEditText().setText("");

                            leading.setVisibility(v.GONE);
                            btnEnreg.setVisibility(v.VISIBLE);
                            finish();



                        }

                        @Override
                        public void inputErrors(Map<String, String> errors) {
                            leading.setVisibility(v.GONE);
                            btnEnreg.setVisibility(v.VISIBLE);

                            if (errors.get("Pseudo") != null ){
                                t_n.setError(errors.get("Pseudo"));
                            }else{
                                t_n.setErrorEnabled(false);
                            }
                            if (errors.get("Email") != null ){
                                t_e.setError(errors.get("Email"));
                            }else{
                                t_e.setErrorEnabled(false);
                            }
                            if (errors.get("Tel") != null ){
                                t_e.setError(errors.get("Tel"));
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
                            leading.setVisibility(v.GONE);
                            btnEnreg.setVisibility(v.VISIBLE);

                        }
                    });

                }else {
                    new SweetAlertDialog(InscriptionClient.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Veuillez remplir tout les champs").show();
                    leading.setVisibility(v.GONE);
                    btnEnreg.setVisibility(v.VISIBLE);


                }
            }
        });

    }

}
