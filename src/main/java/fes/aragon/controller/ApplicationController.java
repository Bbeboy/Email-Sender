package fes.aragon.controller;

import fes.aragon.modelo.Persona;
import fes.aragon.modelo.SingletonDatos;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.undo.UndoableEditSupport;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class ApplicationController implements Initializable {

    @FXML
    private Button btnBuscar;

    @FXML
    private ChoiceBox<String> chcSexo;

    @FXML
    private TableView<Persona> tblPersona;

    @FXML
    private TableColumn<Persona, String> clmCorreo;

    @FXML
    private TableColumn<Persona, String> clmEdad;

    @FXML
    private TableColumn<Persona, String> clmNombre;

    @FXML
    private Label lbl;

    @FXML
    private TextField txtEdadMaxima;

    @FXML
    private TextField txtEdadMinima;
    @FXML
    private Button btnMandar;
    @FXML
    private TableColumn<?, ?> clmMandar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmMandar.setCellFactory(CheckBoxTableCell.forTableColumn((i)->
                tblPersona.getItems().get(i).seleccionProperty()));
        tblPersona.setOnMouseClicked(mouseEvent -> {
            if(tblPersona.getSelectionModel().getSelectedItem()!=null){
                boolean valor=tblPersona.getSelectionModel().getSelectedItem().seleccionProperty().get();
                tblPersona.getSelectionModel().getSelectedItem().seleccionProperty().set(!valor);
            }
        });
        //tblPersona.setItems(FXCollections.emptyObservableList());  //Quitar los "//" del inicio de la línea del código para poner una lista vacía
        tblPersona.setItems(Persona.crearLista()); //Comentar esta linea si se necesita mostrar la tabla vacia

        ObservableList<String> listaSexo = FXCollections.observableArrayList();
        listaSexo.add("Masculino");
        listaSexo.add("Femenino");
        listaSexo.add("Ambos");
        chcSexo.setItems(listaSexo);

        txtEdadMinima.setTextFormatter(new TextFormatter<>(
                c->{
                    if(!c.getControlNewText().matches("\\d*")){
                        return null;
                    } else{
                        if (c.getControlNewText().length() < 3){
                            return c;
                        }else{
                            return null;
                        }
                    }
                }
        ));
        txtEdadMaxima.setTextFormatter(new TextFormatter<>(
                c->{
                    if(!c.getControlNewText().matches("\\d*")){
                        return null;
                    } else{
                        if (c.getControlNewText().length() < 3){
                            return c;
                        }else{
                            return null;
                        }
                    }
                }
        ));
        btnMandar.setDisable(true);
    }
    private boolean tipoDeEvaluacion(){
        String mensaje = "";
        boolean correcto = true;
        if (txtEdadMinima.getText() == null || txtEdadMinima.getText().trim().isEmpty()) {
        mensaje += "Ingrese una Edad Minima\n";
        correcto = false;
        }
        if (txtEdadMaxima.getText() == null || txtEdadMaxima.getText().trim().isEmpty()) {
            mensaje += "Ingrese una Edad Maxima\n";
            correcto = false;
        }
        if (chcSexo.getSelectionModel().getSelectedIndex() == -1){
            mensaje += "Ingrese un Tipo de Sexo\n";
            correcto = false;
        }
        if(correcto){
            int minimo=Integer.valueOf(txtEdadMinima.getText());
            int maximo=Integer.valueOf(txtEdadMaxima.getText());
            if(minimo>maximo){
                mensaje+="La edad mínima no debe ser mayor a la máxima \n";
                correcto=false;
            }
        }
        if (!correcto) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje");
            alert.setHeaderText("Verifique lo siguiente");
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
        return correcto;
    }

    public void accionBuscar(javafx.event.ActionEvent actionEvent) {
        boolean respuesta = this.tipoDeEvaluacion();
        if(respuesta){
            int chcIndice = chcSexo.getSelectionModel().getSelectedIndex();
            List<Persona> d=Persona.crearLista().stream()
                    .filter(usuarios->{ // Filtro por sexos
                        if(Persona.Sexo.valueOf(chcSexo.getValue().toUpperCase()) == Persona.Sexo.MASCULINO){ //Comprobacion de Sexo seleccionado con uno de los disponibles
                            return usuarios.getGenero() == Persona.Sexo.valueOf(chcSexo.getValue().toUpperCase()); //Ingreso de los usuarios a la lista
                            // que cumplen con las condicion anterior
                        } else if (Persona.Sexo.valueOf(chcSexo.getValue().toUpperCase()) == Persona.Sexo.FEMENINO) {

                            return usuarios.getGenero() == Persona.Sexo.valueOf(chcSexo.getValue().toUpperCase());
                        } else { //Ingresa aqui en caso de que ninguno cumpla con la condicion
                            return true; //Regresa la lista sin ningun filtro
                        }
                    }).filter(usuarios -> { //Ahora se debera filtrar por edades
                        return (usuarios.getEdad() >= Integer.parseInt(txtEdadMinima.getText()))
                                && (usuarios.getEdad() <= Integer.parseInt(txtEdadMaxima.getText())); //Aqui se filtran los elementos por el rango de edades,
                        // los que no cumplen no se agregaran a la lista
                    }).toList(); //Se agregan los elementos ya filtrados a la lista d
            ObservableList<Persona> tablaActualizada = FXCollections.observableArrayList(d);
            tblPersona.setItems(tablaActualizada);
            if (!tablaActualizada.isEmpty()){
                btnMandar.setDisable(false);
            }else {
                btnMandar.setDisable(true);
            }



        }

    }

    public void AccionMandar(javafx.event.ActionEvent actionEvent) {
        List<Persona> lista = tblPersona.getItems().stream().filter(persona -> persona.seleccionProperty().getValue()).toList();
        SingletonDatos.getInstance().setListaGeneral(lista);
        if(lista.isEmpty()){
            btnMandar.setDisable(true);
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje");
            alert.setHeaderText("Verifique lo siguiente");
            alert.setContentText("Debe haber por lo menos una persona seleccionada");
            alert.showAndWait();
        }else {
            try {
                FXMLLoader load=new FXMLLoader(getClass().getResource("/fes/aragon/xml/Mensaje.fxml"));
                Parent parent=load.load();
                Scene scene=new Scene(parent);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                stage.requestFocus();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
