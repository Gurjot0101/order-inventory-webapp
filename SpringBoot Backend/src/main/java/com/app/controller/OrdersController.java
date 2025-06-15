package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.OrderDTO;
import com.app.exceptions.list.InternalServerErrorException;
import com.app.exceptions.list.InvalidDataException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.services.IorderService;
import com.app.util.Status;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow cross-origin requests from React frontend
@RequestMapping("/api/v1/orders") // Base URL for order-related requests
public class OrdersController {

	@Autowired
	private IorderService ordersService;

	/**
	 * Retrieve all orders.
	 * 
	 * @return ResponseEntity containing the list of OrderDTOs and HTTP status code
	 * @throws InternalServerErrorException if no orders are found
	 */
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getOrders() throws InternalServerErrorException {
		List<OrderDTO> list = ordersService.getOrders();
		if (list == null) {
			throw new InternalServerErrorException("Orders not found");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * Create a new order.
	 * 
	 * @param order OrderDTO object representing the new order
	 * @return ResponseEntity containing the created OrderDTO and HTTP status code
	 * @throws ResourceNotFoundException if the order cannot be created
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) throws ResourceNotFoundException {
		return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.createOrder(order));
	}

	/**
	 * Update an existing order.
	 * 
	 * @param order OrderDTO object representing the updated order
	 * @return ResponseEntity containing the updated OrderDTO and HTTP status code
	 * @throws ResourceNotFoundException if the order cannot be found or updated
	 */
	@PutMapping
	public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO order) throws ResourceNotFoundException {
		return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.updateOrder(order));
	}

	/**
	 * Get the count of orders by their status.
	 * 
	 * @return ResponseEntity containing a list of Status objects and HTTP status
	 *         code
	 */
	@GetMapping("/status")
	public ResponseEntity<List<Status>> orderStatus() {
		return ResponseEntity.ok(ordersService.orderStatus());
	}

	/**
	 * Retrieve all orders for a specific store.
	 * 
	 * @param store Store name to filter the orders
	 * @return ResponseEntity containing a list of OrderDTOs for the specified store
	 */
	@GetMapping("/store/{store}")
	public ResponseEntity<List<OrderDTO>> getOrdersByStore(@PathVariable("store") String store) {
		return ResponseEntity.ok(ordersService.getOrdersByStore(store));
	}

	/**
	 * Retrieve an order by its ID.
	 * 
	 * @param id ID of the order to be retrieved
	 * @return ResponseEntity containing the OrderDTO and HTTP status code
	 * @throws ResourceNotFoundException if the order is not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") int id) throws ResourceNotFoundException {
		return ResponseEntity.ok(ordersService.getOrderById(id));
	}

	/**
	 * Retrieve all orders for a specific customer by their customer ID.
	 * 
	 * @param customerId Customer ID to filter the orders
	 * @return ResponseEntity containing a list of OrderDTOs for the specified
	 *         customer
	 */
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<OrderDTO>> getOrdersByCustomerId(@PathVariable("customerId") int customerId) {
		return ResponseEntity.ok(ordersService.getOrdersByCustomerId(customerId));
	}

	/**
	 * Cancel an order by its ID.
	 * 
	 * @param id ID of the order to be cancelled
	 * @return ResponseEntity containing a confirmation message
	 * @throws ResourceNotFoundException if the order is not found
	 */
	@GetMapping("/{id}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable("id") int id) throws ResourceNotFoundException {
		ordersService.cancelOrder(id);
		return ResponseEntity.ok("Order Cancelled");
	}

	/**
	 * Retrieve all orders by their status.
	 * 
	 * @param status Status of the orders to be retrieved
	 * @return List of OrderDTOs for the specified status
	 */
	@GetMapping("/status/{status}")
	public List<OrderDTO> getOrdersByStatus(@PathVariable("status") String status) {
		return ordersService.getOrdersByStatus(status);
	}

	/**
	 * Retrieve all orders within a specified date range.
	 * 
	 * @param startDate Start date of the range (format: yyyy-MM-dd)
	 * @param endDate   End date of the range (format: yyyy-MM-dd)
	 * @return List of OrderDTOs within the specified date range
	 * @throws InvalidDataException if the date format or range is invalid
	 */
	@GetMapping("/date/{startDate}/{endDate}")
	public List<OrderDTO> getOrdersByDate(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) throws InvalidDataException {
		return ordersService.getOrdersByDate(startDate, endDate);
	}

	/**
	 * Retrieve all orders associated with a specific customer's email.
	 * 
	 * @param email Email of the customer to filter the orders
	 * @return List of OrderDTOs associated with the specified email
	 */
	@GetMapping("/customer/email/{email}")
	public List<OrderDTO> getOrdersByEmail(@PathVariable("email") String email) {
		return ordersService.getOrdersByEmail(email);
	}
}
