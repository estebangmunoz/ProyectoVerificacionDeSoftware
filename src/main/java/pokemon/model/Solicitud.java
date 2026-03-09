package main.java.pokemon.model;

public class Solicitud {
    private int id;
    private int cartaSolicitada;
    private int cartaOfrecida;
    private String usuarioSolicita;
    private String duenoOriginal;
    private String estado;
    private String fechaSolicitud;

    // Campos extra para mostrar en la interfaz
    private String nombreCartaSolicitada;
    private String nombreCartaOfrecida;

    public Solicitud() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCartaSolicitada() { return cartaSolicitada; }
    public void setCartaSolicitada(int cartaSolicitada) { this.cartaSolicitada = cartaSolicitada; }

    public int getCartaOfrecida() { return cartaOfrecida; }
    public void setCartaOfrecida(int cartaOfrecida) { this.cartaOfrecida = cartaOfrecida; }

    public String getUsuarioSolicita() { return usuarioSolicita; }
    public void setUsuarioSolicita(String usuarioSolicita) { this.usuarioSolicita = usuarioSolicita; }

    public String getDuenoOriginal() { return duenoOriginal; }
    public void setDuenoOriginal(String duenoOriginal) { this.duenoOriginal = duenoOriginal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(String fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public String getNombreCartaSolicitada() { return nombreCartaSolicitada; }
    public void setNombreCartaSolicitada(String nombreCartaSolicitada) { this.nombreCartaSolicitada = nombreCartaSolicitada; }

    public String getNombreCartaOfrecida() { return nombreCartaOfrecida; }
    public void setNombreCartaOfrecida(String nombreCartaOfrecida) { this.nombreCartaOfrecida = nombreCartaOfrecida; }
}
