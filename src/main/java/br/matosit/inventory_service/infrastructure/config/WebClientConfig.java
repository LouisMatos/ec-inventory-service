package br.matosit.inventory_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  private final ApplicationProperties applicationProperties;

  public WebClientConfig(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Bean
  public WebClient webClient(WebClient.Builder builder) {
    return builder.baseUrl(applicationProperties.getWebClientBaseUrl()).build();
  }
}
