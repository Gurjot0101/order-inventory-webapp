import axios from "axios";

// Generic function to fetch data from a given URL and handle response/error
export const fetchApi = async (url, setData, setError) => {
  try {
    const response = await axios.get(url); // Make GET request to the specified URL
    setData(response.data); // Set the retrieved data in state
    setError(null); // Clear any previous error
  } catch (error) {
    console.log(error); // Log the error for debugging
    setError(`Error fetching data from database: ${error.message}`); // Set error message in state
    setData(null); // Clear any previous data
  }
};

// Fetch all inventory data
export const handleGetAllInventory = (setInventoryData, setError) => {
  fetchApi(
    "http://localhost:8084/api/v1/inventory", // API endpoint for all inventory
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};

// Fetch inventory data by store ID
export const handleGetInventoryByStoreId = (
  storeId,
  setInventoryData,
  setError
) => {
  fetchApi(
    `http://localhost:8084/api/v1/inventory?storeid=${storeId}`, // API endpoint with query parameter for store ID
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};

// Fetch inventory data by category
export const handleGetInventoryByCategory = (
  category,
  setInventoryData,
  setError
) => {
  fetchApi(
    `http://localhost:8084/api/v1/inventory/category/${category}`, // API endpoint for category
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};

// Fetch inventory data by order ID
export const handleGetInventoryByOrderId = (
  orderId,
  setInventoryData,
  setError
) => {
  fetchApi(
    `http://localhost:8084/api/v1/inventory/${orderId}`, // API endpoint for specific order ID
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};

// Fetch product details associated with a specific order ID
export const handleGetProductDetailsByOrderId = (
  orderId,
  setInventoryData,
  setError
) => {
  fetchApi(
    `http://localhost:8084/api/v1/inventory/${orderId}/details`, // API endpoint for order details
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};

// Fetch the count of shipments by status
export const handleGetShipmentCountByStatus = (setInventoryData, setError) => {
  fetchApi(
    "http://localhost:8084/api/v1/inventory/shipment/count", // API endpoint for shipment count
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};

// Fetch inventory data along with associated shipment data
export const handleGetInventoryAndShipments = (setInventoryData, setError) => {
  fetchApi(
    "http://localhost:8084/api/v1/inventory/shipment", // API endpoint for inventory and shipments
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};

// Fetch inventory data based on product ID and store ID
export const handleGetInventoryByProductIdAndStoreId = (
  orderId,
  storeId,
  setInventoryData,
  setError
) => {
  fetchApi(
    `http://localhost:8084/api/v1/inventory/product/${orderId}/store/${storeId}`, // API endpoint for specific product and store IDs
    setInventoryData, // Function to set inventory data in state
    setError // Function to set error message in state
  );
};
