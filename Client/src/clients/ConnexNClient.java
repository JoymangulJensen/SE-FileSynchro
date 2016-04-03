/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import ClassPartager.UnClient;
import Inteface.MainGUI;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Thread pour connecter au serveur un nouveau client et envoie tout les informations du client
 */
public class ConnexNClient implements Runnable {

    public static Thread t;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final UnClient client;
    private final MainGUI GUI;
    private String destination;

    public ConnexNClient(ObjectInputStream ois, ObjectOutputStream oos, UnClient client,MainGUI GUI) {
        this.ois = ois;
        this.oos = oos;
        this.client = client;
        this.GUI = GUI;
    }

    @Override
    public void run() {
        try {
            oos.writeInt(2);    //envoie 2 au serveur pour lui dire qu'un nouveau client veut se connecter
            oos.flush();
            oos.writeObject(client);    //envoie les infirmations du clients
            GUI.cilentaccepter = ois.readBoolean(); //le serveur dit au client s'il a bien ajouter le client
            
            destination = "..//SerDoss//" +client.getIdentifiant() + "//";
            if(GUI.cilentaccepter)
            {
                //afficher les info du client dans l'interface
                GUI.nom.setText(client.getNom()+" "+client.getPrenom());
                GUI.identifiant1.setText(client.getIdentifiant());
                GUI.courriel.setText(client.getCourriel());
                GUI.dossier.setText(client.getDosssource());
                GUI.etat.setForeground(Color.GREEN);
                GUI.etat.setText("connecté");
                t = new Thread(new Synchronization(ois,oos,client.getDosssource(),destination, GUI));
                t.start();
            }
        } catch (IOException ex) {
            System.out.println("erreur");
            Logger.getLogger(ConnexNClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
