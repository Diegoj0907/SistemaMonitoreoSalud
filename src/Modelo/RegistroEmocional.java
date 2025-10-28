package Modelo;

import java.util.Date;

public class RegistroEmocional {
    private int id;
    private int usuarioID;
    private Date fecha;
    private String texto;
    private String emocion;
    private double puntaje;

    public RegistroEmocional() {}

    public RegistroEmocional(int usuarioID, Date fecha, String texto, String emocion, double puntaje) {
        this.usuarioID = usuarioID;
        this.fecha = fecha;
        this.texto = texto;
        this.emocion = emocion;
        this.puntaje = puntaje;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioID() { return usuarioID; }
    public void setUsuarioID(int usuarioID) { this.usuarioID = usuarioID; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getEmocion() { return emocion; }
    public void setEmocion(String emocion) { this.emocion = emocion; }
    public double getPuntaje() { return puntaje; }
    public void setPuntaje(double puntaje) { this.puntaje = puntaje; }
}
