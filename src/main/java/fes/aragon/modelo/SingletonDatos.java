package fes.aragon.modelo;

import java.util.List;

public class SingletonDatos {
    private static SingletonDatos datos;

    private List<Persona> listaGeneral;

    private SingletonDatos(){

    }
    public static SingletonDatos getInstance(){
        if(datos==null){
            datos=new SingletonDatos();
        }
        return datos;
    }

    public void setListaGeneral(List<Persona> listaGeneral) {
        this.listaGeneral = listaGeneral;
    }
}
