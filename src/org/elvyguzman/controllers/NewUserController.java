package org.elvyguzman.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.elvyguzman.system.Main;


public class NewUserController implements Initializable {
    private Main escenarioPrincipal;
    
    @FXML
    private TextField txtNombres, txtApellidos, txtTelefono, txtEmail;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnAceptar, btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void eventoAceptar(ActionEvent event){
        
    }
    
    @FXML
    public void eventoCancelar(ActionEvent event){
        escenarioPrincipal.login();
    } 
}