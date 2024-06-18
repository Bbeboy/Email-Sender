module fes.aragon.filtrocorreo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    exports fes.aragon.inicio;
    opens fes.aragon.inicio to javafx.fxml;
    exports fes.aragon.controller;
    opens fes.aragon.controller to javafx.fxml;
    exports fes.aragon.modelo;
    opens fes.aragon.modelo to javafx.fxml;
}