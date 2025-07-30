package org.elvyguzman.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.elvyguzman.controllers.LoginController;
import org.elvyguzman.controllers.NewUserController;
       
public class Main extends Application{
    private final String PAQUETE_VISTA = "/org/elvyguzman/views/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    public static void main(String[] args) {
        launch(args);   
    }
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("AuthSystem Fundamentos 2025");
        login();    //carga la vista
        escenarioPrincipal.show();
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Main.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Main.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    //Cargan la vista de los fxml
    public void login(){
        try{
            LoginController login = (LoginController) cambiarEscena("LoginView.fxml", 600, 400);
            login.setEscenarioPrincipal(this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void newUser(){
        try{
           NewUserController newUser =  (NewUserController) cambiarEscena("NewUserView.fxml", 600, 400);
           newUser.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}