package br.matosit.inventory_service.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import br.matosit.inventory_service.application.dto.ProductCreatedEvent;
import br.matosit.inventory_service.application.ports.ProductEventHandler;
import br.matosit.inventory_service.domain.entities.Product;

@Component
public class KafkaProductListener {

  private final ProductEventHandler productEventHandler;

  private static final Logger log = LoggerFactory.getLogger(KafkaProductListener.class);

  // Constructor
  public KafkaProductListener(ProductEventHandler productEventHandler) {
    this.productEventHandler = productEventHandler;
  }

  @KafkaListener(topics = "product-created", containerFactory = "kafkaListenerContainerFactory")
  public void listen(ProductCreatedEvent event, Acknowledgment acknowledgment) {
    try {
      Product product = mapToProduct(event);
      productEventHandler.handleProductCreated(product);
      acknowledgment.acknowledge(); // Acknowledge the message after successful processing
    } catch (Exception e) {
      // Log and handle the exception
      log.error("Error processing event: {}", event, e);
    }
  }


  private Product mapToProduct(ProductCreatedEvent event) {
    Product product = new Product();
    product.setId(event.getId());
    product.setName(event.getName());
    product.setQuantity(event.getStockQuantity());
    return product;
  }
}
