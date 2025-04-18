package br.matosit.inventory_service.presentation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.matosit.inventory_service.application.dto.InventoryDTO;
import br.matosit.inventory_service.application.usecases.FindInventoryUseCase;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

  private final FindInventoryUseCase findInventoryUseCase;

  Logger log = LoggerFactory.getLogger(InventoryController.class);

  public InventoryController(FindInventoryUseCase findInventoryUseCase) {
    this.findInventoryUseCase = findInventoryUseCase;
  }

  @GetMapping("/{productId}")
  public ResponseEntity<InventoryDTO> getInventoryByProductId(@PathVariable String productId) {
    log.info("Buscando produto com id: {}", productId);
    InventoryDTO response = findInventoryUseCase.execute(productId);
    log.info("Produto encontrado: {}", response);
    return ResponseEntity.ok(response);
  }
}
