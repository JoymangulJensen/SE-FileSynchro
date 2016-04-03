/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ClassPartager.UnClient;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import server.AccepterConnexion;
import server.Clients;

/**
 *
 * @author Selwyn
 */
public class MainGUI extends javax.swing.JFrame {

    /**
     * Creates new form MainGUI
     */
    public ServerSocket ss = null;
    public Thread t;
    public Clients listclients = new Clients(); //Liste de tout les clients

    /**
     * initialisation de l'interface princiaple et le lancement du JDialoge pour
     * choisir de port de connection
     */
    public MainGUI() throws IOException {
        //changement du theme
        UIManager.put("nimbusBase", new Color(63, 62, 62));
        UIManager.put("nimbusBlueGrey", new Color(63, 62, 62));
        UIManager.put("control", new Color(63, 62, 62));
        //fin de changement du theme

        initComponents();

        //lancement du JDialog pour choisir le port de connection
        ServerCon servercon = new ServerCon(this, true);
        servercon.setVisible(true);
        int portcon = 3540;
        if (servercon.ok) {
            portcon = servercon.PORT;   //recuperation du port entrée
        }
        //fin lancement du JDialog

        //connection du serveur
        ss = new ServerSocket(portcon);
        port.setText(String.valueOf(ss.getLocalPort()));    //recuperation du port du serveur
        ip.setText(InetAddress.getLocalHost().getHostAddress());    //recuperation de IP locale de la machine serveur
        chargeClient();     //chargement de la liste des clients
        creationGal();      //creation de la liste des clients sur l'interface
        //lancement du thread pour accepter les clients
        t = new Thread(new AccepterConnexion(ss, listclients, this));
        t.start();

    }

    /**
     * fonction pour afficher tout les clients dans la liste des clients
     */
    public void creationGal() {
        MainGUI.panClients.removeAll(); //supprimer tout les elements présents sur le panel ou on afficher les clients

        //parcourir la liste des clietns
        for (final UnClient client : listclients.getClients()) {
            //creation d'un JPanel pour mettre tout les informations concernant un client
            JPanel pan = new JPanel();
            pan.setBackground(new Color(63, 62, 62));
            pan.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));     //mettre le panel en FlowLayout

            //Creation du JLabel pour mettre l'identifiant du client
            JLabel ID = new JLabel();
            ID.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
            ID.setForeground(new java.awt.Color(11, 161, 224));
            ID.setPreferredSize(new Dimension(75, 30));
            ID.setText(client.getIdentifiant());
            pan.add(ID);
            //Fin JLabel identifiant

            //Creation du JLabel pour mettre le nom du client
            JLabel nom = new JLabel();
            nom.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
            nom.setForeground(new java.awt.Color(11, 161, 224));
            nom.setPreferredSize(new Dimension(90, 30));
            nom.setText(client.getNom());
            pan.add(nom);
            //Fin JLabel nom

            //Creation du JLabel pour mettre le prenom du client
            JLabel prenom = new JLabel();
            prenom.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
            prenom.setForeground(new java.awt.Color(11, 161, 224));
            prenom.setPreferredSize(new Dimension(90, 25));
            prenom.setText(client.getPrenom());
            pan.add(prenom);
            //Fin JLabel prenom

