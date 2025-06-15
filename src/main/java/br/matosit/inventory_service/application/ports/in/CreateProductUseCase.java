package br.matosit.inventory_service.application.ports.in;

import br.matosit.inventory_service.domain.entities.Product;

public interface CreateProductUseCase {
  void handleProductCreated(Product product);
}
