package com.app.services;

import java.util.List;

import com.app.model.OrderItem;

public interface IorderItemService {

	/**
	 * Retrieves all order items.
	 *
	 * @return the list of order items
	 */
	public List<OrderItem> getAllOrderItem();
}
