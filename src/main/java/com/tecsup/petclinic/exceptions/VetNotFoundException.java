package com.tecsup.petclinic.exceptions;

import java.io.Serial;

public class VetNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public VetNotFoundException(String message) {
        super(message);
    }
}
