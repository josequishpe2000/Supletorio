package Entidad;

public class ClsEntidadListaReproduccion {
    private String idLista_reproduccion;
    private String nombrelista;
    private String idusuario;

    public ClsEntidadListaReproduccion(String idLista_reproduccion, String nombrelista, String idusuario) {
        this.idLista_reproduccion = idLista_reproduccion;
        this.nombrelista = nombrelista;
        this.idusuario = idusuario;
    }

    public ClsEntidadListaReproduccion(String nombrelista, String idusuario) {
        this.nombrelista = nombrelista;
        this.idusuario = idusuario;
    }

    public ClsEntidadListaReproduccion() {
    }

    public String getIdLista_reproduccion() {
        return idLista_reproduccion;
    }

    public void setIdLista_reproduccion(String idLista_reproduccion) {
        this.idLista_reproduccion = idLista_reproduccion;
    }

    public String getNombrelista() {
        return nombrelista;
    }

    public void setNombrelista(String nombrelista) {
        this.nombrelista = nombrelista;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }
    
            
}
