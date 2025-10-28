package Modelo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ReporteDAO {

    public DefaultTableModel obtenerResumen(java.util.Date inicio, java.util.Date fin) {
        String[] columnas = {"ID", "Usuario", "Fecha", "Texto", "Emoción", "Puntaje"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "{CALL dbo.ObtenerResumenEmocional(?, ?)}";

        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setDate(1, new java.sql.Date(inicio.getTime()));
            cs.setDate(2, new java.sql.Date(fin.getTime()));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id_registro"),
                        rs.getString("usuario"),
                        rs.getTimestamp("fecha"),
                        rs.getString("texto"),
                        rs.getString("emocion"),
                        rs.getDouble("puntaje")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            System.err.println("❌ Error en obtenerResumen: " + ex.getMessage());
            ex.printStackTrace();
        }

        return modelo;
    }

    public DefaultTableModel obtenerPorUsuarioYRango(int usuarioID, java.util.Date inicio, java.util.Date fin) {
        String[] columnas = {"ID", "Usuario", "Fecha", "Texto", "Emoción", "Puntaje"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        String sql = "{CALL dbo.ObtenerResumenEmocionalPorUsuarioYRango(?, ?, ?)}";
        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, usuarioID);
            cs.setDate(2, new java.sql.Date(inicio.getTime()));
            cs.setDate(3, new java.sql.Date(fin.getTime()));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id_registro"),
                        rs.getString("usuario"),
                        rs.getTimestamp("fecha"),
                        rs.getString("texto"),
                        rs.getString("emocion"),
                        rs.getDouble("puntaje")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            System.err.println("❌ Error en obtenerPorUsuarioYRango: " + ex.getMessage());
            ex.printStackTrace();
        }

        return modelo;
    }
}
