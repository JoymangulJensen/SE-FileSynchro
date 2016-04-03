package server;

import ClassPartager.UnClient;
import Interface.MainGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Methode pour accepter le client voulant ce connecter au serveur
 * apres soit une authentification si le client existe déjà  en lançant le Thread Authentification
 * sinon soit la creation d'un nouveau client en lançant le Thread AccepterNConnexion
 */
public class AccepterConnexion implements Runnable {

    private ServerSocket socketserver = null;
    private Socket socket = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    public Thread t1;
    public Clients listclient;
    private MainGUI GUI;

    public AccepterConnexion(ServerSocket ss, Clients clients, MainGUI GUI) {
        socketserver = ss;
        listclient = clients;
        this.GUI = GUI;
    }

    public void run() {
        try {
            while (true) {
                socket = socketserver.accept();
                System.out.println("Un client veut se connecter  ");
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                //le client envoie 1 si le client exite déja sinon elle envoie 2 si c'est un nouveau client
                //et si le client envoie 3 la fonction le deconnect
                int i = ois.readInt();  //lecture du type du client
                if (i == 1) {
                    //lancement du Thread d'authentification
                    t1 = new Thread(new Authentification(socket, ois, oos, listclient,GUI));
                    t1.start();
                }
                else if(i == 2){
                    //lancement du Thread pour créer un nouveau client
                    t1 = new Thread(new AccepterNConnexion(socket, ois, oos, listclient,GUI));
                    t1.start();
                } else if(i == 3) {
                    //deconnexion du client
                    socket.close();
                }

            }
        } catch (IOException e) {
            System.err.println("Erreur serveur");
        }

    }
}