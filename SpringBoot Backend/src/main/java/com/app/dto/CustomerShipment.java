package com.app.dto;

import java.util.List;

import com.app.model.Customer;
import com.app.model.Shipment;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required (final) fields
public class CustomerShipment {
    private final Customer customer; // Represents the customer details
    private final List<Shipment> shipment; // Represents the list of shipments associated with the customer
}

