package br.matosit.inventory_service.adapters.out.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import br.matosit.inventory_service.domain.entities.Product;
import br.matosit.inventory_service.domain.exceptions.ProductIntegrationException;
import reactor.core.publisher.Mono;

@Component
public class ProductServiceGateway {

  private static final Logger log = LoggerFactory.getLogger(ProductServiceGateway.class);
  private final WebClient webClient;

  public ProductServiceGateway(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Product> fetchProductById(String productId) {
    return webClient.get() //
        .uri("/api/products/{id}", productId) //
        .retrieve() //
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), //
            response -> { //
              log.error("Erro HTTP ao buscar produto: {}", response.statusCode()); //
              return Mono.error(new ProductIntegrationException( //
                  "Erro ao buscar produto: " + response.statusCode())); //
            }) //
        .bodyToMono(Product.class) //
        .doOnError(error -> log.error("Erro ao buscar produto", error));//
  }
}
