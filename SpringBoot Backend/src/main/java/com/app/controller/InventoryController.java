package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.InventoryDTO;
import com.app.dto.InventoryStoreProductCustomerDTO;
import com.app.dto.InventoryStoreProductStatusDTO;
import com.app.dto.ProductOrderDetailsDTO;
import com.app.dto.ShipmentStatusCountDTO;
import com.app.exceptions.list.InternalServerErrorException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.services.IinventoryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/inventory")
public class InventoryController {

	@Autowired
	private IinventoryService inventoryService;

	// Get all inventory records
	@GetMapping
	public ResponseEntity<?> getAllInventory() {
		List<InventoryDTO> inventoryList = inventoryService.getAllInventory();
		// If no inventory records found, throw an internal server error
		if (inventoryList.isEmpty()) {
			throw new InternalServerErrorException(
					"An internal server error occurred while fetching all inventory records.");
		}
		// Return the list of inventory records
		return ResponseEntity.ok(inventoryList);
	}

	// Get inventory by store ID, with storeId passed as a request parameter
	@GetMapping(params = "storeid")
	public ResponseEntity<?> getInventoryByStoreId(@RequestParam("storeid") int storeId)
			throws ResourceNotFoundException {
		List<InventoryStoreProductStatusDTO> inventory = inventoryService.getInventoryByStoreId(storeId);
		// If no matching records are found, throw an Unauthorized exception
		if (inventory.isEmpty()) {
			throw new ResourceNotFoundException("Inventory records matching the specified store ID not found.");
		}
		// Return the list of inventory records matching the store ID
		return ResponseEntity.ok(inventory);
	}

	// Get inventory along with associated shipment details
	@GetMapping("/shipment")
	public ResponseEntity<?> getInventoriesAndShipments() {
		List<InventoryDTO> inventoryShipments = inventoryService.getInventoryAndShipments();
		// If no matching records are found, throw an internal server error
		if (inventoryShipments.isEmpty()) {
			throw new InternalServerErrorException(
					"An internal server error occurred while fetching inventories matching shipments.");
		}
		// Return the list of inventory and shipment details
		return ResponseEntity.ok(inventoryShipments);
	}

	// Get inventory details by order ID
	@GetMapping("/{orderid}")
	public ResponseEntity<?> getInventoryByOrderId(@PathVariable("orderid") int orderId)
			throws ResourceNotFoundException {
		List<InventoryStoreProductCustomerDTO> results = inventoryService.getInventoryByOrderId(orderId);
		// If no matching records are found, throw a ResourceNotFoundException
		if (results.isEmpty()) {
			throw new ResourceNotFoundException(
					"Store, product, and customer data for the specified order ID not found.");
		}
		// Return the inventory details for the given order ID
		return ResponseEntity.ok(results);
	}

	// Get count of shipments based on their status
	@GetMapping("/shipment/count")
	public ResponseEntity<?> getShipmentCountByStatus() {
		List<ShipmentStatusCountDTO> shipmentCount = inventoryService.getShipmentCountByStatus();
		// If no shipment data is found, throw an internal server error
		if (shipmentCount.isEmpty()) {
			throw new InternalServerErrorException(
					"An internal server error occurred while fetching shipment status and count of sold products.");
		}
		// Return the count of shipments by status
		return ResponseEntity.ok(shipmentCount);
	}

	// Get product details by order ID, including shipment and store information
	@GetMapping("/{orderid}/details")
	public ResponseEntity<?> getProductDetailsByOrderId(@PathVariable("orderid") int orderId) {
		List<ProductOrderDetailsDTO> productDetails = inventoryService.getProductDetailsByOrderId(orderId);
		// If no product details are found, throw an internal server error
		if (productDetails.isEmpty()) {
			throw new InternalServerErrorException(
					"List of products in the specified order ID not found with store details, shipment status, and total amount.");
		}
		// Return the product details for the specified order ID
		return ResponseEntity.ok(productDetails);
	}

	// Get inventory records by product ID and store ID
	@GetMapping("/product/{productId}/store/{storeId}")
	public ResponseEntity<?> getInventoryByProductAndStore(@PathVariable("productId") int productId,
			@PathVariable("storeId") int storeId) throws ResourceNotFoundException {
		List<InventoryDTO> inventoryList = inventoryService.getInventoryByProductIdAndStoreId(productId, storeId);
		// If no matching inventory is found, throw a ResourceNotFoundException
		if (inventoryList.isEmpty()) {
			throw new ResourceNotFoundException("Inventory records for the specified product and store not found.");
		}
		// Return the inventory records for the specified product and store ID
		return ResponseEntity.ok(inventoryList);
	}

	// Get inventory by product category
	@GetMapping("/category/{category}")
	public ResponseEntity<?> getInventoryByCategory(@PathVariable("category") String category)
			throws ResourceNotFoundException {
		List<InventoryDTO> inventoryDTOs = inventoryService.getInventoryByProductCategory(category);
		// If no matching inventory records are found, throw a ResourceNotFoundException
		if (inventoryDTOs.isEmpty()) {
			throw new ResourceNotFoundException("Inventory records for the specified category not found.");
		}
		// Return the inventory records for the specified category
		return ResponseEntity.ok(inventoryDTOs);
	}
}
