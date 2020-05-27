//*********************************************************************
//
//     Mace Alexy    Etudiant 3
//     11/03/2020
//     Projet: Gestion intégrée Open Source d'un réseau informatique
//     Code valeurs
//
//*********************************************************************


package com.example.myapplication;

 class Switch {

    private Integer _id;
    private String _nom;
    private String _marque;
    private String _mac;
    private String _ip;
    private String _masque;
    private String _passerelle;
    private String _batiment;

     Switch() {
    }

     Switch(int id, String nom, String marque, String Mac, String ip, String masque, String passerelle, String batiment) {
        _id         = id;
        _nom        = nom;
        _marque     = marque;
        _mac        = Mac;
        _ip         = ip;
        _masque     = masque;
        _passerelle = passerelle;
        _batiment   = batiment;
    }

     Integer GetId() {
        return _id;
    }

     void SetId(Integer id) {
        this._id = id;
    }


     String GetNom() {
        return _nom;
    }

     void SetNom(String nom) {
        this._nom = nom;
    }


     String GetMarque() {
        return _marque;
    }

     void SetMarque(String marque) {
        this._marque = marque;
    }


     String GetMac() {
        return _mac;
    }

     void SetMac(String mac) {
        this._mac = mac;
    }


     String GetIp() {
        return _ip;
    }

     void SetIp(String ip) {
        this._ip = ip;
    }


     String GetMasque() {
        return _masque;
    }

     void SetMasque(String masque) {
        this._masque = masque;
    }


     String GetPasserelle() {
        return _passerelle;
    }

     void SetPasserelle(String passerelle) {
        this._passerelle = passerelle;
    }


     String GetBatiment() {
        return _batiment;
    }

     void SetBatiment(String batiment) {
        this._batiment = batiment;
    }
}
