package fes.aragon.controller;

import fes.aragon.modelo.GetCorreo;
import fes.aragon.modelo.SingletonDatos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;

public class MensajeController {
    File archivo;

    @FXML
    private Button btnBuscarArchivo;

    @FXML
    private Button btnMandarCorreo;

    @FXML
    private TextArea txaMensaje;

    @FXML
    private TextField txtAsunto;

    @FXML
    private TextField txtNombreArchivo;

    @FXML
    private StackPane stcPane;

    @FXML
    void accionBuscarArchivo(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Imagenes (*.png)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(ext);
        File f=fileChooser.showOpenDialog(btnBuscarArchivo.getScene().getWindow());
        if(f!=null){
            archivo = f.getAbsoluteFile();
            txtNombreArchivo.setText(f.getName());
        }


    }

    @FXML
    void accionMandarCorreo(ActionEvent event) {
        boolean respuesta = this.tipoDeEvaluacion();
        final GetCorreo servicio = new GetCorreo();
        if (respuesta){
            Region ventana = new Region();
            ventana.setStyle("-fx-background-color:rgba(0,0,0,.4)");
            ProgressIndicator p = new ProgressIndicator();
            p.setMaxSize(140, 140);
            p.progressProperty().bind(servicio.progressProperty());
            ventana.visibleProperty().bind(servicio.runningProperty());
            p.visibleProperty().bind(servicio.runningProperty());
            stcPane.getChildren().addAll(ventana, p);

            //Obtener los datos
            SingletonDatos.getInstance().setAsunto(txtAsunto.getText());
            SingletonDatos.getInstance().setMsj(txaMensaje.getText());
            SingletonDatos.getInstance().setArchivo(archivo.getAbsoluteFile());
            servicio.start();

        }
    }
    private boolean tipoDeEvaluacion() {
        String mensaje = "";
        boolean correcto = true;
        if (txtAsunto.getText() == null || txaMensaje.getText() == null) {
            mensaje += "";
            correcto = false;
        }
        return correcto;
    }

}
