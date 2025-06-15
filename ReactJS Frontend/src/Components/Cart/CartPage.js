import React, { useState } from "react";
import "./Cart.css";
import Navbar from "../Util Components/NavBar/Navbar";
import { useRecoilState } from "recoil";
import { cartState, userState } from "../../recoil"; // Import cartState and userState
import instance from "../../axios";

const CartPage = () => {
  // Get cartItems and user info from Recoil state
  const [cartItems, setCartItems] = useRecoilState(cartState); 
  const [user] = useRecoilState(userState); 

  // State for delivery address and error messages
  const [deliveryAddress, setDeliveryAddress] = useState({
    name: "",
    address: "",
    city: "",
    state: "",
    zip: ""
  });
  const [addressError, setAddressError] = useState("");
  const [successMessage, setSuccessMessage] = useState(""); // State for success message

  // Handle quantity changes for cart items
  const handleQuantityChange = (id, delta) => {
    const updatedItems = cartItems
      .map((item) => {
        if (item.id === id) {
          const newQuantity = item.quantity + delta;
          // If the new quantity is less than or equal to 0, return null to remove it
          return newQuantity > 0 ? { ...item, quantity: newQuantity } : null;
        }
        return item;
      })
      .filter(item => item !== null); // Remove items with null value
  
    setCartItems(updatedItems); // Update the cart in Recoil state
  };

  // Handle removing an item from the cart
  const handleRemoveItem = (id) => {
    const updatedItems = cartItems.filter((item) => item.id !== id);
    setCartItems(updatedItems); // Update the cart in Recoil state
  };

  // Handle changes to the delivery address inputs
  const handleAddressChange = (e) => {
    const { name, value } = e.target;
    setDeliveryAddress((prevAddress) => ({
      ...prevAddress,
      [name]: value,
    }));
  };

  // Calculate the subtotal of the cart items
  const subtotal = cartItems.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

  // Validate the delivery address fields
  const validateAddress = () => {
    const { name, address, city, state, zip } = deliveryAddress;
    if (!name || !address || !city || !state || !zip) {
      setAddressError("All address fields are required."); // Set error message if fields are empty
      return false;
    }
    setAddressError(""); // Clear any previous error
    return true;
  };

  // Handle placing the order
  const placeOrder = async (e) => {
    e.preventDefault(); // Prevent the default form submission

    if (!validateAddress()) {
      return; // Do not proceed if validation fails
    }

    if(cartItems.length === 0){
      setAddressError("Cart is Empty !") // Set error if cart is empty
      return;
    }

    const fullAddress = `${deliveryAddress.name}, ${deliveryAddress.address}, ${deliveryAddress.city}, ${deliveryAddress.state}, ${deliveryAddress.zip}`;

    // Prepare order data to be sent to the backend
    const orderData = {
      customerId: user.id,
      storeId: 1, // Replace with the actual storeId
      orderStatus: "OPEN",
      orderTms: new Date().toISOString(),
      // deliveryAddress: fullAddress, // Uncomment if using the address
    };

    try {
      const orderResponse = await instance.post("/api/v1/orders", orderData);
      const orderId = orderResponse.data.id; // Get the order ID from the response

      // (Optional) Send order items here...

      setSuccessMessage("Order placed successfully!"); // Set success message
      setCartItems([]); // Clear cart after placing the order
    } catch (error) {
      console.error("Error placing order:", error);
      setSuccessMessage(""); // Clear message on error
    }
  };

  return (
    <div className="cart">
      <Navbar />
      <div className="cart-container">
        <h2>
          Your Cart ({cartItems.reduce((acc, item) => acc + item.quantity, 0)}{" "}
          Items)
        </h2>
        <div className="cart-header">
          <span>Item</span>
          <span>Price</span>
          <span>Quantity</span>
          <span>Total</span>
          <span>Remove</span>
        </div>
        {cartItems.map((item) => (
          <div key={item.id} className="cart-item">
            <span>{item.name}</span>
            <span>₹{item.price}</span>
            <div className="quantity-selector">
              <button
                className="minus-btn"
                onClick={() => handleQuantityChange(item.id, -1)}
              >
                -
              </button>
              <span className="quantity">{item.quantity}</span>
              <button
                className="plus-btn"
                onClick={() => handleQuantityChange(item.id, 1)}
              >
                +
              </button>
            </div>
            <span>₹{item.price * item.quantity}</span>
            <button
              className="remove-btn"
              onClick={() => handleRemoveItem(item.id)}
            >
              Remove
            </button>
          </div>
        ))}
        <div className="delivery-address">
          <h2>Delivery Address</h2>
          <input
            type="text"
            name="name"
            placeholder="Name"
            onChange={handleAddressChange}
            value={deliveryAddress.name}
          />
          <input
            type="text"
            name="address"
            placeholder="Address"
            onChange={handleAddressChange}
            value={deliveryAddress.address}
          />
          <input
            type="text"
            name="city"
            placeholder="City"
            onChange={handleAddressChange}
            value={deliveryAddress.city}
          />
          <input
            type="text"
            name="state"
            placeholder="State"
            onChange={handleAddressChange}
            value={deliveryAddress.state}
          />
          <input
            type="text"
            name="zip"
            placeholder="Zip Code"
            onChange={handleAddressChange}
            value={deliveryAddress.zip}
          />
          {addressError && <p className="error-message">{addressError}</p>} {/* Display address error */}
        </div>
        <br />
        {successMessage && <p className="success-message">{successMessage}</p>} {/* Display success message */}
        <div className="cart-summary">
          <button className="back-btn">Back to Products</button>
          <span>Subtotal: ₹{subtotal}</span>
          <button className="order-btn" onClick={placeOrder}>
            Place Order
          </button>
        </div>
      </div>
    </div>
  );
};

export default CartPage;
