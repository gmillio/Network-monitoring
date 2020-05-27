//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************


package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class pageJsonTest extends AppCompatActivity {
    TextView       affichage;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsontest);
        affichage = findViewById(R.id.affichageJson);

        new JsonTask().execute("http://172.20.20.74/api/Switches/getSwitches");


        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        String extra_json = preferences.getString(valeurs.EXTRA_JSON, "FFFFFF");

        affichage.setText(extra_json);

        try {
            JSONArray jArray = new JSONArray(extra_json);


            String debug = "OH YESS ! ";

            Log.d("       LECTURE", debug);

            for (int i = 0; i < jArray.length(); ++i) {


                String id = jArray.getJSONObject(i).getString("switchId");
                String nomSwitch = jArray.getJSONObject(i).getString("nomSwitch");
                String mac = jArray.getJSONObject(i).getString("mac");
                String salle = jArray.getJSONObject(i).getString("localisation");
                String marqueId = jArray.getJSONObject(i).getString("modeleId");


                //--------------Pour entrer dans configSwitch et récupérer les valeurs--------------------------------//
                JSONObject configSwitch = new JSONObject(jArray.getJSONObject(i).getString("configSwitch"));


                String marque = "";

                if (marqueId.equals("1")) {
                    marque = "HP";
                } else if (marqueId.equals("2")) {
                    marque = "CISCO";
                } else {
                    marque = "DLINK";
                }

                String ip = configSwitch.getString("adIpCidr");
                String gateway = configSwitch.getString("gateway");
                //-----------------------------------------------------------------------------------------------------//


                String yo = (" \n ID:            " + id + "\n Nom:           " + nomSwitch + "\n Marque:        " + marque + "\n Adresse IP:    " + ip + "\n Gateway:       " + gateway + "\n Adresse MAC:   " + mac + "\n Emplacement:   " + salle + System.getProperty("line.separator"));
                Log.d("       AFFICHEUR", yo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(pageJsonTest.this);
            pd.setMessage("Veuillez patienter");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                String test = "yessss";
                Log.d("       BDD URL JSON 1", test);


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                String test1 = "yessss";
                Log.d("       BDD URL JSON 2", test1);

                StringBuffer buffer = new StringBuffer();
                String line = "";
                String test2 = "yessss";
                Log.d("       BDD URL JSON 3", test2);

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    String test3 = "yessss";
                    Log.d("       BDD URL JSON 4", test3);
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)


                    SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageJsonTest.this);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString(valeurs.EXTRA_JSON, buffer.toString());
                    editor.apply();
                    Log.d("       BDD URL JSON BIS", valeurs.EXTRA_JSON);


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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            Log.d("       BDD URL JSON 5", result);
        }
    }
}
