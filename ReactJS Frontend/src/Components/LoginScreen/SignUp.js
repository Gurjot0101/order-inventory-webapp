import { useState } from "react"; // Import useState for managing local state
import instance from "../../axios"; // Import the axios instance for API requests
import "./Login.css"; // Import CSS styles for the component
import { useNavigate } from "react-router-dom"; // Import hook for navigation

export default function SignUp() {
  // State variables for name, email, password, error message, and success message
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null); // State for error message
  const [successMessage, setSuccessMessage] = useState(null); // State for success message
  const navigate = useNavigate(); // Use navigate for redirection

  // Function to handle user sign-up
  const signUp = async (e) => {
    e.preventDefault(); // Prevent default form submission behavior
    setError(null); // Clear any existing error message
    setSuccessMessage(null); // Clear any existing success message
    try {
      // Fetch the existing user list to check for duplicates
      const response = await instance.get(`/api/v1/customers`);
      const userList = response.data; // Extract user list from response

      // Check if the user already exists in the user list
      const userExists = userList.some((u) => u.email === email);
      if (userExists) throw new Error("User Already Exists"); // Throw error if user exists

      // Create a new user by sending a POST request
      await instance.post(`/api/v1/customers`, {
        fullName: name, // User's full name
        emailAddress: email, // User's email address
        password: password, // User's password
      });

      setSuccessMessage("Signed up successfully!"); // Set success message upon successful sign-up
      // Redirect to the login page after a short delay
      setTimeout(() => {
        navigate("/auth"); // Navigate to the authentication page
      }, 2000); // 2 seconds delay before redirection
    } catch (error) {
      setError("User Already Exists"); // Set error message if the user already exists
    }
  };

  return (
    <form onSubmit={signUp}> {/* Form to handle user sign-up */}
      <input
        className="sign-btn" // Class for styling
        onClick={() => setError("")} // Clear error message on input focus
        onChange={(e) => {
          setName(e.target.value); // Update name state
          setError(""); // Clear error when user types
        }}
        type="text" // Input type for text
        placeholder="Enter Name" // Placeholder text
        required // Make input required
      />
      <br />
      <br />
      <input
        className="sign-btn" // Class for styling
        onClick={() => setError("")} // Clear error message on input focus
        onChange={(e) => {
          setEmail(e.target.value); // Update email state
          setError(""); // Clear error when user types
        }}
        type="email" // Input type for email
        placeholder="Enter Email" // Placeholder text
        required // Make input required
      />
      <br />
      <br />
      <input
        className="sign-btn" // Class for styling
        onChange={(e) => {
          setPassword(e.target.value); // Update password state
          setError(""); // Clear error when user types
        }}
        type="password" // Input type for password
        placeholder="Enter Password" // Placeholder text
        required // Make input required
      />
      {error && <div id="wrongId" style={{ color: 'red' }}>{error}</div>} {/* Display error message if it exists */}
      {successMessage && <div id="successMessage" style={{ color: 'green' }}>{successMessage}</div>} {/* Display success message if it exists */}
      <button className="login-btn" type="submit"> {/* Submit button for the form */}
        Sign Up
      </button>
    </form>
  );
}
