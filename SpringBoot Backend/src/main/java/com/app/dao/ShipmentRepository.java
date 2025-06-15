package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Shipment;

@Repository // Indicates that this interface is a Spring Data repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    // Fetches all shipments from the database
    List<Shipment> findAll(); // This method is inherited from JpaRepository and does not need to be explicitly defined
}

