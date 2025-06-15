package com.app.dto;

import com.app.model.Store;
import com.app.model.Product;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class InventoryDTO {

    // Ensures the inventoryId is a positive integer
    @Positive(message = "Inventory ID must be positive.")
    private int inventoryId;

    // Ensures the store object is not null when setting the store property
    @NotNull(message = "Store cannot be null.")
    private Store store;

    // Ensures the productInventory is a positive integer (non-zero quantity)
    @Positive(message = "Product inventory must be positive.")
    private int productInventory;

    // Ensures the product object is not null when setting the products property
    @NotNull(message = "Product cannot be null.")
    private Product products;
}
