package com.arquiweb.backend.models;

import com.arquiweb.backend.models.Exception.NoExisteElStatus;

public enum ItemStatus {
    DONE, TODO;

    public static ItemStatus fromString(String status) {
        if (status.equals("DONE")) {
            return DONE;
        } else if (status.equals("TODO")) {
            return TODO;
        } else {
            throw new NoExisteElStatus();
        }

    }

}
