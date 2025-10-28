package Modelo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioDAO {

    public DefaultTableModel obtenerTodosLosUsuarios() {
        String[] columnas = {"ID", "Nombre", "Rol", "Correo"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "{CALL ObtenerUsuarios_Todos_SP}";

        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("rol"),
                    rs.getString("correo")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelo;
    }

    public boolean insertarUsuario(Usuario u, String correo, String clave) {
        String sql = "{CALL InsertarUsuario_SP(?, ?, ?, ?)}";

        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, u.getNombre());
            cs.setString(2, u.getRol());
            cs.setString(3, correo);
            cs.setString(4, clave);

            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarUsuario(Usuario u, String correo, String clave) {
        String sql = "{CALL ActualizarUsuario_SP(?, ?, ?, ?, ?)}";

        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, u.getId());
            cs.setString(2, u.getNombre());
            cs.setString(3, u.getRol());
            cs.setString(4, correo);
            cs.setString(5, clave);

            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {
        String sql = "{CALL EliminarUsuario_SP(?)}";

        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
