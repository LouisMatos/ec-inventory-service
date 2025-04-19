package br.matosit.inventory_service.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products_inventory")
public class Product {

  @Id
  private String id;
  private String name;
  private Integer quantity;

  // Constructor, getters and setters
  public Product(String id, String name, Integer quantity) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
  }

  public Product(String name, Integer quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public Product() {
    // Default constructor for MongoDB
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "Product [id=" + id + ", name=" + name + ", quantity=" + quantity + "]";
  }

}
