package com.example.jimenez.appmunitacna.Clases;

public class Usuario {
    private String keyFirebase;
    private String nombres;
    private String correo;
    private String celular;
    private String dni;
    private String direccion;

    public Usuario(String nombres, String correo, String celular, String dni, String direccion) {
        this.nombres = nombres;
        this.correo = correo;
        this.celular = celular;
        this.dni = dni;
        this.direccion = direccion;
    }

    public String getKeyFirebase() {
        return keyFirebase;
    }

    public void setKeyFirebase(String keyFirebase) {
        this.keyFirebase = keyFirebase;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario() {
    }
}
