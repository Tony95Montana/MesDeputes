package com.example.mesdeputes.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mesdeputes.models.Deputy;
import com.example.mesdeputes.models.Vote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiServices {

    private static final String URL_API_SEARCH="https://www.nosdeputes.fr/recherche/";
    private static final String URL_API_VOTE="https:/2017-2022.nosdeputes.fr/";
    private static final String URL_END_VOTE_JSON="/votes/json";
    private static final String URL_END_FORMATJSON= "?format=json";
    private static final String URL_AVATAR="https://www.nosdeputes.fr/depute/photo/";

    public static void searchRequest(Context context, String search, SearchObserver listener){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(URL_API_SEARCH + search + URL_END_FORMATJSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("results");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject object= jsonArray.getJSONObject(i);
                                if(object.getString("document_type").equals("Parlementaire")){
                                    getDeputyInfo(context, object.getString("document_url"), listener);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }
    public static void callVote(Context context, String search, VoteObserver listener){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(URL_API_VOTE + search + URL_END_VOTE_JSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("votes");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i).getJSONObject("vote");
                                JSONObject scrutin = object.getJSONObject("scrutin");
                                JSONArray demandeurs = scrutin.getJSONArray("demandeurs");
                                String demandeur = "";
                                if (demandeurs.length() != 0) demandeur = demandeurs.getJSONObject(0).getString("demandeur");
                                Vote vote = new Vote(scrutin.getInt("numero"),
                                        object.getString("position"),
                                        scrutin.getString("titre"),
                                        scrutin.getString("sort"),
                                        scrutin.getString("date"),
                                        scrutin.getInt("nombre_votants"),
                                        scrutin.getInt("nombre_pours"),
                                        scrutin.getInt("nombre_contres"),
                                        demandeur);
                                listener.onReceiveVoteDeputy(vote);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public static void getDeputyInfo(Context context, String urlInfoDeputy, SearchObserver listener){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(urlInfoDeputy,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectDeputy = jsonObject.getJSONObject("depute");
                            Deputy deputy = new Deputy(jsonObjectDeputy.getInt("id"),
                                    jsonObjectDeputy.getString("prenom"),
                                    jsonObjectDeputy.getString("nom_de_famille"),
                                    jsonObjectDeputy.getString("date_naissance"),
                                    jsonObjectDeputy.getString("lieu_naissance"),
                                    jsonObjectDeputy.getString("sexe"),
                                    jsonObjectDeputy.getJSONArray("sites_web"),
                                    jsonObjectDeputy.getString("num_deptmt"),
                                    jsonObjectDeputy.getInt("num_circo"),
                                    jsonObjectDeputy.getString("nom_circo"));

                            deputy.setGroupe(jsonObjectDeputy.getString("groupe_sigle"));
                            deputy.setEmail(jsonObjectDeputy.getJSONArray("emails") .getJSONObject(0).getString("email"));
                            listener.onReceiveDeputyInfo(deputy);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public static void loadDeputyAvatar(Context context, String deputyName, final ImageView imageView){
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest request = new ImageRequest( URL_AVATAR+deputyName+"/160",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imageView.setImageResource(android.R.drawable.ic_menu_gallery);
                    }
                });
        queue.add(request);
    }
}