            //Creation du JLabel pour mettre l'état du client
            JLabel etat = new JLabel();
            etat.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16));
            etat.setPreferredSize(new Dimension(90, 25));
            if (client.getEtat().equalsIgnoreCase("Deconnecté")) {
                //mettre l"etat en rouge si le client est deconnecté
                etat.setForeground(new java.awt.Color(255, 0, 0));
            } else {
                //mettre l"etat en vert si le client est connecté
                etat.setForeground(new java.awt.Color(0, 255, 0));
            }
            etat.setText(client.getEtat());
            pan.add(etat);
            //Fin JLabel etat

            //Creation du bouton "kick" pour deconecter un client
            final JLabel kick = new JLabel(new ImageIcon("../Image/kick.png"));
            kick.setPreferredSize(new Dimension(100, 30));
            if (client.getEtat().equalsIgnoreCase("Deconnecté")) {
                kick.enable(false);
            }
            kick.addMouseListener(new java.awt.event.MouseListener() {

                @Override
                /**
                 * action à faire en cliquant sur le bouton "kick"
                 */
                public void mouseClicked(MouseEvent me) {
                    try {
                        if (!client.getEtat().equalsIgnoreCase("Deconnecté")) {
                            client.setEtat("Deconnecté");
                            client.getSocket().close(); //deconnecté le client
                            client.setSocket(null);
                            creationGal();  //regeneration de l'afficahe de la liste des clients
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    kick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/kickClick.png")));
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    kick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/kickHover.png")));
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    kick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/kick.png")));
                }
            });
            pan.add(kick);
            //fin du bouton "kick"

            //Creation du bouton "ban" pour sprrimer le client dans la liste des clients
            final JLabel ban = new JLabel(new ImageIcon("../Image/ban.png"));
            ban.setPreferredSize(new Dimension(100, 30));
            ban.addMouseListener(new java.awt.event.MouseListener() {

                @Override
                /**
                 * action à faire en cliquant sur le bouton "ban"
                 */
                public void mouseClicked(MouseEvent me) {
                    try {
                        if (client.getEtat().equalsIgnoreCase("Connecté")) {
                            client.getSocket().close(); //deconnection du clients
                            listclients.suppClient(client);
                            creationGal();
                        } else {
                            
                            listclients.suppClient(client); //supprimer du client dna sla liste
                            creationGal();  //regeneration de l'afficahe de la liste des clients
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    ban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/banClick.png")));
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    ban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/banHover.png")));
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    ban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ban.png")));
                }
            });
            pan.add(ban);
            //fin du bouton "ban"

            MainGUI.panClients.add(pan);
        }
        MainGUI.panClients.revalidate();
    }

    /**
     * Methode pour chargé la liste des clients
     */
    public void chargeClient() throws IOException {
        try {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("listclient.info42"))) {
                listclients = (Clients) ois.readObject();   //lecture de la liste
            } //lecture de la liste
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * sauvegarde de la liste des clients dans un fichier externe
     */
    public void sauvegardeClient() {
        try {
            File fichier;          //création du fichier
            fichier = new File("listclient.info42");
            fichier.createNewFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
                oos.writeObject(listclients);           //sauvegarder la lsite des clients
                oos.flush();
                oos.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deconnex = new javax.swing.JLabel();
        ip = new javax.swing.JLabel();
        port = new javax.swing.JLabel();
        labelIP = new javax.swing.JLabel();
        labelPORT = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panClients = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(63, 62, 62));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deconnex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/deconnexBut.png"))); // NOI18N
        deconnex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deconnexMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deconnexMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deconnexMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                deconnexMousePressed(evt);
            }
        });
        getContentPane().add(deconnex, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, 40));

        ip.setFont(new java.awt.Font("Segoe UI Black", 0, 30)); // NOI18N
        ip.setForeground(new java.awt.Color(11, 161, 224));
        getContentPane().add(ip, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 250, 50));

        port.setFont(new java.awt.Font("Segoe UI Black", 0, 30)); // NOI18N
        port.setForeground(new java.awt.Color(11, 161, 224));
        getContentPane().add(port, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 90, 50));

        labelIP.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        labelIP.setForeground(new java.awt.Color(11, 161, 224));
        labelIP.setText("IP :");
        getContentPane().add(labelIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 50, 50));

        labelPORT.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        labelPORT.setForeground(new java.awt.Color(11, 161, 224));
        labelPORT.setText("PORT : ");
        getContentPane().add(labelPORT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 100, 50));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panClients.setBackground(new java.awt.Color(63, 62, 62));
        panClients.setForeground(new java.awt.Color(63, 62, 62));
        panClients.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane1.setViewportView(panClients);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 610, 350));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * action faite en appuyant sur la touch e de deconnexion
     */
    private void deconnexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexMouseClicked
        this.sauvegardeClient();
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_deconnexMouseClicked

    private void deconnexMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexMouseEntered
        deconnex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/deconnexButHover.png")));
    }//GEN-LAST:event_deconnexMouseEntered

    private void deconnexMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexMouseExited
        deconnex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/deconnexBut.png")));
    }//GEN-LAST:event_deconnexMouseExited

    private void deconnexMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deconnexMousePressed
        deconnex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/deconnexButClick.png")));
    }//GEN-LAST:event_deconnexMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new MainGUI().setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel deconnex;
    private static javax.swing.JLabel ip;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelIP;
    private javax.swing.JLabel labelPORT;
    private static javax.swing.JPanel panClients;
    private static javax.swing.JLabel port;
    // End of variables declaration//GEN-END:variables
}
