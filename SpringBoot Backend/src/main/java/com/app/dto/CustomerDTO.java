package com.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.app.model.Customer;

import lombok.Data;

@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
public class CustomerDTO {
    private int id; // Customer ID
    private String fullName; // Full name of the customer
    private String emailAddress; // Email address of the customer
    private String password; // Password of the customer

    // Static method to convert a Customer entity to a CustomerDTO
    public static CustomerDTO fromEntity(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getCustomerId()); // Set the ID from the entity
        dto.setFullName(customer.getFullName()); // Set the full name from the entity
        dto.setEmailAddress(customer.getEmailAddress()); // Set the email address from the entity
        // Password is not set here, assuming it's handled separately for security reasons
        return dto;
    }

    // Static method to convert a list of Customer entities to a list of CustomerDTOs
    public static List<CustomerDTO> fromEntities(List<Customer> customers) {
        return customers.stream()
                .map(CustomerDTO::fromEntity) // Map each Customer entity to a CustomerDTO
                .collect(Collectors.toList()); // Collect the results into a list
    }
}

