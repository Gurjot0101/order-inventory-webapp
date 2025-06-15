package com.app.services;

import com.app.dto.ShipmentDto;

import java.util.List;

public interface IShipmentService {

    /**
     * Retrieves a list of all shipments.
     *
     * @return A list of ShipmentDto objects representing all shipments.
     */
    List<ShipmentDto> getAllShipments();

}

