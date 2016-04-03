/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import ClassPartager.UnClient;
import Interface.MainGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Cette methode permet d'accepter un nouveau au client et ainsi ajouter ce client dans la liste des clients
 */
public class AccepterNConnexion implements Runnable {
    private Socket socket;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    public Thread t1;
    public UnClient client;
    public Clients listclient;
    private MainGUI GUI;


    public AccepterNConnexion(Socket socket, ObjectInputStream in, ObjectOutputStream out, Clients listClient, MainGUI GUI) {
        this.socket = socket;
        this.ois = in;
        this.oos = out;
        this.listclient = listClient;
        this.GUI  = GUI;
    }

    @Override
    public void run() {
        try {
            client = (UnClient) ois.readObject();   //lecture des infos envoyé par le client
            client.setSocket(socket);
            
            boolean clientajouter = listclient.ajouteClient(client);    //retourne vrai si le client a bien été ajouter dans la liste
            oos.writeBoolean(clientajouter);    //envoie au client s'il a bien été ajouter dans le serveur

            if (clientajouter) {    //si le client a bien été ajouter
                this.GUI.creationGal(); //regeneration de la liste des clients à afficher dans l'interface
                //lancement du Thread pour synchroniser les dossier entre clients et serveur
                t1 = new Thread(new Synchronization(ois, oos, "..//SerDoss//" + client.getIdentifiant() + "//", client.getDosssource() + "//",client, GUI));
                t1.start();
            }
            else {
                //si le client n'a pas été ajouter alors le serveur ferme c'est flux
                oos.close();
                ois.close();
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(AccepterNConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
