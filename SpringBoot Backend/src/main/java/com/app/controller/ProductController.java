package com.app.controller; // Specify the package for the controller

import java.util.List; // Import List for handling collections

import org.springframework.beans.factory.annotation.Autowired; // Import for dependency injection
import org.springframework.http.HttpStatus; // Import for HTTP status codes
import org.springframework.http.ResponseEntity; // Import for ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin; // Import for CORS configuration
import org.springframework.web.bind.annotation.DeleteMapping; // Import for DELETE mapping
import org.springframework.web.bind.annotation.GetMapping; // Import for GET mapping
import org.springframework.web.bind.annotation.PathVariable; // Import for path variable handling
import org.springframework.web.bind.annotation.PostMapping; // Import for POST mapping
import org.springframework.web.bind.annotation.PutMapping; // Import for PUT mapping
import org.springframework.web.bind.annotation.RequestBody; // Import for request body handling
import org.springframework.web.bind.annotation.RequestMapping; // Import for request mapping
import org.springframework.web.bind.annotation.RequestParam; // Import for request parameter handling
import org.springframework.web.bind.annotation.RestController; // Import for REST controller annotation

import com.app.dto.ProductDto; // Import ProductDto for data transfer
import com.app.exceptions.list.BadRequestException; // Import custom exception for bad requests
import com.app.exceptions.list.InternalServerErrorException; // Import custom exception for server errors
import com.app.exceptions.list.ResourceNotFoundException; // Import custom exception for not found resources
import com.app.services.IproductService; // Import the product service interface

// Controller class for handling product-related endpoints
@RestController // Annotation to indicate that this class is a REST controller
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for the specified origin
@RequestMapping("/api/v1/products") // Base URL for all endpoints in this controller
public class ProductController {

	@Autowired // Automatically inject the product service implementation
	private IproductService productService;

	// Get all products
	@GetMapping // Handle GET requests for retrieving all products
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> products = productService.getAllProducts(); // Fetch all products

		// Check if the product list is empty and throw an error if so
//		if (products.isEmpty()) {
//			throw new InternalServerErrorException("An internal server error occurred while fetching all products.");
//		}

		// Return the list of products with a 200 OK status
		return ResponseEntity.ok(products);
	}

	// Create a new product
	@PostMapping(consumes = "application/json") // Handle POST requests for creating a new product
	public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) throws BadRequestException {
		// Validate the product name in the request
		if (productDto.getName() == null || productDto.getName().isEmpty()) {
			throw new BadRequestException("Invalid request. Please provide valid product data for creation.");
		}

		// Create the product and get a success message
		String message = productService.createProduct(productDto);

		// Return a success message with a 201 Created status
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	// Update an existing product
	@PutMapping("/{id}") // Handle PUT requests for updating a product by ID
	public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto)
			throws ResourceNotFoundException {
		// Update the product and get a success message
		String message = productService.updateProduct(id, productDto);

		// Return the success message with a 200 OK status
		return ResponseEntity.ok(message);
	}

	// Get products by category
	@GetMapping("/category/{category}") // Handle GET requests for products by category
	public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("category") String category)
			throws ResourceNotFoundException {
		List<ProductDto> products = productService.findProductsByCategory(category); // Fetch products by category

		// Check if the product list is empty and throw an error if so
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products with the specified category not found.");
		}

		// Return the list of products with a 200 OK status
		return ResponseEntity.ok(products);
	}

	// Delete a product
	@DeleteMapping("/{id}") // Handle DELETE requests for a product by ID
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) throws ResourceNotFoundException {
		String message = productService.deleteProduct(id); // Delete the product and get a success message

		// Return the success message with a 200 OK status
		return ResponseEntity.ok(message);
	}

	// Get products by name
	@GetMapping("/name/{name}") // Handle GET requests for products by name
	public ResponseEntity<List<ProductDto>> searchProductsByName(@PathVariable("name") String name)
			throws ResourceNotFoundException {
		List<ProductDto> products = productService.findProductsByName(name); // Fetch products by name

		// Check if the product list is empty and throw an error if so
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Product(s) with the specified name not found.");
		}

		// Return the list of products with a 200 OK status
		return ResponseEntity.ok(products);
	}

	// Get products by brand
	@GetMapping("/brand/{brand}") // Handle GET requests for products by brand
	public ResponseEntity<List<ProductDto>> getProductsByBrand(@PathVariable("brand") String brand)
			throws ResourceNotFoundException {
		List<ProductDto> products = productService.findProductsByBrand(brand); // Fetch products by brand

		// Check if the product list is empty and throw an error if so
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products with the specified brand not found.");
		}

		// Return the list of products with a 200 OK status
		return ResponseEntity.ok(products);
	}

	// Get products by color
	@GetMapping("/color/{color}") // Handle GET requests for products by color
	public ResponseEntity<List<ProductDto>> getProductsByColor(@PathVariable("color") String color)
			throws ResourceNotFoundException {
		List<ProductDto> products = productService.findProductsByColor(color); // Fetch products by color

		// Check if the product list is empty and throw an error if so
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products with the specified color not found.");
		}

		// Return the list of products with a 200 OK status
		return ResponseEntity.ok(products);
	}

	// Get products by price range
	@GetMapping("/price") // Handle GET requests for products by price range
	public ResponseEntity<List<ProductDto>> getProductsByPriceRange(@RequestParam("min") double min,
			@RequestParam("max") double max) throws BadRequestException {
		List<ProductDto> products = productService.findProductsByPriceRange(min, max); // Fetch products by price range

		// Check if the product list is empty and throw an error if so
		if (products.isEmpty()) {
			throw new BadRequestException("Invalid request. Please provide valid minimum and maximum unit prices.");
		}

		// Return the list of products with a 200 OK status
		return ResponseEntity.ok(products);
	}

	// Find a product by ID
	@GetMapping("/{id}") // Handle GET requests for a product by ID
	public ResponseEntity<ProductDto> getProductsById(@PathVariable int id) throws BadRequestException {
		ProductDto products = productService.findProductsById(id); // Fetch product by ID

		// Return the product with a 200 OK status
		return ResponseEntity.ok(products);
	}

	// Sort products
	@GetMapping("/sort") // Handle GET requests for sorting products
	public ResponseEntity<List<ProductDto>> sortProducts(@RequestParam("field") String field)
			throws BadRequestException {
		List<ProductDto> products = productService.sortProductsByField(field); // Fetch sorted products

		// Check if the product list is empty and throw an error if so
		if (products.isEmpty()) {
			throw new BadRequestException("Invalid request. Please provide a valid field for sorting.");
		}

		// Return the list of sorted products with a 200 OK status
		return ResponseEntity.ok(products);
	}
}
