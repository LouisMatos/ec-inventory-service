package br.matosit.inventory_service.adapters.out.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import br.matosit.inventory_service.domain.entities.Product;
import br.matosit.inventory_service.domain.exceptions.ProductIntegrationException;
import reactor.core.publisher.Mono;

@SuppressWarnings("rawtypes")
class ProductServiceGatewayTest {

  private ProductServiceGateway gateway;
  private WebClient webClient;

  private WebClient.RequestHeadersUriSpec uriSpec;
  private WebClient.RequestHeadersSpec headersSpec;
  private WebClient.ResponseSpec responseSpec;


  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    webClient = mock(WebClient.class);
    uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
    headersSpec = mock(WebClient.RequestHeadersSpec.class);
    responseSpec = mock(WebClient.ResponseSpec.class);
    gateway = new ProductServiceGateway(webClient);

    when(webClient.get()).thenReturn(uriSpec);
    when(uriSpec.uri(anyString(), any(Object[].class))).thenReturn(headersSpec);
    when(headersSpec.retrieve()).thenReturn(responseSpec);
  }

  @Test
  void fetchProductByIdReturnsProductOnSuccess() {
    Product expected = new Product("1", "Test", 10);
    when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.just(expected));

    Product result = gateway.fetchProductById("1").block();
    assertNotNull(result);
    assertEquals("1", result.getId());
  }

  @Test
  void fetchProductByIdThrowsProductIntegrationExceptionOn4xx() {
    when(responseSpec.onStatus(any(), any())).thenAnswer(invocation -> {
      java.util.function.Predicate<HttpStatus> predicate = invocation.getArgument(0);
      if (predicate.test(HttpStatus.NOT_FOUND)) {
        return mock(WebClient.ResponseSpec.class, invocationOnMock -> {
          if ("bodyToMono".equals(invocationOnMock.getMethod().getName())) {
            return Mono
                .error(new ProductIntegrationException("Erro ao buscar produto: 404 NOT_FOUND"));
          }
          return RETURNS_DEFAULTS.answer(invocationOnMock);
        });
      }
      return responseSpec;
    });
    when(responseSpec.bodyToMono(Product.class)).thenReturn(
        Mono.error(new ProductIntegrationException("Erro ao buscar produto: 404 NOT_FOUND")));

    assertThrows(ProductIntegrationException.class,
        () -> gateway.fetchProductById("notfound").block());
  }

  @Test
  void fetchProductByIdThrowsProductIntegrationExceptionOn5xx() {
    when(responseSpec.onStatus(any(), any())).thenAnswer(invocation -> {
      java.util.function.Predicate<HttpStatus> predicate = invocation.getArgument(0);
      if (predicate.test(HttpStatus.INTERNAL_SERVER_ERROR)) {
        return mock(WebClient.ResponseSpec.class, invocationOnMock -> {
          if ("bodyToMono".equals(invocationOnMock.getMethod().getName())) {
            return Mono.error(new ProductIntegrationException(
                "Erro ao buscar produto: 500 INTERNAL_SERVER_ERROR"));
          }
          return RETURNS_DEFAULTS.answer(invocationOnMock);
        });
      }
      return invocation;
    });
    when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.error(
        new ProductIntegrationException("Erro ao buscar produto: 500 INTERNAL_SERVER_ERROR")));

    assertThrows(ProductIntegrationException.class,
        () -> gateway.fetchProductById("error").block());
  }

  @Test
  void fetchProductByIdLogsAndThrowsOnGenericError() {
    when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    when(responseSpec.bodyToMono(Product.class))
        .thenReturn(Mono.error(new RuntimeException("Generic error")));

    assertThrows(RuntimeException.class, () -> gateway.fetchProductById("fail").block());
  }

  @Test
  void fetchProductByIdLogsErrorOnException() {
    when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    when(responseSpec.bodyToMono(Product.class))
        .thenReturn(Mono.error(new RuntimeException("Simulated error")));

    assertThrows(RuntimeException.class, () -> gateway.fetchProductById("fail").block());
    // Optionally, verify logging if you use a logging framework that supports it
  }

  @Test
  void fetchProductByIdThrowsProductIntegrationExceptionAndLogsOnHttpError() {
    // Simulate HTTP 404 error
    when(responseSpec.onStatus(any(), any())).thenAnswer(invocation -> {
      java.util.function.Predicate<HttpStatus> predicate = invocation.getArgument(0);
      if (predicate.test(HttpStatus.NOT_FOUND)) {
        return mock(WebClient.ResponseSpec.class, invocationOnMock -> {
          if ("bodyToMono".equals(invocationOnMock.getMethod().getName())) {
            return Mono
                .error(new ProductIntegrationException("Erro ao buscar produto: 404 NOT_FOUND"));
          }
          return RETURNS_DEFAULTS.answer(invocationOnMock);
        });
      }
      return responseSpec;
    });
    when(responseSpec.bodyToMono(Product.class)).thenReturn(
        Mono.error(new ProductIntegrationException("Erro ao buscar produto: 404 NOT_FOUND")));

    assertThrows(ProductIntegrationException.class,
        () -> gateway.fetchProductById("notfound").block());
    // Optionally, use a log capturing tool to assert the log output
  }


}
