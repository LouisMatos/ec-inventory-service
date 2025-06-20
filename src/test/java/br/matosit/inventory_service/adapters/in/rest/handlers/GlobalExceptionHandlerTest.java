package br.matosit.inventory_service.adapters.in.rest.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import br.matosit.inventory_service.adapters.in.rest.responses.ErrorResponse;
import br.matosit.inventory_service.domain.exceptions.DomainException;

class GlobalExceptionHandlerTest {

  @Test
  void returnsBadRequestAndErrorResponseWhenDomainExceptionIsThrown() {
    var handler = new GlobalExceptionHandler();
    var ex = new DomainException("Domain error", "CODE-123") {
      private static final long serialVersionUID = -715560882778371469L;
    };
    ResponseEntity<ErrorResponse> response = handler.handleDomainException(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("CODE-123", response.getBody().getCode());
    assertEquals("Domain error", response.getBody().getMessage());
  }

  @Test
  void returnsBadRequestAndValidationErrorsWhenValidationExceptionIsThrown() {
    var handler = new GlobalExceptionHandler();
    var bindingResult = Mockito.mock(BindingResult.class);
    var fieldError = new FieldError("object", "field", "must not be null");
    Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));
    var ex = new MethodArgumentNotValidException(
        Mockito.mock(org.springframework.core.MethodParameter.class), bindingResult);

    ResponseEntity<ErrorResponse> response = handler.handleValidationExceptions(ex);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("VALIDATION-001", response.getBody().getCode());
    assertTrue(response.getBody().getErrors().stream()
        .anyMatch(e -> "field".equals(e.getField()) && "must not be null".equals(e.getMessage())));
  }

  @Test
  void returnsInternalServerErrorAndGenericErrorResponseWhenUnhandledExceptionIsThrown() {
    var handler = new GlobalExceptionHandler();
    var ex = new RuntimeException("Unexpected error");
    ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("INTERNAL-001", response.getBody().getCode());
    assertEquals("Ocorreu um erro interno no servidor", response.getBody().getMessage());
  }
}
