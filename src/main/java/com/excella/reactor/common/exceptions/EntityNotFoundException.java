package com.excella.reactor.common.exceptions;

public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException(String message) {
    super(message);
  }
  public static BookNotFoundException of(String message) {
    return new BookNotFoundException(message);
  }
}
