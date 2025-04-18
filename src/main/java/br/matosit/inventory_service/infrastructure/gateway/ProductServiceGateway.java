package br.matosit.inventory_service.infrastructure.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductServiceGateway {

  private final WebClient webClient;

  public ProductServiceGateway(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<String> fetchData(String endpoint) {
    try {
      return webClient.get().uri(endpoint).retrieve().bodyToMono(String.class)
          .doOnError(error -> System.err.println("Error fetching data: " + error.getMessage()));
    } catch (Exception e) {
      System.err.println("Exception occurred: " + e.getMessage());
    }
    return null;

  }

}
