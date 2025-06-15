package com.app.services;

import java.util.List;

import com.app.dto.OrderDTO;
import com.app.exceptions.list.InvalidDataException;
import com.app.exceptions.list.ResourceNotFoundException;
import com.app.util.Status;

public interface IorderService {

	List<OrderDTO> getOrders();

	List<OrderDTO> getOrdersByStore(String store);

	OrderDTO getOrderById(int id) throws ResourceNotFoundException;

	List<OrderDTO> getOrdersByCustomerId(int customerId);

	List<OrderDTO> getOrdersByEmail(String email);

	List<OrderDTO> getOrdersByDate(String startDate, String endDate) throws InvalidDataException;

	List<OrderDTO> getOrdersByStatus(String status);

	Boolean cancelOrder(int id) throws ResourceNotFoundException;

	List<Status> orderStatus();

	OrderDTO createOrder(OrderDTO order) throws ResourceNotFoundException;

	OrderDTO updateOrder(OrderDTO order) throws ResourceNotFoundException;

}
