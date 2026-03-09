package main.java.pokemon.model;

public class Carta {
    private int id;
    private String dueno;
    private String nombre;
    private int puntos;
    private String tipo;
    private String fechaAlta;
    private String estado;

    public Carta() {
    }

    public Carta(int id, String dueno, String nombre, int puntos, String tipo, String fechaAlta, String estado) {
        this.id = id;
        this.dueno = dueno;
        this.nombre = nombre;
        this.puntos = puntos;
        this.tipo = tipo;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}