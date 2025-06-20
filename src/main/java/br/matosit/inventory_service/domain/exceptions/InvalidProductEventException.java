package br.matosit.inventory_service.domain.exceptions;

public class InvalidProductEventException extends RuntimeException {

  private static final long serialVersionUID = 7233178418738114187L;

  public InvalidProductEventException(String message) {
    super(message);
  }
}
