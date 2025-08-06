package org.ikerguzman.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.ikerguzman.dao.Conexion;
import org.ikerguzman.models.Usuario;
import org.ikerguzman.system.Main;
import org.ikerguzman.utils.AlertaUtil;

public class LoginController implements Initializable{
    private Main escenarioPrincipal;
    private Usuario usuario = new Usuario();
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button Aceptar, Cancelar;
    
    @FXML
    private Label IblRegistrarse;
    
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
    public void eventoAceptar(ActionEvent evento){
        if(txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()){
            AlertaUtil.mostrarAlerta(Alert.AlertType.WARNING,
                    "Campos vacíos",
                    "Los datos estan incompletos",
                    "Llenar todos los campos para continuar");
            return;
        }
        buscarUsuario();
        limpiarControles();
    }
    
    private void limpiarControles(){
        txtEmail.setText("");
        txtPassword.setText("");
    }
    
    private void buscarUsuario(){
        Connection conexion = Conexion.getInstance().getConexion();
        if(conexion == null){
            AlertaUtil.mostrarAlerta(Alert.AlertType.ERROR,
                    "Base de datos",
                    "Error de conexión",
                    "No fue posible conectarse a la db, revisa configuraciones");
        }
        
        String query = "call sp_validar_usuario(?,?)";
        try(PreparedStatement sp = conexion.prepareStatement(query)){
            sp.setString(1, txtEmail.getText());
            sp.setString(2, txtPassword.getText());
            ResultSet resultado = sp.executeQuery();
            
            if(resultado.next()){
                String respuesta = resultado.getString("resultado");
                
                switch(respuesta){
                    case "NO_EMAIL" -> AlertaUtil.mostrarAlerta(Alert.AlertType.ERROR,
                        "Login",
                        "Falló inicio de sesión",
                        "No es un correo válido");
                    case "CONTRASENIA_INCORRECTA" -> AlertaUtil.mostrarAlerta(Alert.AlertType.ERROR,
                        "Login",
                        "Falló inicio de sesión",
                        "Contraseña ingresada es incorrecta");
                    case "OK" -> {
                        String email = resultado.getString("email");
                        usuario.setEmail(email);
                        AlertaUtil.mostrarAlerta(Alert.AlertType.INFORMATION,
                                "Bienvenido!!!",
                                "Inicio de sesión exitoso",
                                "Todo esta bien bienvenido al programa " + usuario.getEmail());
                    }
                    default -> AlertaUtil.mostrarAlerta(Alert.AlertType.ERROR,
                        "Login",
                        "Falló inicio de sesión",
                        "Respuesta inesperada del server");
                }
            }else{
                AlertaUtil.mostrarAlerta(Alert.AlertType.ERROR,
                        "Login",
                        "Falló inicio de sesión",
                        "Email o password inválido, intenta de nuevo");
            }    
        }catch(SQLException e){
            System.err.println("Error en la consulta SQL " + e.getMessage());
            AlertaUtil.mostrarAlerta(Alert.AlertType.ERROR,
                    "Base de datos",
                    "Error en consulta SQL",
                    "No fue posible completar la consulta a la base de datos");
        }
    }
        
    @FXML
    public void eventoCancelar(ActionEvent evento){
        Optional<ButtonType> eleccion = AlertaUtil.mostrarConfirmacion("¿Seguro que quieres salir?");
        if(eleccion.isPresent() && eleccion.get() == ButtonType.YES){
            Platform.exit();
        }
    }
    
     @FXML
    public void eventoRegistrarse(MouseEvent evento){
        if(escenarioPrincipal != null){
            escenarioPrincipal.newUser();
        }
        else{
            AlertaUtil.mostrarAlerta(Alert.AlertType.ERROR,
                    "Nuevo Usuario",
                    "Error en formulario de registro",
                    "No fue posible mostrar el formulario de registro de usuarios");
        }    
    }
}
