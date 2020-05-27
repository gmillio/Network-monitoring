//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************


package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView     date;
    Button       quit;
    PopupWindow  Compte, nonCompte;
    Button       changerCompte, fermerChangerCompte, seConnecter, fermerSeConnecter;
    LinearLayout affichepopup;

    private static final int REQUEST_CAMERA_PERMISSION = 201;


    ProgressDialog chargement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        flash        = findViewById(R.id.button8);
        date         = findViewById(R.id.date);
        quit         = findViewById(R.id.button7);
        affichepopup = findViewById(R.id.affichepopup);

        final boolean hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        //------------lorsque le bouton du flash est cliqué-------------
        flash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //si la demande de permition est acceptée
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    //si il y a bien un flash sur le téléphone
                    if (hasCameraFlash) {
                        //si le flash est allumé
                        if (flashLightStatus)
                            flashLightOff();
                            //si le flash est éteint
                        else
                            flashLightOn();
                        //si il n'y a pas de flash sur le téléphone
                    } else {
                        Toast.makeText(MainActivity.this, "Pas de flash disponible sur ce téléphone",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            }
        });
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String couleur = "Base de donnée actualisée le                   " + preferences.getString(valeurs.DERNIERE_DATE, "01/01/2020 à 00:00:00");

        date.setText(couleur);
    }

    Button flash;
    private boolean flashLightStatus = false;

    public void page1(View view) {
        afficherPopup();

        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String code = preferences.getString(valeurs.EXTRA_AUTHENTIFICATION, "");
        Log.d("        TEST", code);

        if (code.equals("autorise")) {
            startActivity(new Intent(this, pageQRcode.class));
        }
    }

    public void page2(View view) {
        afficherPopup();

        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String code = preferences.getString(valeurs.EXTRA_AUTHENTIFICATION, "");

        if (code.equals("autorise")) {
            startActivity(new Intent(this, pageRecherche.class));
        }
    }

    public void page3(View view) {
        afficherPopup();

        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String code = preferences.getString(valeurs.EXTRA_AUTHENTIFICATION, "");

        if (code.equals("autorise")) {
            startActivity(new Intent(this, pageDocPrec.class));
        }
    }

    public void page4(View view) {

        startActivity(new Intent(this, pageInfosParam.class));
    }

    public void page5(View view) {

    }

    public void page6(View view) {
        afficherPopup();

        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String code = preferences.getString(valeurs.EXTRA_AUTHENTIFICATION, "");

        if (code.equals("autorise")) {


            this.deleteDatabase("switchDB.db");
            final MyDBHandler dbHandler1 = new MyDBHandler(this, null, null, 1);


            //----------------------------------Lis les données JSON---------------------------------------------------//

            new MainActivity.JsonTask().execute(valeurs.URL);


            SharedPreferences preferences1 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

            String extra_json = preferences1.getString(valeurs.EXTRA_JSON, "FFFFFF");


            //----------------pour éviter le spam du boutton-----------------//
            quit.setEnabled(false);                                          //
            Handler handler1 = new Handler();                                //
            handler1.postDelayed(new Runnable() {                             //
                @Override                                                    //
                public void run() {                                      //
                    quit.setEnabled(true);                                   //
                }                                                            //
            }, 5);                                             //
            //---------------------------------------------------------------//


            try {
                JSONArray jArray = new JSONArray(extra_json);

                for (int i = 0; i < jArray.length(); ++i) {

                    // Récupère les valeurs aux balises correspondantes
                    String id = jArray.getJSONObject(i).getString("switchId");
                    String nomSwitch = jArray.getJSONObject(i).getString("nomSwitch");
                    String mac = jArray.getJSONObject(i).getString("mac");
                    String salle = jArray.getJSONObject(i).getString("localisation");
                    String marqueId = jArray.getJSONObject(i).getString("modeleId");


                    //--------------Pour entrer dans configSwitch et récupérer les valeurs--------------------------------//
                    JSONObject configSwitch = new JSONObject(jArray.getJSONObject(i).getString("configSwitch"));   //
                    //
                    String marque;                                                                                        //
                    //
                    if (marqueId.equals("1")) {                                                                           //
                        marque = "HP";                                                                                    //
                    } else if (marqueId.equals("2")) {                                                                    //
                        marque = "CISCO";                                                                                 //
                    } else {                                                                                              //
                        marque = "DLINK";                                                                                 //
                    }                                                                                                     //
                    //
                    String ip = configSwitch.getString("adIpCidr");                                                //
                    String gateway = configSwitch.getString("gateway");                                            //
                    //----------------------------------------------------------------------------------------------------//


                    // Affiche toutes les valeurs dans le debug
                    String debug = (" \n ID:            " + id + "\n Nom:           " + nomSwitch + "\n Marque:        " + marque + "\n Adresse IP:    " + ip + "\n Gateway:       " + gateway + "\n Adresse MAC:   " + mac + "\n Emplacement:   " + salle + System.getProperty("line.separator"));
                    Log.d("       AFFICHEUR", debug);

                    dbHandler1.addHandler(id, marque, mac, nomSwitch, ip, gateway, salle);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //-------------------------------------------------------------------------------------------------------------------------//
        }
    }


    //---------------------------Lis le JSON sur le site internet------------------//
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            chargement = new ProgressDialog(MainActivity.this);
            chargement.setMessage("Veuillez patienter");
            chargement.setCancelable(false);
            chargement.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (chargement.isShowing()) {
                        chargement.dismiss();
                        Toast.makeText(getApplicationContext(), "Actualisation impossible", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        String extra_json = preferences.getString(valeurs.EXTRA_JSON, "FFFFFF");
                        onPostExecute(extra_json);

                    }
                }
            }, 8000);
        }

        // Tâche ne arrière plan: essaye de se connecter au serveur web
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                    Log.d("Response: ", "> " + line);

                    SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString(valeurs.EXTRA_JSON, buffer.toString());
                    editor.apply();
                    Log.d("       BDD URL JSON", valeurs.EXTRA_JSON);
                }
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        // Si la connection c'est effectuée
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (chargement.isShowing()) {
                        chargement.dismiss();
                        date();
                        Toast.makeText(getApplicationContext(), "Base de donnée actualisée", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 300);
        }
    }

    //Allume le flash
    private void flashLightOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);


            try {
                if(cameraManager != null) {

                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, true);
                    flashLightStatus = true;
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //Eteind le flash
    private void flashLightOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);


            try {
                if (cameraManager != null) {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, false);
                    flashLightStatus = false;
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //Récupère et affiche la date
    private void date() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY à HH:mm:ss");
        String result = formatter.format(now);

        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(valeurs.DERNIERE_DATE, result);

        editor.apply();
        String texteDate = "Base de donnée actualisée le                   " + preferences.getString(valeurs.DERNIERE_DATE, "01/01/2020 à 00:00:00");

        date.setText(texteDate);


    }

    void afficherPopup() {
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String login = preferences.getString(valeurs.EXTRA_LOGIN, "");

        //Vérifie qui est connecté
        switch (login) {
            //Affiche la popup mauvais compte
            case "felix":

                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (layoutInflater != null) {

                    View customView = layoutInflater.inflate(R.layout.popupmauvaiscompte, null);

                    changerCompte = customView.findViewById(R.id.changerCompte);
                    fermerChangerCompte = customView.findViewById(R.id.fermerChangerCompte);

                    Compte = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    //display la popup window
                    Compte.showAtLocation(affichepopup, Gravity.CENTER, 0, 0);
                    Compte.setFocusable(true);


                    //ferme la popup quand le bouton est cliqué
                    fermerChangerCompte.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Compte.dismiss();
                        }
                    });

                    //lance la page quand le bouton est cliqué
                    changerCompte.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, pageInfosParam.class));
                            Compte.dismiss();
                        }
                    });
                    SharedPreferences.Editor editor2 = preferences.edit();
                    editor2.putString(valeurs.EXTRA_AUTHENTIFICATION, "nonAutorise");
                    editor2.apply();
                }
                break;

            //Accède à la page
            case "admin":
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(valeurs.EXTRA_AUTHENTIFICATION, "autorise");
                editor.apply();
                break;

            //Affiche la popup pas de compte connecté
            case "":

                LayoutInflater layoutInflater1 = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (layoutInflater1 != null) {

                    View customView1 = layoutInflater1.inflate(R.layout.popupnonconnecte, null);

                    seConnecter = customView1.findViewById(R.id.seConnecter);
                    fermerSeConnecter = customView1.findViewById(R.id.fermerSeConnecter);

                    nonCompte = new PopupWindow(customView1, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    nonCompte.setFocusable(true);


                    //affiche la popup window
                    nonCompte.showAtLocation(affichepopup, Gravity.CENTER, 0, 0);

                    //ferme la popup quand le bouton est cliqué
                    fermerSeConnecter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nonCompte.dismiss();
                        }
                    });

                    //lance la page quand le bouton est cliqué
                    seConnecter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, pageAuthentification.class));
                            nonCompte.dismiss();
                        }
                    });
                    SharedPreferences.Editor editor1 = preferences.edit();
                    editor1.putString(valeurs.EXTRA_AUTHENTIFICATION, "nonAutorise");
                    editor1.apply();
                }
                break;
        }

    }

}
