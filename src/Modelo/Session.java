package Modelo;

public class Session {

    private static Session instancia;

    private Usuario usuario;

    private Session() {}

    public static Session getInstance() {
        if (instancia == null) {
            instancia = new Session();
        }
        return instancia;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isSesionActiva() {
        return usuario != null;
    }

    public void cerrarSesion() {
        usuario = null;
    }

    public static void reiniciar() {
        instancia = null;
    }
}
