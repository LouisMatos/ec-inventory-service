package br.matosit.inventory_service.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.matosit.inventory_service.adapters.out.gateway.ProductServiceGateway;
import br.matosit.inventory_service.domain.entities.Product;
import br.matosit.inventory_service.domain.exceptions.ProductNotFoundException;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class FindInventoryUseCaseImplTest {

  @Mock
  private ProductServiceGateway productServiceGateway;

  @InjectMocks
  private FindInventoryUseCaseImpl useCase;

  @Test
  void find_ShouldReturnProduct_WhenProductExists() {
    String productId = "123";
    Product product = new Product(productId, "Test Product", 10);
    when(productServiceGateway.fetchProductById(productId)).thenReturn(Mono.just(product));

    Product result = useCase.find(productId);

    assertNotNull(result);
    assertEquals(productId, result.getId());
    verify(productServiceGateway).fetchProductById(productId);
  }

  @Test
  void find_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {
    String productId = "not_found";
    when(productServiceGateway.fetchProductById(productId)).thenReturn(Mono.empty());

    assertThrows(ProductNotFoundException.class, () -> useCase.find(productId));
    verify(productServiceGateway).fetchProductById(productId);
  }
}
