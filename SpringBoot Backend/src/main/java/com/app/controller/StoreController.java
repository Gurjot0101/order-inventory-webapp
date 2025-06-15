package com.app.controller; // Specify the package for the controller

import java.util.List; // Import List for handling collections

import org.springframework.beans.factory.annotation.Autowired; // Import for dependency injection
import org.springframework.http.ResponseEntity; // Import for ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin; // Import for CORS configuration
import org.springframework.web.bind.annotation.GetMapping; // Import for GET mapping
import org.springframework.web.bind.annotation.PathVariable; // Import for path variable handling
import org.springframework.web.bind.annotation.RequestMapping; // Import for request mapping
import org.springframework.web.bind.annotation.RestController; // Import for REST controller annotation

import com.app.exceptions.list.ResourceNotFoundException; // Import custom exception for not found resources
import com.app.model.Store; // Import the Store model
import com.app.services.impl.StoreService; // Import the Store service interface

// Controller class for handling store-related endpoints
@RestController // Annotation to indicate that this class is a REST controller
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for the specified origin
@RequestMapping("/api/v1/stores") // Base URL for all endpoints in this controller
public class StoreController {

	@Autowired // Automatically inject the store service implementation
	private StoreService storeService;

	// Get all stores
	@GetMapping() // Handle GET requests for retrieving all stores
	public List<Store> getAllStores() {
		List<Store> stores = storeService.getAllStores(); // Fetch all stores
		// Log each store to the console for debugging purposes
		for (Store s : stores) {
			System.out.println(s);
		}
		// Return the list of stores
		return stores;
	}

	// Get a store by ID
	@GetMapping("/{id}") // Handle GET requests for a store by ID
	public ResponseEntity<Store> getStoreById(@PathVariable Integer id) throws ResourceNotFoundException {
		Store store = storeService.getStoreById(id); // Fetch the store by ID
		// Return the store with a 200 OK status
		return ResponseEntity.ok(store);
	}
}
