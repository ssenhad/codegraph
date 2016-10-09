package com.dnfeitosa.codegraph.server.api.services;

public class ItemDoesNotExistException extends RuntimeException {

    public ItemDoesNotExistException(String message) {
        super(message);
    }
}
