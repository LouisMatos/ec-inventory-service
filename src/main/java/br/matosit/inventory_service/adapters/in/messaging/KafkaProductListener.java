package br.matosit.inventory_service.adapters.in.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import br.matosit.inventory_service.adapters.in.rest.mappers.ProductEventMapper;
import br.matosit.inventory_service.application.dto.ProductCreatedEvent;
import br.matosit.inventory_service.application.ports.in.CreateProductUseCase;
import br.matosit.inventory_service.domain.entities.Product;
import br.matosit.inventory_service.domain.exceptions.InvalidProductEventException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
@Validated
public class KafkaProductListener {

  private static final Logger log = LoggerFactory.getLogger(KafkaProductListener.class);

  private final CreateProductUseCase createProductUseCase;
  private final ProductEventMapper mapper;
  private final Counter failureCounter;

  public KafkaProductListener(CreateProductUseCase createProductUseCase, ProductEventMapper mapper,
      MeterRegistry meterRegistry) {
    this.createProductUseCase = createProductUseCase;
    this.mapper = mapper;
    this.failureCounter = meterRegistry.counter("kafka_product_created_event_failures");
  }

  @KafkaListener(topics = "product-created", groupId = "inventory-service",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(ProductCreatedEvent event, Acknowledgment acknowledgment) {
    log.debug("Received product-created event: {}", event);

    try {
      validateEvent(event);

      Product product = mapper.toDomain(event);
      createProductUseCase.handleProductCreated(product);

      acknowledgment.acknowledge();
      log.info("Successfully processed product: {}", product.getId());

    } catch (InvalidProductEventException e) {
      log.warn("Invalid product event. Sending to dead-letter (simulado): {}", event, e);
      failureCounter.increment();
      acknowledgment.acknowledge(); // Ack mesmo assim para evitar reprocessamento infinito
    } catch (Exception e) {
      log.error("Unexpected error while processing product-created event: {}", event, e);
      failureCounter.increment();
      // Mensagem não é acked, permitindo retry futuro automático
    }
  }

  private void validateEvent(ProductCreatedEvent event) {
    if (event.getId() == null || event.getName() == null || event.getStockQuantity() == null) {
      throw new InvalidProductEventException("Campos obrigatórios ausentes no evento.");
    }
  }
}
