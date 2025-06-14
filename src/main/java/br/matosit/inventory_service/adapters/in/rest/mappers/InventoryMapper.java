package br.matosit.inventory_service.adapters.in.rest.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.matosit.inventory_service.adapters.in.rest.responses.InventoryResponse;
import br.matosit.inventory_service.application.dto.ProductDTO;
import br.matosit.inventory_service.domain.entities.Product;
import br.matosit.inventory_service.domain.exceptions.ProductIntegrationException;

public class InventoryMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static Product toProduct(String productData) {
    try {
      ProductDTO productDTO = objectMapper.readValue(productData, ProductDTO.class);

      // Validate required fields
      if (productDTO.getId() == null) {
        throw new IllegalArgumentException("Missing required fields in product data");
      }

      return new Product(productDTO.getId(), productDTO.getName(), productDTO.getStockQuantity());
    } catch (Exception e) {
      throw new RuntimeException("Failed to map product data", e);
    }
  }

  public static InventoryResponse toResponse(Product product) {
    return new InventoryResponse(product.getId(), product.getStockQuantity());
  }


  public static Product fromJson(String json, ObjectMapper mapper) {
    try {
      Product product = mapper.readValue(json, Product.class);
      if (product.getId() == null) {
        throw new ProductIntegrationException("Produto sem ID");
      }
      return product;
    } catch (Exception e) {
      throw new ProductIntegrationException(
          "Erro ao converter produto" + " para o formato esperado: " + e.getMessage());
    }
  }
}
