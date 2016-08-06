package com.dnfeitosa.codegraph.api.services;

public class ItemDoesNotExistException extends RuntimeException {

    public ItemDoesNotExistException(String message) {
        super(message);
    }
}
