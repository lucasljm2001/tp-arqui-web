package com.arquiweb.backend.models.Exception;

public class NombreVacioException  extends  RuntimeException{
    @Override
    public String getMessage() {
        return "No pueden crearse objetos con nombre vacio";
    }
}
