package com.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.app.services.impl.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.app.dao.CustomerRepository;
import com.app.dao.OrderRepository;
import com.app.dao.ProductRepository;
import com.app.dao.ShipmentRepository;
import com.app.dao.StoreRepository;
import com.app.dto.OrderDTO;
import com.app.exceptions.list.InvalidDataException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Store;

public class OrderServiceTest {

	// Mocking repositories and ModelMapper for the service
	@Mock
	private OrderRepository ordersRepo;

	@Mock
	private StoreRepository storeRepo;

	@Mock
	private CustomerRepository customerRepo;

	@Mock
	private ProductRepository productRepo;

	@Mock
	private ShipmentRepository shipmentRepo;

	@Mock
	private ModelMapper modelMapper;

	// Injecting the mocked dependencies into the service class
	@InjectMocks
	private OrderService orderService;

	// Initializes the mocks before each test case
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Test case for the getOrders method
	@Test
	public void testGetOrders() {
		// Setting up mock data
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		orders.add(order);

		OrderDTO orderDTO = new OrderDTO();
		when(ordersRepo.findAll()).thenReturn(orders); // Mocking repository response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		List<OrderDTO> result = orderService.getOrders();

		// Validating the results
		assertNotNull(result); // Ensures the result is not null
		assertEquals(1, result.size()); // Ensures the size of the result is correct
		assertEquals(orderDTO, result.get(0)); // Validates the result content
	}

	// Test case for getOrdersByStore method
	@Test
	public void testGetOrdersByStore() {
		String storeName = "Test Store";
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		orders.add(order);

		OrderDTO orderDTO = new OrderDTO();
		when(ordersRepo.findOrdersByStoreName(storeName)).thenReturn(orders); // Mocking repository response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		List<OrderDTO> result = orderService.getOrdersByStore(storeName);

		// Validating the results
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(orderDTO, result.get(0));
	}

	// Test case for getOrderById method
	@Test
	public void testGetOrderById() throws ResourceNotFoundException {
		int orderId = 1;
		Order order = new Order();
		OrderDTO orderDTO = new OrderDTO();
		when(ordersRepo.findById(orderId)).thenReturn(java.util.Optional.of(order)); // Mocking repository response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		OrderDTO result = orderService.getOrderById(orderId);

		// Validating the result
		assertNotNull(result);
		assertEquals(orderDTO, result);
	}

	// Test case for getOrdersByCustomerId method
	@Test
	public void testGetOrdersByCustomerId() {
		int customerId = 1;
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		orders.add(order);

		OrderDTO orderDTO = new OrderDTO();
		when(ordersRepo.findByCustomerId(customerId)).thenReturn(orders); // Mocking repository response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		List<OrderDTO> result = orderService.getOrdersByCustomerId(customerId);

		// Validating the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(orderDTO, result.get(0));
	}

	// Test case for getOrdersByEmail method
	@Test
	public void testGetOrdersByEmail() throws InvalidDataException {
		String email = "test@example.com";
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		orders.add(order);

		OrderDTO orderDTO = new OrderDTO();
		when(ordersRepo.findByEmail(email)).thenReturn(orders); // Mocking repository response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		List<OrderDTO> result = orderService.getOrdersByEmail(email);

		// Validating the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(orderDTO, result.get(0));
	}

	// Test case for getOrdersByDate method
	@Test
	public void testGetOrdersByDate() throws InvalidDataException {
		String startDate = "01012024";
		String endDate = "31012024";
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		orders.add(order);

		OrderDTO orderDTO = new OrderDTO();
		when(ordersRepo.findOrdersByDateRange(any(Timestamp.class), any(Timestamp.class))).thenReturn(orders); // Mocking
																												// repository
																												// response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		List<OrderDTO> result = orderService.getOrdersByDate(startDate, endDate);

		// Validating the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(orderDTO, result.get(0));
	}

	// Test case for cancelOrder method
	@Test
	public void testCancelOrder() throws ResourceNotFoundException {
		int orderId = 1;
		when(ordersRepo.findById(orderId)).thenReturn(java.util.Optional.of(new Order())); // Mocking repository
																							// response

		// Calling the method being tested
		Boolean result = orderService.cancelOrder(orderId);

		// Validating the result
		assertTrue(result);
	}

	// Test case for getOrdersByStatus method
	@Test
	public void testGetOrdersByStatus() {
		String status = "Open";
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		orders.add(order);

		OrderDTO orderDTO = new OrderDTO();
		when(ordersRepo.findByStatus(status)).thenReturn(orders); // Mocking repository response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		List<OrderDTO> result = orderService.getOrdersByStatus(status);

		// Validating the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(orderDTO, result.get(0));
	}

	// Test case for createOrder method
	@Test
	public void testCreateOrder() throws ResourceNotFoundException {
		OrderDTO orderDTO = new OrderDTO();
		Order order = new Order();
		when(modelMapper.map(orderDTO, Order.class)).thenReturn(order); // Mocking ModelMapper response
		when(ordersRepo.save(order)).thenReturn(order); // Mocking repository response
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		OrderDTO result = orderService.createOrder(orderDTO);

		// Validating the result
		assertNotNull(result);
		assertEquals(orderDTO, result);
	}

	// Test case for updateOrder method
	@Test
	public void testUpdateOrder() throws ResourceNotFoundException {
		OrderDTO orderDTO = new OrderDTO();
		Order order = new Order();
		when(ordersRepo.findById(orderDTO.getOrderId())).thenReturn(java.util.Optional.of(order)); // Mocking repository
																									// response
		when(customerRepo.findById(orderDTO.getCustomerId())).thenReturn(java.util.Optional.of(new Customer())); // Mocking
																													// Customer
																													// repository
																													// response
		when(storeRepo.findById(orderDTO.getStoreId())).thenReturn(java.util.Optional.of(new Store())); // Mocking Store
																										// repository
																										// response
		when(ordersRepo.save(order)).thenReturn(order); // Mocking repository save method
		when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO); // Mocking ModelMapper response

		// Calling the method being tested
		OrderDTO result = orderService.updateOrder(orderDTO);

		// Validating the result
		assertNotNull(result);
		assertEquals(orderDTO, result);
	}

}
