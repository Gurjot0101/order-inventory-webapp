// src/Stores.js
import React, { useState, useEffect } from "react"; // Import React and hooks
import axios from "axios"; // Import axios for making HTTP requests
import "./Stores.css"; // Import CSS styles for the component

const Stores = () => {
  // State to hold the list of stores
  const [stores, setStores] = useState([]);
  // State to indicate loading status
  const [loading, setLoading] = useState(true);
  // State to hold any error messages
  const [error, setError] = useState(null);

  // Effect to fetch stores data when the component mounts
  useEffect(() => {
    const fetchStores = async () => {
      try {
        // Make a GET request to fetch stores from the API
        const response = await axios.get("http://localhost:8084/api/v1/stores");
        setStores(response.data); // Set the stores data in state
        setLoading(false); // Update loading state
      } catch (error) {
        setError(error.message); // Set error message in state if request fails
        setLoading(false); // Update loading state
      }
    };

    fetchStores(); // Call the function to fetch stores
  }, []); // Empty dependency array means this effect runs once when the component mounts

  // Conditional rendering for loading state
  if (loading) return <div className="loading">Loading...</div>;
  // Conditional rendering for error state
  if (error) return <div className="error">Error: {error}</div>;

  // Main component render
  return (
    <div className="stores-container">
      <h1 className="stores-heading">Stores List</h1>
      <table className="stores-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Web Address</th>
            <th>Physical Address</th>
          </tr>
        </thead>
        <tbody>
          {/* Map through the stores array and render each store in a table row */}
          {stores.map((store) => (
            <tr key={store.storeId}>
              <td>{store.storeId}</td> {/* Store ID */}
              <td>{store.storeName}</td> {/* Store Name */}
              <td>{store.webAddress ? store.webAddress : "N/A"}</td>{" "}
              {/* Web Address or 'N/A' */}
              <td>
                {store.physicalAddress ? store.physicalAddress : "N/A"}
              </td>{" "}
              {/* Physical Address or 'N/A' */}
              {/* Additional table data can be added here if needed */}
              {/* <td>{store.webAddress}</td> */}
              {/* <td>{store.physicalAddress}</td> */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Stores; // Export the Stores component for use in other files
