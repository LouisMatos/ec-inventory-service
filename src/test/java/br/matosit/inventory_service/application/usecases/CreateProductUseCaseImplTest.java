package br.matosit.inventory_service.application.usecases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.matosit.inventory_service.application.ports.out.ProductRepository;
import br.matosit.inventory_service.domain.entities.Product;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseImplTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private CreateProductUseCaseImpl useCase;

  @Test
  void createProductShouldSaveProduct() {
    Product product = new Product("1", "Test", 10);
    // Mock the repository to return the product when save is called
    when(productRepository.save(product)).thenReturn(product);

    useCase.handleProductCreated(product);

    verify(productRepository).save(product);
  }

}
