package com.app.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.app.services.IinventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class InventoryService implements IinventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Retrieve all inventory records and map them to InventoryDTO objects
	public List<InventoryDTO> getAllInventory() {
		List<Inventory> inventories = inventoryRepository.findAll();
		// Map each Inventory entity to a corresponding DTO
		return inventories.stream().map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
				.collect(Collectors.toList());
	}

	// Fetch inventory details by store ID
	public List<InventoryStoreProductStatusDTO> getInventoryByStoreId(int storeId) {
		List<Object[]> inventoryData = inventoryRepository.findByStoreId(storeId);
		// Map the raw query results to DTOs
		return inventoryData.stream().map(data -> {
			InventoryStoreProductStatusDTO dto = new InventoryStoreProductStatusDTO();
			dto.setProduct((Product) data[1]);
			dto.setStore((Store) data[2]);
			dto.setOrderStatus((String) data[3]);
			return dto;
		}).collect(Collectors.toList());
	}

	// Retrieve inventory records that match associated shipments
	public List<InventoryDTO> getInventoryAndShipments() {
		List<Inventory> inventories = inventoryRepository.findInventoryMatchingShipments();
		// Map each Inventory entity to InventoryDTO for the response
		return inventories.stream().map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
				.collect(Collectors.toList());
	}

	// Get inventory, store, product, and customer details by order ID
	public List<InventoryStoreProductCustomerDTO> getInventoryByOrderId(int orderId) {
		List<Object[]> results = inventoryRepository.findByOrderId(orderId);
		List<InventoryStoreProductCustomerDTO> inventoryDTOs = new ArrayList<>();

		// Map the result objects to InventoryStoreProductCustomerDTO
		for (Object[] result : results) {
			Store store = (Store) result[0];
			Product product = (Product) result[1];
			Customer customer = (Customer) result[2];

			InventoryStoreProductCustomerDTO dto = new InventoryStoreProductCustomerDTO();
			dto.setStore(store);
			dto.setProduct(product);
			dto.setCustomer(customer);

			inventoryDTOs.add(dto);
		}

		return inventoryDTOs;
	}

	// Get the count of shipments by their status
	public List<ShipmentStatusCountDTO> getShipmentCountByStatus() {
		List<Object[]> shipmentData = inventoryRepository.findShipmentCountByStatus();
		// Convert the raw data into ShipmentStatusCountDTO objects
		return shipmentData.stream().map(data -> {
			ShipmentStatusCountDTO dto = new ShipmentStatusCountDTO();
			dto.setShipmentStatus((String) data[0]);
			dto.setTotalProductsSold((Long) data[1]);
			return dto;
		}).collect(Collectors.toList());
	}

	// Fetch product details by order ID, including shipment and total amount
	public List<ProductOrderDetailsDTO> getProductDetailsByOrderId(int orderId) {
		List<Object[]> productDetails = inventoryRepository.findProductDetailsByOrderId(orderId);
		// Map each result to ProductOrderDetailsDTO
		return productDetails.stream().map(data -> {
			ProductOrderDetailsDTO dto = new ProductOrderDetailsDTO();
			dto.setProduct((Product) data[0]);
			dto.setStore((Store) data[1]);
			dto.setShipmentStatus((String) data[2]);
			dto.setTotalAmount((BigDecimal) data[3]);
			return dto;
		}).collect(Collectors.toList());
	}

	// Get inventory by product ID and store ID
	public List<InventoryDTO> getInventoryByProductIdAndStoreId(int productId, int storeId) {
		List<Inventory> inventories = inventoryRepository.findByProductIdAndStoreId(productId, storeId);
		// Map the Inventory entities to DTOs
		return inventories.stream().map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
				.collect(Collectors.toList());
	}
	
	// Get inventory by product category
    public List<InventoryDTO> getInventoryByProductCategory(String category) {
        List<Inventory> inventories = inventoryRepository.findInventoryByProductCategory(category);
        // Convert each Inventory entity to InventoryDTO
        return inventories.stream()
                .map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
                .collect(Collectors.toList());
    }
}
