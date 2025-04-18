package br.matosit.inventory_service.application.usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.matosit.inventory_service.application.dto.InventoryDTO;
import br.matosit.inventory_service.application.dto.ProductDTO;
import br.matosit.inventory_service.domain.exceptions.ProductNotFoundException;
import br.matosit.inventory_service.infrastructure.gateway.ProductServiceGateway;
import reactor.core.publisher.Mono;

@Service
public class FindInventoryUseCase {
  private final ProductServiceGateway productServiceGateway;
  private final ObjectMapper objectMapper;
  private static final Logger log = LoggerFactory.getLogger(FindInventoryUseCase.class);

  public FindInventoryUseCase(ProductServiceGateway productServiceGateway,
      ObjectMapper objectMapper) {
    this.productServiceGateway = productServiceGateway;
    this.objectMapper = objectMapper;
  }

  public InventoryDTO execute(String productId) {
    log.info("Fetching product with id: {}", productId);

    String productData = productServiceGateway.fetchData("/api/products/" + productId)
        .doOnNext(data -> log.info("Product data retrieved successfully for id: {}", productId))
        .blockOptional().orElseThrow(() -> new ProductNotFoundException(productId));

    return toDTO(productData);
  }

  private InventoryDTO toDTO(String productData) {
    try {
      ProductDTO productDTO = objectMapper.readValue(productData, ProductDTO.class);

      // Validate required fields
      if (productDTO.getId() == null) {
        throw new IllegalArgumentException("Missing required fields in product data");
      }

      return new InventoryDTO(productDTO.getId(), 1);
    } catch (Exception e) {
      log.error("Error mapping product data to InventoryDTO", e);
      throw new RuntimeException("Failed to map product data", e);
    }
  }
}
