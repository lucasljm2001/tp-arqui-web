package com.arquiweb.backend.models.Exception;

public class NoExisteElStatus extends RuntimeException {



    @Override
    public String getMessage() {
        return "No existe el status";
    }
}
