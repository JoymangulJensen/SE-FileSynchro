package clients;

import ClassPartager.FileInfo;
import Inteface.MainGUI;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *Thread pour la synchronisation des dossier entre client et serveur
 * en lançant deux THread; l'un envoie des fichier et l'autre reçoie les fichier
 */
class Synchronization implements Runnable {

    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;
    private final FileInfo fileInfo = null;
    public String source, destination;
    private Thread t1, t2;
    private final MainGUI GUI;

    public Synchronization(ObjectInputStream in, ObjectOutputStream out, String src, String dest, MainGUI GI) {
        ois = in;
        oos = out;
        this.source = src;
        this.destination = dest;
        GUI = GI;

    }

    @Override
    public void run() {
        //lançement du Thread pour recevoir les fichiers
        t1 = new Thread(new RecevoirFichier(ois, GUI));
        t1.start();

        //lançement du Thread pour envoyer les fichiers
        t2 = new Thread(new EnvoieDossier(oos, source, destination, GUI));
        t2.start();

    }
}
