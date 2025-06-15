package com.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.app.services.IcustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.CustomerRepository;
import com.app.dto.CustomerOrders;
import com.app.dto.CustomerShipment;
import com.app.dto.LoginDTO;
import com.app.dto.ShipmentStatusCountCustomer;
import com.app.exceptions.list.BadRequestException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Shipment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service // Marks this class as a service component in the Spring context
public class CustomerService implements IcustomerService {

    @Autowired
    private CustomerRepository repo; // Injects the CustomerRepository dependency for database operations

    @PersistenceContext
    private EntityManager entityManager; // Provides access to the persistence context for entity operations

    // Retrieves all customers from the database
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @Transactional // Marks this method as transactional, ensuring that the operations within it are executed within a transaction context
    public Customer addCustomer(Customer customer) {
        // Retrieves the highest customerId from the database
        Integer lastId = (Integer) entityManager.createQuery("SELECT MAX(c.customerId) FROM Customer c")
                .getSingleResult();
        if (lastId != null) {
            customer.setCustomerId(lastId + 1); // Sets the new customerId as one greater than the current maximum
        } else {
            customer.setCustomerId(1); // If no customers exist, start with ID 1
        }
        return repo.save(customer); // Saves the new customer to the database
    }

    // Updates an existing customer in the database
    public Customer updateCustomer(Customer customer) {
        Customer cust = repo.findById(customer.getCustomerId()).orElse(null);
        if (cust != null) {
            cust.setEmailAddress(customer.getEmailAddress());
            cust.setFullName(customer.getFullName());
            return repo.save(cust); // Saves the updated customer to the database
        }
        return null; // Return null if the customer was not found
    }

    // Deletes a customer by ID from the database
    public void deleteCustomer(int customerId) throws BadRequestException {
        if (repo.existsById(customerId)) {
            repo.deleteById(customerId); // Deletes the customer if it exists
        } else {
            throw new BadRequestException("Customer with ID " + customerId + " does not exist."); // Throws an exception if the customer does not exist
        }
    }

    // Retrieves a customer by their email address
    public List<Customer> getCustomerByEmailAddress(String emailAddress) {
        return repo.findByEmailAddress(emailAddress); // Assumes there is at least one customer with the given email address
    }

    // Retrieves a list of customers whose full name contains the specified string
    public List<Customer> getCustomersByFullName(String fullName) {
        return repo.findByFullNameContains(fullName);
    }

    // Retrieves the count of customers grouped by shipment status
    public List<ShipmentStatusCountCustomer> getOrderCountByStatus() {
        return repo.getOrderCountByStatus();
    }

    // Retrieves a customer and their orders by customer ID
    public CustomerOrders getCustomerOrders(int customerId) {
        Customer customer = repo.findById(customerId).orElse(null);
        List<Order> order = repo.getCustomerOrders(customerId);
        return new CustomerOrders(customer, order); // Returns a CustomerOrders object containing the customer and their orders
    }

    // Retrieves a customer and their shipments by customer ID
    public CustomerShipment getCustomerShipment(int customerId) throws ResourceNotFoundException {
        // Fetches the customer and handles the case where the customer is not found
        Customer customer = repo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        // Fetches the shipments and ensures the list is not null
        List<Shipment> shipment = repo.getCustomerShipments(customerId);
        if (shipment == null)
            shipment = new ArrayList<>();
        return new CustomerShipment(customer, shipment); // Returns a CustomerShipment object containing the customer and their shipments
    }


    @Override
	public Customer getCustomerById(int customerId) throws ResourceNotFoundException {
	    return repo.findById(customerId)
	               .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
	}
    

    @Override
    public LoginDTO customerValidation(String email, String password) {
        return repo.getCustomerDetails(email, password); // Validates customer login credentials
    }
}

