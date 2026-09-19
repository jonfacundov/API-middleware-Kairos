package com.evalucion.tvmazemiddleware.exception;

public class ShowNotFoundException extends RuntimeException {

    public ShowNotFoundException(long showId) {
        super("No se encontro el show con id " + showId);
    }
}
