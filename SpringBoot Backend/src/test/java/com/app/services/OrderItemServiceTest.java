package com.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.app.services.impl.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.dao.OrderItemRepository;
import com.app.model.OrderItem;

public class OrderItemServiceTest {

    // Mock the repository to simulate database operations
    @Mock
    private OrderItemRepository repo;

    // Inject the mocked repository into the service
    @InjectMocks
    private OrderItemService orderItemService;

    // Setup method to initialize mocks before each test case
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Open the mocks before each test
    }

    /*
     * Test for the getAllOrderItems method.
     * This test ensures that the service returns all order items from the repository.
     */
    @Test
    public void testGetAllOrderItem() {
        // Arrange: Set up mock data to return from the repository
        OrderItem item1 = new OrderItem(); // Initialize with necessary fields
        OrderItem item2 = new OrderItem(); // Initialize with necessary fields
        List<OrderItem> mockOrderItems = Arrays.asList(item1, item2);
        
        // Mock the behavior of repo.findAll() to return the mock data
        when(repo.findAll()).thenReturn(mockOrderItems);

        // Act: Call the method being tested
        List<OrderItem> result = orderItemService.getAllOrderItems();

        // Assert: Verify that the result matches the expected data
        assertEquals(2, result.size()); // Ensure the size is correct
        assertEquals(mockOrderItems, result); // Ensure the list returned is correct
    }
}
