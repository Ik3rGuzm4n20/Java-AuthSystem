package org.elvyguzman.controllers;

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
import org.elvyguzman.dao.Conexion;
import org.elvyguzman.models.Usuario;
import org.elvyguzman.system.Main;

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
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Base de datos",
                    "Error de conexión",
                    "No fue posible conectarse a la db, revisa configuraciones");
        }
        
        String query = "call sp_buscar_usuario(?,?)";
        try(PreparedStatement sp = conexion.prepareStatement(query)){
            sp.setString(1, txtEmail.getText());
            sp.setString(2, txtPassword.getText());
            ResultSet resultado = sp.executeQuery();
            
            if(resultado.next()){
                usuario.setEmail(resultado.getString("email"));
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Bienvenido!!!",
                        "Inicio de sesión exitoso",
                        "Todo esta bien bienvenido al programa");
            }else{
                mostrarAlerta(Alert.AlertType.ERROR,
                        "Login",
                        "Falló inicio de sesión",
                        "Email o password inválido, intenta de nuevo");
            }
            
        }catch(SQLException e){
            System.err.println("Error en la consulta SQL " + e.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Base de datos",
                    "Error en consulta SQL",
                    "No fue posible completar la consulta a la base de datos");
        }
    }
        
    @FXML
    public void eventoCancelar(ActionEvent evento){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Seguro que quieres salir?",
                    ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> eleccion = alerta.showAndWait();
        if(eleccion.isPresent() && eleccion.get() == ButtonType.YES) Platform.exit();
    }
    
     @FXML
    public void eventoRegistrarse(MouseEvent evento){
        if(escenarioPrincipal != null){
            escenarioPrincipal.newUser();
        }else{
            mostrarAlerta(Alert.AlertType.ERROR, 
                    "Nuevo Usuario",
                    "Error en formulario de registro",
                    "No fue posible mostrar el formulario de registro de usuarios");
        }
        
    }
    
    private void mostrarAlerta(Alert.AlertType tipo,
            String titulo, String encabezado, String mensaje){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
}
