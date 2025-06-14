
package br.matosit.inventory_service.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventories")
public class Inventory {

  @Id
  private Long id;
  private String productId;
  private Integer availableQuantity;

  public Inventory(Long id, String productId, Integer availableQuantity) {
    this.id = id;
    this.productId = productId;
    this.availableQuantity = availableQuantity;
  }

  public Inventory(String productId, Integer availableQuantity) {
    this.productId = productId;
    this.availableQuantity = availableQuantity;
  }

  public Inventory() {
    // Construtor padrão para o MongoDB
  }
  // Getters e Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Integer availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  @Override
  public String toString() {
    return "Inventory [id=" + id + ", productId=" + productId + ", availableQuantity="
        + availableQuantity + "]";
  }

}
