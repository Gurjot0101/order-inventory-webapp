import React from 'react';
import { Link, Outlet, useLocation } from 'react-router-dom';
import { FaProductHunt, FaUserCircle, FaHistory, FaCog, FaArrowLeft, FaTruck, FaStore, FaBoxes } from 'react-icons/fa';
import './AdminDashboard.css'; // Make sure your CSS is updated
import { useRecoilState } from 'recoil';
import { userState } from '../../../recoil';

const AdminDashboard = () => {
  // Get the current location object to determine which page is active
  const location = useLocation();
  // Check if the current page is the home page of the admin dashboard
  const isHomePage = location.pathname === '/admin';
  
  // Use Recoil to manage user state
  const [user, setUser] = useRecoilState(userState);

  // Handle logout by clearing user state and local storage
  const handleLogout = () => {
    setUser(null); // Set user state to null to indicate no user is logged in
    localStorage.clear(); // Clear local storage
  };

  return (
    <div className="admin-dashboard">
      <aside className="sidebar">
        <div className="sidebar-header">
          <h2>Dashboard</h2>
        </div>
        <nav className="sidebar-nav">
          <ul>
            {/* Navigation links for various sections of the admin dashboard */}
            <li>
              <Link to="order-history">
                <FaHistory /> Order History
              </Link>
            </li>
            <li>
              <Link to="products">
                <FaProductHunt /> Products
              </Link>
            </li>
            <li>
              <Link to="customers">
                <FaUserCircle /> Customer List
              </Link>
            </li>
            <li>
              <Link to="shipments">
                <FaTruck /> Shipments
              </Link>
            </li>
            <li>
              <Link to="stores">
                <FaStore /> Stores
              </Link>
            </li>
            <li>
              <Link to="inventory">
                <FaBoxes /> Inventory
              </Link>
            </li>
            {/* Link to go back to the main dashboard (if applicable) */}
            <li>
              <Link to="/">
                <FaArrowLeft /> Back to Dashboard
              </Link>
            </li>
          </ul>
        </nav>
      </aside>

      <div className="main-content">
        <header className="dashboard-header">
          <div className="profile-section">
            {/* Profile icon for the admin */}
            <FaUserCircle className="profile-icon" />
            {/* Uncomment the following line to display the admin's name */}
            {/* <span className="admin-label">{user}</span> */}
          </div>
          <div className="header-actions">
            {/* Logout Button */}
            <button className="logout-button button" onClick={handleLogout}>
                {/* Link to the auth page after logout */}
                <Link to="/auth">
                  <i className="fa-solid fa-arrow-right-from-bracket"></i>{" "}
                  Logout
                </Link>
            </button>
          </div>
        </header>

        <section className="content-section">
          {/* Conditionally render content based on the current page */}
          {isHomePage ? (
            <div className="welcome-image-container">
              <h1>Welcome To Admin Dashboard</h1>
              <img
                src="https://vencru.com/wp-content/uploads/2023/09/Inventory-management-1024x536.png"
                alt="Welcome"
                className="welcome-image"
              />
            </div>
          ) : (
            // Render nested routes defined in the router
            <Outlet />
          )}
        </section>
      </div>
    </div>
  );
};

export default AdminDashboard;
