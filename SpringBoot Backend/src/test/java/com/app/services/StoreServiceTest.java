package com.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.services.impl.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.dao.StoreRepository;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Store;

public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository; // Mock the StoreRepository for simulating database operations.

    @InjectMocks
    private StoreService storeService; // StoreService instance to be tested.

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test.
    }

    @Test
    public void testGetAllStores() {
        // Arrange: Set up test data for stores
        List<Store> stores = new ArrayList<>(); // List to hold Store entities
        Store store1 = new Store(); // Create first store
        store1.setStoreId(1); // Set ID for store 1
        store1.setStoreName("Store 1"); // Set name for store 1

        Store store2 = new Store(); // Create second store
        store2.setStoreId(2); // Set ID for store 2
        store2.setStoreName("Store 2"); // Set name for store 2

        stores.add(store1); // Add store 1 to the list
        stores.add(store2); // Add store 2 to the list

        // Mock the repository method to return the prepared list of stores
        when(storeRepository.findAll()).thenReturn(stores);

        // Act: Call the method under test
        List<Store> result = storeService.getAllStores();

        // Assert: Validate the results
        assertNotNull(result); // Ensure the result is not null
        assertEquals(2, result.size()); // Check the number of stores returned
        assertEquals("Store 1", result.get(0).getStoreName()); // Verify the name of the first store
        assertEquals("Store 2", result.get(1).getStoreName()); // Verify the name of the second store
    }

    @Test
    public void testGetStoreByIdFound() throws ResourceNotFoundException {
        // Arrange: Set up test data for a single store
        Store store = new Store(); // Create a store
        store.setStoreId(1); // Set ID for the store
        store.setStoreName("Store 1"); // Set name for the store

        // Mock the repository method to return the store when queried by ID
        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        // Act: Call the method under test
        Store result = storeService.getStoreById(1);

        // Assert: Validate the results
        assertNotNull(result); // Ensure the result is not null
        assertEquals(1, result.getStoreId()); // Check the ID of the returned store
        assertEquals("Store 1", result.getStoreName()); // Check the name of the returned store
    }

    @Test
    public void testGetStoreByIdNotFound() {
        // Arrange: Set up the repository to return an empty Optional when the store is not found
        when(storeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Verify that a RuntimeException is thrown when a store is not found
        RuntimeException exception = assertThrows(RuntimeException.class, () -> storeService.getStoreById(1));
        assertEquals("Store not found", exception.getMessage()); // Check the exception message
    }
}
