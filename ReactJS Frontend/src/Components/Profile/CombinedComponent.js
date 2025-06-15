import React, { useState, useEffect } from "react";
import { useRecoilState } from "recoil"; // Recoil hook for managing state
import { userState } from "../../recoil"; // Import the Recoil user state
import "./CombinedComponent.css"; // Import CSS for styling
import MessageModal from "./MessageModal"; // Import a custom modal component for displaying messages
import Navbar from "../Util Components/NavBar/Navbar";
import instance from "../../axios";

function Profile() {
  // Use Recoil state for user data
  const [user, setUser] = useRecoilState(userState); // Access and modify user state
  const [name, setName] = useState(""); // Initialize empty state for name
  const [email, setEmail] = useState(""); // Initialize empty state for email
  const [error, setError] = useState(""); // Initialize error state
  const [successMessage, setSuccessMessage] = useState(""); // Success message state
  const [modalType, setModalType] = useState(""); // Modal type (success or error)

  // Populate name and email fields from Recoil user state when the component loads
  useEffect(() => {
    if (user) {
      setName(user.fullName || ""); // Set name if available
      setEmail(user.emailAddress || ""); // Set email if available
    }
  }, [user]); // This effect runs when `user` changes

  // Function to handle saving customer details
  const handleSave = async () => {
    // Validation for name and email
    const emailRegex = /^\S+@\S+\.\S+$/;
    const nameRegex = /^[a-zA-Z\s]+$/;

    if (!name.trim() || !email.trim()) {
      setError("Name and Email are required.");
      setModalType("error");
      return;
    }

    // Validate name for non-integer values (only alphabetic characters)
    if (!nameRegex.test(name)) {
      setError("Name must only contain alphabetic characters.");
      setModalType("error");
      return;
    }

    // Validate email format
    if (!emailRegex.test(email)) {
      setError("Please enter a valid email address.");
      setModalType("error");
      return;
    }

    // Create customer object for API
    const customer = {
      customerId: user?.id, // Use the customer ID from the Recoil state (non-editable)
      fullName: name.trim(),
      emailAddress: email.trim(),
    };

    try {
      // PUT request to update customer details
      const response = await instance.put("/api/v1/customers", customer);
      console.log("Saved:", response.data);

      // Update Recoil state with the new user data
      setUser((prevUser) => ({
        ...prevUser,
        fullName: customer.fullName,
        emailAddress: customer.emailAddress,
      }));
      setSuccessMessage("Customer details updated successfully!");
      setModalType("success");
      setError("");
    } catch (error) {
      console.error(
        "Error details:",
        error.response ? error.response.data : error.message
      );
      setError("Failed to save customer details. Please try again.");
      setModalType("error");
    }
  };

  // Function to clear all input fields and messages
  const handleClear = () => {
    setName(""); // Clear name input
    setEmail(""); // Clear email input
    setError(""); // Clear error message
    setSuccessMessage(""); // Clear success message
  };

  // Function to close the modal and clear messages
  const handleCloseModal = () => {
    setError("");
    setSuccessMessage("");
    setModalType("");
  };

  return (
    <div className="profile-cont">
      <div className="profile">
        <Navbar />

        {/* Form section */}
        <div className="edit-user-form">
          {error && <p className="error-message">{error}</p>}
          {/* Input fields */}
          <div className="form-group">
            <input
              type="text"
              id="customerId"
              value={user?.id || ""} // Automatically filled with Recoil state
              readOnly // Makes the field read-only
              placeholder="Customer ID"
              style={{ visibility: "hidden" }}
            />
          </div>
          {/* Header section */}
          <header className="main-header">
            <h1>User Edit</h1>
          </header>
          <div className="form-group">
            <label htmlFor="name">Customer Name:</label>
            <input
              type="text"
              id="name"
              value={name} // Use the local state for name
              onChange={(e) => setName(e.target.value)} // Update local state on change
              placeholder="Enter your name"
            />
          </div>
          <div className="form-group">
            <label htmlFor="email">Customer Email:</label>
            <input
              type="email"
              id="email"
              value={email} // Use the local state for email
              onChange={(e) => setEmail(e.target.value)} // Update local state on change
              placeholder="Enter your email"
            />
          </div>
          {/* Button group */}
          <div className="button-group">
            <button onClick={handleSave}>Save Details</button>
            <button onClick={handleClear} className="clear-button">
              Clear
            </button>
          </div>
        </div>
        {/* Modal for displaying messages */}
        {(error || successMessage) && (
          <MessageModal
            message={error || successMessage}
            type={modalType}
            onClose={handleCloseModal}
          />
        )}
      </div>
    </div>
  );
}

export default Profile;
