package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.dto.LoginDTO;
import com.app.dto.ShipmentStatusCountCustomer;
import com.app.model.Customer;
import com.app.model.Order;
import com.app.model.Shipment;

@Repository // Indicates that this interface is a Spring Data repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Find customers by their email address
    public List<Customer> findByEmailAddress(String emailAddress);

    // Find customers whose full name contains the specified string
    public List<Customer> findByFullNameContains(String fullName);

    // Custom query to get the count of customers by shipment status
    @Query("SELECT new com.app.dto.ShipmentStatusCountCustomer(s.shipmentStatus, COUNT(s.customer) as customerCount) "
            + "FROM Shipment s " + "GROUP BY s.shipmentStatus")
    public List<ShipmentStatusCountCustomer> getOrderCountByStatus();

    // Custom query to get all orders for a specific customer
    @Query("SELECT o " + "FROM Customer c, Order o " + "WHERE c.customerId = :Id "
            + "AND c.customerId = o.customer.customerId")
    public List<Order> getCustomerOrders(@Param("Id") int customerId);

    // Custom query to get all shipments for a specific customer
    @Query("SELECT s FROM Shipment s " + "WHERE s.customer.customerId = :Id ")
    public List<Shipment> getCustomerShipments(@Param("Id") int customerId);

    // Custom query to authenticate a customer and return their details
    @Query("SELECT new com.app.dto.LoginDTO(c.customerId, c.emailAddress, c.fullName) "
            + "FROM Customer c WHERE LOWER(c.emailAddress) = LOWER(:email) AND c.password = :password")
    LoginDTO getCustomerDetails(@Param("email") String email, @Param("password") String password);
}

