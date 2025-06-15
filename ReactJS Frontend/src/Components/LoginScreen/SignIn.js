import { useState } from "react";
import "./Login.css"; // Import CSS styles
import instance from "../../axios"; // Import the axios instance for API requests
import { useRecoilState } from "recoil"; // Import Recoil for state management
import { userState } from "../../recoil"; // Import user state from Recoil
import { useNavigate } from "react-router-dom"; // Import hook for navigation

export default function SignIn() {
  // State variables for email, password, error message, loading status, and user data
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [user, setUser] = useRecoilState(userState); // Get user state from Recoil
  const redirect = useNavigate(); // Use navigate for redirection

  // Function to handle sign-in
  const signIn = async (e) => {
    e.preventDefault(); // Prevent default form submission
    setError(null); // Reset error state
    setLoading(true); // Start loading state

    // Check if email is provided
    if (!email) {
      setError("Please enter your email!"); // Set error message
      setLoading(false); // Stop loading state
      return; // Exit function
    }

    try {
      // Check if the admin exists by making a GET request
      const adminRes = await instance.get("/api/v1/auth/admin", {
        params: { email, password },
      });
      console.log(adminRes); // Log the admin response

      // Check if adminRes.data is valid
      if (adminRes.data) {
        // Admin login successful
        setUser(adminRes.data); // Store the entire admin object in state
        localStorage.setItem("user", JSON.stringify(adminRes.data)); // Save admin data in local storage
        redirect("/admin", { replace: true }); // Redirect to admin page
        return; // Exit function
      }

      // Fetch customer data if not an admin
      const response = await instance.get(`/api/v1/auth/customer`, {
        params: { email, password },
      });
      console.log(response); // Log the customer response
      if (response.data) {
        setUser(response.data); // Store the entire customer object in state
        localStorage.setItem("user", JSON.stringify(response.data)); // Save customer data in local storage
        redirect("/", { replace: true }); // Redirect to homepage
      } else {
        setError("Invalid Credentials"); // Set error message for invalid credentials
      }
    } catch (error) {
      console.error(error); // Log any errors
      if (error.response) {
        // If server responded with a status other than 200
        setError(
          error.response.status === 401
            ? "Invalid email or password." // Specific error for 401
            : error.response.data.message || // Get error message from response if available
              "Error signing in. Please try again." // General error message
        );
      } else {
        // Handle network error or other issues
        setError("Network error. Please try again later."); // Set network error message
      }
    } finally {
      setLoading(false); // Stop loading state
    }
  };

  return (
    <form onSubmit={signIn}> {/* Form to handle sign-in */}
      <input
        className="sign-btn" // Class for styling
        type="email" // Input type for email
        onClick={() => setError("")} // Clear error message on focus
        onChange={(e) => setEmail(e.target.value)} // Update email state
        placeholder="Enter Email" // Placeholder text
        required // Make input required
      />
      <br />
      <br />
      <input
        className="sign-btn" // Class for styling
        onClick={() => setError("")} // Clear error message on focus
        onChange={(e) => setPassword(e.target.value)} // Update password state
        type="password" // Input type for password
        placeholder="Enter Password" // Placeholder text
        required // Make input required
      />

      {error && <div id="wrongId">{error}</div>} {/* Display error message if exists */}
      <button className="login-btn" type="submit" disabled={loading}> {/* Submit button */}
        {loading ? "Signing In..." : "Sign In"} {/* Change button text based on loading state */}
      </button>
    </form>
  );
}
