package com.app.services;

import java.util.List;

import com.app.dto.CustomerOrders;
import com.app.dto.CustomerShipment;
import com.app.dto.LoginDTO;
import com.app.dto.ShipmentStatusCountCustomer;
import com.app.exceptions.list.BadRequestException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Customer;

public interface IcustomerService {

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The customer with the specified ID.
     * @throws ResourceNotFoundException 
     */
    Customer getCustomerById(int customerId) throws ResourceNotFoundException;

    /**
     * Finds customers whose full name contains the specified string.
     *
     * @param fullName The substring to search for in customer full names.
     * @return A list of customers matching the given full name substring.
     */
    List<Customer> getCustomersByFullName(String fullName);

    /**
     * Retrieves all customers in the system.
     *
     * @return A list of all customers.
     */
    List<Customer> getAllCustomers();

    /**
     * Adds a new customer to the system.
     *
     * @param customer The customer to add.
     * @return The added customer with generated ID.
     */
    Customer addCustomer(Customer customer);

    /**
     * Updates an existing customer's details.
     *
     * @param customer The customer with updated details.
     * @return The updated customer.
     */
    Customer updateCustomer(Customer customer);

    /**
     * Deletes a customer by their unique ID.
     *
     * @param id The ID of the customer to delete.
     * @throws BadRequestException If the customer with the given ID does not exist.
     */
    void deleteCustomer(int id) throws BadRequestException;

    /**
     * Retrieves a customer by their email address.
     *
     * @param emailAddress The email address of the customer to retrieve.
     * @return The customer with the specified email address.
     */
    List<Customer> getCustomerByEmailAddress(String emailAddress);

    /**
     * Retrieves the count of customers grouped by shipment status.
     *
     * @return A list of ShipmentStatusCountCustomer objects representing the shipment status counts.
     */
    List<ShipmentStatusCountCustomer> getOrderCountByStatus();

    /**
     * Retrieves a customer's orders by their unique ID.
     *
     * @param customerId The ID of the customer whose orders are to be retrieved.
     * @return A CustomerOrders object containing the customer and their orders.
     */
    CustomerOrders getCustomerOrders(int customerId);

    /**
     * Retrieves a customer's shipments by their unique ID.
     *
     * @param customerId The ID of the customer whose shipments are to be retrieved.
     * @return A CustomerShipment object containing the customer and their shipments.
     * @throws ResourceNotFoundException If the customer with the given ID is not found.
     */
    CustomerShipment getCustomerShipment(int customerId) throws ResourceNotFoundException;

    /**
     * Validates customer login credentials.
     *
     * @param email The email address of the customer.
     * @param password The password of the customer.
     * @return A LoginDTO object containing the customer details if the credentials are valid.
     */
    LoginDTO customerValidation(String email, String password);
}
