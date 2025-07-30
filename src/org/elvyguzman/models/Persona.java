package org.elvyguzman.models;

public class Persona {
    private String idPersona;
    private String nombres; 
    private String apellidos;
    private String telefono;
    
    
public Persona (){}    
    
    public Persona(String idPersona, String nombres, String apellidos, String telefono){
        this.idPersona = idPersona;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono; 
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" + "idPersona=" + idPersona + ", nombres=" + nombres + ", apellidos=" + apellidos + ", telefono=" + telefono + '}';
    }

}

