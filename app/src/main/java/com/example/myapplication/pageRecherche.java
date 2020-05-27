//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************
package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.ListIterator;

public class pageRecherche extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageButton    imageButton;
    EditText       editText;
    TextView       deconumero, txtnumero, txtmarque, txtbatiment, page2;
    RelativeLayout RelativeLayout1;
    Spinner        base, marque, batiment;
    TextView       infos, infos3, textInfo, separeteur;
    LinearLayout   lbatiments, lmarque, scrool, scrool2;

    private static final String TAG              = pageRecherche.class.getSimpleName();
    public static final String EXTRA_ID          = "com.example.myapplication.example.EXTRA_ID";
    public static final String EXTRA_NOM         = "com.example.myapplication.example.EXTRA_NOM";
    public static final String EXTRA_MARQUE      = "com.example.myapplication.example.EXTRA_MARQUE";
    public static final String EXTRA_MAC         = "com.example.myapplication.example.EXTRA_MAC";
    public static final String EXTRA_IP          = "com.example.myapplication.example.EXTRA_IP";
    public static final String EXTRA_MASQUE      = "com.example.myapplication.example.EXTRA_MASQUE";
    public static final String EXTRA_PASSERELLE  = "com.example.myapplication.example.EXTRA_PASSERELLE";
    public static final String EXTRA_SALLE       = "com.example.myapplication.example.EXTRA_SALLE";
    public static final String EXTRA_SELMARQUE   = "com.example.myapplication.example.EXTRA_SELMARQUE";
    public static final String EXTRA_SELBATIMENT = "com.example.myapplication.example.EXTRA_SELBATIMENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche);

        //------------Récupère les vues voulues------------
        infos = findViewById(R.id.affiche1);
        infos3 = findViewById(R.id.affichebatiment);
        textInfo = findViewById(R.id.textInfos);
        txtmarque = findViewById(R.id.Txtmarque);
        marque = findViewById(R.id.spinnerMarque);
        imageButton = findViewById(R.id.imageButton1);
        RelativeLayout1 = findViewById(R.id.RelativeLayout1);
        deconumero = findViewById(R.id.DecoNumero);
        txtnumero = findViewById(R.id.TxtNumero);
        editText = findViewById(R.id.editText);
        batiment = findViewById(R.id.spinnerBatiment);
        txtbatiment = findViewById(R.id.TxtBatiment);
        base = findViewById(R.id.spinner);
        lbatiments = findViewById(R.id.dynamique);
        lmarque = findViewById(R.id.affiche);
        page2 = findViewById(R.id.textViewTest);
        separeteur = findViewById(R.id.separateur);
        scrool = findViewById(R.id.scrool);
        scrool2 = findViewById(R.id.scrool2);

        //----------Créé le premier menu déroulant---------
        String[] items = new String[]{"numéro", "marque", "batiment"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinnerdesign, items);
        base.setAdapter(adapter);
        base.setOnItemSelectedListener(this);

        //----------Créé le deuxième menu déroulant---------
        String[] items1 = new String[]{"HP", "CISCO", "DLINK"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinnerdesign, items1);
        marque.setAdapter(adapter1);
        marque.setOnItemSelectedListener(this);

        //----------Créé le troisième menu déroulant---------
        String[] items2 = new String[]{"  A  ", "  B  ", "  C  ", "  D  ", "  E  ", "  F  ", "  G  "};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinnerdesign, items2);
        batiment.setAdapter(adapter2);
        batiment.setOnItemSelectedListener(this);

    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String typeRecherche = parent.getItemAtPosition(position).toString();

        final MyDBHandler dbHandler1 = new MyDBHandler(this, null, null, 1);

        View v = getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null){
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }

        //----Selon le type de recherche choisi----
        switch (typeRecherche){
            // pour rechercher par numéro
            case "numéro":
                //fait disparaître et apparaître les éléments nécessaires
                txtmarque.setVisibility(View.INVISIBLE);
                marque.setVisibility(View.INVISIBLE);
                txtbatiment.setVisibility(View.INVISIBLE);
                batiment.setVisibility(View.INVISIBLE);
                lmarque.setVisibility(View.INVISIBLE);
                infos3.setVisibility(View.INVISIBLE);
                textInfo.setVisibility(View.INVISIBLE);
                lbatiments.setVisibility(View.INVISIBLE);
                separeteur.setVisibility(View.INVISIBLE);
                scrool.setVisibility(View.INVISIBLE);
                scrool2.setVisibility(View.INVISIBLE);

                RelativeLayout1 = findViewById(R.id.RelativeLayout1);

                imageButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {


                        Switch switchs = dbHandler1.findHandlerId(editText.getText().toString());


                        //--------Si le EditText est vide--------

                        if (editText.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Veuillez entrer une valeur", Toast.LENGTH_SHORT).show();


                            //--------Si l'ID recherché n'est pas valide--------

                        } else if (switchs.GetId() == 0) {
                            Toast.makeText(getApplicationContext(), "Ce switch n'existe pas", Toast.LENGTH_SHORT).show();

                            Log.d("testestestestest", switchs.GetId().toString());


                            //--------Si l'ID recherché est valide--------

                        } else if (switchs.GetId() != 0) {

                            Intent intent = new Intent(pageRecherche.this, pageSwitch1.class);
                            // Récupère les valeurs correspondant dans la BDD locale
                            SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageRecherche.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(EXTRA_ID, switchs.GetId().toString());
                            editor.putString(EXTRA_NOM, switchs.GetNom());
                            editor.putString(EXTRA_MAC, switchs.GetMac());
                            editor.putString(EXTRA_MARQUE, switchs.GetMarque());
                            editor.putString(EXTRA_IP, switchs.GetIp());
                            editor.putString(EXTRA_MASQUE, switchs.GetMasque());
                            editor.putString(EXTRA_PASSERELLE, switchs.GetPasserelle());
                            editor.putString(EXTRA_SALLE, switchs.GetBatiment());
                            editor.apply();

                            startActivity(intent);
                        }
                    }


                });
                imageButton.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                deconumero.setVisibility(View.VISIBLE);
                txtnumero.setVisibility(View.VISIBLE);
                break;

            // Pour rechercher par marque
            case "marque":
                //fait disparaître et apparaître les éléments nécessaires
                imageButton.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                deconumero.setVisibility(View.INVISIBLE);
                txtnumero.setVisibility(View.INVISIBLE);
                txtbatiment.setVisibility(View.INVISIBLE);
                batiment.setVisibility(View.INVISIBLE);
                infos3.setVisibility(View.INVISIBLE);
                lmarque.setVisibility(View.VISIBLE);
                txtmarque.setVisibility(View.VISIBLE);
                marque.setVisibility(View.VISIBLE);
                textInfo.setVisibility(View.VISIBLE);
                separeteur.setVisibility(View.VISIBLE);
                lbatiments.setVisibility(View.INVISIBLE);
                scrool2.setVisibility(View.INVISIBLE);
                scrool.setVisibility(View.VISIBLE);
                break;

            // Pour rechercher par bâtiment
            case "batiment":
                //fait disparaître et apparaître les éléments nécessaires
                txtmarque.setVisibility(View.INVISIBLE);
                marque.setVisibility(View.INVISIBLE);
                imageButton.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                deconumero.setVisibility(View.INVISIBLE);
                txtnumero.setVisibility(View.INVISIBLE);
                lmarque.setVisibility(View.INVISIBLE);
                textInfo.setVisibility(View.VISIBLE);
                lbatiments.setVisibility(View.VISIBLE);
                txtbatiment.setVisibility(View.VISIBLE);
                batiment.setVisibility(View.VISIBLE);
                infos3.setVisibility(View.VISIBLE);
                separeteur.setVisibility(View.VISIBLE);
                scrool.setVisibility(View.INVISIBLE);
                scrool2.setVisibility(View.VISIBLE);
                break;
        }
        String marques = parent.getItemAtPosition(position).toString();
        String batiments = parent.getItemAtPosition(position).toString();


        //-------------Affichage valeurs de la BDD------------------------------------

        //Selon la marque choisi
        switch (marques){
            //Quand HP est choisi
            case "HP":
                SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(EXTRA_SELMARQUE, "HP");
                editor.apply();
                InfosMarques();
                break;

            //Quand CISCO est choisi
            case "CISCO":
                SharedPreferences preferences1 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString(EXTRA_SELMARQUE, "CISCO");
                editor1.apply();
                InfosMarques();
                break;

            //Quand DLINK est choisi
            case "DLINK":
                SharedPreferences preferences2 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor2 = preferences2.edit();
                editor2.putString(EXTRA_SELMARQUE, "DLINK");
                editor2.apply();
                InfosMarques();
                break;
        }

        //Selon le bâtiment choisi
        switch (batiments){
            //Quand le bâtiment A est choisi
            case "  A  ":
                SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(EXTRA_SELBATIMENT, "A");
                editor.apply();
                InfosBatiment();
                break;

            case "  B  ":
                SharedPreferences preferences1 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putString(EXTRA_SELBATIMENT, "B");
                editor1.apply();
                InfosBatiment();
                break;

            case "  C  ":
                SharedPreferences preferences2 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor2 = preferences2.edit();
                editor2.putString(EXTRA_SELBATIMENT, "C");
                editor2.apply();
                InfosBatiment();
                break;

            case "  D  ":
                SharedPreferences preferences3 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor3 = preferences3.edit();
                editor3.putString(EXTRA_SELBATIMENT, "D");
                editor3.apply();
                InfosBatiment();
                break;

            case "  E  ":
                SharedPreferences preferences4 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor4 = preferences4.edit();
                editor4.putString(EXTRA_SELBATIMENT, "E");
                editor4.apply();
                InfosBatiment();
                break;

            case "  F  ":
                SharedPreferences preferences5 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor5 = preferences5.edit();
                editor5.putString(EXTRA_SELBATIMENT, "F");
                editor5.apply();
                InfosBatiment();
                break;

            case "  G  ":
                SharedPreferences preferences6 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor6 = preferences6.edit();                editor6.putString(EXTRA_SELBATIMENT, "G");
                editor6.apply();
                InfosBatiment();
                break;
        }
    }

    //Récupère les informations des switchs pour la recherche par marque
    void InfosMarques(){
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageRecherche.this);
        String selection = preferences.getString(EXTRA_SELMARQUE, "FFFFFF");

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        lmarque.removeAllViews();
        //Recherche les switchs ayant la bonne marque
        List<Switch> switchs2 = dbHandler.findHandlermarque(selection);
        ListIterator<Switch> ligne2 = switchs2.listIterator();
        //lit les valeurs du tableau sur chaque ligne
        while (ligne2.hasNext()) {
            final Switch valeur = ligne2.next();
            final Button btn = new Button(this);
            btn.setWidth(1000);
            btn.setBackground(this.getResources().getDrawable(R.drawable.buttonshape));
            String texteBouton = "   " + valeur.GetId() + "  |  " + valeur.GetMarque() + "  |  " + valeur.GetIp() + "  |  " + valeur.GetBatiment() + "   ";
            btn.setText(texteBouton);
            final TextView txt = new TextView(this);
            lmarque.addView(btn);
            lmarque.addView(txt);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = valeur.GetId().toString();
                    Log.d(TAG, valeur.GetMarque());
                    Intent intent = new Intent(pageRecherche.this, pageSwitch1.class);

                    SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageRecherche.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(EXTRA_ID, id);
                    editor.putString(EXTRA_NOM, valeur.GetNom());
                    editor.putString(EXTRA_MAC, valeur.GetMac());
                    editor.putString(EXTRA_MARQUE, valeur.GetMarque());
                    editor.putString(EXTRA_IP, valeur.GetIp());
                    editor.putString(EXTRA_MASQUE, valeur.GetMasque());
                    editor.putString(EXTRA_PASSERELLE, valeur.GetPasserelle());
                    editor.putString(EXTRA_SALLE, valeur.GetBatiment());
                    editor.apply();

                    startActivity(intent);
                }
            });
        }
    }

    //Récupère les informations des switchs pour la recherche par bâtiment
    void InfosBatiment(){
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageRecherche.this);
        String selection = preferences.getString(EXTRA_SELBATIMENT, "FFFFFF");

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        lbatiments.removeAllViews();
        //Recherche les switchs étant dans le bon bâtiment
        List<Switch> switchs = dbHandler.findHandlerBatiment(selection);
        ListIterator<Switch> ligne = switchs.listIterator();
        //lit les valeurs du tableau sur chaque ligne
        while (ligne.hasNext()) {
            final Switch valeur = ligne.next();
            final Button btn = new Button(this);
            btn.setWidth(1000);
            btn.setBackground(this.getResources().getDrawable(R.drawable.buttonshape));
            String texteBouton = "   " + valeur.GetId() + "  |  " + valeur.GetMarque() + "  |  " + valeur.GetIp() + "  |  " + valeur.GetBatiment() + "   ";

            btn.setText(texteBouton);
            final TextView txt = new TextView(this);
            lbatiments.addView(btn);
            lbatiments.addView(txt);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = valeur.GetId().toString();
                    Log.d(TAG, valeur.GetMarque());
                    Intent intent = new Intent(pageRecherche.this, pageSwitch1.class);

                    SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageRecherche.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(EXTRA_ID, id);
                    editor.putString(EXTRA_NOM, valeur.GetNom());
                    editor.putString(EXTRA_MAC, valeur.GetMac());
                    editor.putString(EXTRA_MARQUE, valeur.GetMarque());
                    editor.putString(EXTRA_IP, valeur.GetIp());
                    editor.putString(EXTRA_MASQUE, valeur.GetMasque());
                    editor.putString(EXTRA_PASSERELLE, valeur.GetPasserelle());
                    editor.putString(EXTRA_SALLE, valeur.GetBatiment());
                    editor.apply();

                    startActivity(intent);
                }
            });
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {}
}
