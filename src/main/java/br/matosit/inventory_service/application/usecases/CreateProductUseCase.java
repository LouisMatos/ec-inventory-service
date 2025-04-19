package br.matosit.inventory_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.inventory_service.application.ports.ProductEventHandler;
import br.matosit.inventory_service.application.ports.ProductRepository;
import br.matosit.inventory_service.domain.entities.Product;

@Service
public class CreateProductUseCase implements ProductEventHandler {

  private final ProductRepository productRepository;

  private static final Logger log = LoggerFactory.getLogger(CreateProductUseCase.class);

  public CreateProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void handleProductCreated(Product product) {
    log.info("Saving product to inventory: {}", product);
    Product savedProduct = productRepository.save(product);
    log.info("Product saved successfully with ID: {}", savedProduct.getId());
  }
}
