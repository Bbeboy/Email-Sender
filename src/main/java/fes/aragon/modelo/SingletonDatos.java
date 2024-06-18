package fes.aragon.modelo;

import java.io.File;
import java.util.List;

public class SingletonDatos {
    private static SingletonDatos datos;

    private List<Persona> listaGeneral;
    private String asunto;
    private String msj;
    private File archivo;

    private SingletonDatos(){

    }
    public static SingletonDatos getInstance(){
        if(datos==null){
            datos=new SingletonDatos();
        }
        return datos;
    }

    public List<Persona> getListaGeneral() {
        return listaGeneral;
    }

    public void setListaGeneral(List<Persona> listaGeneral) {
        this.listaGeneral = listaGeneral;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
}
