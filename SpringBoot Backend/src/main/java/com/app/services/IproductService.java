package com.app.services;

import java.util.List;

import com.app.dto.ProductDto;
import com.app.exceptions.list.BadRequestException;
import com.app.exceptions.list.ResourceNotFoundException;

// Interface defining the contract for Product service operations
public interface IproductService {
	
	// Method to retrieve all products
	List<ProductDto> getAllProducts();

	// Method to create a new product
	String createProduct(ProductDto productDTO) throws BadRequestException; // Throws BadRequestException for invalid requests

	// Method to update an existing product by its ID
	String updateProduct(int productId, ProductDto productDto) throws ResourceNotFoundException; // Throws ResourceNotFoundException if product not found

	// Method to delete a product by its ID
	String deleteProduct(int id) throws ResourceNotFoundException; // Throws ResourceNotFoundException if product not found

	// Method to find products by their name
	List<ProductDto> findProductsByName(String name) throws ResourceNotFoundException; // Throws ResourceNotFoundException if no products found

	// Method to find products by their brand
	List<ProductDto> findProductsByBrand(String brand) throws ResourceNotFoundException; // Throws ResourceNotFoundException if no products found

	// Method to find products by their color
	List<ProductDto> findProductsByColor(String color) throws ResourceNotFoundException; // Throws ResourceNotFoundException if no products found

	// Method to find products within a specified price range
	List<ProductDto> findProductsByPriceRange(double min, double max) throws BadRequestException; // Throws BadRequestException for invalid price range

	// Method to sort products based on a specified field
	List<ProductDto> sortProductsByField(String field) throws BadRequestException; // Throws BadRequestException for invalid sorting field

	// Method to find products by their category
	List<ProductDto> findProductsByCategory(String category) throws ResourceNotFoundException; // Throws ResourceNotFoundException if no products found

	// Method to find a product by its ID
	ProductDto findProductsById(int id) throws BadRequestException; // Throws BadRequestException for invalid ID
}
