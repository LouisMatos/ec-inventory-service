package br.matosit.inventory_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.matosit.inventory_service.application.ports.in.CreateProductUseCase;
import br.matosit.inventory_service.application.ports.out.ProductRepository;
import br.matosit.inventory_service.domain.entities.Product;

@Service
public class CreateProductUseCaseImpl implements CreateProductUseCase {

  private final ProductRepository productRepository;

  private static final Logger log = LoggerFactory.getLogger(CreateProductUseCaseImpl.class);

  public CreateProductUseCaseImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void handleProductCreated(Product product) {
    log.info("Saving product to inventory: {}", product);
    Product savedProduct = productRepository.save(product);
    log.info("Product saved successfully with ID: {}", savedProduct.getId());
  }
}
