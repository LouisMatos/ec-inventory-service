package br.matosit.inventory_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.inventory_service.adapters.out.gateway.ProductServiceGateway;
import br.matosit.inventory_service.application.ports.in.FindInventoryUseCase;
import br.matosit.inventory_service.domain.entities.Product;
import br.matosit.inventory_service.domain.exceptions.ProductNotFoundException;

@Service
public class FindInventoryUseCaseImpl implements FindInventoryUseCase {

  private final ProductServiceGateway productServiceGateway;
  private static final Logger log = LoggerFactory.getLogger(FindInventoryUseCaseImpl.class);

  public FindInventoryUseCaseImpl(ProductServiceGateway productServiceGateway) {
    this.productServiceGateway = productServiceGateway;
  }

  @Override
  public Product find(String productId) {
    log.info("Fetching product with id: {}", productId);
    Product product = productServiceGateway.fetchProductById(productId).blockOptional()
        .orElseThrow(() -> new ProductNotFoundException(productId));
    return product;
  }
}
