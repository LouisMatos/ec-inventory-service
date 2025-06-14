package br.matosit.inventory_service.application.dto;

public class ProductDTO {

  private String id;
  private String name;
  private String description;
  private double price;
  private int stockQuantity;
  private String image3D;

  public ProductDTO() {
    // Construtor padrão
  }
  
  // Construtor
  public ProductDTO(String id, String name, String description, double price, int stockQuantity,
      String image3D) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.image3D = image3D;
  }

  // Getters e Setters
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public String getImage3D() {
    return image3D;
  }

  public void setImage3D(String image3D) {
    this.image3D = image3D;
  }
}
