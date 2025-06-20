package br.matosit.inventory_service.adapters.in.messaging;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;
import br.matosit.inventory_service.adapters.in.rest.mappers.ProductEventMapper;
import br.matosit.inventory_service.application.dto.ProductCreatedEvent;
import br.matosit.inventory_service.application.ports.in.CreateProductUseCase;
import br.matosit.inventory_service.domain.entities.Product;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@ExtendWith(MockitoExtension.class)
class KafkaProductListenerTest {

  @Mock
  CreateProductUseCase createProductUseCase;
  @Mock
  ProductEventMapper mapper;
  @Mock
  MeterRegistry meterRegistry;
  @Mock
  Counter failureCounter;
  @Mock
  Acknowledgment acknowledgment;
  @Mock
  Product product;

  @InjectMocks
  KafkaProductListener listener;

  @BeforeEach
  void setUp() {
    when(meterRegistry.counter(anyString())).thenReturn(failureCounter);
    listener = new KafkaProductListener(createProductUseCase, mapper, meterRegistry);
  }

  @Test
  void successfullyProcessesValidProductCreatedEvent() {
    ProductCreatedEvent event = new ProductCreatedEvent();
    event.setId("1");
    event.setName("Test Product");
    event.setStockQuantity(10);

    when(mapper.toDomain(event)).thenReturn(product);
    when(product.getId()).thenReturn("1");

    listener.listen(event, acknowledgment);

    verify(mapper).toDomain(event);
    verify(createProductUseCase).handleProductCreated(product);
    verify(acknowledgment).acknowledge();
    verify(failureCounter, never()).increment();
  }

  @Test
  void acknowledgesAndIncrementsCounterOnInvalidProductEvent() {
    ProductCreatedEvent event = new ProductCreatedEvent();
    event.setId(null);
    event.setName("Test Product");
    event.setStockQuantity(10);

    listener.listen(event, acknowledgment);

    verify(failureCounter).increment();
    verify(acknowledgment).acknowledge();
    verifyNoInteractions(mapper, createProductUseCase);
  }

  @Test
  void incrementsCounterAndDoesNotAcknowledgeOnUnexpectedException() {
    ProductCreatedEvent event = new ProductCreatedEvent();
    event.setId("1");
    event.setName("Test Product");
    event.setStockQuantity(10);

    when(mapper.toDomain(event)).thenReturn(product);
    doThrow(new RuntimeException("Unexpected")).when(createProductUseCase)
        .handleProductCreated(product);

    listener.listen(event, acknowledgment);

    verify(failureCounter).increment();
    verify(acknowledgment, never()).acknowledge();
  }
}
