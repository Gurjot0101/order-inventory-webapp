package com.app.dto;

import lombok.Data;

@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
public class ShipmentStatusCountCustomer {

    private String shipmentStatus; // Represents the status of the shipment
    private Long count; // Represents the count of customers associated with the shipment status

    // Constructor to initialize ShipmentStatusCountCustomer with specific shipmentStatus and count
    public ShipmentStatusCountCustomer(String shipmentStatus, Long count) {
        super();
        this.shipmentStatus = shipmentStatus; // Set the shipment status
        this.count = count; // Set the count of customers
    }

    // No-argument constructor for default instantiation
    public ShipmentStatusCountCustomer() {
        super();
    }
}

