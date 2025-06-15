package com.app.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.app.services.IproductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ProductRepository;
import com.app.dto.ProductDto;
import com.app.exceptions.list.BadRequestException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Product;

// Service class for handling business logic related to products
@Service
public class ProductService implements IproductService {

	@Autowired
	private ProductRepository productDao; // DAO for interacting with the product database

	@Autowired
	private ModelMapper modelMapper; // Mapper for converting between Product and ProductDto

	// Retrieve all products from the database
	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> products = productDao.findAll(); // Fetch all products from the repository
		List<ProductDto> productDtos = new ArrayList<>(); // List to hold the mapped DTOs

		// Map each Product to ProductDto
		for (Product product : products) {
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			productDtos.add(productDto);
		}

		return productDtos; // Return the list of ProductDtos
	}

	// Create a new product in the database
	public String createProduct(ProductDto productDto) {
		Product product = new Product(); // Create a new Product instance

		// Set the product fields from the DTO
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setColor(productDto.getColor());
		product.setBrand(productDto.getBrand());
		product.setSize(productDto.getSize());
		product.setRating(productDto.getRating());
		product.setCategory(productDto.getCategory());
		product.setImage(productDto.getImage()); // Set the Base64 image

		productDao.save(product); // Save the product to the database

		return "Product created successfully!"; // Return success message
	}

	// Update an existing product by its ID
	public String updateProduct(int id, ProductDto productDto) {
		// Find the product by ID or throw an exception if not found
		Product product = productDao.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		// Update the product fields from the DTO
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setColor(productDto.getColor());
		product.setBrand(productDto.getBrand());
		product.setSize(productDto.getSize());
		product.setRating(productDto.getRating());
		product.setCategory(productDto.getCategory());
		product.setImage(productDto.getImage()); // Update the Base64 image

		productDao.save(product); // Save the updated product

		return "Product updated successfully!"; // Return success message
	}

	// Delete a product by its ID
	@Override
	public String deleteProduct(int productId) throws ResourceNotFoundException {
		// Check if the product exists; if not, throw an exception
		if (!productDao.existsById(productId)) {
			throw new ResourceNotFoundException("Product with the specified ID not found for deletion.");
		}

		productDao.deleteById(productId); // Delete the product from the database

		return "Product deleted successfully with ID: " + productId; // Return success message
	}

	// Find a product by its ID
	@Override
	public ProductDto findProductsById(int productId) throws BadRequestException {
		// Find the product by ID or throw an exception if not found
		Product product = productDao.findById(productId)
				.orElseThrow(() -> new BadRequestException("Invalid request. Please provide a valid Id."));

		return modelMapper.map(product, ProductDto.class); // Return the mapped ProductDto
	}

	// Find products by their name
	@Override
	public List<ProductDto> findProductsByName(String name) throws ResourceNotFoundException {
		List<Product> products = productDao.findByName(name); // Find products by name

		// Check if products were found; if not, throw an exception
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Product(s) with the specified name not found.");
		}

		List<ProductDto> productDtos = new ArrayList<>(); // List to hold the mapped DTOs

		// Map each Product to ProductDto
		for (Product product : products) {
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			productDtos.add(productDto);
		}

		return productDtos; // Return the list of ProductDtos
	}

	// Find products by their brand
	@Override
	public List<ProductDto> findProductsByBrand(String brand) throws ResourceNotFoundException {
		List<Product> products = productDao.findByBrand(brand); // Find products by brand

		// Check if products were found; if not, throw an exception
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products with the specified brand not found.");
		}

		List<ProductDto> productDtos = new ArrayList<>(); // List to hold the mapped DTOs

		// Map each Product to ProductDto
		for (Product product : products) {
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			productDtos.add(productDto);
		}

		return productDtos; // Return the list of ProductDtos
	}

	// Find products by their color
	@Override
	public List<ProductDto> findProductsByColor(String color) throws ResourceNotFoundException {
		List<Product> products = productDao.findByColor(color); // Find products by color

		// Check if products were found; if not, throw an exception
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products with the specified color not found.");
		}

		List<ProductDto> productDtos = new ArrayList<>(); // List to hold the mapped DTOs

		// Map each Product to ProductDto
		for (Product product : products) {
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			productDtos.add(productDto);
		}

		return productDtos; // Return the list of ProductDtos
	}

	// Find products within a specified price range
	@Override
	public List<ProductDto> findProductsByPriceRange(double min, double max) throws BadRequestException {
		// Validate the price range
		if (min < 0 || max < 0 || min > max) {
			throw new BadRequestException("Invalid request. Please provide valid minimum and maximum unit prices.");
		}

		List<Product> products = productDao.findByPriceBetween(min, max); // Find products within the price range
		List<ProductDto> productDtos = new ArrayList<>(); // List to hold the mapped DTOs

		// Map each Product to ProductDto
		for (Product product : products) {
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			productDtos.add(productDto);
		}

		return productDtos; // Return the list of ProductDtos
	}

	// Sort products based on a specified field
	@Override
	public List<ProductDto> sortProductsByField(String field) throws BadRequestException {
		List<Product> products = productDao.findAll(); // Fetch all products from the repository

		// Check if products are empty; if so, throw an exception
		if (products.isEmpty()) {
			throw new BadRequestException("Invalid request. Please provide a valid field for sorting.");
		}

		// Sort the products based on the specified field
		switch (field.toLowerCase()) {
		case "price":
			products.sort(Comparator.comparing(Product::getPrice, Comparator.nullsLast(Comparator.naturalOrder())));
			break;

		case "name":
			products.sort(Comparator.comparing(Product::getName));
			break;

		case "brand":
			products.sort(Comparator.comparing(Product::getBrand));
			break;

		case "color":
			products.sort(Comparator.comparing(Product::getColor));
			break;

		default:
			throw new BadRequestException("Invalid request. Please provide a valid field for sorting.");
		}

		// Map sorted products to ProductDto and return the list
		return products.stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
	}

	// Find products by their category
	@Override
	public List<ProductDto> findProductsByCategory(String category) throws ResourceNotFoundException {
		List<Product> products = productDao.findByCategory(category); // Find products by category

		// Check if products were found; if not, throw an exception
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products with the specified category not found.");
		}

		List<ProductDto> productDtos = new ArrayList<>(); // List to hold the mapped DTOs

		// Map each Product to ProductDto
		for (Product product : products) {
			ProductDto productDto = modelMapper.map(product, ProductDto.class);
			productDtos.add(productDto);
		}

		return productDtos; // Return the list of ProductDtos
	}

}
