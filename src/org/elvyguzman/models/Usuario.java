package org.elvyguzman.models;

public class Usuario {
    private String idUsuario; 
    private String email; 
    private String password; 
    private String idPersona;
    
    public Usuario (){}    
    
    public Usuario(String idUsuario, String email, String password, String idPersona){
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
        this.idPersona = idPersona; 
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", email=" + email + ", password=" + password + ", idPersona=" + idPersona + '}';
    }
}
