/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassPartager;

import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author Selwyn
 */
public class UnClient implements Serializable {

    private String identifiant;
    private String password;
    private String nom;
    private String prenom;
    private String courriel;
    private String dosssource;
    private String etat;
    private Socket socket;

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public void setDosssource(String dosssource) {
        this.dosssource = dosssource;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getCourriel() {
        return courriel;
    }

    public String getDosssource() {
        return dosssource;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public UnClient(String id, String pw, String nom, String pnom, String courriel, String dossource) {
        this.identifiant = id;
        this.password = pw;
        this.nom = nom;
        this.prenom = pnom;
        this.courriel = courriel;
        this.dosssource = dossource;
        this.etat = "connecté";
    }
}
