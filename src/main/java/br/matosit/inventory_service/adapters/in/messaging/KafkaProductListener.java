package br.matosit.inventory_service.adapters.in.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import br.matosit.inventory_service.adapters.in.rest.mappers.ProductEventMapper;
import br.matosit.inventory_service.application.dto.ProductCreatedEvent;
import br.matosit.inventory_service.application.ports.in.CreateProductUseCase;
import br.matosit.inventory_service.domain.entities.Product;

@Component
public class KafkaProductListener {

  private static final Logger log = LoggerFactory.getLogger(KafkaProductListener.class);

  private final CreateProductUseCase createProductUseCase;
  private final ProductEventMapper mapper;

  public KafkaProductListener(CreateProductUseCase createProductUseCase,
      ProductEventMapper mapper) {
    this.createProductUseCase = createProductUseCase;
    this.mapper = mapper;
  }

  @KafkaListener(topics = "product-created", groupId = "inventory-service",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(ProductCreatedEvent event, Acknowledgment acknowledgment) {
    log.info("Received product-created event: {}", event);
    try {
      Product product = mapper.toDomain(event);
      createProductUseCase.handleProductCreated(product);
      acknowledgment.acknowledge();
      log.debug("Successfully processed product: {}", product.getId());
    } catch (IllegalArgumentException e) {
      log.warn("Invalid product event received. Skipping message: {}", event, e);
    } catch (Exception e) {
      log.error("Error while processing product-created event: {}", event, e);
    }
  }
}
