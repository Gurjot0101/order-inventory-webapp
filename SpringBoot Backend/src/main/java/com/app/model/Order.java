package com.app.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // Indicates that this class is a JPA entity
@Table(name = "orders") // Specifies the table name in the database
@Data // Lombok annotation to generate getters, setters, and other utility methods
public class Order {

	@Id // Indicates the primary key of the entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the primary key value
	@Column(name = "order_id") // Maps the field to the corresponding column in the database
	private int orderId;

	@Column(name = "order_status") // Maps the field to the corresponding column in the database
	private String orderStatus;

	@Column(name = "order_tms", nullable = false) // Maps the field and marks it as non-nullable
	private Timestamp orderTms; // Timestamp to record when the order was placed

	@ManyToOne(fetch = FetchType.EAGER) // Defines a many-to-one relationship with Store
	@JoinColumn(name = "store_id") // Specifies the foreign key column
	private Store store; // The store associated with this order

	@ManyToOne(fetch = FetchType.EAGER) // Defines a many-to-one relationship with Customer
	@JoinColumn(name = "customer_id", nullable = false) // Specifies the foreign key column and marks it as non-nullable
	private Customer customer; // The customer who placed the order

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Defines a one-to-many relationship with OrderItem
	@JsonBackReference // Prevents infinite recursion during JSON serialization
	private List<OrderItem> orderItems; // List of order items associated with this order
}
