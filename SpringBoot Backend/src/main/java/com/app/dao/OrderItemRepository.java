package com.app.dao;

import com.app.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    // Automatically derived query based on the method name
    List<OrderItem> findByOrder_OrderId(int orderId);
}
