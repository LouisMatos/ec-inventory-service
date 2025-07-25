package br.matosit.inventory_service.adapters.in.rest.responses;

public class InventoryResponse {

  private String productId;
  private Integer availableQuantity;

  public InventoryResponse() {
    // Default constructor
  }

  public InventoryResponse(String productId, Integer availableQuantity) {
    this.productId = productId;
    this.availableQuantity = availableQuantity;
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
    return "InventoryDTO [productId=" + productId + ", availableQuantity=" + availableQuantity
        + "]";
  }

}
