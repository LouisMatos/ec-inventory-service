package br.matosit.inventory_service.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

  @Value("${webclient.product.base-url}")
  private String webClientBaseUrl;

  public String getWebClientBaseUrl() {
    return webClientBaseUrl;
  }

}
