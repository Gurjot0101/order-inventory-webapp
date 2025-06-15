//package com.product.model;
//
//public class Product {
//
//}
///
package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id") // This should match the referencedColumnName in OrderItem
	private Long productId;

	// @Column(name = "product_name")
	private String name;

	// @Column(name = "unit_price")
	private Double price;

    // @Column(name = "colour")
	private String color;

	private String brand;

	private String size;

	private Integer rating;

	private String category;
	
	@Column(name = "image",columnDefinition = "TEXT")
	private String image;

}
