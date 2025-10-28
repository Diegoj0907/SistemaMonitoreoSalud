package Vista;

public class FrmChatbot extends javax.swing.JFrame {

    public FrmChatbot() {
        initComponents(); 
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtChat = new javax.swing.JTextArea();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        lblEmocion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblEmocion1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtChat.setColumns(20);
        txtChat.setRows(5);
        jScrollPane1.setViewportView(txtChat);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 390, 120));
        getContentPane().add(txtMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 380, 160));

        btnEnviar.setBackground(new java.awt.Color(51, 255, 51));
        btnEnviar.setText("Enviar");
        getContentPane().add(btnEnviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 150, 50));

        btnFinalizar.setBackground(new java.awt.Color(255, 204, 204));
        btnFinalizar.setText("Finalizar conversación");
        getContentPane().add(btnFinalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, 150, 50));

        lblEmocion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmocion.setText("¡Hola! Dime cúal es tu estado de animo hoy.");
        getContentPane().add(lblEmocion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 280, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImageIco/chatbot.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 100, 110));

        lblEmocion1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmocion1.setText("Emoción detectada: ");
        getContentPane().add(lblEmocion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImageIco/fonddo.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FrmChatbot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmChatbot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmChatbot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmChatbot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmChatbot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnEnviar;
    public javax.swing.JButton btnFinalizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblEmocion;
    public javax.swing.JLabel lblEmocion1;
    public javax.swing.JTextArea txtChat;
    public javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables
}
