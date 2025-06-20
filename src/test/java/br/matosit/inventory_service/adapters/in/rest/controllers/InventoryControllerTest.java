package br.matosit.inventory_service.adapters.in.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import br.matosit.inventory_service.adapters.in.rest.mappers.InventoryMapper;
import br.matosit.inventory_service.adapters.in.rest.responses.InventoryResponse;
import br.matosit.inventory_service.application.ports.in.FindInventoryUseCase;
import br.matosit.inventory_service.domain.entities.Product;

class InventoryControllerTest {

  FindInventoryUseCase findInventoryUseCase;
  InventoryController controller;

  @BeforeEach
  void setUp() {
    findInventoryUseCase = mock(FindInventoryUseCase.class);
    controller = new InventoryController(findInventoryUseCase);
  }

  @Test
  void returnsOkAndInventoryResponseWhenProductIsFound() {
    var product = new Product();
    when(findInventoryUseCase.find("prod-1")).thenReturn(product);

    var expectedResponse = new InventoryResponse();
    mockStatic(InventoryMapper.class).when(() -> InventoryMapper.toResponse(product))
        .thenReturn(expectedResponse);

    ResponseEntity<InventoryResponse> response = controller.getInventoryByProductId("prod-1");

    assertEquals("200 OK", response.getStatusCode().toString());
    assertSame(expectedResponse, response.getBody());
  }

  @Test
  void throwsExceptionWhenProductDoesNotExist() {
    when(findInventoryUseCase.find("missing")).thenThrow(new RuntimeException("Not found"));

    assertThrows(RuntimeException.class, () -> controller.getInventoryByProductId("missing"));
  }
}
