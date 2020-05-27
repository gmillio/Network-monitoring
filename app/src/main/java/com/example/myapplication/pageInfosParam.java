//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************

package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class pageInfosParam extends AppCompatActivity {
    Button         json;
    RelativeLayout info;
    TextView       compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infosparam);
        json   = findViewById(R.id.btnnjson);
        info   = findViewById(R.id.info);
        compte = findViewById(R.id.compteconnecté);

        Log.d("           TEST", valeurs.EXTRA_LOGIN);

        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String login = preferences.getString(valeurs.EXTRA_LOGIN, "");

        Log.d("           TEST2", login);
        String texteBouton  = "Se Déconnecter";
        String texteBouton2 = "Se Connecter";
        String userConnecte = "Utilisateur connecté: " + login;



        switch (login){
            case "felix":
            case "admin":

                compte.setVisibility(View.VISIBLE);
                Button btn = new Button(pageInfosParam.this);
                btn.setText(texteBouton);
                btn.setTextSize(22);
                btn.setBackground(this.getResources().getDrawable(R.drawable.buttonshape));
                info.addView(btn);

                compte.setText(userConnecte);
                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageInfosParam.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(valeurs.EXTRA_LOGIN, "");
                        editor.putString(valeurs.EXTRA_MDP, "");
                        editor.apply();
                        startActivity(new Intent(pageInfosParam.this, MainActivity.class));
                    }
                });
                break;

            case "":
                compte.setVisibility(View.INVISIBLE);
                btn = new Button(pageInfosParam.this);
                btn.setWidth(1000);
                btn.setText(texteBouton2);
                btn.setTextSize(22);
                btn.setBackground(this.getResources().getDrawable(R.drawable.buttonshape));
                info.addView(btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(pageInfosParam.this, pageAuthentification.class));
                    }
                });
                break;
        }
    }

    public void oui(View v) {
        startActivity(new Intent(pageInfosParam.this, pagepopup.class));
    }

    public void json(View v) {
        startActivity(new Intent(pageInfosParam.this, pageJsonTest.class));
    }


    public static final String CHANNEL_ID = "100";
    public static final int NOTIFICATION_ID = 1988;

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.common_google_play_services_notification_channel_name);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            //pour éviter null pointer exception
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void notif(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_switch_add)
                .setColor(Color.parseColor("#000000"))
                .setContentTitle("Problème réseau")
                .setContentText("Problème détécté sur le switch n°1")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
        createNotificationChannel();

    }

}
