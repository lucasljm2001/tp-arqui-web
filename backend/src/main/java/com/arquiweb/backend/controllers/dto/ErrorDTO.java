package com.arquiweb.backend.controllers.dto;

public class ErrorDTO {

    private String mensaje;

    public ErrorDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public ErrorDTO() {
    }

    public String getMensaje() {
        return mensaje;
    }

}