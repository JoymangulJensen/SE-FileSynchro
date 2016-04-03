/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import ClassPartager.UnClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Selwyn
 */
public class Clients implements Serializable {

    private List<UnClient> clients; //une liste de clients

    public List<UnClient> getClients() {
        return clients;
    }

    public void setClients(List<UnClient> clients) {
        this.clients = clients;
    }

    public Clients() {
        clients = new ArrayList();
    }

    /**
     * methode qui ajoute un client dans la liste des clients
     * @param c le client à ajouter dans la liste
     * @return vrais si l'ajoute a été réalisé avec success sinon retourne false
     */
    public boolean ajouteClient(UnClient c) {
        if (this.rechClient(c.getIdentifiant()) == null) {
            return this.getClients().add(c);
        } else {
            return false;
        }
    }

    /**
     * suprresion d'un client dans la liste
     * @param c le client à supprimer dans la liste
     * @return vrais si la suppression a été réalisé avec success sinon retourne false
     */
    public boolean suppClient(UnClient c) {
        return this.getClients().remove(c);
    }

    /**
     * recherche un client en fonction de son identifiant
     * @param identifiant l'identifiant du client que l'on souhaite rechercher
     * @return le client correpondant au identifiant si la fonction retourve aucun client elle retourne null
     */
    public UnClient rechClient(String identifiant) {
        for (int i = 0; i < this.getClients().size(); i++) {
            if (this.getClients().get(i).getIdentifiant().equalsIgnoreCase(identifiant)) {
                return this.getClients().get(i);
            }
        }
        return null;
    }

    /**
     * authentification du client avec son mot de pass
     * @param id identifier du client
     * @param pass mot de pass du client
     * @return le client correpondant à l'identifiant et le mot de passe
     */
    public UnClient authentifier(String id, String pass) {
        for (int i = 0; i < this.getClients().size(); i++) {
            if (this.getClients().get(i).getIdentifiant().equals(id)
                    && this.getClients().get(i).getPassword().equals(pass)) {
                return this.getClients().get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s;
        s = " ";
        for (int i = 0; i < this.getClients().size(); i++) {
            s+=this.getClients().get(i).toString();
            s+="\n";
        }
        return s;
    }
}
