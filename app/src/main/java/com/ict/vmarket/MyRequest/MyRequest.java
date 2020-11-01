package com.ict.vmarket.MyRequest;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyRequest {

    private Context context;
    private RequestQueue queue;

    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void Inscription(final String ImageEntreprise, final String NomEntreprise, final String Email, final String Password, final String Password2, final String Description, final String Tel, final String Ville, final String Pays, final String Adresse, final InscriptionCallback callback){

        String url ="http://market.paytickettogo.com/vmarket/Inscription.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Map<String, String> errors = new HashMap<>();

                        try {
                            JSONObject json = new JSONObject(response);

                            Boolean error = json.getBoolean("error");

                            if (!error){
                                // l'inscription s'est bien déroulée
                                callback.onSuccess("l'inscription s'est bien déroulé");
                            }else{
                                JSONObject message = json.getJSONObject("message");

                                if (message.has("NomEntreprise")){
                                    errors.put("NomEntreprise", message.getString("NomEntreprise"));
                                }
                                if (message.has("Email")){
                                    errors.put("Email", message.getString("Email"));
                                }
                                if (message.has("ImageEntreprise")){
                                    errors.put("ImageEntreprise", message.getString("ImageEntreprise"));
                                }
                                if (message.has("Password")){
                                    errors.put("Password", message.getString("Password"));
                                }
                                callback.inputErrors(errors);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("APP", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }else if (error instanceof VolleyError){
                    callback.onError("Veuillez réessayer ! une erreur s'est produite");
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put("ImageEntreprise", ImageEntreprise);
                params.put("NomEntreprise", NomEntreprise);
                params.put("Email", Email);
                params.put("Password", Password);
                params.put("Password2", Password2);
                params.put("Description", Description);
                params.put("Tel", Tel);
                params.put("Ville", Ville);
                params.put("Pays", Pays);
                params.put("Adresse", Adresse);
                return params;
            }
        };

        queue.add(request);
    }
    public interface InscriptionCallback{

        void onSuccess (String message);
        void inputErrors(Map <String, String> errors);
        void onError(String message);
    }

    //annonce

    public void Annonce(final String Title, final String Msg, final String NomEntreprise, final String dte, final AnnonceCallback callback){

        String url ="http://market.paytickettogo.com/vmarket/Annonce.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Map<String, String> errors = new HashMap<>();

                        try {
                            JSONObject json = new JSONObject(response);

                            Boolean error = json.getBoolean("error");

                            if (!error){
                                // l'inscription s'est bien déroulée
                                callback.onSuccess("l'annonce s'est bien passé");
                            }else{
                                JSONObject message = json.getJSONObject("message");

                                if (message.has("Title")){
                                    errors.put("Title", message.getString("Title"));
                                }
                                if (message.has("Msg")){
                                    errors.put("Msg", message.getString("Msg"));
                                }
                                if (message.has("NomEntreprise")){
                                    errors.put("NomEntreprise", message.getString("NomEntreprise"));
                                }

                                callback.inputErrors(errors);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("APP", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }else if (error instanceof VolleyError){
                    callback.onError("Veuillez réessayer ! une erreur s'est produite");
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put("Title", Title);
                params.put("Msg", Msg);
                params.put("NomEntreprise", NomEntreprise);
                params.put("dte", String.valueOf(dte));
                return params;
            }
        };

        queue.add(request);
    }
    public interface AnnonceCallback{

        void onSuccess (String message);
        void inputErrors(Map <String, String> errors);
        void onError(String message);
    }

    //client


    public void Client(final String Pseudo, final String Email, final String Tel, final String Password, final String Password2, final ClientCallback callback){

        String url ="http://market.paytickettogo.com/vmarket/InscriptionClient.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Map<String, String> errors = new HashMap<>();

                        try {
                            JSONObject json = new JSONObject(response);

                            Boolean error = json.getBoolean("error");

                            if (!error){
                                // l'inscription s'est bien déroulée
                                callback.onSuccess("l'inscription s'est bien passé");
                            }else{
                                JSONObject message = json.getJSONObject("message");

                                if (message.has("Pseudo")){
                                    errors.put("Pseudo", message.getString("Pseudo"));
                                }
                                if (message.has("Email")){
                                    errors.put("Email", message.getString("Email"));
                                }
                                if (message.has("Tel")){
                                    errors.put("Tel", message.getString("Tel"));
                                }
                                if (message.has("Password")){
                                    errors.put("Password", message.getString("Password"));
                                }

                                callback.inputErrors(errors);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("APP", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }else if (error instanceof VolleyError){
                    callback.onError("Veuillez réessayer ! une erreur s'est produite");
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put("Pseudo", Pseudo);
                params.put("Email", Email);
                params.put("Tel", Tel);
                params.put("Password", Password);
                params.put("Password2", Password2);
                return params;
            }
        };

        queue.add(request);
    }
    public interface ClientCallback{

        void onSuccess (String message);
        void inputErrors(Map <String, String> errors);
        void onError(String message);
    }

    //pub

    public void Promotion(final String nomPdt, final String prix, final String img, final String promo, final String desc, final String boutique, final String dt, final String dtfin, final String cat, final PromotionCallback callback){

        String url ="http://market.paytickettogo.com/vmarket/Produit.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Map<String, String> errors = new HashMap<>();

                        try {
                            JSONObject json = new JSONObject(response);

                            Boolean error = json.getBoolean("error");

                            if (!error){
                                // l'inscription s'est bien déroulée
                                callback.onSuccess("la publication s'est bien déroulé");
                            }else{
                                JSONObject message = json.getJSONObject("message");

                                if (message.has("LibelleProduit")){
                                    errors.put("LibelleProduit", message.getString("LibelleProduit"));
                                }
                                if (message.has("description")){
                                    errors.put("description", message.getString("description"));
                                }
                                if (message.has("prix")){
                                    errors.put("prix", message.getString("prix"));
                                }
                                if (message.has("Promotion")){
                                    errors.put("Promotion", message.getString("Promotion"));
                                }
                                callback.inputErrors(errors);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("APP", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }else if (error instanceof VolleyError){
                    callback.onError("Veuillez réessayer ! une erreur s'est produite");
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put("libelle", nomPdt);
                params.put("prix", prix);
                params.put("promotion", promo);
                params.put("Description", desc);
                params.put("nomBoutique", boutique);
                params.put("dateDebut", dt);
                params.put("dateFin", dtfin);
                params.put("image", img);
                params.put("categorie", cat);
                return params;
            }
        };

        queue.add(request);
    }
    public interface PublicationCallback{

        void onSuccess (String message);
        void inputErrors(Map <String, String> errors);
        void onError(String message);
    }

    public void Publication(final String nomPdt, final String prix, final String img, final String desc, final String boutique, final String cat, final PublicationCallback callback){

        String url ="http://market.paytickettogo.com/vmarket/Produit.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Map<String, String> errors = new HashMap<>();

                        try {
                            JSONObject json = new JSONObject(response);

                            Boolean error = json.getBoolean("error");

                            if (!error){
                                // l'inscription s'est bien déroulée
                                callback.onSuccess("la publication s'est bien déroulé");
                            }else{
                                JSONObject message = json.getJSONObject("message");

                                if (message.has("LibelleProduit")){
                                    errors.put("LibelleProduit", message.getString("LibelleProduit"));
                                }
                                if (message.has("description")){
                                    errors.put("description", message.getString("description"));
                                }
                                if (message.has("prix")){
                                    errors.put("prix", message.getString("prix"));
                                }
                                if (message.has("Promotion")){
                                    errors.put("Promotion", message.getString("Promotion"));
                                }
                                callback.inputErrors(errors);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.d("APP", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }else if (error instanceof VolleyError){
                    callback.onError("Veuillez réessayer ! une erreur s'est produite");
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> params = new HashMap<>();
                params.put("libelle", nomPdt);
                params.put("prix", prix);
                params.put("Description", desc);
                params.put("nomBoutique", boutique);
                params.put("image", img);
                params.put("categorie", cat);
                return params;
            }
        };

        queue.add(request);
    }
    public interface PromotionCallback{

        void onSuccess (String message);
        void inputErrors(Map <String, String> errors);
        void onError(String message);
    }
}
