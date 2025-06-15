package com.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.app.services.impl.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.app.dao.ProductRepository;
import com.app.dto.ProductDto;
import com.app.exceptions.list.BadRequestException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Product;

public class ProductServiceTest {

	@Mock
	private ProductRepository productDao; // Mocked ProductRepository to simulate database operations.

	@Mock
	private ModelMapper modelMapper; // Mocked ModelMapper for object mapping between Product and ProductDto.

	@InjectMocks
	private ProductService productService; // Service class under test.

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this); // Initialize mocks before each test.
	}

	@Test
	public void testGetAllProducts() {
		// Arrange
		Product product1 = new Product();
		product1.setProductId(1L);
		product1.setName("Product1");

		Product product2 = new Product();
		product2.setProductId(2L);
		product2.setName("Product2");

		List<Product> products = List.of(product1, product2); // Create a list of products.

		ProductDto dto1 = new ProductDto();
		dto1.setId(1);
		dto1.setName("Product1");

		ProductDto dto2 = new ProductDto();
		dto2.setId(2);
		dto2.setName("Product2");

		List<ProductDto> dtos = List.of(dto1, dto2); // Expected list of ProductDto.

		// Mock repository and mapper behavior.
		when(productDao.findAll()).thenReturn(products);
		when(modelMapper.map(product1, ProductDto.class)).thenReturn(dto1);
		when(modelMapper.map(product2, ProductDto.class)).thenReturn(dto2);

		// Act
		List<ProductDto> result = productService.getAllProducts();

		// Assert
		assertNotNull(result); // Ensure the result is not null.
		assertEquals(2, result.size()); // Ensure the size matches.
		assertEquals(dtos, result); // Ensure the returned DTOs match the expected.
	}

	@Test
	public void testCreateProduct() {
		// Arrange
		ProductDto productDto = new ProductDto();
		productDto.setName("NewProduct"); // Create a new product DTO.

		Product product = new Product();
		product.setName("NewProduct"); // Create a new Product entity.

		when(modelMapper.map(productDto, Product.class)).thenReturn(product); // Mock the mapping.

		// Act
		String result = productService.createProduct(productDto);

		// Assert
		verify(productDao).save(product); // Verify that save was called on the repository.
		assertEquals("Product created successfully!", result); // Ensure success message.
	}

	@Test
	public void testUpdateProduct() {
		// Arrange
		ProductDto productDto = new ProductDto();
		productDto.setName("UpdatedProduct"); // New name for update.

		Product existingProduct = new Product();
		existingProduct.setProductId(1L);
		existingProduct.setName("OldProduct"); // Existing product.

		Product updatedProduct = new Product();
		updatedProduct.setProductId(1L);
		updatedProduct.setName("UpdatedProduct"); // Updated product.

		when(productDao.findById(1)).thenReturn(java.util.Optional.of(existingProduct)); // Mock existing product
																							// retrieval.
		when(modelMapper.map(productDto, Product.class)).thenReturn(updatedProduct); // Mock mapping to updated product.

		// Act
		String result = productService.updateProduct(1, productDto);

		// Assert
		verify(productDao).save(updatedProduct); // Verify save was called with the updated product.
		assertEquals("Product updated successfully!", result); // Ensure success message.
	}

	@Test
	public void testDeleteProductNotFound() {
		// Arrange
		when(productDao.existsById(1)).thenReturn(false); // Mock non-existence of product.

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			productService.deleteProduct(1); // Attempt to delete non-existing product.
		});
	}

	@Test
	public void testFindProductsById() throws BadRequestException {
		// Arrange
		Product product = new Product();
		product.setProductId(1L);
		product.setName("Product1"); // Existing product.

		ProductDto dto = new ProductDto();
		dto.setId(1);
		dto.setName("Product1"); // Expected DTO.

		when(productDao.findById(1)).thenReturn(java.util.Optional.of(product)); // Mock product retrieval.
		when(modelMapper.map(product, ProductDto.class)).thenReturn(dto); // Mock mapping to DTO.

		// Act
		ProductDto result = productService.findProductsById(1);

		// Assert
		assertNotNull(result); // Ensure result is not null.
		assertEquals(dto, result); // Ensure returned DTO matches expected.
	}

	@Test
	public void testFindProductsByIdNotFound() {
		// Arrange
		when(productDao.findById(1)).thenReturn(java.util.Optional.empty()); // Mock empty result.

		// Act & Assert
		assertThrows(BadRequestException.class, () -> {
			productService.findProductsById(1); // Attempt to find non-existing product.
		});
	}

	@Test
	public void testFindProductsByName() throws ResourceNotFoundException {
		// Arrange
		Product product = new Product();
		product.setProductId(1L);
		product.setName("Product1"); // Existing product.

		ProductDto dto = new ProductDto();
		dto.setId(1);
		dto.setName("Product1"); // Expected DTO.

		List<Product> products = List.of(product); // List of products.
		List<ProductDto> dtos = List.of(dto); // Expected DTOs.

		when(productDao.findByName("Product1")).thenReturn(products); // Mock product retrieval by name.
		when(modelMapper.map(product, ProductDto.class)).thenReturn(dto); // Mock mapping to DTO.

		// Act
		List<ProductDto> result = productService.findProductsByName("Product1");

		// Assert
		assertNotNull(result); // Ensure result is not null.
		assertEquals(dtos, result); // Ensure returned DTOs match expected.
	}

	@Test
	public void testFindProductsByNameNotFound() {
		// Arrange
		when(productDao.findByName("Product1")).thenReturn(new ArrayList<>()); // Mock empty result.

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			productService.findProductsByName("Product1"); // Attempt to find non-existing product by name.
		});
	}

	@Test
	public void testFindProductsByBrand() throws ResourceNotFoundException {
		// Arrange
		Product product = new Product();
		product.setProductId(1L);
		product.setBrand("Brand1"); // Existing product.

		ProductDto dto = new ProductDto();
		dto.setId(1);
		dto.setBrand("Brand1"); // Expected DTO.

		List<Product> products = List.of(product); // List of products.
		List<ProductDto> dtos = List.of(dto); // Expected DTOs.

		when(productDao.findByBrand("Brand1")).thenReturn(products); // Mock product retrieval by brand.
		when(modelMapper.map(product, ProductDto.class)).thenReturn(dto); // Mock mapping to DTO.

		// Act
		List<ProductDto> result = productService.findProductsByBrand("Brand1");

		// Assert
		assertNotNull(result); // Ensure result is not null.
		assertEquals(dtos, result); // Ensure returned DTOs match expected.
	}

	@Test
	public void testFindProductsByBrandNotFound() {
		// Arrange
		when(productDao.findByBrand("Brand1")).thenReturn(new ArrayList<>()); // Mock empty result.

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			productService.findProductsByBrand("Brand1"); // Attempt to find non-existing product by brand.
		});
	}

	@Test
	public void testFindProductsByColor() throws ResourceNotFoundException {
		// Arrange
		Product product = new Product();
		product.setProductId(1L);
		product.setColor("Red"); // Existing product.

		ProductDto dto = new ProductDto();
		dto.setId(1);
		dto.setColor("Red"); // Expected DTO.

		List<Product> products = List.of(product); // List of products.
		List<ProductDto> dtos = List.of(dto); // Expected DTOs.

		when(productDao.findByColor("Red")).thenReturn(products); // Mock product retrieval by color.
		when(modelMapper.map(product, ProductDto.class)).thenReturn(dto); // Mock mapping to DTO.

		// Act
		List<ProductDto> result = productService.findProductsByColor("Red");

		// Assert
		assertNotNull(result); // Ensure result is not null.
		assertEquals(dtos, result); // Ensure returned DTOs match expected.
	}

	@Test
	public void testFindProductsByColorNotFound() {
		// Arrange
		when(productDao.findByColor("Red")).thenReturn(new ArrayList<>()); // Mock empty result.

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			productService.findProductsByColor("Red"); // Attempt to find non-existing product by color.
		});
	}
}
