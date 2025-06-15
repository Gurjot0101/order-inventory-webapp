package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.OrderItem;
import com.app.services.impl.OrderItemService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow cross-origin requests from React frontend
@RequestMapping("/api/v1/order-items") // Base URL for all order item-related requests
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;

	/**
	 * Create multiple order items. This is typically used after an order is created
	 * to add items to the order.
	 * 
	 * @param orderItems List of OrderItem objects to be created
	 * @return ResponseEntity containing the list of created OrderItem objects and
	 *         HTTP status code
	 */
	@PostMapping
	public ResponseEntity<List<OrderItem>> createOrderItems(@RequestBody List<OrderItem> orderItems) {
		List<OrderItem> createdOrderItems = orderItemService.createOrderItems(orderItems);
		return new ResponseEntity<>(createdOrderItems, HttpStatus.CREATED);
	}

	/**
	 * Fetch all order items (optional feature, useful for admin or debugging
	 * purposes).
	 * 
	 * @return ResponseEntity containing the list of all OrderItem objects and HTTP
	 *         status code
	 */
	@GetMapping
	public ResponseEntity<List<OrderItem>> getAllOrderItems() {
		List<OrderItem> orderItems = orderItemService.getAllOrderItems();
		return new ResponseEntity<>(orderItems, HttpStatus.OK);
	}

	/**
	 * Fetch all order items associated with a specific order. This is used to
	 * retrieve all items belonging to an order by its order ID.
	 * 
	 * @param orderId ID of the order for which the order items are to be fetched
	 * @return ResponseEntity containing the list of OrderItem objects for the
	 *         specified order and HTTP status code
	 */
	@GetMapping("/{orderId}")
	public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable int orderId) {
		List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
		return new ResponseEntity<>(orderItems, HttpStatus.OK);
	}
}
