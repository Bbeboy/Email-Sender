module fes.aragon.filtrocorreo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires jakarta.mail;
    requires org.eclipse.angus.mail;

    exports fes.aragon.inicio;
    opens fes.aragon.inicio to javafx.fxml;
    exports fes.aragon.controller;
    opens fes.aragon.controller to javafx.fxml;
    exports fes.aragon.modelo;
    opens fes.aragon.modelo to javafx.fxml;

}