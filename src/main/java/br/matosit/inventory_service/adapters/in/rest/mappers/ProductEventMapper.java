package br.matosit.inventory_service.adapters.in.rest.mappers;

import org.springframework.stereotype.Component;
import br.matosit.inventory_service.application.dto.ProductCreatedEvent;
import br.matosit.inventory_service.domain.entities.Product;

@Component
public class ProductEventMapper {

  public Product toDomain(ProductCreatedEvent event) {
    return new Product(event.getId(), event.getName(), event.getStockQuantity());
  }

}
