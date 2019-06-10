package com.excella.reactor.common.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public static ResourceNotFoundException of(String message) {
        return new ResourceNotFoundException(message);
    }
}
