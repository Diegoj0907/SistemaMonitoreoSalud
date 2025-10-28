package Controlador;

import Modelo.*;
import Vista.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorPrincipal {
    private final FrmPrincipal vista;
    private final Usuario usuarioActual;

    public ControladorPrincipal(FrmPrincipal vista) {
        this.vista = vista;
        this.usuarioActual = Session.getInstance().getUsuario();

        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(vista,
                    "⚠️ No hay usuario en sesión. Inicia sesión primero.",
                    "Sesión expirada", JOptionPane.WARNING_MESSAGE);
            vista.dispose();

            FrmLogin login = new FrmLogin();
            new ControladorLogin(login);
            login.setVisible(true);
            return;
        }

        configurarVentanasSegunRol();
        iniciarEventos();

        if (!usuarioActual.getRol().equalsIgnoreCase("estudiante")) {
            cargarResumenEmocional();
        }
    }

    private void cargarResumenEmocional() {
        RegistroEmocionalDAO dao = new RegistroEmocionalDAO();
        DefaultTableModel modelo = dao.obtenerResumen();
        vista.tablaResumen.setModel(modelo);
    }

    private void iniciarEventos() {

        vista.btnIniciarChat.addActionListener(e -> {
            FrmChatbot chat = new FrmChatbot();
            new ControladorChatbot(chat, usuarioActual.getId());
            chat.setLocationRelativeTo(null);
            chat.setVisible(true);
            vista.dispose();
        });

        vista.btnVerReportes.addActionListener(e -> {
            FrmReportes rep = new FrmReportes();
            new ControladorReportes(rep);
            rep.setLocationRelativeTo(null);
            rep.setVisible(true);
            vista.dispose();
        });

        vista.btnConfiguracion.addActionListener(e -> {
            FrmConfiguracion config = new FrmConfiguracion();
            new ControladorConfiguracion(config, usuarioActual);
            config.setLocationRelativeTo(null);
            config.setVisible(true);
            vista.dispose();
        });

        if (vista.btnSalir != null) {
            vista.btnSalir.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(vista,
                        "¿Deseas cerrar sesión y volver al login?",
                        "Cerrar sesión",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    Session.getInstance().cerrarSesion();
                    vista.dispose();

                    FrmLogin login = new FrmLogin();
                    new ControladorLogin(login);
                    login.setLocationRelativeTo(null);
                    login.setVisible(true);
                }
            });
        }
    }

    private void configurarVentanasSegunRol() {
        String rol = usuarioActual.getRol().toLowerCase();

        vista.btnIniciarChat.setVisible(false);
        vista.btnVerReportes.setVisible(false);
        vista.btnConfiguracion.setVisible(false);
        vista.tablaResumen.setVisible(false);
        vista.jScrollPane1.setVisible(false);

        switch (rol) {
            case "admin" -> {
                vista.btnConfiguracion.setVisible(true);
                vista.btnVerReportes.setVisible(true);
                vista.tablaResumen.setVisible(true);
                vista.jScrollPane1.setVisible(true);
            }
            case "psicologo" -> {
                vista.btnVerReportes.setVisible(true);
                vista.tablaResumen.setVisible(true);
                vista.jScrollPane1.setVisible(true);
            }
            case "estudiante" -> vista.btnIniciarChat.setVisible(true);
        }

        vista.setTitle("Sistema Monitoreo Salud - " +
                usuarioActual.getNombre() + " (" + usuarioActual.getRol() + ")");
    }
}
