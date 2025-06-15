package com.app.dto;

import com.app.model.Customer;
import com.app.model.Product;
import com.app.model.Store;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class InventoryStoreProductCustomerDTO {

	@NotNull(message = "Product cannot be null.")
	private Product product;

	@NotNull(message = "Store cannot be null.")
	private Store store;

	@NotNull(message = "Customer cannot be null.")
	private Customer customer;
}
