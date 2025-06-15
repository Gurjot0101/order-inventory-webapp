package com.app.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.app.services.impl.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.CustomerRepository;
import com.app.dto.CustomerOrders;
import com.app.dto.CustomerShipment;
import com.app.dto.ShipmentStatusCountCustomer;
import com.app.exceptions.list.BadRequestException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Shipment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class CustomerServiceTest {

	@Mock
	private CustomerRepository repo;

	@Mock
	private EntityManager entityManager; // Mock EntityManager

	@InjectMocks
	private CustomerService customerService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer());
		when(repo.findAll()).thenReturn(customers);

		List<Customer> result = customerService.getAllCustomers();
		assertEquals(customers, result);
	}

	@Test
	@Transactional
	public void testAddCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setEmailAddress("test@example.com");
		customer.setFullName("Test User");

		Query query = mock(Query.class);
		when(entityManager.createQuery("SELECT MAX(c.customerId) FROM Customer c")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(1); // Mock the EntityManager behavior
		when(repo.save(any(Customer.class))).thenReturn(customer);

		Customer result = customerService.addCustomer(customer);
		assertNotNull(result);
		assertEquals("test@example.com", result.getEmailAddress());
	}

	@Test
	public void testUpdateCustomer() {
		Customer existingCustomer = new Customer();
		existingCustomer.setCustomerId(1);
		existingCustomer.setEmailAddress("old@example.com");
		existingCustomer.setFullName("Old Name");

		Customer updatedCustomer = new Customer();
		updatedCustomer.setCustomerId(1);
		updatedCustomer.setEmailAddress("new@example.com");
		updatedCustomer.setFullName("New Name");

		when(repo.findById(anyInt())).thenReturn(Optional.of(existingCustomer));
		when(repo.save(any(Customer.class))).thenReturn(updatedCustomer);

		Customer result = customerService.updateCustomer(updatedCustomer);
		assertNotNull(result);
		assertEquals("new@example.com", result.getEmailAddress());
	}

	@Test
	public void testDeleteCustomer() throws BadRequestException {
		when(repo.existsById(anyInt())).thenReturn(true);

		customerService.deleteCustomer(1);
		verify(repo, times(1)).deleteById(1);
	}

	@Test
	public void testDeleteCustomerNotFound() {
		when(repo.existsById(anyInt())).thenReturn(false);

		BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
			customerService.deleteCustomer(1);
		});
		assertEquals("Customer with ID 1 does not exist.", thrown.getMessage());
	}

	
//	@Test
//	public void testGetCustomerByEmailAddress() {
//	    Customer customer = new Customer(); // Create a single customer object
//	    when(repo.findByEmailAddress(anyString())).thenReturn(Collections.singletonList(customer)); // Return a list containing the single customer
//
//	    List<Customer> result = customerService.getCustomerByEmailAddress("test@example.com"); // Expect a single customer, not a list
//	    assertEquals(customer, result); // Compare the single customer object
//	}
	@Test
	public void testGetCustomerByEmailAddress() {
	    Customer customer = new Customer(); // Create a single customer object
	    when(repo.findByEmailAddress(anyString())).thenReturn(Collections.singletonList(customer)); // Return a list containing the single customer

	    List<Customer> result = customerService.getCustomerByEmailAddress("test@example.com"); // Call the method, expecting a list
	    assertEquals(1, result.size()); // Check if the list contains exactly one customer
	    assertEquals(customer, result.get(0)); // Compare the customer object with the first element in the list
	}



	@Test
	public void testGetCustomersByFullName() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer());
		when(repo.findByFullNameContains(anyString())).thenReturn(customers);

		List<Customer> result = customerService.getCustomersByFullName("Test User");
		assertEquals(customers, result);
	}

	@Test
	public void testGetOrderCountByStatus() {
		List<ShipmentStatusCountCustomer> statusCount = new ArrayList<>();
		when(repo.getOrderCountByStatus()).thenReturn(statusCount);

		List<ShipmentStatusCountCustomer> result = customerService.getOrderCountByStatus();
		assertEquals(statusCount, result);
	}

	@Test
	public void testGetCustomerOrders() {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		List<Order> orders = new ArrayList<>();
		when(repo.findById(anyInt())).thenReturn(Optional.of(customer));
		when(repo.getCustomerOrders(anyInt())).thenReturn(orders);

		CustomerOrders result = customerService.getCustomerOrders(1);
		assertNotNull(result);
		assertEquals(customer, result.getCustomer());
		assertEquals(orders, result.getOrder());
	}

	@Test
	public void testGetCustomerShipment() throws ResourceNotFoundException {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		List<Shipment> shipments = new ArrayList<>();
		when(repo.findById(anyInt())).thenReturn(Optional.of(customer));
		when(repo.getCustomerShipments(anyInt())).thenReturn(shipments);

		CustomerShipment result = customerService.getCustomerShipment(1);
		assertNotNull(result);
		assertEquals(customer, result.getCustomer());
		assertEquals(shipments, result.getShipment());
	}

	@Test
	public void testGetCustomerShipmentNotFound() {
		when(repo.findById(anyInt())).thenReturn(Optional.empty());

		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			customerService.getCustomerShipment(1);
		});
		assertEquals("Customer not found with ID: 1", thrown.getMessage());
	}

	@Test
	public void testGetCustomerById() throws ResourceNotFoundException {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		when(repo.findById(anyInt())).thenReturn(Optional.of(customer));

		Customer result = customerService.getCustomerById(1);
		assertNotNull(result);
		assertEquals(1, result.getCustomerId());
	}
}
