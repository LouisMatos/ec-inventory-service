package br.matosit.inventory_service.application.ports;

import br.matosit.inventory_service.domain.entities.Product;

public interface ProductEventHandler {
  void handleProductCreated(Product product);
}
