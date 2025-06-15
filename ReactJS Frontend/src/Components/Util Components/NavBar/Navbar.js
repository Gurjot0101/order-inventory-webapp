import React from "react";
import { Link } from "react-router-dom"; // Import Link for navigation
import "./Navbar.css"; // Import CSS for styling the navbar
import { useRecoilState } from "recoil"; // Import Recoil hooks for state management
import { userState } from "../../../recoil"; // Import user state atom
import "@fortawesome/fontawesome-free/css/all.min.css"; // Import Font Awesome for icons

function Navbar() {
  // Get the current user and the setter function from Recoil state
  const [user, setUser] = useRecoilState(userState);

  // Function to handle user logout
  const handleLogout = () => {
    setUser(null); // Set user state to null (logged out)
    localStorage.clear(); // Clear localStorage on logout
  };

  return (
    <header className="header">
      <div className="logo">Order Inventory System</div> {/* Logo section */}
      <nav>
        <ul>
          {/* Link to home page */}
          <li>
            <Link to="/">
              <i className="fa-solid fa-house"></i> Home
            </Link>
          </li>
          {user && ( // Conditional rendering based on user state
            <>
              {/* Link to cart page */}
              <li>
                <Link to="/cart">
                  <i className="fa-solid fa-cart-shopping"></i> Cart
                </Link>
              </li>
              {/* Link to order history page */}
              <li>
                <Link to="/orderhistory">
                  <i className="fa-solid fa-file-lines"></i> Past Orders
                </Link>
              </li>
              {/* Link to user profile, displaying the user's name */}
              <li>
                <Link to="/profile">
                  <i className="fa-regular fa-user"></i> Welcome, {user.name}
                </Link>
              </li>
              {/* Link to logout, triggering the handleLogout function */}
              <li onClick={handleLogout}>
                <Link to="/auth">
                  <i className="fa-solid fa-arrow-right-from-bracket"></i>{" "}
                  Logout
                </Link>
              </li>
            </>
          )}
          {!user && ( // If no user is logged in, show login link
            <li>
              <Link to="/auth">Login</Link>
            </li>
          )}
          {/* Link to about us page */}
          <li>
            <Link to="/aboutus">About Us</Link>
          </li>
          {/* Link to contact us page */}
          <li>
            <Link to="/contactus">Contact Us</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default Navbar; // Export the Navbar component
