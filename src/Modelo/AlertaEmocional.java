package Modelo;

import java.util.Date;

public class AlertaEmocional {
    private int id;
    private int usuarioID;
    private Date fecha;
    private String descripcion;
    private int nivelUrgencia;

    public AlertaEmocional() {}

    public AlertaEmocional(int usuarioID, Date fecha, String descripcion, int nivelUrgencia) {
        this.usuarioID = usuarioID;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nivelUrgencia = nivelUrgencia;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUsuarioID() { return usuarioID; }
    public void setUsuarioID(int usuarioID) { this.usuarioID = usuarioID; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getNivelUrgencia() { return nivelUrgencia; }
    public void setNivelUrgencia(int nivelUrgencia) { this.nivelUrgencia = nivelUrgencia; }
}
