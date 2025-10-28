package Controlador;

import Modelo.*;
import Vista.FrmConfiguracion;
import Vista.FrmPrincipal;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorConfiguracion {

    private final FrmConfiguracion vista;
    private final UsuarioDAO dao;
    private final Usuario usuarioActual;
    private int idSeleccionado = -1;

    public ControladorConfiguracion(FrmConfiguracion v, Usuario usuarioActual1) {
        this.vista = v;
        this.dao = new UsuarioDAO();
        this.usuarioActual = Session.getInstance().getUsuario();

        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(vista, "⚠️ No hay sesión activa. Inicie sesión nuevamente.");
            vista.dispose();
            return;
        }

        cargarUsuarios();
        iniciarEventos();
    }

    private void cargarUsuarios() {
        DefaultTableModel modelo = dao.obtenerTodosLosUsuarios();
        vista.tablaUsuarios.setModel(modelo);
        if (vista.tablaUsuarios.getColumnCount() > 0)
            vista.tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);
    }

    private void iniciarEventos() {
        vista.btnNuevo.addActionListener(e -> limpiarFormulario());
        vista.btnGuardar.addActionListener(e -> guardarUsuario());
        vista.btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
        vista.btnVolver.addActionListener(e -> volverMenuPrincipal());

        vista.tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarDatosSeleccionados();
        });
    }

    private void guardarUsuario() {
        String nombre = vista.txtNombre.getText().trim();
        String rol = (String) vista.cmbRol.getSelectedItem();
        String correo = vista.txtCorreo.getText().trim();
        String clave = new String(vista.txtClave.getPassword());

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Complete todos los campos.");
            return;
        }

        Usuario usuario = new Usuario(idSeleccionado, nombre, rol);
        boolean exito = (idSeleccionado == -1)
                ? dao.insertarUsuario(usuario, correo, clave)
                : dao.actualizarUsuario(usuario, correo, clave);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "✅ Usuario guardado correctamente.");
            limpiarFormulario();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(vista, "❌ Error al guardar usuario.");
        }
    }

    private void eliminarUsuarioSeleccionado() {
        int fila = vista.tablaUsuarios.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario para eliminar.");
            return;
        }

        int id = (int) vista.tablaUsuarios.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Eliminar usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION && dao.eliminarUsuario(id)) {
            JOptionPane.showMessageDialog(vista, "Usuario eliminado.");
            cargarUsuarios();
            limpiarFormulario();
        }
    }

    private void cargarDatosSeleccionados() {
        int fila = vista.tablaUsuarios.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Integer.parseInt(vista.tablaUsuarios.getValueAt(fila, 0).toString());
            vista.txtNombre.setText(vista.tablaUsuarios.getValueAt(fila, 1).toString());
            vista.cmbRol.setSelectedItem(vista.tablaUsuarios.getValueAt(fila, 2).toString());
            vista.txtCorreo.setText(vista.tablaUsuarios.getValueAt(fila, 3).toString());
            vista.txtClave.setText("");
        }
    }

    private void limpiarFormulario() {
        vista.txtNombre.setText("");
        vista.txtCorreo.setText("");
        vista.txtClave.setText("");
        vista.cmbRol.setSelectedIndex(0);
        idSeleccionado = -1;
        vista.tablaUsuarios.clearSelection();
    }

    private void volverMenuPrincipal() {
        vista.dispose();
        FrmPrincipal principal = new FrmPrincipal();
        new ControladorPrincipal(principal);
        principal.setVisible(true);
    }
}
