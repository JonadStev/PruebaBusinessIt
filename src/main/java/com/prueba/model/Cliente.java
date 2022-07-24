package com.prueba.model;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name="clienteId")
public class Cliente extends Persona {

    private String contraseña;
    private String estado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Cuenta> cuentas;

    public Cliente(){
        super();
    }

    public Cliente(String nombre, String genero, int edad, String identificacion, String direccion, String telefono) {
        super(nombre, genero, edad, identificacion, direccion, telefono);
    }

    public Cliente(String nombre, String genero, int edad, String identificacion, String direccion, String telefono, String contraseña, String estado) {
        super(nombre, genero, edad, identificacion, direccion, telefono);
        this.contraseña = contraseña;
        this.estado = estado;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
