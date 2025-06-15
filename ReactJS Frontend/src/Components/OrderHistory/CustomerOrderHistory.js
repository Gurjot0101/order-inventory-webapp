import React, { useEffect, useState } from "react"; // Import necessary hooks from React
import { useRecoilValue } from "recoil"; // Import Recoil hook to access global state
import { userState } from "../../recoil"; // Import user state from Recoil
import styles from "./orderHistory.module.css"; // Import CSS module for styling
import Navbar from "../Util Components/NavBar/Navbar"; // Import Navbar component
import instance from "../../axios"; // Import axios instance for API requests

const OrderHistory = () => {
  // State variables for storing orders, loading state, and error messages
  const [orders, setOrders] = useState([]); 
  const [loading, setLoading] = useState(true); // Loading state
  const [error, setError] = useState(null); // State for error messages
  const user = useRecoilValue(userState); // Get the user state from Recoil

  // useEffect to fetch orders when the component mounts or when the user changes
  useEffect(() => {
    const fetchOrders = async () => {
      // Check if the user object is available and has an id
      if (user && user.id) {
        try {
          // Fetch the orders for the logged-in user from the API
          const response = await instance.get(
            `/api/v1/orders/customer/${user.id}`
          );
          setOrders(response.data); // Set the fetched orders to state
        } catch (err) {
          console.error("Error fetching orders:", err); // Log error to console
          setError("Failed to load orders. Please try again later."); // Set error message
        } finally {
          setLoading(false); // Stop loading state after request completion
        }
      } else {
        setLoading(false); // Stop loading state if user is not available
      }
    };

    fetchOrders(); // Call the function to fetch orders
  }, [user]); // Dependency array: re-run the effect if user changes

  // Function to handle order cancellation
  const handleCancelOrder = async (orderId) => {
    try {
      // Send request to cancel the order
      await instance.get(`/api/v1/orders/${orderId}/cancel`);
      alert("Order Cancelled Successfully"); // Notify user of success
      // Optionally, update the order status in the local state after cancellation
      setOrders((prevOrders) =>
        prevOrders.map((order) =>
          order.orderId === orderId ? { ...order, orderStatus: "CANCELLED" } : order
        )
      );
    } catch (error) {
      console.error("Error cancelling order:", error); // Log error to console
      alert("Failed to cancel the order. Please try again later."); // Notify user of failure
    }
  };

  // Show loading state while fetching data
  if (loading) {
    return <div>Loading...</div>; // Loading message
  }

  return (
    <div className={styles["order-history-page"]}> {/* Main container for order history */}
      <Navbar /> {/* Navbar component */}
      <main className={styles["main-content"]}> {/* Main content area */}
        <div className={styles["order-history-table"]}> {/* Table for displaying order history */}
          <div className={styles["table-header"]}> {/* Table header */}
            <div>Id</div> {/* Column for Order ID */}
            <div>Customer ID</div> {/* Column for Customer ID */}
            <div>Status</div> {/* Column for Order Status */}
            <div>Time</div> {/* Column for Order Time */}
            <div>Action</div> {/* Column for Actions */}
          </div>
          {loading ? ( // Check if still loading
            <div className={styles["loading-message"]}>Loading...</div> // Loading message
          ) : error ? ( // Check if there's an error
            <div className={styles["error-message"]}>{error}</div> // Display error message
          ) : orders.length > 0 ? ( // Check if there are any orders
            orders.map((order, index) => ( // Map through the orders to display them
              <div key={index} className={styles["table-row"]}> {/* Table row for each order */}
                <div>{order.orderId}</div> {/* Order ID */}
                <div>{order.customerId}</div> {/* Customer ID */}
                <div
                  className={`${styles.status} ${styles[order.orderStatus]}`} // Dynamic class for order status
                >
                  {order.orderStatus} {/* Order status */}
                </div>
                <div>{new Date(order.orderTms).toLocaleString()}</div> {/* Formatted order time */}
                <div className={styles.action}> {/* Action column */}
                  <button
                    onClick={() => handleCancelOrder(order.orderId)} // Cancel order on button click
                    disabled={order.orderStatus === "CANCELLED"} // Disable button if order is already cancelled
                  >
                    Cancel
                  </button>
                </div>
              </div>
            ))
          ) : ( // If no orders are found
            <div>No orders found for this customer.</div> // Message indicating no orders
          )}
        </div>
      </main>
    </div>
  );
};

export default OrderHistory; // Export the component for use in other parts of the application
