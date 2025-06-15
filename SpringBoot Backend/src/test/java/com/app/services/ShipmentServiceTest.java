package com.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.app.services.impl.ShipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.dao.ShipmentRepository;
import com.app.dto.ShipmentDto;
import com.app.model.Customer;
import com.app.model.Shipment;
import com.app.model.Store;

public class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository; // Mocked ShipmentRepository for simulating database operations.

    @InjectMocks
    private ShipmentService shipmentService; // ShipmentService instance to be tested.

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test.
    }

    @Test
    public void testGetAllShipments() {
        // Prepare test data
        Shipment shipment1 = new Shipment();
        shipment1.setShipmentId(1); // Set ID for shipment 1
        shipment1.setDeliveryAddress("123 Main St"); // Set delivery address for shipment 1
        shipment1.setShipmentStatus("Shipped"); // Set status for shipment 1
        shipment1.setStore(new Store()); // Associate a Store with shipment 1
        shipment1.setCustomer(new Customer()); // Associate a Customer with shipment 1

        Shipment shipment2 = new Shipment();
        shipment2.setShipmentId(2); // Set ID for shipment 2
        shipment2.setDeliveryAddress("456 Elm St"); // Set delivery address for shipment 2
        shipment2.setShipmentStatus("Delivered"); // Set status for shipment 2
        shipment2.setStore(new Store()); // Associate a Store with shipment 2
        shipment2.setCustomer(new Customer()); // Associate a Customer with shipment 2

        List<Shipment> shipments = new ArrayList<>(); // List to hold Shipment entities
        shipments.add(shipment1); // Add shipment 1 to the list
        shipments.add(shipment2); // Add shipment 2 to the list

        // Mock the repository method to return the prepared list of shipments
        when(shipmentRepository.findAll()).thenReturn(shipments);

        // Expected DTOs to match the Shipments
        ShipmentDto dto1 = new ShipmentDto();
        dto1.setShipmentId(1); // Set expected ID for DTO 1
        dto1.setDeliveryAddress("123 Main St"); // Set expected delivery address for DTO 1
        dto1.setShipmentStatus("Shipped"); // Set expected status for DTO 1
        // Get associated Store and Customer IDs for DTO 1
        dto1.setStoreId(shipment1.getStore() != null ? shipment1.getStore().getStoreId() : 0);
        dto1.setCustomerId(shipment1.getCustomer() != null ? shipment1.getCustomer().getCustomerId() : 0);

        ShipmentDto dto2 = new ShipmentDto();
        dto2.setShipmentId(2); // Set expected ID for DTO 2
        dto2.setDeliveryAddress("456 Elm St"); // Set expected delivery address for DTO 2
        dto2.setShipmentStatus("Delivered"); // Set expected status for DTO 2
        // Get associated Store and Customer IDs for DTO 2
        dto2.setStoreId(shipment2.getStore() != null ? shipment2.getStore().getStoreId() : 0);
        dto2.setCustomerId(shipment2.getCustomer() != null ? shipment2.getCustomer().getCustomerId() : 0);

        List<ShipmentDto> expectedDtos = new ArrayList<>(); // List to hold expected ShipmentDto objects
        expectedDtos.add(dto1); // Add expected DTO 1 to the list
        expectedDtos.add(dto2); // Add expected DTO 2 to the list

        // Call the method under test
        List<ShipmentDto> result = shipmentService.getAllShipments();

        // Assert results
        assertNotNull(result); // Ensure the result is not null
        assertEquals(expectedDtos.size(), result.size()); // Ensure the size matches expected

        // Verify content of the result matches expected DTOs
        for (int i = 0; i < expectedDtos.size(); i++) {
            assertEquals(expectedDtos.get(i).getShipmentId(), result.get(i).getShipmentId()); // Check IDs
            assertEquals(expectedDtos.get(i).getDeliveryAddress(), result.get(i).getDeliveryAddress()); // Check addresses
            assertEquals(expectedDtos.get(i).getShipmentStatus(), result.get(i).getShipmentStatus()); // Check statuses
            assertEquals(expectedDtos.get(i).getStoreId(), result.get(i).getStoreId()); // Check Store IDs
            assertEquals(expectedDtos.get(i).getCustomerId(), result.get(i).getCustomerId()); // Check Customer IDs
        }
    }
}
