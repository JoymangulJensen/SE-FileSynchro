/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassPartager;

/**
 *
 * @author jj128194
 */
import java.io.Serializable;

public class FileInfo implements Serializable {

    //constructeur par defaut
    public FileInfo() {
    }

    private static final long serialVersionUID = 1L;
    private String destination; //destination du fichier
    private String source;      //source du fichier
    private String nom;         //nom du fichier
    private long fileSize;      //contenant la taille du fichier
    private byte[] fileData;    //contenant le tableau de byte du fichier
    private String status;      //status du fichier durant le transfer
    private int remainder;
    private long modifier;      //l dernier date de modification du fichier

    public String getDestinationDirectory() {
        return destination;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destination = destinationDirectory;
    }

    public String getSourceDirectory() {
        return source;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.source = sourceDirectory;
    }

    public String getFilename() {
        return nom;
    }

    public void setFilename(String filename) {
        this.nom = filename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public long getModifier() {
        return modifier;
    }

    public void setModifier(long modifier) {
        this.modifier = modifier;
    }
}
