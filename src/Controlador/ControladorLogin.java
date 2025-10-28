package Controlador;

import Modelo.*;
import Vista.*;
import java.sql.*;
import javax.swing.*;

public class ControladorLogin {
    private final FrmLogin vista;

    public ControladorLogin(FrmLogin vista) {
        this.vista = vista;
        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.btnIngresar.addActionListener(e -> autenticarUsuario());
        vista.txtClave.addActionListener(e -> autenticarUsuario());
        vista.jbtnSalirLogin.addActionListener(e -> salir());
    }

    private void autenticarUsuario() {
        String nombre = vista.txtUsuario.getText().trim();
        String clave = new String(vista.txtClave.getPassword()).trim();

        if (nombre.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "⚠️ Complete usuario y contraseña");
            return;
        }

        try (Connection con = ConexionDAO.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id, nombre, rol, correo FROM Usuario WHERE nombre = ? AND clave = ?")) {

            ps.setString(1, nombre);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("rol")
                    );

                    Session.getInstance().setUsuario(usuario);

                    JOptionPane.showMessageDialog(vista,
                        "✅ Bienvenido " + usuario.getNombre() + " (" + usuario.getRol() + ")");

                    vista.dispose();

                    FrmPrincipal frmPrincipal = new FrmPrincipal();
                    new ControladorPrincipal(frmPrincipal);
                    frmPrincipal.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vista, "❌ Usuario o contraseña incorrectos");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "❌ Error de conexión: " + ex.getMessage());
        }
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Deseas salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
    }
}
