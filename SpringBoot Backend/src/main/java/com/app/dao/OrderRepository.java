package com.app.dao;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.model.Order;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	/*
	 * Find all orders by store name. This query joins the `Order` entity with the
	 * `Store` entity and filters by the `storeName`.
	 */
	@Query("SELECT o FROM Order o JOIN o.store s WHERE s.storeName = :storeName")
	List<Order> findOrdersByStoreName(@Param("storeName") String storeName);

	/*
	 * Find all orders by customer's email address. It assumes the `Order` entity
	 * has a relationship with the `Customer` entity and the `Customer` has an
	 * `emailAddress` field.
	 */
	@Query("SELECT o FROM Order o WHERE o.customer.emailAddress = :email")
	public List<Order> findByEmail(@Param("email") String email);

	/*
	 * Find all orders by customer ID. This query retrieves orders by the
	 * `customerId` from the `Customer` entity, assuming a relationship between
	 * `Order` and `Customer`.
	 */
	@Query("SELECT o FROM Order o WHERE o.customer.customerId = :customerId")
	public List<Order> findByCustomerId(@Param("customerId") int customerId);

	/*
	 * Find orders by their status. The `orderStatus` field is compared with the
	 * provided `status` parameter.
	 */
	@Query("SELECT o FROM Order o WHERE o.orderStatus = :status")
	public Collection<Order> findByStatus(@Param("status") String status);

	/*
	 * Find all orders within a specified date range. The `orderTms` (timestamp)
	 * field is used to filter orders placed between the given `startDate` and
	 * `endDate`.
	 */
	@Query("SELECT o FROM Order o WHERE o.orderTms >= :startDate AND o.orderTms <= :endDate")
	List<Order> findOrdersByDateRange(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

	/*
	 * Update the status of an order to 'CANCELLED' by order ID. This is a modifying
	 * query, so it requires the `@Modifying` and `@Transactional` annotations.
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Order o SET o.orderStatus = 'CANCELLED' WHERE o.orderId = :id")
	int updateOrderStatus(@Param("id") int id);

	/*
	 * Count the number of orders by their status. This method returns the number of
	 * orders that match the specified `status`.
	 */
	@Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = :status")
	int countByStatus(@Param("status") String status);
}
