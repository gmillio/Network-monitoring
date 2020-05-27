//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************


package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class pageAuthentification extends AppCompatActivity {


    Button   valider;
    EditText login, mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification);

        login   = findViewById(R.id.login);
        mdp     = findViewById(R.id.mdp);
        valider = findViewById(R.id.valider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().isEmpty() || mdp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Champ(s) vide", Toast.LENGTH_SHORT).show();

                } else if (login.getText().toString().equals("felix")) {
                    if (mdp.getText().toString().equals("felix22")) {


                        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageAuthentification.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(valeurs.EXTRA_LOGIN, "felix");
                        editor.putString(valeurs.EXTRA_MDP, "felix22");
                        editor.apply();


                        startActivity(new Intent(pageAuthentification.this, MainActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Mot de passe invalide", Toast.LENGTH_SHORT).show();
                    }
                } else if (mdp.getText().toString().equals("felix22")) {

                    if (login.getText().toString().equals("felix")) {
                        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageAuthentification.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(valeurs.EXTRA_LOGIN, "felix");
                        editor.putString(valeurs.EXTRA_MDP, "felix22");
                        editor.apply();

                        startActivity(new Intent(pageAuthentification.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Login invalide", Toast.LENGTH_SHORT).show();
                    }




                } else if (login.getText().toString().equals("admin")) {
                    if (mdp.getText().toString().equals("admin")) {


                        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageAuthentification.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(valeurs.EXTRA_LOGIN, "admin");
                        editor.putString(valeurs.EXTRA_MDP, "admin");
                        editor.apply();


                        startActivity(new Intent(pageAuthentification.this, MainActivity.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Mot de passe invalide", Toast.LENGTH_SHORT).show();
                    }
                } else if (mdp.getText().toString().equals("admin")) {
                    if (login.getText().toString().equals("admin")) {


                        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageAuthentification.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(valeurs.EXTRA_LOGIN, "admin");
                        editor.putString(valeurs.EXTRA_MDP, "admin");
                        editor.apply();


                        startActivity(new Intent(pageAuthentification.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Login invalide", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Login et mot de passe invalide", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
