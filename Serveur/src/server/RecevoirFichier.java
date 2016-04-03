/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import ClassPartager.FileInfo;
import ClassPartager.UnClient;
import Interface.MainGUI;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *THread pour recevoir et la supression des fichiers
 * mais aussi la deconnexion du client
 */
class RecevoirFichier implements Runnable {

    private ObjectInputStream ois = null;
    private FileInfo fileInfo;
    private File dstFile = null;
    public FileOutputStream fos = null;
    private final UnClient client;
    private ObjectOutputStream oos = null;
    private MainGUI GUI;

    public RecevoirFichier(ObjectInputStream ois, ObjectOutputStream oos, UnClient client, MainGUI GUI) {
        this.ois = ois;
        this.oos = oos;
        this.client = client;
        this.GUI = GUI;    
    }

    @Override
    public void run() {
        while (true) {
            try {
                String etat = ois.readUTF(); //lecture de la tache à faire envoyer par le client
                                            //suivant la tache envoyer le serveur fait appel à la fonction correspondante
                if (etat.equalsIgnoreCase("envoie")) {
                    recevoirDossier();  //fonction pour recevoir des dossiers
                } else if (etat.equalsIgnoreCase("supprimer")) {
                    suppfichier(ois.readUTF()); //fonction pour la supreeesion des dossier
                } else if(etat.equalsIgnoreCase("modification")) {
                    recevoirModification();     //fonction pour recevoir les modification apporter à un fichier
                }
                else if (etat.equalsIgnoreCase("Deconnecté")) { //deconnextion du client
                    client.setEtat("Deconnecté");   // mettre etat du client comme deconnecté
                    client.setSocket(null);
                    ois.close();
                    oos.close();
                    this.GUI.creationGal(); //regeneration de la liste des clients dans l'interface
                    Thread.currentThread().stop();
                }
            } catch (IOException ex) {
                Thread.currentThread().stop();
                Logger.getLogger(RecevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Methode recrsive pour supprimer un dossier avec ses sous dossier
     * @param chemin chemin du dossier ou fichier a supprimer
     */
    public void suppfichier(String chemin) {
        Path path = Paths.get(chemin);
        if (path.toFile().isDirectory()) {  //si c'esst un dossier
            try (DirectoryStream<Path> listing = Files.newDirectoryStream(path)) {
                if (path != null) {
                    for (Path nom : listing) {  //parsourir les fichier et sou dossier
                        if (!Files.isDirectory(nom)) {
                            Files.delete(nom);
                        } else {
                            suppfichier(nom.toFile().getAbsolutePath());
                        }
                    }
                    Files.delete(path);
                } else {
                    Files.delete(path);
                }
            } catch (IOException e) {
                File file = new File(chemin);
                file.delete();
            }
        } else {
            File file = new File(chemin);
            file.delete();
        }
    }

    /**
     * Methode pour recevoir les dossiers
     */
    public void recevoirDossier() {
        try {
            fileInfo = (FileInfo) ois.readObject(); //lire les informations du fichier
            //chemin du fichier en sortie
            String outputFile = fileInfo.getDestinationDirectory() + fileInfo.getFilename();
            dstFile = new File(outputFile);
            if (!new File(fileInfo.getDestinationDirectory()).exists()) {   //si le fichier n'existe pas
                new File(fileInfo.getDestinationDirectory()).mkdirs();  //creation du fichier
                fos = new FileOutputStream(dstFile);
                fos.write(fileInfo.getFileData());      //ecrire les données dans le fichier
                fos.flush();
                fileInfo.setFileData(null);
                System.gc();
                fos.close();
                dstFile.setLastModified(fileInfo.getModifier());

            } else if (dstFile.lastModified() < fileInfo.getModifier()) {   //si le fichier existe et que c'est une nouvelle version alors
                fos = new FileOutputStream(dstFile);
                fos.write(fileInfo.getFileData());
                fos.flush();
                fileInfo.setFileData(null);
                fos.close();
                dstFile.setLastModified(fileInfo.getModifier());
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RecevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Methode pour recevoir les modification apporté à un dossier
     */
    public void recevoirModification() {
        try {
            fileInfo = (FileInfo) ois.readObject(); //lire les informations du fichier
            //chemin du fichier en sortie
            String outputFile = fileInfo.getDestinationDirectory() + fileInfo.getFilename();
            dstFile = new File(outputFile);
            //creation sous forme de dd-MM-yyyy HH-mm-ss la dernière date modifier
            String dateMod = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date(dstFile.lastModified()));
            //creation du dossier pour mettre l'ancienne version du fichier
            String oldDest = "../SerDoss/" + client.getIdentifiant() + "/OLD/" + dstFile.getName() + "/" + dateMod + "/";
            creerRepertoire(oldDest);
            //copier l'ancienne version dans le dossier qui vient d'e^tre créer
            copie(dstFile.getCanonicalPath(), oldDest).toFile().setLastModified(dstFile.lastModified());
            fos = new FileOutputStream(dstFile);
            fos.write(fileInfo.getFileData());
            fos.flush();
            fileInfo.setFileData(null);
            fos.close();
            dstFile.setLastModified(fileInfo.getModifier());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RecevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Methode pour créer un repertoire
     * @param chemin le chelin ou on veut créer un dossier
     * @return  le path du repertoire créer
     * @throws IOException 
     */
    public Path creerRepertoire(String chemin) throws IOException {
        Path monRepertoire = Paths.get(chemin);
        Path file = Files.createDirectories(monRepertoire);
        return file;
    }

    /**
     * methode pour recopier un fichier
     * @param source chemin su fichier source
     * @param destination chemine de la destination
     * @return path du fichier recopier
     * @throws IOException 
     */
    public Path copie(String source, String destination) throws IOException {
        Path root = Paths.get(source);
        Path cible = Paths.get(destination);

        Path copy;
        copy = Files.copy(root, cible.resolve(root.getFileName()), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);

        return copy;
    }
}
