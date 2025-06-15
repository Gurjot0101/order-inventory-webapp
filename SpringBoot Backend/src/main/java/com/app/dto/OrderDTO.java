package com.app.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderDTO {
	@Min(value = 1, message = "Order ID must be a positive integer")
	private int orderId;

	@Min(value = 1, message = "Customer ID must be a positive integer")
	private int customerId;

	@NotBlank(message = "Order status is required")
	private String orderStatus;

	@NotNull(message = "Order timestamp is required")
	private Timestamp orderTms;

	@Min(value = 1, message = "Store ID must be a positive integer")
	private int storeId;
}
