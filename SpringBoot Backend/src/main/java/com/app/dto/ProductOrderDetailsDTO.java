package com.app.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.app.model.Product;
import com.app.model.Store;

import lombok.Data;

@Data
public class ProductOrderDetailsDTO {
	@NotNull(message = "Product cannot be null.")
	private Product product;

	@NotNull(message = "Store cannot be null.")
	private Store store;

	@NotNull(message = "Shipment status cannot be null.")
	private String shipmentStatus;

	@Positive(message = "Total amount must be positive.")
	private BigDecimal totalAmount;
}
