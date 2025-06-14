package br.matosit.inventory_service.domain.exceptions;

public class ProductIntegrationException extends DomainException {

  private static final long serialVersionUID = 8078814249958268178L;

  public ProductIntegrationException(String message) {
    super(message, "PRODUCT-003");
  }
}
