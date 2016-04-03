/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import ClassPartager.UnClient;
import Interface.MainGUI;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *Thread pour la synchronisation des dossier entre client et serveur
 * en lançant deux THread; l'un envoie des fichier et l'autre reçoie les fichier
 */
public class Synchronization implements Runnable {

    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private String destination, source;
    private UnClient client;
    private Thread t1, t2;
    private static MainGUI GUI;

    public Synchronization(ObjectInputStream ois, ObjectOutputStream oos, String src, String dest, UnClient client, MainGUI GUI) {
        this.ois = ois;
        this.oos = oos;
        this.source = src;
        this.client = client;
        this.destination = dest;
        this.GUI = GUI;
    }

    @Override
    public void run() {
        //lançement du Thread pour recevoir les fichiers
        t1 = new Thread(new RecevoirFichier(ois,oos, client, GUI));
        t1.start();
        
        //lançement du Thread pour envoyer les fichiers
        t2 = new Thread(new EnvoieDossier(oos, source, destination));
        t2.start();
    }
}
