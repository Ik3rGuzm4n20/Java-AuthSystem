package org.ikerguzman.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.ikerguzman.dao.Conexion;
import org.ikerguzman.models.Persona;
import org.ikerguzman.models.Usuario;
import org.ikerguzman.system.Main;
import org.ikerguzman.utils.AlertaUtil;

//1, agregar persona, buscar persona, agregar usuario

public class NewUserController implements Initializable {
    private Main escenarioPrincipal;
    private Persona nuevaPersona = new Persona();
    private Usuario nuevoUsuario = new Usuario();
    
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
    
    private void limpiarControles(){
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
    
    private void agregarPersona(){
        nuevaPersona.setNombres(txtNombres.getText());
        nuevaPersona.setApellidos(txtApellidos.getText());
        nuevaPersona.setTelefono(txtTelefono.getText());
        try{
            PreparedStatement sp = Conexion.getInstance()
                    .getConexion()
                    .prepareCall("CALL sp_agregar_persona(?,?,?);");
            sp.setString(1, nuevaPersona.getNombres());
            sp.setString(2, nuevaPersona.getApellidos());
            sp.setString(3, nuevaPersona.getTelefono());
            sp.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private String idPersona(){
        String id = null;
        try{
            CallableStatement cs = Conexion.getInstance()//paramentros de salida
                    .getConexion()
                    .prepareCall("{Call sp_buscar_persona(?)}");
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);
            cs.execute();
            id = cs.getString(1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return id;
    }
    
    private void agregarUsuario(String idPersona){
        nuevoUsuario.setEmail(txtEmail.getText());
        nuevoUsuario.setPassword(txtPassword.getText());
        nuevoUsuario.setIdPersona(idPersona);
        try{
            PreparedStatement sp = Conexion.getInstance()
                    .getConexion()
                    .prepareCall("CALL sp_agregar_usuario(?,?,?);");
            sp.setString(1, nuevoUsuario.getEmail());
            sp.setString(2, nuevoUsuario.getPassword());
            sp.setString(3, nuevoUsuario.getIdPersona());
            sp.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void eventoAceptar(ActionEvent event){
        if(txtNombres.getText().isEmpty() ||
                txtApellidos.getText().isEmpty() ||
                txtEmail.getText().isEmpty() ||
                txtPassword.getText().isEmpty())
        {
            Alert alertaCampos = new Alert(AlertType.WARNING);
            alertaCampos.setTitle("Campos vacíos");
            alertaCampos.setHeaderText("No hay datos");
            alertaCampos.setContentText("Por favor llene todos los campos.");
            alertaCampos.showAndWait();
            return;
        }
        agregarPersona();
        String id = idPersona();
        agregarUsuario(id);
        AlertaUtil.mostrarAlerta(AlertType.INFORMATION,
        "Registro de usuarios",
        "Éxito!!!",
        "El registro se realizó de manera correcta");
        limpiarControles();
        Optional<ButtonType> seleccion = AlertaUtil.mostrarConfirmacion("¿Deseas iniciar sesión?");
        if(seleccion.get() == ButtonType.YES){
          escenarioPrincipal.login();
        }else{
            AlertaUtil.mostrarAlerta(Alert.AlertType.INFORMATION,
            "Registro de usuarios",
            "Adiós",
            "Gracias por utilizar mi programa");
            Platform.exit();
        }
    }
    
    @FXML
    public void eventoCancelar(ActionEvent event){
        escenarioPrincipal.login();
    } 
}