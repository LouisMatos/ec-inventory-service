package br.matosit.inventory_service.application.ports;

import br.matosit.inventory_service.domain.entities.Product;

public interface ProductRepository {

  Product save(Product customer);

}
