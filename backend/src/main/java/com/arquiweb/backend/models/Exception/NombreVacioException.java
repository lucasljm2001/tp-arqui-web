package com.arquiweb.backend.models.Exception;

public class NombreVacioException  extends  RuntimeException{
    @Override
    public String getMessage() {
        return "No pueden haber objetos con nombre vacio";
    }
}
