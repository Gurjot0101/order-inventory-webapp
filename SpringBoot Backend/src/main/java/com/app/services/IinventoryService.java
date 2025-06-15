package com.app.services;

import java.util.List;

import com.app.dto.InventoryDTO;
import com.app.dto.InventoryStoreProductCustomerDTO;
import com.app.dto.InventoryStoreProductStatusDTO;
import com.app.dto.ProductOrderDetailsDTO;
import com.app.dto.ShipmentStatusCountDTO;

public interface IinventoryService {

    // Retrieve all inventory records
    List<InventoryDTO> getAllInventory();

    // Fetch inventory and product status by store ID
    List<InventoryStoreProductStatusDTO> getInventoryByStoreId(int storeId);

    // Retrieve inventory details including store, product, and customer by order ID
    List<InventoryStoreProductCustomerDTO> getInventoryByOrderId(int orderId);

    // Get detailed product information by order ID including store, shipment status, and total amount
    List<ProductOrderDetailsDTO> getProductDetailsByOrderId(int orderId);

    // Fetch inventory based on product ID and store ID
    List<InventoryDTO> getInventoryByProductIdAndStoreId(int productId, int storeId);

    // Retrieve the count of shipments grouped by shipment status
    List<ShipmentStatusCountDTO> getShipmentCountByStatus();

    // Get inventory records that match shipment data
    List<InventoryDTO> getInventoryAndShipments();

    // Fetch inventory by product category
    List<InventoryDTO> getInventoryByProductCategory(String category);
}
