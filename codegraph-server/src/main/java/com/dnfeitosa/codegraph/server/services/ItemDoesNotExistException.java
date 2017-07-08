package com.dnfeitosa.codegraph.server.services;

public class ItemDoesNotExistException extends RuntimeException {

    public ItemDoesNotExistException(String message) {
        super(message);
    }
}
