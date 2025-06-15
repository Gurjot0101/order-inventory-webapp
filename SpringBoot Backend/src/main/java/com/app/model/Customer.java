package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Marks this class as a JPA entity, meaning it will be mapped to a database table
@Table(name = "customers") // Specifies the name of the database table to which this entity is mapped
@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
public class Customer {

    @Id // Specifies the primary key of the entity
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // Optional: Automatically generates a unique value for the primary key 
    @Column(name = "customer_id") 
    private int customerId; // Unique identifier for the customer

    @Column(name = "email_address", unique = true, nullable = false) 
    private String emailAddress; // Email address of the customer

    @Column(name = "full_name", nullable = false) 
    private String fullName; // Full name of the customer

    private String password; // Password of the customer 
}

