package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.LoginDTO;
import com.app.services.IadminService;
import com.app.services.IcustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow cross-origin requests from React frontend
@RequestMapping("/api/v1/auth") // Base URL for authentication-related requests
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private IadminService adminService;

	@Autowired
	private IcustomerService customerService;

	/**
	 * Admin login validation endpoint. Checks whether the admin with the provided
	 * email and password exists.
	 * 
	 * @param email    Admin's email address
	 * @param password Admin's password
	 * @return ResponseEntity containing the admin details (LoginDTO) if validation
	 *         is successful, or null if not found
	 */
	@GetMapping("/admin")
	public ResponseEntity<LoginDTO> adminValidation(@RequestParam String email, @RequestParam String password) {
		logger.info("Validating admin with email: {}", email);

		// Validate admin credentials and retrieve details
		LoginDTO adminDetails = adminService.adminValidation(email, password);

		if (adminDetails != null) {
			logger.info("Admin validated: {}", adminDetails.getEmail());
			return ResponseEntity.ok(adminDetails); // Return admin details if validation is successful
		} else {
			logger.warn("Admin not found for email: {}", email);
			return ResponseEntity.ok(null); // Return OK with null if admin is not found
		}
	}

	/**
	 * Customer login validation endpoint. Checks whether the customer with the
	 * provided email and password exists.
	 * 
	 * @param email    Customer's email address
	 * @param password Customer's password
	 * @return ResponseEntity containing the customer details (LoginDTO) if
	 *         validation is successful, or null if not found
	 */
	@GetMapping("/customer")
	public ResponseEntity<LoginDTO> customerValidation(@RequestParam String email, @RequestParam String password) {
		logger.info("Validating customer with email: {}", email);

		// Validate customer credentials and retrieve details
		LoginDTO customerDetails = customerService.customerValidation(email, password);

		if (customerDetails != null) {
			logger.info("Customer validated: {}", customerDetails.getEmail());
			return ResponseEntity.ok(customerDetails); // Return customer details if validation is successful
		} else {
			logger.warn("Customer not found for email: {}", email);
			return ResponseEntity.ok(null); // Return OK with null if customer is not found
		}
	}
}
