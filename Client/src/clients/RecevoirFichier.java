package clients;

import ClassPartager.FileInfo;
import Inteface.MainGUI;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * THread pour recevoir et la supression des fichiers mais aussi la deconnexion
 * du client
 */
class RecevoirFichier implements Runnable {

    private ObjectInputStream ois = null;
    private FileInfo fileInfo;
    private File dstFile = null;
    public FileOutputStream fos = null;
    private final MainGUI GUI;

    public RecevoirFichier(ObjectInputStream ois, MainGUI GI) {
        this.ois = ois;
        GUI = GI;
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
                }
            } catch (IOException ex) {
                GUI.etat.setForeground(Color.RED);
                GUI.etat.setText("deconnecté");     //mettre etat du client comme deconnecté
                Thread.currentThread().stop();
                Logger.getLogger(RecevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Methode recrsive pour supprimer un dossier avec ses sous dossier
     *
     * @param chemin chemin du dossier ou fichier a supprimer
     */
    public void suppfichier(String chemin) {
        Path path = Paths.get(chemin);
        if (path.toFile().isDirectory()) {
            try (DirectoryStream<Path> listing = Files.newDirectoryStream(path)) {
                if (path != null) {
                    for (Path nom : listing) {
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
                System.out.println("path invalide");
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
                new File(fileInfo.getDestinationDirectory()).mkdirs();      //creation du fichier
                fos = new FileOutputStream(dstFile);
                fos.write(fileInfo.getFileData());       //ecrire les données dans le fichier
                fos.flush();
                fileInfo.setFileData(null);
                System.gc();
                fos.close();
                GUI.reception.setText(dstFile.getName() + "\n" + GUI.reception.getText());  //afficher le nom du fichier reçu

            } else if (dstFile.lastModified() < fileInfo.getModifier()) {   //si le fichier existe et que c'est une nouvelle version alors
                fos = new FileOutputStream(dstFile);
                fos.write(fileInfo.getFileData());
                fos.flush();
                fileInfo.setFileData(null);
                fos.close();
                dstFile.setLastModified(fileInfo.getModifier());
                GUI.reception.setText(dstFile.getName() + "\n" + GUI.reception.getText());  //afficher le nom du fichier reçu
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(RecevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
