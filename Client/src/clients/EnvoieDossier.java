 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import ClassPartager.FileInfo;
import Inteface.MainGUI;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Envoie des dossier vers le serveur
 */
class EnvoieDossier implements Runnable {

    private static ObjectOutputStream oos = null;
    private String source, destination;
    private FileInfo fileInfo = null;
    WatchService watcher;
    private final MainGUI GUI;
    int taileDos, max;

    public EnvoieDossier(ObjectOutputStream out, String src, String dest, MainGUI GI) {
        oos = out;
        source = src;
        destination = dest;
        GUI = GI;
        taileDos = getFolderSize(new File(source));
        max = 200000000;
    }

    @Override
    public void run() {
        try {
            GUI.quota.setMaximum(max);  //mettre le nombre maximum dans le Jprogressbar
            taileDos = getFolderSize(new File(source));
            GUI.quota.setValue(taileDos);   //mettre la taille du dossier 
            String textTaille = String.valueOf(taileDos + " / " + max);
            GUI.quota.setString(textTaille);
            this.envoieDossier(source, destination);
            
            watcher = FileSystems.getDefault().newWatchService();   //pour surveriler les dossiers 
            File src = new File(source);
            this.registerAll(src.toPath());     //pour enregistrer tout les dossier et sous dossier dans le watcher
            while (true) {
                taileDos = getFolderSize(new File(source));     //recupérer la taille du dossier
                this.DossierModifier();
                taileDos = getFolderSize(new File(source));     //recupérer la taille du dossier
                GUI.quota.setValue(taileDos);
                textTaille = String.valueOf(taileDos);
                GUI.quota.setString(textTaille);
            }
        } catch (IOException ex) {
            Logger.getLogger(EnvoieDossier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Methode recursive pour calculer la taille d'un dossier 
     * @param dir le dossier que l'on veut recupérer sa taille
     * @return la taille du dossier passé en paramètre
     */
    public static int getFolderSize(File dir) {
        int size = 0;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }
        return size;
    }

    /**
     * Methode qui enregistre tout les dossier et sous dossier dans le watcher 
     * methode prise sur internet : http://docs.oracle.com/javase/tutorial/essential/io/examples/WatchDir.java
     */
    private void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Méthode qui regarde les evenement opérer sur un fichier et suivant les evênement
     * et tâches correspondants sont exécutés
     */
    public void DossierModifier() throws IOException {

        try {

            WatchKey watchKey = watcher.take(); //le watchkey c'est comme le système de jeton,on prend un jeton
            System.gc();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                if (event.kind() == ENTRY_CREATE) { //si un fichier est créer
                    System.gc();
                    System.out.println("Create: " + event.context().toString());
                    Path x = (Path) watchKey.watchable(); //path du dossier parent à celui qui vient d'être créer
                    //chemin complete du fichier créer
                    String srcMod = x.toAbsolutePath().toString() + "\\" + event.context().toString();
                     //chemin du parent du fichier créer
                    String path = x.toAbsolutePath().toString() + "\\";
                    //le chemin de la source
                    String base = source;
                    //faire le chemin du fichier créer relative au dossier source
                    String relative = new File(base).toURI().relativize(new File(srcMod).toURI()).getPath();
                    Thread.sleep(5000);

                    if (new File(srcMod).isDirectory()) { //si on créer un dossier alors
                        File src = new File(source);
                        this.registerAll(src.toPath());
                        envoieDossier(srcMod, destination + relative + "\\");   //appel à la methode pour envoyer un dossier
                    } else {
                        relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
                        envoieFichier(srcMod, destination + relative + "\\");   //appel à la methode pour envoyer un fichier
                    }

                    watchKey.reset();
                }

                if (event.kind() == ENTRY_DELETE) { //si un fichier a été suprimé
                    System.gc();
                    Path x = (Path) watchKey.watchable();   //path du dossier parent à celui qui vient d'être supprimer
                    //chemin complete du fichier supprimer
                    String srcMod = x.toAbsolutePath().toString() + "\\" + event.context().toString();
                    File file = new File(srcMod);
                    file.delete();  //suprresion du fichier
                    //chemin du parent du fichier supprimer
                    String path = x.toAbsolutePath().toString() + "\\";
                    String base = source;
                    //faire le chemin du fichier créer relative au dossier source
                    String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
                    //appel à la methode pour suprimer le fichier côté client aussi 
                    suppFichier(destination + relative + "/" + event.context().toString());
                    watchKey.reset();
                }

                if (event.kind() == ENTRY_MODIFY) { //si un fichier a été modifié
                    System.gc();
                    Path x = (Path) watchKey.watchable();   //path du dossier parent à celui qui vient d'être modifier
                    //chemin complete du fichier modifier
                    String srcMod = x.toAbsolutePath().toString() + "\\" + event.context().toString();
                    //chemin du parent du fichier modiier
                    String path = x.toAbsolutePath().toString() + "\\";
                    String base = source;
                    //faire le chemin du fichier créer relative au dossier source
                    String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
                    if (new File(srcMod).isFile()) {
                        envoieModification(srcMod, destination + relative);     //envoire le fichier
                    }
                    watchKey.reset();
                }
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(EnvoieDossier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * methode pour supprimer un client cpoté client 
     * @param chemin chemin du fichier quîl faut suprrimer côté client
     */
    public void suppFichier(String chemin) {
        try {
            oos.writeUTF("supprimer");
            oos.flush();
            oos.writeUTF(chemin);       //envoie le chemin a suprrimer côté client
            oos.flush();    
        } catch (IOException ex) {
            Logger.getLogger(EnvoieDossier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * methode récursive pour envoyer in dossier 
     * @param source chemmin source du fichier à envoyer
     * @param destination chemin destination du fichier à envoyer
     */
    public void envoieDossier(String source, String destination) {
        File srcDir = new File(source);
        if (!srcDir.isDirectory()) {     //si le chemin source n'est pas un dossier
            //envoie le fichier grâce à la methode anvoieFichier
            envoieFichier(srcDir.getAbsolutePath(), destination);
        } else {    //si c'est un dossier alors
            File[] files = srcDir.listFiles();      //listing de tout les fichier dans le dossier
            int fileCount = files.length;   //nombre de fichier contenu dans le dossier
            for (int i = 0; i < fileCount; i++) {   //parcourir tout les fichier
                if (files[i].isFile()) {
                    envoieDossier(files[i].getPath(), destination);
                } else {
                    envoieDossier(files[i].getPath(), destination + files[i].getName() + "\\");
                }
            }
        }
    }

    /**
     * methode pour envoyer un fichier 
     * @param source chemmin source du fichier à envoyer
     * @param destination chemin destination du fichier à envoyer
     */
    public void envoieFichier(String source, String destination) {

        taileDos = getFolderSize(new File(this.source));
        GUI.quota.setValue(taileDos);
        String textTaille = String.valueOf(taileDos + "/" + max);
        GUI.quota.setString(textTaille);
        if (taileDos < max) {   //verification si le quota n'as pas été dépasée
            GUI.envoie.setText(new File(source).getName() + "\n" + GUI.envoie.getText());
            fileInfo = new FileInfo();      //creation de FileInfo pour mettre tout les info du fichier à envoyer
            fileInfo.setDestinationDirectory(destination);  //mettre le chemin destination
            fileInfo.setSourceDirectory(source);    //mettre le chemin source
            File file = new File(source);
            fileInfo.setModifier(file.lastModified());      //recupérer la dernière date modifier
            fileInfo.setFilename(file.getName());   //mettre le nom du fichier à envoyer
            DataInputStream diStream = null;
            try {
                diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];

                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileInfo.setFileData(fileBytes);
                fileBytes = null;
                fileInfo.setStatus("Success");

            } catch (IOException e) {
                fileInfo.setStatus("Error");

            }

            try {
                oos.writeUTF("envoie");
                oos.flush();
                oos.writeObject(fileInfo);  //envoie de tout les info du fichier
                fileInfo = null;
                System.gc();
            } catch (IOException e) {
            }
        } else {
            System.out.println("eafvgaqegvzegv000");
            GUI.quota.setString("Quote depassée");
        }
    }

    public void envoieModification(String source, String destination) {
        taileDos = getFolderSize(new File(this.source));
        GUI.quota.setValue(taileDos);
        String textTaille = String.valueOf(taileDos + "/" + max);
        GUI.quota.setString(textTaille);

        if (taileDos < max) {
            GUI.envoie.setText(new File(source).getName() + "\n" + GUI.envoie.getText());
            fileInfo = new FileInfo();
            fileInfo.setDestinationDirectory(destination);
            fileInfo.setSourceDirectory(source);
            File file = new File(source);
            fileInfo.setModifier(file.lastModified());
            System.out.println(fileInfo.getModifier());
            fileInfo.setFilename(file.getName());
            DataInputStream diStream = null;
            try {
                diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];

                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileInfo.setFileData(fileBytes);
                fileBytes = null;
                fileInfo.setStatus("Success");

            } catch (IOException e) {
                fileInfo.setStatus("Error");

            }

            try {
                oos.writeUTF("modification");
                oos.flush();
                oos.writeObject(fileInfo);
                fileInfo = null;
                System.gc();
            } catch (IOException e) {
            }
        } else {
            System.out.println("eafvgaqegvzegv000");
            GUI.quota.setString("Quote depassée");
        }
    }
}
