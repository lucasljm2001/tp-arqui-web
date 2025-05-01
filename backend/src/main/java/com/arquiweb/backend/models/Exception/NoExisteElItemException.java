package com.arquiweb.backend.models.Exception;

public class NoExisteElItemException extends  RuntimeException {
    @Override
    public String getMessage() {
        return "El Item no existe";
    }
}
