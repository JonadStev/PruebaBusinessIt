package com.prueba.dto;

import java.util.Date;

public class ConsultarMovimientos {

    private long idCliente;
    private String fechaInicio;
    private String fechaFinal;

    public ConsultarMovimientos(long idCliente, String fechaInicio, String fechaFinal) {
        this.idCliente = idCliente;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}
