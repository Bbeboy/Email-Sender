package fes.aragon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    void accionBuscarArchivo(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File f=fileChooser.showOpenDialog(btnBuscarArchivo.getScene().getWindow());
        if(f!=null){
            archivo = f.getAbsoluteFile();
            txtNombreArchivo.setText(f.getName());
        }

    }

    @FXML
    void accionMandarCorreo(ActionEvent event) {

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
