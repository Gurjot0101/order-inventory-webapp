package com.app.dto;

import java.util.List;

import com.app.model.Customer;
import com.app.model.Order;

import lombok.Data;

@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
public class CustomerOrders {
    private Customer customer; // Customer details
    private List<Order> order; // List of orders associated with the customer

    // Constructor with parameters to initialize the CustomerOrders object with a customer and a list of orders
    public CustomerOrders(Customer customer, List<Order> order) {
        super();
        this.customer = customer;
        this.order = order;
    }

    // No-argument constructor required for serialization/deserialization and default instantiation
    public CustomerOrders() {
        super();
    }
}

