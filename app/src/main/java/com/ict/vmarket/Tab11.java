package com.ict.vmarket;



import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab11 extends Fragment {


    public Tab11() {
        // Required empty public constructor
    }


    private Spinner spinner = null;
    private CircleImageView circleImageView;
    private Uri mainImageURI = null;
    private TextInputLayout til1, til2, til4, til5;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tab11, container, false);

        til1 = v.findViewById(R.id.til_nom_p);
        til2 = v.findViewById(R.id.til_prix_p);
        til4 = v.findViewById(R.id.til_desc_p);
        til5 = v.findViewById(R.id.til_boutiq_p);

        b1 = v.findViewById(R.id.btnpubprod_p);
        b2 = v.findViewById(R.id.btnanullprod_p);
        b3 = v.findViewById(R.id.button3_up);
        imageView = v.findViewById(R.id.Uploadimage);

        leading = v.findViewById(R.id.progressBar_insc_tab11);

        spinner = v.findViewById(R.id.spinnert_p);
       // circleImageView = v.findViewById(R.id.circleimageviewTab11_p);

        final ArrayList<String> sp1 = new ArrayList<>();
        sp1.add("Apple");
        sp1.add("Nike");
        sp1.add("Accessoir");
        sp1.add("Xiaomi");
        sp1.add("Samsung");
        sp1.add("Sony");
        sp1.add("Autres");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sp1);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        request = new MyRequest(getContext(), queue);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til1.getEditText().setText("");
                til2.getEditText().setText("");
                til4.getEditText().setText("");
                til5.getEditText().setText("");

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                leading.setVisibility(View.VISIBLE);
                b2.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);

                final String nom = til1.getEditText().getText().toString().trim();
                final String prix = til2.getEditText().getText().toString().trim();
                final String desc = til4.getEditText().getText().toString().trim();
                final String btq = til5.getEditText().getText().toString().trim();
                //final String img = circleImageView.toString().trim();
                final String cat = spinner.getSelectedItem().toString();
                //final String imageData = imageToString(bitmap);

                if (nom.length() > 0 &&  til2.getEditText().getText().toString().trim().length() > 0 && desc.length() > 0 && btq.length() > 0 ) {
                    request.Publication(nom, prix, encodedImage, nom, btq, cat, new MyRequest.PublicationCallback(){

                        @Override
                        public void onSuccess(String message) {

                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Votre produit à été publié").show();

                            // finish();
                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);

                            til1.getEditText().setText("");
                            til2.getEditText().setText("");
                            til4.getEditText().setText("");
                            til5.getEditText().setText("");

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
                            if (errors.get("NomEntreprise") != null ){
                                til5.setError(errors.get("NomEntreprise"));
                            }else{
                                til5.setErrorEnabled(false);
                            }

                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onError(String message) {

                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            leading.setVisibility(View.GONE);
                            b2.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);

                        }
                    });

                }else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Veuillez remplir tout les champs").show();

                    leading.setVisibility(View.GONE);
                    b2.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);

                }
            }

        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST);
            }
        });


       /* circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("Vous n'avez pas la permission... ").show();

                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    }else {

                        CropImage.activity()
                                .setGuidelines ( CropImageView.Guidelines.ON )
                                .setAspectRatio(1,1)
                                .start (getActivity());
                    }
                }
            }
        });*/

        return v;
    }

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode ==  CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {
            CropImage.ActivityResult result =  CropImage.getActivityResult (data);
            if (resultCode ==  RESULT_OK ) {
                mainImageURI = result.getUri();
                circleImageView.setImageURI(mainImageURI);
            } else  if (resultCode ==  CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE ) {
                Exception error = result.getError();
            }
        }
    }
*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("Vous n'avez pas la permission... ").show();
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
                InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
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
