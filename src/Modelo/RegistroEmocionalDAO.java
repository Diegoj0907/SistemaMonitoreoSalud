package Modelo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class RegistroEmocionalDAO {

    public int insertarRegistro(RegistroEmocional r) {
        int idGenerado = -1;
        String proc = "dbo.InsertarRegistroEmocional";
        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = ConexionDAO.prepararProcedimiento(con, proc, 4)) {

            cs.setInt(1, r.getUsuarioID());
            cs.setString(2, r.getTexto());
            cs.setString(3, r.getEmocion());
            cs.setDouble(4, r.getPuntaje());

            cs.execute();

            try (ResultSet rs = cs.getResultSet()) {
                if (rs != null && rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            System.err.println("❌ Error al insertar registro emocional: " + ex.getMessage());
        }
        return idGenerado;
    }

    public DefaultTableModel obtenerResumen() {
        String[] columnas = {"ID Registro", "Usuario", "Fecha", "Texto", "Emoción", "Puntaje"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall("{call dbo.ObtenerResumenEmocionalTodo()}");
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id_registro"),
                    rs.getString("usuario"),
                    rs.getTimestamp("fecha"),
                    rs.getString("texto"),
                    rs.getString("emocion"),
                    rs.getBigDecimal("puntaje")
                };
                modelo.addRow(fila);
            }

        } catch (SQLException ex) {
            System.err.println("❌ Error al obtener resumen emocional: " + ex.getMessage());
        }

        return modelo;
    }

    public DefaultTableModel obtenerRegistrosPorRango(int usuarioId, Date inicio, Date fin) {
        String[] columnas = {"ID Registro", "Fecha", "Texto", "Emoción", "Puntaje"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = con.prepareCall("{call dbo.ObtenerResumenEmocionalPorUsuarioYRango(?,?,?)}")) {

            cs.setInt(1, usuarioId);
            cs.setDate(2, new java.sql.Date(inicio.getTime()));
            cs.setDate(3, new java.sql.Date(fin.getTime()));

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getInt("id_registro"),
                        rs.getTimestamp("fecha"),
                        rs.getString("texto"),
                        rs.getString("emocion"),
                        rs.getBigDecimal("puntaje")
                    };
                    modelo.addRow(fila);
                }
            }

        } catch (SQLException ex) {
            System.err.println("❌ Error al obtener registros por rango: " + ex.getMessage());
        }

        return modelo;
    }
}
