package com.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Marks this class as a JPA entity, which means it will be mapped to a database table
@Table(name = "shipments") // Specifies the name of the database table to which this entity is mapped
@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
public class Shipment {

    @Id // Specifies the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates unique values for the primary key
    @Column(name = "shipment_id") 
    private int shipmentId; // Unique identifier for the shipment

    @Column(name = "delivery_address", nullable = false, length = 512) 
    private String deliveryAddress; // Address where the shipment is to be delivered

    @Column(name = "shipment_status", nullable = false, length = 100) 
    private String shipmentStatus; // Current status of the shipment (e.g., "Shipped", "Pending")

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Defines a many-to-one relationship with the Store entity. Uses LAZY loading to optimize performance.
    @JoinColumn(name = "store_id")
    private Store store; // Represents the store associated with the shipment

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Defines a many-to-one relationship with the Customer entity. Uses LAZY loading to optimize performance.
    @JoinColumn(name = "customer_id") 
    private Customer customer; // Represents the customer associated with the shipment
}

