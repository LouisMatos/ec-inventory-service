package br.matosit.inventory_service.application.dto;


public class ProductCreatedEvent {
  private String id;
  private String name;
  private Integer stockQuantity;


  public ProductCreatedEvent(String id, String name, Integer stockQuantity) {
    this.id = id;
    this.name = name;
    this.stockQuantity = stockQuantity;
  }

  public ProductCreatedEvent() {
    // Default constructor
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

  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(Integer stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  @Override
  public String toString() {
    return "ProductCreatedEvent [id=" + id + ", name=" + name + ", quantity=" + stockQuantity + "]";
  }
}
