//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************


package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class pageDocPrec extends AppCompatActivity {
    TextView     docprecedent, iddoc;
    ImageView    logo;
    LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docprec);


        //------------Récupère les vues voulues------------
        view         = findViewById(R.id.linearLayout1);
        docprecedent = findViewById(R.id.docprecedent);
        iddoc        = findViewById(R.id.iddocprec);
        logo         = findViewById(R.id.logo);


        //----------------------------Récupère les données stockées dans la base de donnée locale----------------------------------
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String id                     = preferences.getString(pageRecherche.EXTRA_ID, "FFFFFF");
        String nom                    = preferences.getString(pageRecherche.EXTRA_NOM, "FFFFFF");
        String marque                 = preferences.getString(pageRecherche.EXTRA_MARQUE, "FFFFFF");
        String mac                    = preferences.getString(pageRecherche.EXTRA_MAC, "FFFFFF");
        String ip                     = preferences.getString(pageRecherche.EXTRA_IP, "FFFFFF");
        String masque                 = preferences.getString(pageRecherche.EXTRA_MASQUE, "FFFFFF");
        String passerelle             = preferences.getString(pageRecherche.EXTRA_PASSERELLE, "FFFFFF");
        String salle                  = preferences.getString(pageRecherche.EXTRA_SALLE, "FFFFFF");


        //-----Ecrit l'ID et le nom du switch dans le haut de la page-----
        String idNomSwitch = "Switch n°" + id + ": " + nom;
        iddoc.setText(idNomSwitch);


        //----------------------Selon la marque, affiche le bon logo------------------------------------
         switch (marque){
            case "HP":
                logo.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_hp_logo_2012));
            break;

            case "DLINK":
                logo.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_dlink_logo));
            break;

            case "CISCO":
                logo.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_cisco_logo));
            break;
        }


        //--------------------------------------------Ecrit les données sur la page --------------------------------------------------------------------------------------
        String donneesSwitch = " ID du Switch:     " + id + "\n Nom:                   " + nom + "\n Marque:              " + marque + "\n Adresse MAC:   " +
                mac + "\n Adresse IP:        " + ip + "\n Masque:             " + masque + "\n Passerelle:         " + passerelle + "\n Emplacement:   " + salle;
        docprecedent.setText(donneesSwitch);
    }
}
