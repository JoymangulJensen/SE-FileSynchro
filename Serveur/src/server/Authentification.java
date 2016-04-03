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
 * Cette methode permet de verifier si un client existe dans la liste des clients
 * et elle permet a ce client de ce connecter au serveur sinon la methode deconnect le client
 */
public class Authentification implements Runnable {

    private Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private static String login = null, pass = null, destination = null, source = null;
    public boolean authentifier = false;
    public static String authentification = null;
    public Thread t2;
    Clients listClient;
    UnClient client;
    public MainGUI GUI;

    Authentification(Socket socket, ObjectInputStream in, ObjectOutputStream out, Clients listClient,MainGUI GUI) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.listClient = listClient;
        this.GUI = GUI;
    }

    @Override
    public void run() {
            while (!authentifier) {
                try {
                    //lecture de identiifiant
                    login = in.readUTF();
                    System.out.println("Login : " + login);
                    
                    //lecture du mot de passe
                    pass = in.readUTF();
                    System.out.println("Password : " + pass);
                    
                    //verification si le cilent existe 
                    if (listClient.authentifier(login, pass) != null) {
                        client = listClient.authentifier(login, pass);
                        client.setEtat("Connecté"); //mrttr l'etat du client comme connecté
                        out.writeObject(client);    //envoie de toute les ifnormations du client 
                        client.setSocket(socket); 
                        destination = client.getDosssource() + "/"; //le dossier sur la machine client
                        source = "../SerDoss/"+ client.getIdentifiant() + "/";  //le dossier sur la machine serveur
                        out.writeUTF(source);   //envoie le chemin du dossier ver le cilent
                        out.flush();
                        authentifier = true;
                        this.GUI.creationGal(); //regeneration de la liste des cilents sur l'interface
                        //lancement du Thread de synchronization pou synchroniser tout les dossier entre et serveur
                        t2 = new Thread(new Synchronization(in, out, source, destination, client, GUI));
                        t2.start();
                    } else {
                        //le cilent n'exute pas ou l'authentification a echouer alors il y une erreur et le client est deconnecté
                        out.flush();
                        out.close();
                        in.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
                }

            }


    }
}
