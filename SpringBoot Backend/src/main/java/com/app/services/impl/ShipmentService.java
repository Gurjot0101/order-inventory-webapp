package com.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.app.services.IShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ShipmentRepository;
import com.app.dto.ShipmentDto;
import com.app.model.Shipment;

@Service
public class ShipmentService implements IShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    /**
     * Retrieves all shipments and converts them to DTOs.
     *
     * @return A list of ShipmentDto objects representing all shipments.
     */
    @Override
    public List<ShipmentDto> getAllShipments() {
        // Fetch all shipments from the repository
        List<Shipment> shipments = shipmentRepository.findAll();
        
        // Convert the list of shipments to a list of ShipmentDto
        return shipments.stream()
                        .map(this::convertToDTO) // Convert each Shipment to ShipmentDto
                        .collect(Collectors.toList()); // Collect results into a list
    }

    /**
     * Converts a Shipment entity to a ShipmentDto.
     *
     * @param shipment The Shipment entity to convert.
     * @return The corresponding ShipmentDto.
     */
    private ShipmentDto convertToDTO(Shipment shipment) {
        ShipmentDto dto = new ShipmentDto();
        
        // Map fields from Shipment to ShipmentDto
        dto.setShipmentId(shipment.getShipmentId());
        dto.setDeliveryAddress(shipment.getDeliveryAddress());
        dto.setShipmentStatus(shipment.getShipmentStatus());
        
        // Set store and customer IDs if they exist
        dto.setStoreId(shipment.getStore() != null ? shipment.getStore().getStoreId() : null);
        dto.setCustomerId(shipment.getCustomer() != null ? shipment.getCustomer().getCustomerId() : null);
        
        return dto;
    }
}

