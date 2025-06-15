import Login from "./Components/LoginScreen/Login";
import Home from "./Components/Homepage/Home";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import CustomerOrderHistory from "./Components/OrderHistory/CustomerOrderHistory";
import CartPage from "./Components/Cart/CartPage";
import AdminDashboard from "./Components/Admin/AdminDashboard/AdminDashboard";
import OrderHistory from "./Components/Admin/OrderHistoryAdmin/OrderHistory";
import ProductManagement from "./Components/Admin/ProductManagement/ProductManagement";
import Customers from "./Components/Admin/Customers/Customers";
import Shipments from "./Components/Admin/Shipments/Shipments";
import Stores from "./Components/Admin/Stores/Stores";
import InventoryTest from "./Components/Admin/Inventory/Inventory";
import ContactInfo from "./Components/ContactUs/ContactInfo";
import AboutUs from "./Components/AboutUs/AboutUs";
import Profile from "./Components/Profile/CombinedComponent";

function App() {
  return (
    <div className="App">
      {/* Set up routing for the application */}
      <BrowserRouter>
        <Routes>
          {/* Route for the login screen */}
          <Route path="/auth" element={<Login />} />
          {/* Route for the home page */}
          <Route path="/" element={<Home />} />
          {/* Route for the shopping cart */}
          <Route path="/cart" element={<CartPage />} />
          {/* Route for the customer order history */}
          <Route path="/orderhistory" element={<CustomerOrderHistory />} />
          {/* Route for the contact us page */}
          <Route path="/contactus" element={<ContactInfo />} />
          {/* Route for the about us page */}
          <Route path="/aboutus" element={<AboutUs />} />
          {/* Route for the user profile page */}
          <Route path="/profile" element={<Profile />} />
          {/* Route for the admin dashboard */}
          <Route path="/admin" element={<AdminDashboard />}>
            {/* Nested routes for different admin functionalities */}
            <Route path="order-history" element={<OrderHistory />} />
            <Route path="products" element={<ProductManagement />} />
            <Route path="customers" element={<Customers />} />
            <Route path="shipments" element={<Shipments />} />
            <Route path="stores" element={<Stores />} />
            <Route path="inventory" element={<InventoryTest />} />
            {/* Default route for the admin dashboard */}
            <Route index element={<h2>Welcome to Admin Dashboard</h2>} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
