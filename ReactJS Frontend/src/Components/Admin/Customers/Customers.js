import React, { useState, useEffect } from 'react';
import './Customers.css';

const Customers = () => {
  // State to hold the list of customers
  const [customers, setCustomers] = useState([]);

  // Fetch customer data from the API when the component mounts
  useEffect(() => {
    fetch('http://localhost:8084/api/v1/customers')
      .then(response => response.json()) // Parse the JSON from the response
      .then(data => setCustomers(data.reverse())) // Set customers state with the reversed data
      .catch(error => console.error('Error fetching data:', error)); // Handle any errors during fetch
  }, []); // Empty dependency array means this runs once when the component mounts

  // Function to handle customer deletion
  const handleDelete = (id) => {
    fetch(`http://localhost:8084/api/v1/customers/${id}`, {
      method: 'DELETE', // Set the method to DELETE
    })
      .then(response => {
        if (response.ok) { // Check if the response is successful
          // Update customers state to remove the deleted customer
          setCustomers(prevCustomers =>
            prevCustomers.filter(customer => customer.customerId !== id)
          );
        } else {
          console.error('Failed to delete customer'); // Log an error if the delete failed
        }
      })
      .catch(error => console.error('Error deleting customer:', error)); // Handle any errors during delete
  };

  return (
    <div className="customers-container">
      <h1 className="customers-heading">Customer List</h1>
      <table className="customers-table">
        <thead>
          <tr>
            <th>Customer ID</th>
            <th>Full Name</th>
            <th>Email Address</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {/* Map through the customers array and create a table row for each customer */}
          {customers.map(customer => (
            <tr key={customer.customerId}>
              <td>{customer.customerId}</td>
              <td>{customer.fullName}</td>
              <td>{customer.emailAddress}</td>
              <td>
                {/* Button to delete the customer */}
                <button
                  className="delete-button"
                  onClick={() => handleDelete(customer.customerId)} // Call handleDelete with customer ID
                >
                  Remove
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Customers;
