/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inteface;

/**
 *
 * @author Selwyn
 */
public class ClientCon extends javax.swing.JDialog {

    public boolean ok;
    public String IP;
    public int PORT;

    /**
     * Creates new form NewJDialog
     *
     * @param parent
     * @param modal
     */
    public ClientCon(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        port = new javax.swing.JTextField();
        ip = new javax.swing.JTextField();
        connection = new javax.swing.JLabel();
        fermer = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAutoRequestFocus(false);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        port.setBackground(new java.awt.Color(62, 62, 62));
        port.setFont(new java.awt.Font("Segoe UI Semilight", 0, 42)); // NOI18N
        port.setForeground(new java.awt.Color(11, 161, 224));
        port.setText("3540");
        port.setToolTipText("");
        port.setBorder(null);
        port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portActionPerformed(evt);
            }
        });
        getContentPane().add(port, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 100, 50));

        ip.setBackground(new java.awt.Color(62, 62, 62));
        ip.setFont(new java.awt.Font("Segoe UI Semilight", 0, 42)); // NOI18N
        ip.setForeground(new java.awt.Color(11, 161, 224));
        ip.setText("127.0.0.1");
        ip.setToolTipText("");
        ip.setBorder(null);
        ip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipActionPerformed(evt);
            }
        });
        getContentPane().add(ip, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 240, 50));

        connection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ConBut2.png"))); // NOI18N
        connection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connectionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                connectionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                connectionMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                connectionMousePressed(evt);
            }
        });
        getContentPane().add(connection, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        fermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/CloseBut.png"))); // NOI18N
        fermer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fermerMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fermerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fermerMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fermerMousePressed(evt);
            }
        });
        getContentPane().add(fermer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ClientCon.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void connectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMouseEntered
        this.connection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ConBut2Hover.png")));
    }//GEN-LAST:event_connectionMouseEntered

    private void connectionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMouseExited
        this.connection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ConBut2.png")));
    }//GEN-LAST:event_connectionMouseExited

    private void connectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMouseClicked
        this.IP = this.ip.getText();    //recupérer l'ip 
        this.PORT = Integer.parseInt(this.port.getText());  //recupérer le port
        ok = true;

        setVisible(false);
        dispose();
    }//GEN-LAST:event_connectionMouseClicked

    private void connectionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMousePressed
        this.connection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ConBut2Click.png")));
    }//GEN-LAST:event_connectionMousePressed

    private void fermerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMouseEntered
        this.fermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/CloseButHover.png")));
    }//GEN-LAST:event_fermerMouseEntered

    private void fermerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMouseExited
        this.fermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/CloseBut.png")));
    }//GEN-LAST:event_fermerMouseExited

    private void fermerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMousePressed
        this.fermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/CloseButClick.png")));
    }//GEN-LAST:event_fermerMousePressed

    private void portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portActionPerformed

    private void ipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipActionPerformed

    private void fermerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMouseClicked
        ok = false;
        this.dispose(); //fermeture du JDialog
        System.exit(0);
    }//GEN-LAST:event_fermerMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientCon dialog = new ClientCon(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel connection;
    private javax.swing.JLabel fermer;
    private javax.swing.JTextField ip;
    private javax.swing.JTextField port;
    // End of variables declaration//GEN-END:variables
}