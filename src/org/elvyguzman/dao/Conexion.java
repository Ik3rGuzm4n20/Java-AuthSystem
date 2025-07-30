package org.elvyguzman.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexion {
    private static Conexion instancia;
    private Connection conexion;
    
    private static final String URL = "jdbc:mysql://localhost:3306/auth_system?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "elvyguzman2024";
    
    public Conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la db");
        }catch(ClassNotFoundException e){
            System.err.println("Error: Driver de MySQL no encontrado");
            System.out.println(e.getMessage());
        }catch(SQLException e){
            System.err.println("Error: No fue posible conectarse a la db");
            System.out.println(e.getMessage());
        }
    }
    
    public static Conexion getInstance(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Connection getConexion(){
        if(conexion == null){
            System.err.println("No se estableció la conexión, verifica la configuración");
        }
        return conexion;
    } 
}
