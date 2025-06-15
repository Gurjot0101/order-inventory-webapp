package com.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.app.services.impl.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.app.dao.InventoryRepository;
import com.app.dto.InventoryDTO;
import com.app.dto.InventoryStoreProductCustomerDTO;
import com.app.dto.InventoryStoreProductStatusDTO;
import com.app.dto.ProductOrderDetailsDTO;
import com.app.dto.ShipmentStatusCountDTO;
import com.app.model.Customer;
import com.app.model.Inventory;
import com.app.model.Product;
import com.app.model.Store;

// Unit tests for InventoryService class
public class InventoryServiceTest {

	@Mock
	private InventoryRepository inventoryRepository; // Mocked InventoryRepository for database interactions

	@Mock
	private ModelMapper modelMapper; // Mocked ModelMapper for DTO conversions

	@InjectMocks
	private InventoryService inventoryService; // Service being tested

	// Setup method to initialize mocks before each test
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Test method for retrieving all inventory items
	@Test
	public void testGetAllInventory() {
		List<Inventory> inventories = new ArrayList<>();
		Inventory inventory = new Inventory();
		inventories.add(inventory); // Add a sample inventory item

		InventoryDTO inventoryDTO = new InventoryDTO();
		// Define the behavior of the mocked repository and model mapper
		when(inventoryRepository.findAll()).thenReturn(inventories);
		when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(inventoryDTO);

		// Call the method under test
		List<InventoryDTO> result = inventoryService.getAllInventory();
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(inventoryDTO, result.get(0));
	}

	// Test method for retrieving inventory by store ID
	@Test
	public void testGetInventoryByStoreId() {
		Store store = new Store();
		Product product = new Product();
		Object[] data = { null, product, store, "InStock" }; // Sample data for inventory
		List<Object[]> inventoryData = new ArrayList<>();
		inventoryData.add(data);

		InventoryStoreProductStatusDTO dto = new InventoryStoreProductStatusDTO();
		dto.setProduct(product);
		dto.setStore(store);
		dto.setOrderStatus("InStock"); // Expected DTO for assertions

		// Define the behavior of the mocked repository
		when(inventoryRepository.findByStoreId(anyInt())).thenReturn(inventoryData);

		// Call the method under test
		List<InventoryStoreProductStatusDTO> result = inventoryService.getInventoryByStoreId(1);
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(dto, result.get(0));
	}

	// Test method for retrieving inventory and shipments
	@Test
	public void testGetInventoryAndShipments() {
		List<Inventory> inventories = new ArrayList<>();
		Inventory inventory = new Inventory();
		inventories.add(inventory); // Sample inventory item

		InventoryDTO inventoryDTO = new InventoryDTO();
		// Define the behavior of the mocked repository and model mapper
		when(inventoryRepository.findInventoryMatchingShipments()).thenReturn(inventories);
		when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(inventoryDTO);

		// Call the method under test
		List<InventoryDTO> result = inventoryService.getInventoryAndShipments();
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(inventoryDTO, result.get(0));
	}

	// Test method for retrieving inventory by order ID
	@Test
	public void testGetInventoryByOrderId() {
		Store store = new Store();
		Product product = new Product();
		Customer customer = new Customer();
		Object[] data = { store, product, customer }; // Sample data for inventory
		List<Object[]> results = new ArrayList<>();
		results.add(data);

		InventoryStoreProductCustomerDTO dto = new InventoryStoreProductCustomerDTO();
		dto.setStore(store);
		dto.setProduct(product);
		dto.setCustomer(customer); // Expected DTO for assertions

		// Define the behavior of the mocked repository
		when(inventoryRepository.findByOrderId(anyInt())).thenReturn(results);

		// Call the method under test
		List<InventoryStoreProductCustomerDTO> result = inventoryService.getInventoryByOrderId(1);
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(dto.getStore(), result.get(0).getStore());
		assertEquals(dto.getProduct(), result.get(0).getProduct());
		assertEquals(dto.getCustomer(), result.get(0).getCustomer());
	}

	// Test method for retrieving shipment count by status
	@Test
	public void testGetShipmentCountByStatus() {
		List<Object[]> shipmentData = new ArrayList<>();
		Object[] data = { "Shipped", 10L }; // Sample shipment data
		shipmentData.add(data);

		ShipmentStatusCountDTO dto = new ShipmentStatusCountDTO();
		dto.setShipmentStatus("Shipped");
		dto.setTotalProductsSold(10L); // Expected DTO for assertions

		// Define the behavior of the mocked repository
		when(inventoryRepository.findShipmentCountByStatus()).thenReturn(shipmentData);

		// Call the method under test
		List<ShipmentStatusCountDTO> result = inventoryService.getShipmentCountByStatus();
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(dto.getShipmentStatus(), result.get(0).getShipmentStatus());
		assertEquals(dto.getTotalProductsSold(), result.get(0).getTotalProductsSold());
	}

	// Test method for retrieving product details by order ID
	@Test
	public void testGetProductDetailsByOrderId() {
		Product product = new Product();
		Store store = new Store();
		Object[] data = { product, store, "Shipped", BigDecimal.valueOf(100) }; // Sample product order details
		List<Object[]> productDetails = new ArrayList<>();
		productDetails.add(data);

		ProductOrderDetailsDTO dto = new ProductOrderDetailsDTO();
		dto.setProduct(product);
		dto.setStore(store);
		dto.setShipmentStatus("Shipped");
		dto.setTotalAmount(BigDecimal.valueOf(100)); // Expected DTO for assertions

		// Define the behavior of the mocked repository
		when(inventoryRepository.findProductDetailsByOrderId(anyInt())).thenReturn(productDetails);

		// Call the method under test
		List<ProductOrderDetailsDTO> result = inventoryService.getProductDetailsByOrderId(1);
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(dto.getProduct(), result.get(0).getProduct());
		assertEquals(dto.getStore(), result.get(0).getStore());
		assertEquals(dto.getShipmentStatus(), result.get(0).getShipmentStatus());
		assertEquals(dto.getTotalAmount(), result.get(0).getTotalAmount());
	}

	// Test method for retrieving inventory by product ID and store ID
	@Test
	public void testGetInventoryByProductIdAndStoreId() {
		List<Inventory> inventories = new ArrayList<>();
		Inventory inventory = new Inventory();
		inventories.add(inventory); // Sample inventory item

		InventoryDTO inventoryDTO = new InventoryDTO();
		// Define the behavior of the mocked repository and model mapper
		when(inventoryRepository.findByProductIdAndStoreId(anyInt(), anyInt())).thenReturn(inventories);
		when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(inventoryDTO);

		// Call the method under test
		List<InventoryDTO> result = inventoryService.getInventoryByProductIdAndStoreId(1, 1);
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(inventoryDTO, result.get(0));
	}

	// Test method for retrieving inventory by product category
	@Test
	public void testGetInventoryByProductCategory() {
		List<Inventory> inventories = new ArrayList<>();
		Inventory inventory = new Inventory();
		inventories.add(inventory); // Sample inventory item

		InventoryDTO inventoryDTO = new InventoryDTO();
		// Define the behavior of the mocked repository and model mapper
		when(inventoryRepository.findInventoryByProductCategory(anyString())).thenReturn(inventories);
		when(modelMapper.map(inventory, InventoryDTO.class)).thenReturn(inventoryDTO);

		// Call the method under test
		List<InventoryDTO> result = inventoryService.getInventoryByProductCategory("Electronics");
		// Assertions to verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(inventoryDTO, result.get(0));
	}
}
