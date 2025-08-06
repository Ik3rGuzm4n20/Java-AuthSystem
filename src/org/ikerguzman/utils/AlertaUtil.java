package org.ikerguzman.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class AlertaUtil {
    public static void mostrarAlerta(AlertType tipo, String titulo, String encabezado, String mensaje){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
        
    }
    
    public static Optional<ButtonType> mostrarConfirmacion(String mensaje){
        Alert alerta = new Alert(AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
        return alerta.showAndWait();
    }
}
