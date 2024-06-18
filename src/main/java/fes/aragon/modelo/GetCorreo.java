package fes.aragon.modelo;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.security.Provider;

public class GetCorreo extends Service<Integer> {
    @Override
    protected Task<Integer> createTask() {
        return new CorreoServicio();
    }
}
