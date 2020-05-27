//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************


package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class pageQRcode extends AppCompatActivity {

    SurfaceView              surfaceView;
    TextView                 txtBarcodeValue, whaou;
    BarcodeDetector          barcodeDetector;
    private CameraSource     cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String                   intentData = "";
    boolean                  isEmail = false;

    Button                   closePopupBtn, PopupBtn;
    PopupWindow              popupWindow;
    LinearLayout             linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        whaou           = findViewById(R.id.whaou);
        surfaceView     = findViewById(R.id.surfaceView);
    }

    private void initialiseDetectorsAndSources() {
        txtBarcodeValue.setVisibility(View.INVISIBLE);

        Toast.makeText(getApplicationContext(), "Barcode scanner lancé", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(pageQRcode.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(pageQRcode.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "Lecteur code barre quitté", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {

                            cameraSource.stop();

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                            } else {
                                isEmail = false;
                                intentData = barcodes.valueAt(0).displayValue;

                                linearLayout2 = findViewById(R.id.linearLayout2);

                                LayoutInflater layoutInflater = (LayoutInflater) pageQRcode.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View customView;
                                if (layoutInflater != null) {


                                    Log.d("fdffdsfsffsf",intentData);
                                    //intentData.matches("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$")


                                    //--------Si le texte contenu dans le Qr code contient http--------
                                    if (intentData.contains("http")) {

                                        customView = layoutInflater.inflate(R.layout.popupinternet, null);

                                        closePopupBtn = customView.findViewById(R.id.closePopupBtn);
                                        PopupBtn = customView.findViewById(R.id.PopupBtn);


                                        txtBarcodeValue.setVisibility(View.INVISIBLE);


                                        //instancie la popup window
                                        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                        //display la popup window
                                        popupWindow.showAtLocation(linearLayout2, Gravity.CENTER, 0, 0);

                                        //ferme la popup quand le bouton est cliqué
                                        closePopupBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                popupWindow.dismiss();
                                                startActivity(new Intent(pageQRcode.this, pageQRcode.class));
                                            }
                                        });

                                        //lance la page internet quand le bouton est cliqué
                                        PopupBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (intentData.length() > 0) {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                                                }
                                                popupWindow.dismiss();
                                            }
                                        });


                                        //--------Si le texte contenu dans le Qr code ne contient pas http--------
                                    } else if(intentData.matches("^([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])$")){

                                        customView = layoutInflater.inflate(R.layout.popup, null);

                                        closePopupBtn = customView.findViewById(R.id.closePopupBtn);
                                        PopupBtn = customView.findViewById(R.id.PopupBtn);


                                        String mac = intentData;
                                        final MyDBHandler dbHandler = new MyDBHandler(pageQRcode.this, null, null, 1);
                                        Switch switchs = dbHandler.findHandlerMac(mac);
                                        Intent intent = new Intent(pageQRcode.this, pageSwitch1.class);

                                        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(pageQRcode.this);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(pageRecherche.EXTRA_ID, switchs.GetId().toString());
                                        editor.putString(pageRecherche.EXTRA_NOM, switchs.GetNom());
                                        editor.putString(pageRecherche.EXTRA_MAC, switchs.GetMac());
                                        editor.putString(pageRecherche.EXTRA_MARQUE, switchs.GetMarque());
                                        editor.putString(pageRecherche.EXTRA_IP, switchs.GetIp());
                                        editor.putString(pageRecherche.EXTRA_MASQUE, switchs.GetMasque());
                                        editor.putString(pageRecherche.EXTRA_PASSERELLE, switchs.GetPasserelle());
                                        editor.putString(pageRecherche.EXTRA_SALLE, switchs.GetBatiment());
                                        editor.apply();

                                        //instancie la popup window
                                        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                        //display la popup window
                                        popupWindow.showAtLocation(linearLayout2, Gravity.CENTER, 0, 0);

                                        //ferme la popup quand le bouton est cliqué
                                        closePopupBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                popupWindow.dismiss();
                                                startActivity(new Intent(pageQRcode.this, pageQRcode.class));
                                            }
                                        });

                                        //lance la page internet quand le bouton est cliqué
                                        PopupBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (intentData.length() > 0) {
                                                    startActivity(new Intent(pageQRcode.this, pageSwitch1.class));
                                                }
                                                popupWindow.dismiss();
                                            }
                                        });


                                    }else {
                                        txtBarcodeValue.setVisibility(View.VISIBLE);
                                        txtBarcodeValue.setText(intentData);

                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}
