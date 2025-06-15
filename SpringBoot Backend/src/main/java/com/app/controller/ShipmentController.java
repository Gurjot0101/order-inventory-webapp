package com.app.controller; // Specify the package for the controller

import java.util.List; // Import List for handling collections

import org.springframework.beans.factory.annotation.Autowired; // Import for dependency injection
import org.springframework.http.ResponseEntity; // Import for ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin; // Import for CORS configuration
import org.springframework.web.bind.annotation.GetMapping; // Import for handling GET requests
import org.springframework.web.bind.annotation.RequestMapping; // Import for request mapping
import org.springframework.web.bind.annotation.RestController; // Import for REST controller annotation

import com.app.dto.ShipmentDto; // Import the Shipment Data Transfer Object (DTO)
import com.app.services.IShipmentService; // Import the Shipment service interface

// Controller class for handling shipment-related endpoints
@RestController // Annotation to indicate that this class is a REST controller
@RequestMapping("/api/v1/shipments") // Base URL for all endpoints in this controller
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for the specified origin
public class ShipmentController {

    @Autowired // Automatically inject the shipment service implementation
    private IShipmentService shipmentService;

    // Get all shipments
    @GetMapping // Handle GET requests for retrieving all shipments
    public ResponseEntity<List<ShipmentDto>> getAllShipments() {
        List<ShipmentDto> shipments = shipmentService.getAllShipments(); // Fetch all shipments

        // Check if the list of shipments is empty
        if (shipments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no shipments are found
        }

        // Return the list of shipments with a 200 OK status
        return ResponseEntity.ok(shipments);
    }
}
