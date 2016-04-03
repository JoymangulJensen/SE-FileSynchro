/*
 * To change this template, choose Tools | Templates
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
 *THread pour connecter au serveur un client qui exsite déjà et le client rentre son identifiant et son mot de pass
 */
public class Connexion implements Runnable {

    public static Thread t;
    public static String login = null, pass = null, source = null, destination = null;
    public static UnClient client = null;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean connect = false;
    private final MainGUI GUI;

    public Connexion(ObjectInputStream ois, ObjectOutputStream oos, MainGUI GI, String log, String mp) {
        this.ois = ois;
        this.oos = oos;
        login = log;
        pass = mp;
        GUI = GI;
    }

    @Override
    public void run() {
        try {
            oos.writeInt(1);    //envoire 1 au serveur pour lui dire aue un client existant veut de connecter
            oos.flush();
            while (!connect) {
                oos.writeUTF(login);    //envoie l'identifiant
                oos.flush();
                oos.writeUTF(pass);     //envoie le mot de pass
                oos.flush();

                GUI.cilentaccepter = true;      //le client s'est connecté

                client = (UnClient) ois.readObject();   // retourne les info du clients

                //afficher tout les info du client dans l'interface
                GUI.nom.setText(client.getNom()+" "+client.getPrenom());
                GUI.identifiant1.setText(client.getIdentifiant());
                GUI.courriel.setText(client.getCourriel());
                GUI.dossier.setText(client.getDosssource());
                GUI.etat.setForeground(Color.GREEN);
                GUI.etat.setText("connecté");
                
                destination = ois.readUTF();
                connect = true;
                
                //lancement du Thread de synchronisation
                t = new Thread(new Synchronization(ois, oos, client.getDosssource(), destination, GUI));
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Le serveur ne répond plus");
            GUI.cilentaccepter = false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
