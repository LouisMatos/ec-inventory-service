package br.matosit.inventory_service.adapters.in.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.inventory_service.adapters.in.rest.mappers.InventoryMapper;
import br.matosit.inventory_service.adapters.in.rest.responses.InventoryResponse;
import br.matosit.inventory_service.application.ports.in.FindInventoryUseCase;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

  private final FindInventoryUseCase findInventoryUseCase;
  private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

  public InventoryController(FindInventoryUseCase findInventoryUseCase) {
    this.findInventoryUseCase = findInventoryUseCase;
  }

  @GetMapping("/{productId}")
  public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable String productId) {
    log.info("GET /api/inventory/{}", productId);
    var product = findInventoryUseCase.find(productId);
    return ResponseEntity.ok(InventoryMapper.toResponse(product));
  }
}
