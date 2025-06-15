import React, { useState, useEffect } from "react";
import instance from "../../../axios";
import "./OrderHistory.css";

const OrderHistory = () => {
  // State variables to manage order data and filters
  const [orders, setOrders] = useState([]); // Store orders fetched from the API
  const [statuses, setStatuses] = useState([]); // Store available order statuses
  const [filter, setFilter] = useState("all"); // Current filter for fetching orders
  const [storeName, setStoreName] = useState(""); // Store name for filtering
  const [status, setStatus] = useState(""); // Order status for filtering
  const [orderId, setOrderId] = useState(""); // Order ID for filtering
  const [customerId, setCustomerId] = useState(""); // Customer ID for filtering
  const [email, setEmail] = useState(""); // Customer email for filtering
  const [startDate, setStartDate] = useState(""); // Start date for date range filtering
  const [endDate, setEndDate] = useState(""); // End date for date range filtering

  // Effect to fetch orders based on the selected filter and input values
  useEffect(() => {
    let url = "/api/v1/orders"; // Default URL for fetching all orders

    // Adjust URL based on the selected filter and its associated value
    if (filter === "store" && storeName) {
      url = `/api/v1/orders/store/${storeName}`;
    } else if (filter === "status" && status) {
      url = `/api/v1/orders/status/${status}`;
    } else if (filter === "orderId" && orderId) {
      url = `/api/v1/orders/${orderId}`;
    } else if (filter === "customerId" && customerId) {
      url = `/api/v1/orders/customer/${customerId}`;
    } else if (filter === "email" && email) {
      url = `/api/v1/orders/customer/email/${email}`;
    } else if (filter === "date" && startDate && endDate) {
      url = `/api/v1/orders/date/${startDate}/${endDate}`;
    }

    // Fetch orders from the API
    instance
      .get(url)
      .then((response) => {
        // Normalize response data to ensure it is an array
        const data = Array.isArray(response.data) ? response.data : [response.data];
        setOrders(data.reverse()); // Store orders in state, reversing for latest first
      })
      .catch((error) => console.error("Error fetching data:", error)); // Log errors
  }, [filter, storeName, status, orderId, customerId, email, startDate, endDate]); // Re-run when filter or related states change

  // Effect to fetch available statuses and their counts
  useEffect(() => {
    instance
      .get("/api/v1/orders/status")
      .then((response) => setStatuses(response.data)) // Store fetched statuses in state
      .catch((error) => console.error("Error fetching statuses:", error)); // Log errors
  }, []); // Run once when the component mounts

  // Handler for dropdown filter change
  const handleDropdownChange = (e) => {
    setFilter(e.target.value); // Update filter state based on dropdown selection
  };

  // Handler for updating the status of an order
  const handleStatusChange = (orderId, newStatus) => {
    instance
      .put(`/api/v1/orders`, { orderId, orderStatus: newStatus }) // Send update request
      .then(() => {
        // Update orders in state with the new status
        setOrders((prevOrders) =>
          prevOrders.map((order) =>
            order.orderId === orderId ? { ...order, orderStatus: newStatus } : order
          )
        );
      })
      .catch((error) => console.error("Error updating order status:", error)); // Log errors
  };

  // Format date string for display
  const formatDate = (dateString) => {
    const options = {
      weekday: "short",
      year: "numeric",
      month: "long",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
    };
    const date = new Date(dateString); // Convert date string to Date object
    return date.toLocaleDateString("en-GB", options).replace(",", ""); // Format as 'dd day month, yyyy hh:mm:ss'
  };

  return (
    <div className="order-history-container">
      {/* Status counts section */}
      <div className="status-counts">
        {statuses.length > 0 ? (
          statuses.map((status, i) => (
            <span key={i} style={{ margin: "10px", fontWeight: "Bolder" }}>
              {status.status}: {status.count} orders {/* Display status and count */}
            </span>
          ))
        ) : (
          <div>No statuses found</div> // Message if no statuses available
        )}
      </div>

      <h1 className="order-history-heading">Order History</h1>

      {/* Dropdown and input fields for filtering orders */}
      <div className="dropdown-container">
        <label htmlFor="filter-dropdown">Filter Orders By: </label>
        <select id="filter-dropdown" value={filter} onChange={handleDropdownChange}>
          <option value="all">All Orders</option>
          <option value="store">Store</option>
          <option value="status">Order Status</option>
          <option value="orderId">Order ID</option>
          <option value="customerId">Customer ID</option>
          <option value="email">Customer Email</option>
          <option value="date">Order Date Range</option>
        </select>

        {/* Conditional inputs based on the selected filter */}
        {filter === "store" && (
          <input
            type="text"
            placeholder="Enter Store Name"
            value={storeName}
            onChange={(e) => setStoreName(e.target.value)} // Update store name state
          />
        )}

        {filter === "status" && (
          <input
            type="text"
            placeholder="Enter Order Status"
            value={status}
            onChange={(e) => setStatus(e.target.value)} // Update status state
          />
        )}

        {filter === "orderId" && (
          <input
            type="text"
            placeholder="Enter Order ID"
            value={orderId}
            onChange={(e) => setOrderId(e.target.value)} // Update order ID state
          />
        )}

        {filter === "customerId" && (
          <input
            type="text"
            placeholder="Enter Customer ID"
            value={customerId}
            onChange={(e) => setCustomerId(e.target.value)} // Update customer ID state
          />
        )}

        {filter === "email" && (
          <input
            type="email"
            placeholder="Enter Customer Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)} // Update email state
          />
        )}

        {filter === "date" && (
          <div className="date-filter">
            <input
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)} // Update start date state
            />
            <input
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)} // Update end date state
            />
          </div>
        )}
      </div>

      {/* Table displaying order history */}
      <table className="order-history-table">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer ID</th>
            <th>Order Time</th>
            <th>Order Status</th>
          </tr>
        </thead>
        <tbody>
          {orders.length > 0 ? (
            orders.map((order) => (
              <tr key={order.orderId}>
                <td>{order.orderId}</td>
                <td>{order.customerId}</td>
                <td>{formatDate(order.orderTms)}</td>
                <td className={`status ${order.orderStatus.toLowerCase()}`}>
                  {order.orderStatus} {/* Display order status */}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4">No orders found</td> {/* Message if no orders are available */}
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default OrderHistory;
