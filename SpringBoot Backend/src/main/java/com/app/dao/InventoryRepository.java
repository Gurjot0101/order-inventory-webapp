package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    // Retrieves inventory, product, store, and order status based on the store ID
    @Query("SELECT inv, p, s, o.orderStatus " +
           "FROM Inventory inv " +
           "JOIN inv.products p " +
           "JOIN inv.store s " +
           "JOIN OrderItem oi ON oi.product.productId = p.productId " +
           "JOIN oi.order o " +
           "WHERE s.storeId = :storeId")
    List<Object[]> findByStoreId(@Param("storeId") int storeId);

    // Fetches store, product, and customer details based on order ID
    @Query("SELECT i.store, i.products, o.customer " +
           "FROM Inventory i " +
           "JOIN Order o ON i.store.storeId = o.store.storeId " +
           "WHERE o.orderId = :orderId")
    List<Object[]> findByOrderId(@Param("orderId") int orderId);

    // Retrieves shipment status and the total number of products sold, grouped by shipment status
    @Query("SELECT sh.shipmentStatus, SUM(oi.quantity) AS totalProductsSold " +
           "FROM Inventory inv " +
           "JOIN inv.products p " +
           "JOIN OrderItem oi ON oi.product.productId = p.productId " +
           "JOIN oi.order o " +
           "JOIN Shipment sh ON sh.store.storeId = inv.store.storeId " +
           "WHERE o.store.storeId = inv.store.storeId " +
           "GROUP BY sh.shipmentStatus")
    List<Object[]> findShipmentCountByStatus();

    // Fetches product details, store, shipment status, and total amount for an order
    @Query("SELECT p, i.store, sh.shipmentStatus, SUM(oi.unitPrice * oi.quantity) AS totalAmount " +
           "FROM Order o " +
           "JOIN o.orderItems oi " +
           "JOIN oi.product p " +
           "JOIN Inventory i ON i.products.productId = p.productId " +
           "JOIN Shipment sh ON sh.store.storeId = i.store.storeId " +
           "WHERE o.orderId = :orderId " +
           "GROUP BY p, i.store, sh.shipmentStatus")
    List<Object[]> findProductDetailsByOrderId(@Param("orderId") int orderId);

    // Fetches inventory based on product ID and store ID
    @Query("SELECT i FROM Inventory i " +
           "JOIN i.products p " +
           "JOIN i.store s " +
           "WHERE p.productId = :productId AND s.storeId = :storeId")
    List<Inventory> findByProductIdAndStoreId(@Param("productId") int productId, @Param("storeId") int storeId);

    // Fetches inventory records that match specific shipments
    @Query("SELECT inv " +
           "FROM Inventory inv " +
           "JOIN inv.products p " +
           "JOIN OrderItem oi ON oi.product.productId = p.productId " +
           "JOIN oi.order o " +
           "JOIN Shipment s ON s.store.storeId = inv.store.storeId " +
           "WHERE s.store.storeId = inv.store.storeId")
    List<Inventory> findInventoryMatchingShipments();

    // Retrieves inventory by product category
    @Query("SELECT inv " +
           "FROM Inventory inv " +
           "JOIN inv.products p " +
           "WHERE p.category = :category")
    List<Inventory> findInventoryByProductCategory(@Param("category") String category);
}
