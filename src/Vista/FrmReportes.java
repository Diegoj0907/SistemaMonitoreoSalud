package Vista;

public class FrmReportes extends javax.swing.JFrame {

    public FrmReportes() {
        initComponents();
        setResizable(false); 
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbUsuario = new javax.swing.JComboBox<>();
        spInicio = new javax.swing.JSpinner();
        spFin = new javax.swing.JSpinner();
        btnGenerarPDF = new javax.swing.JButton();
        btnVerGrafico = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cmbUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, -1, -1));
        getContentPane().add(spInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, -1, -1));
        getContentPane().add(spFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, -1, -1));

        btnGenerarPDF.setBackground(new java.awt.Color(102, 255, 102));
        btnGenerarPDF.setText("Generar PDF");
        getContentPane().add(btnGenerarPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 150, 60));

        btnVerGrafico.setBackground(new java.awt.Color(51, 255, 204));
        btnVerGrafico.setText("Ver gráfico");
        getContentPane().add(btnVerGrafico, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 170, 60));

        btnVolver.setText("Volver");
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 510, 130, 40));

        jLabel1.setText("Desde");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 70, 30));

        jLabel2.setText("Usuario");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 60, 20));

        jLabel3.setText("Hasta");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, -1, -1));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabla);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, -1, 120));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImageIco/user_icon-icons.com_57997.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 50, 50));

        jLabel5.setText("Reporte de Estado Mental");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 200, 40));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImageIco/fonddo.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 580));

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
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGenerarPDF;
    public javax.swing.JButton btnVerGrafico;
    public javax.swing.JButton btnVolver;
    public javax.swing.JComboBox<String> cmbUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JSpinner spFin;
    public javax.swing.JSpinner spInicio;
    public javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
