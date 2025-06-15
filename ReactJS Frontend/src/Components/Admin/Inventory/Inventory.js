import React, { useState } from "react";

import styles from "./Inventory.module.css";

import {
  handleGetAllInventory,
  handleGetInventoryByStoreId,
  handleGetInventoryByCategory,
  handleGetInventoryByOrderId,
  handleGetProductDetailsByOrderId,
  handleGetShipmentCountByStatus,
  handleGetInventoryAndShipments,
  handleGetInventoryByProductIdAndStoreId,
} from "./api";

const InventoryTest = () => {
  const [inventoryData, setInventoryData] = useState(null);
  const [storeId, setStoreId] = useState("");
  const [category, setCategory] = useState("");
  const [orderId, setOrderId] = useState("");
  const [productId, setProductId] = useState(""); // Added for Product ID
  const [selectedOption, setSelectedOption] = useState("All Inventory"); // Dropdown selection state
  const [error, setError] = useState(null);

  // Function to render the table based on the structure of inventoryData
  const renderTable = () => {
    if (Array.isArray(inventoryData)) {
      if (inventoryData.length === 0) {
        return <p>No data available</p>;
      }

      // Check if the data includes totalProductsSold
      if (inventoryData[0].totalProductsSold) {
        return (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Shipment Status</th>
                <th>Total Products Sold</th>
              </tr>
            </thead>
            <tbody>
              {inventoryData.map((item, index) => (
                <tr key={index}>
                  <td>{item.shipmentStatus}</td>
                  <td>{item.totalProductsSold}</td>
                </tr>
              ))}
            </tbody>
          </table>
        );
      }

      // Check if the data includes totalAmount
      if (inventoryData[0].totalAmount) {
        return (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>Product Color</th>
                <th>Product Brand</th>
                <th>Product Size</th>
                <th>Product Rating</th>
                <th>Product Category</th>
                <th>Store ID</th>
                <th>Store Name</th>
                <th>Web Address</th>
                <th>Store Address</th>
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Shipment Status</th>
                <th>Total Amount</th>
              </tr>
            </thead>
            <tbody>
              {inventoryData.map((item, index) => (
                <tr key={index}>
                  <td>{item.product?.productId}</td>
                  <td>{item.product?.name}</td>
                  <td>{item.product?.price}</td>
                  <td>{item.product?.color}</td>
                  <td>{item.product?.brand}</td>
                  <td>{item.product?.size}</td>
                  <td>{item.product?.rating}</td>
                  <td>{item.product?.category}</td>
                  <td>{item.store?.storeId}</td>
                  <td>{item.store?.storeName}</td>
                  <td>{item.store?.webAddress}</td>
                  <td>{item.store?.physicalAddress}</td>
                  <td>{item.store?.latitude}</td>
                  <td>{item.store?.longitude}</td>
                  <td>{item.shipmentStatus}</td>
                  <td>{item.totalAmount}</td>
                </tr>
              ))}
            </tbody>
          </table>
        );
      }

      // Check if the data includes customer details
      if (inventoryData[0].customer) {
        return (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>Product Color</th>
                <th>Product Brand</th>
                <th>Product Size</th>
                <th>Product Rating</th>
                <th>Product Category</th>
                <th>Store ID</th>
                <th>Store Name</th>
                <th>Web Address</th>
                <th>Store Address</th>
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Customer Id</th>
                <th>E-mail</th>
                <th>Full Name</th>
              </tr>
            </thead>
            <tbody>
              {inventoryData.map((item, index) => (
                <tr key={index}>
                  <td>{item.product?.productId}</td>
                  <td>{item.product?.name}</td>
                  <td>{item.product?.price}</td>
                  <td>{item.product?.color}</td>
                  <td>{item.product?.brand}</td>
                  <td>{item.product?.size}</td>
                  <td>{item.product?.rating}</td>
                  <td>{item.product?.category}</td>
                  <td>{item.store?.storeId}</td>
                  <td>{item.store?.storeName}</td>
                  <td>{item.store?.webAddress}</td>
                  <td>{item.store?.physicalAddress}</td>
                  <td>{item.store?.latitude}</td>
                  <td>{item.store?.longitude}</td>
                  <td>{item.customer?.customerId}</td>
                  <td>{item.customer?.emailAddress}</td>
                  <td>{item.customer?.fullName}</td>
                </tr>
              ))}
            </tbody>
          </table>
        );
      }

      // Check if the data includes inventoryId
      if (inventoryData[0].inventoryId) {
        return (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>Product Color</th>
                <th>Product Brand</th>
                <th>Product Size</th>
                <th>Product Rating</th>
                <th>Product Category</th>
                <th>Store ID</th>
                <th>Store Name</th>
                <th>Web Address</th>
                <th>Store Address</th>
                <th>Latitude</th>
                <th>Longitude</th>
              </tr>
            </thead>
            <tbody>
              {inventoryData.map((item, index) => (
                <tr key={index}>
                  <td>{item.products?.productId}</td>
                  <td>{item.products?.name}</td>
                  <td>{item.products?.price}</td>
                  <td>{item.products?.color}</td>
                  <td>{item.products?.brand}</td>
                  <td>{item.products?.size}</td>
                  <td>{item.products?.rating}</td>
                  <td>{item.products?.category}</td>
                  <td>{item.store?.storeId}</td>
                  <td>{item.store?.storeName}</td>
                  <td>{item.store?.webAddress}</td>
                  <td>{item.store?.physicalAddress}</td>
                  <td>{item.store?.latitude}</td>
                  <td>{item.store?.longitude}</td>
                </tr>
              ))}
            </tbody>
          </table>
        );
      }

      // Check if the data includes product details
      if (inventoryData[0].product) {
        return (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>Product Color</th>
                <th>Product Brand</th>
                <th>Product Size</th>
                <th>Product Rating</th>
                <th>Product Category</th>
                <th>Store ID</th>
                <th>Store Name</th>
                <th>Web Address</th>
                <th>Store Address</th>
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Order Status</th>
              </tr>
            </thead>
            <tbody>
              {inventoryData.map((item, index) => (
                <tr key={index}>
                  <td>{item.product?.productId}</td>
                  <td>{item.product?.name}</td>
                  <td>{item.product?.price}</td>
                  <td>{item.product?.color}</td>
                  <td>{item.product?.brand}</td>
                  <td>{item.product?.size}</td>
                  <td>{item.product?.rating}</td>
                  <td>{item.product?.category}</td>
                  <td>{item.store?.storeId}</td>
                  <td>{item.store?.storeName}</td>
                  <td>{item.store?.webAddress}</td>
                  <td>{item.store?.physicalAddress}</td>
                  <td>{item.store?.latitude}</td>
                  <td>{item.store?.longitude}</td>
                  <td>{item.orderStatus}</td>
                </tr>
              ))}
            </tbody>
          </table>
        );
      }

      return <p>Unknown data structure</p>;
    }
  };

  // Function to handle fetching data based on the selected option
  const handleFetch = () => {
    switch (selectedOption) {
      case "All Inventory":
        handleGetAllInventory(setInventoryData, setError);
        break;
      case "Store ID":
        handleGetInventoryByStoreId(storeId, setInventoryData, setError);
        break;
      case "Category":
        handleGetInventoryByCategory(category, setInventoryData, setError);
        break;
      case "Order ID":
        handleGetInventoryByOrderId(orderId, setInventoryData, setError);
        break;
      case "Product and Store":
        handleGetInventoryByProductIdAndStoreId(
          productId,
          storeId,
          setInventoryData,
          setError
        );
        break;
      case "Inventory and Shipments":
        handleGetInventoryAndShipments(setInventoryData, setError);
        break;
      case "Product Details by Order ID":
        handleGetProductDetailsByOrderId(orderId, setInventoryData, setError);
        break;
      case "Shipment Count by Status":
        handleGetShipmentCountByStatus(setInventoryData, setError);
        break;
      default:
        setError("Invalid option");
    }
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.header}>Order Inventory</h1>
      <div className={styles.formGroup}>
        <label htmlFor="inventory-select">Choose Inventory Type: </label>
        <select
          id="inventory-select"
          value={selectedOption}
          onChange={(e) => {
            setSelectedOption(e.target.value);
            setError(null); // Clear the error when the dropdown is changed
          }}
        >
          <option value="All Inventory">Get All Inventory</option>
          <option value="Store ID">Get Inventory By Store ID</option>
          <option value="Category">Get Inventory By Category</option>
          <option value="Order ID">Get Inventory By Order ID</option>
          <option value="Product and Store">
            Get Inventory By Product ID and Store ID
          </option>
          <option value="Inventory and Shipments">
            Get Inventory and Shipments
          </option>
          <option value="Product Details by Order ID">
            Get Product Details by Order ID
          </option>
          <option value="Shipment Count by Status">
            Get Shipment Count by Status
          </option>
        </select>
      </div>

      {/* Conditionally render inputs based on selected option */}
      {selectedOption === "Store ID" && (
        <div className={styles.inputGroup}>
          <label>Store ID: </label>
          <input
            className={styles.inputText}
            type="text"
            value={storeId}
            onChange={(e) => setStoreId(e.target.value)}
          />
        </div>
      )}

      {selectedOption === "Category" && (
        <div>
          <label>Category: </label>
          <input
            type="text"
            className={styles.inputText}
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          />
        </div>
      )}

      {selectedOption === "Order ID" && (
        <div className={styles.inputGroup}>
          <label>Order ID: </label>
          <input
            className={styles.inputText}
            type="text"
            value={orderId}
            onChange={(e) => setOrderId(e.target.value)}
          />
        </div>
      )}

      {selectedOption === "Product and Store" && (
        <div className={styles.inputGroup}>
          <label>Product ID: </label>
          <input
            className={styles.inputText}
            type="text"
            value={productId}
            onChange={(e) => setProductId(e.target.value)}
          />
          <label>Store ID: </label>
          <input
            className={styles.inputText}
            type="text"
            value={storeId}
            onChange={(e) => setStoreId(e.target.value)}
          />
        </div>
      )}

      {selectedOption === "Product Details by Order ID" && (
        <div className={styles.inputGroup}>
          <label>Order ID: </label>
          <input
            className={styles.inputText}
            type="text"
            value={orderId}
            onChange={(e) => setOrderId(e.target.value)}
          />
        </div>
      )}

      <button className={styles.button} onClick={handleFetch}>
        Fetch Data
      </button>

      {error && (
        <p className={styles.error}>
          Error: Provided {selectedOption} doesn't exist
        </p>
      )}

      {inventoryData && renderTable()}
    </div>
  );
};

export default InventoryTest;
