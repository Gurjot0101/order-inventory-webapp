package com.app.dto;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class OrderItemDTO {
	@Min(value = 1, message = "Product ID must be a positive integer")
	private int productId;
}
