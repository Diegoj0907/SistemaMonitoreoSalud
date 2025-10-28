package Modelo;

import java.sql.*;

public class AlertaDAO {

    public int insertarAlerta(AlertaEmocional a) {
        int idGenerado = -1;
        String proc = "dbo.InsertarAlertaEmocional";
        try (Connection con = ConexionDAO.conectar();
             CallableStatement cs = ConexionDAO.prepararProcedimiento(con, proc, 3)) {

            cs.setInt(1, a.getUsuarioID());
            cs.setString(2, a.getDescripcion());
            cs.setInt(3, a.getNivelUrgencia());

            boolean hasRS = cs.execute();
            if (hasRS) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) idGenerado = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idGenerado;
    }
}
