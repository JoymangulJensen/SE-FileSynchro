/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

/**
 *
 * @author Selwyn
 */
public class ServerCon extends javax.swing.JDialog {

    public boolean ok;
    public int PORT;
    /**
     * Creates new form ServerCon
     */
    public ServerCon(java.awt.Frame parent, boolean modal) {
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

        connection = new javax.swing.JLabel();
        fermer = new javax.swing.JLabel();
        port = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        getContentPane().add(connection, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, -1));

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
        getContentPane().add(fermer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

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
        getContentPane().add(port, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 190, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ServerCon.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fermeture du JDialig en appuyant sur le bouton fermer
     */
    private void fermerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMouseClicked
        ok = false;
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_fermerMouseClicked

    private void fermerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMouseEntered
        this.fermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/CloseButHover.png")));
    }//GEN-LAST:event_fermerMouseEntered

    private void fermerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMouseExited
        this.fermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/CloseBut.png")));
    }//GEN-LAST:event_fermerMouseExited

    private void fermerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fermerMousePressed
        this.fermer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/CloseButClick.png")));
    }//GEN-LAST:event_fermerMousePressed

    /**
     * Fermeture du JDialog en appuyant sur le bouton connecter
     * Recuperation du port entré
     */
    private void connectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMouseClicked

        ok = true;
        this.PORT = Integer.parseInt(this.port.getText());
        setVisible(false);
        dispose();
    }//GEN-LAST:event_connectionMouseClicked

    private void connectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMouseEntered
        this.connection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ConBut2Hover.png")));
    }//GEN-LAST:event_connectionMouseEntered

    private void connectionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMouseExited
        this.connection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ConBut2.png")));
    }//GEN-LAST:event_connectionMouseExited

    private void connectionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectionMousePressed
        this.connection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ConBut2Click.png")));
    }//GEN-LAST:event_connectionMousePressed

    private void portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portActionPerformed

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
            java.util.logging.Logger.getLogger(ServerCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerCon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServerCon dialog = new ServerCon(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel connection;
    private javax.swing.JLabel fermer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField port;
    // End of variables declaration//GEN-END:variables
}
