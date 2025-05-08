package com.arquiweb.backend.models.Exception;

public class NoExisteLaCarpetaException  extends  RuntimeException {
    @Override
    public String getMessage() {
        return "La carpeta no existe";
    }
}
