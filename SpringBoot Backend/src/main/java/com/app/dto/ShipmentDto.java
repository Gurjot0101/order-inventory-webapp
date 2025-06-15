package com.app.dto;

import lombok.Data;

@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
public class ShipmentDto {

    private int shipmentId; // Unique identifier for the shipment
    private String deliveryAddress; // Address where the shipment is to be delivered
    private String shipmentStatus; // Current status of the shipment (e.g., "Shipped", "Delivered", etc.)
    private int storeId; // Identifier for the store associated with the shipment
    private int customerId; // Identifier for the customer who placed the shipment

    // Constructors, getters, setters (Lombok @Data will generate these automatically)
}

