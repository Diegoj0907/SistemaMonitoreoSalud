package Modelo;

import java.sql.*;

public class ConexionDAO {

    private static final String URL =
        "jdbc:sqlserver://localhost\\SQLEXPRESS01:1433;"
      + "databaseName=SistemaMonitoreoSalud;"
      + "encrypt=false;"
      + "trustServerCertificate=true;";

    private static final String USUARIO = "juan";
    private static final String CONTRASENA = "juan456";

    public static Connection conectar() {
        Connection con = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            con = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("✅ Conectado correctamente a SQL Server como '" + USUARIO + "'.");
        } catch (ClassNotFoundException e) {
            System.err.println("⚠️ No se encontró el driver JDBC de SQL Server. "
                    + "Asegúrate de tener agregado el archivo mssql-jdbc-XX.jar al classpath.\n"
                    + "Detalle: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos:\n" + e.getMessage());
        }
        return con;
    }

    public static CallableStatement prepararProcedimiento(Connection con, String nombreProcedimiento, int cantidadParametros) throws SQLException {
        if (con == null) {
            throw new SQLException("❌ La conexión es nula. Conéctate antes de preparar el procedimiento.");
        }

        StringBuilder call = new StringBuilder("{call ");
        call.append(nombreProcedimiento);

        if (cantidadParametros > 0) {
            call.append("(");
            for (int i = 0; i < cantidadParametros; i++) {
                call.append("?");
                if (i < cantidadParametros - 1) call.append(",");
            }
            call.append(")");
        }
        call.append("}");

        return con.prepareCall(call.toString());
    }

    public static void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
