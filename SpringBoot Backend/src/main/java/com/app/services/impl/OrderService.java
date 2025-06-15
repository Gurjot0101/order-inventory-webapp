package com.app.services.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.app.services.IorderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.CustomerRepository;
import com.app.dao.OrderRepository;
import com.app.dao.ProductRepository;
import com.app.dao.ShipmentRepository;
import com.app.dao.StoreRepository;
import com.app.dto.OrderDTO;
import com.app.exceptions.list.InvalidDataException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.model.Order;
import com.app.util.Status;

import jakarta.transaction.Transactional;

@Service
public class OrderService implements IorderService {

	@Autowired
	OrderRepository ordersRepo;

	@Autowired
	StoreRepository storeRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	ProductRepository productRepo;

	@Autowired
	ShipmentRepository shipmentRepo;

	@Autowired
	ModelMapper modelMapper;

	/*
	 * Fetches all orders and maps them to OrderDTO objects. Uses ModelMapper to
	 * convert Order entities to OrderDTOs.
	 */
	@Override
	public List<OrderDTO> getOrders() {
		List<OrderDTO> list = ordersRepo.findAll().stream().map(order -> modelMapper.map(order, OrderDTO.class))
				.collect(Collectors.toList());
		return list;
	}

	/*
	 * Fetches all orders by store name and maps them to OrderDTO objects. Store
	 * name is provided as a parameter.
	 */
	@Override
	public List<OrderDTO> getOrdersByStore(String store) {
		List<OrderDTO> list = ordersRepo.findOrdersByStoreName(store).stream()
				.map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
		return list;
	}

	/*
	 * Fetches a single order by its ID. Throws ResourceNotFoundException if the
	 * order is not found.
	 */
	@Override
	public OrderDTO getOrderById(int id) throws ResourceNotFoundException {
		Order order = ordersRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with Id: "+id+" not found"));
		return modelMapper.map(order, OrderDTO.class);
	}

	/*
	 * Fetches all orders by customer ID. Maps each Order entity to OrderDTO.
	 */
	@Override
	public List<OrderDTO> getOrdersByCustomerId(int customerId) {
		List<Order> orders = ordersRepo.findByCustomerId(customerId);
		return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
	}

	/*
	 * Fetches orders by customer email. Validates email format using a regex and
	 * throws InvalidDataException if invalid.
	 */
	@Override
	public List<OrderDTO> getOrdersByEmail(String email) throws InvalidDataException {
		String regex = "^[^@].*[@]+.*[^@]$"; // Basic email validation
		if (!email.matches(regex))
			throw new InvalidDataException("Invalid Email: "+email+" !");

		List<OrderDTO> list = ordersRepo.findByEmail(email).stream()
				.map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
		return list;
	}

	/*
	 * Fetches orders between two dates. Validates date format and converts it to
	 * Timestamp before querying. Maps the orders to OrderDTO objects.
	 */
	@Override
	public List<OrderDTO> getOrdersByDate(String startDate, String endDate) throws InvalidDataException {
		if (startDate.length() != 8 || endDate.length() != 8)
			throw new InvalidDataException("Invalid Date Format!");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate start = LocalDate.parse(startDate, formatter);
		LocalDate end = LocalDate.parse(endDate, formatter);

		Timestamp startTimestamp = Timestamp.valueOf(start.atStartOfDay());
		Timestamp endTimestamp = Timestamp.valueOf(end.atStartOfDay());

		List<Order> orders = ordersRepo.findOrdersByDateRange(startTimestamp, endTimestamp);
		return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
	}

	/*
	 * Cancels an order by updating its status to 'CANCELLED'. Throws
	 * ResourceNotFoundException if the order is not found.
	 */
	@Transactional
	@Override
	public Boolean cancelOrder(int id) throws ResourceNotFoundException {
		ordersRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with Id: "+id+" not found"));
		ordersRepo.updateOrderStatus(id);
		return true;
	}

	/*
	 * Returns a list of order status with the count of orders for each status. The
	 * statuses are hardcoded, and the count for each status is fetched from the
	 * repository.
	 */
	@Override
	public List<Status> orderStatus() {
		String[] statusNames = { "OPEN", "COMPLETE", "CANCELLED", "PAID", "REFUNDED", "SHIPPED" };
		List<Status> statusList = new ArrayList<>();
		for (String status : statusNames) {
			Status statusObj = new Status(status);
			statusObj.setCount(ordersRepo.countByStatus(status.toUpperCase()));
			statusList.add(statusObj);
		}
		return statusList;
	}

	/*
	 * Fetches orders by their status. Maps the orders to OrderDTO objects.
	 */
	@Override
	public List<OrderDTO> getOrdersByStatus(String status) {
		List<OrderDTO> list = ordersRepo.findByStatus(status).stream()
				.map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
		return list;
	}

	/*
	 * Creates a new order by saving the provided OrderDTO. Maps the DTO to an Order
	 * entity before saving.
	 */
	@Transactional
	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
		Order order = ordersRepo.save(modelMapper.map(orderDTO, Order.class));
		return modelMapper.map(order, OrderDTO.class);
	}

	/*
	 * Updates an existing order. Fetches the order by ID, updates its fields, and
	 * saves it. Maps the updated order to OrderDTO before returning.
	 */
	@Transactional
	@Override
	public OrderDTO updateOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
		Order order = ordersRepo.findById(orderDTO.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("order with Id: "+orderDTO.getOrderId()+" not found"));

		// Update order details
		order.setOrderTms(orderDTO.getOrderTms());
		order.setOrderStatus(orderDTO.getOrderStatus());
		order.setCustomer(customerRepo.findById(orderDTO.getCustomerId()).orElseThrow());
		order.setStore(storeRepo.findById(orderDTO.getStoreId()).orElseThrow());

		Order savedOrder = ordersRepo.save(order);
		return modelMapper.map(savedOrder, OrderDTO.class);
	}

}
