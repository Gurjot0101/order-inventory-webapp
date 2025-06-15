package com.app.dto;

import javax.validation.constraints.NotNull;

import com.app.model.Product;
import com.app.model.Store;

import lombok.Data;

@Data
public class InventoryStoreProductStatusDTO {
	@NotNull(message = "Product cannot be null.")
	private Product product;

	@NotNull(message = "Store cannot be null.")
	private Store store;

	@NotNull(message = "Order status cannot be null.")
	private String orderStatus;
}
