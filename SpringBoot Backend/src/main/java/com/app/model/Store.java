package com.app.model;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stores")
@Data
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id")
	private int storeId;

	@Column(name = "store_name")
	private String storeName;

	@Column(name = "web_address")
	private String webAddress;

	@Column(name = "physical_address")
	private String physicalAddress;

	@Column(name = "latitude")
	private BigDecimal latitude;

	@Column(name = "longitude")
	private BigDecimal longitude;

	@Column(name = "logo_mime_type")
	private String logoMimeType;

	@Column(name = "logo_filename")
	private String logoFileName;

	@Column(name = "logo_charset")
	private String logoCharSet;

	@Column(name = "logo_last_updated")
	private Date logoLastUpdated;
}
