import React, { useState, useEffect } from "react";
import "./Home.css";
import { useRecoilState, useRecoilValue } from "recoil";
import { productListState, userState, cartState } from "../../recoil"; // Import product and cart states
import instance from "../../axios"; // Axios instance for API calls
import Navbar from "../Util Components/NavBar/Navbar"; // Import Navbar component
import { TypeAnimation } from "react-type-animation"; // Import TypeAnimation for text animation
import { Link, useNavigate } from "react-router-dom"; // Import Link and useNavigate for routing

export default function Home() {
  // Recoil state hooks
  const [products, setProducts] = useRecoilState(productListState); // Products state
  const [filteredProducts, setFilteredProducts] = useState(products); // Filtered products state
  const [searchTerm, setSearchTerm] = useState(""); // Search term state
  const [filter, setFilter] = useState(""); // Filter state
  const [debouncedSearchTerm, setDebouncedSearchTerm] = useState(searchTerm); // Debounced search term
  const [cart, setCart] = useRecoilState(cartState); // Cart state

  const user = useRecoilValue(userState); // Get the user state
  const navigate = useNavigate(); // Hook for programmatic navigation

  // Fetch products data when the component mounts
  useEffect(() => {
    instance.get(`/api/v1/products`).then((response) => {
      setProducts(response.data.reverse()); // Set products in reverse order
      console.log(response.data); // Log fetched products
    });
  }, []); // Empty dependency array ensures this runs only once

  // Debounce the search term input
  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedSearchTerm(searchTerm); // Update debounced search term
    }, 500); // Delay of 500ms

    return () => {
      clearTimeout(handler); // Cleanup the timeout on component unmount
    };
  }, [searchTerm]); // Run effect when searchTerm changes

  // Filter products based on search term and selected filter
  useEffect(() => {
    let sortedProducts = [...products]; // Copy of products array

    // Sort products based on the selected filter
    switch (filter) {
      case "low-to-high":
        sortedProducts.sort((a, b) => a.price - b.price); // Sort by price low to high
        break;
      case "high-to-low":
        sortedProducts.sort((a, b) => b.price - a.price); // Sort by price high to low
        break;
      case "a-to-z":
        sortedProducts.sort((a, b) => a.name.localeCompare(b.name)); // Sort alphabetically A-Z
        break;
      case "z-to-a":
        sortedProducts.sort((a, b) => b.name.localeCompare(a.name)); // Sort alphabetically Z-A
        break;
      default:
        break; // No sorting if no filter is applied
    }

    // Filter products based on the search term
    setFilteredProducts(
      sortedProducts.filter((product) =>
        product.name?.toLowerCase().includes(debouncedSearchTerm.toLowerCase()) // Match search term
      )
    );
  }, [filter, products, debouncedSearchTerm]); // Dependencies for filtering

  // Function to add a product to the cart
  const addToCart = (product) => {
    setCart((prevCart) => {
      const productInCart = prevCart.find(
        (item) => item.id === product.id // Check if product is already in cart
      );

      const updatedCart = productInCart
        ? prevCart.map((item) =>
            item.id === product.id
              ? { ...item, quantity: item.quantity + 1 } // Increment quantity
              : item
          )
        : [...prevCart, { ...product, quantity: 1 }]; // Add new product to cart

      console.log("Updated Cart after adding:", updatedCart); // Log updated cart
      return updatedCart; // Return updated cart
    });
  };

  // Handle adding to cart or redirecting to login
  const handleAddToCart = (product) => {
    if (!user) {
      navigate("/auth"); // Redirect to auth page if user is not logged in
    } else {
      addToCart(product); // Add product to cart if user is logged in
      console.log("Current cart state:", cart); // Log current cart state
    }
  };

  // Function to remove a product from the cart
  const removeFromCart = (product) => {
    setCart((prevCart) => {
      const productInCart = prevCart.find(
        (item) => item.id === product.id // Check if product is in cart
      );

      const updatedCart =
        productInCart.quantity === 1
          ? prevCart.filter((item) => item.id !== product.id) // Remove product if quantity is 1
          : prevCart.map((item) =>
              item.id === product.id
                ? { ...item, quantity: item.quantity - 1 } // Decrement quantity
                : item
            );

      console.log("Updated Cart after removing:", updatedCart); // Log updated cart
      return updatedCart; // Return updated cart
    });
  };

  return (
    <div className="home">
      <Navbar /> {/* Render Navbar component */}
      <div className="search-container">
        <div className="home-dash">
          <br />
          <br />
          <div className="anim-text">
            {/* Animated text displaying various clothing categories */}
            <TypeAnimation
              preRenderFirstString={true}
              sequence={[
                500,
                "We produce \n\n Clothes for MEN",
                1500,
                "We produce \n\n Clothes for WOMEN",
                1500,
                "We produce \n\n Clothes for Kids",
                1500,
                "We produce \n\n Clothes for DOGS",
                500,
              ]}
              speed={150}
              repeat={Infinity} // Infinite loop for animation
            />
          </div>
        </div>
        <br />
        <div className="search">
          <div className="search-box">
            <input
              type="text"
              placeholder="Search for products..."
              className="search-input"
              value={searchTerm} // Bind search input to state
              onChange={(e) => setSearchTerm(e.target.value)} // Update search term on input change
            />
          </div>
          <select
            className="filter-select"
            value={filter} // Bind select to filter state
            onChange={(e) => setFilter(e.target.value)} // Update filter on selection
          >
            <option value="">Sort By:</option>
            <option value="low-to-high">Price: Low to High</option>
            <option value="high-to-low">Price: High to Low</option>
            <option value="a-to-z">A to Z</option>
            <option value="z-to-a">Z to A</option>
          </select>
        </div>
        <div className="products-grid">
          {filteredProducts.map((product) => {
            const cartItem = cart.find(
              (item) => item.id === product.id // Check if product is in cart
            );

            return (
              <div key={product.id} className="product-box">
                {/* <Link
                  to={`/products/${product.id}`}
                  className="product-link"
                > */}
                  <img
                    src={
                      product.image
                        ? `data:image/jpeg;base64,${product.image}` // Base64 image data
                        : "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg" // Placeholder image
                    }
                    className="product-images"
                    //alt={product.name} // Uncomment for accessibility
                  />
                {/* </Link> */}
                <div className="product-name">{product.name}</div>
                <div className="product-brand">{product.brand}</div>
                <br />
                <span className="price">₹{product.price}</span> {/* Display product price */}
                <br />
                <div className="cart-area">
                  {cartItem && cartItem.quantity > 0 ? ( // Check if item is in cart
                    <div>
                      <button
                        onClick={() => removeFromCart(product)} // Remove item from cart
                        className="minus"
                      >
                        <i className="fa-solid fa-circle-minus"></i>
                      </button>
                      <span className="quantity">{cartItem.quantity}</span> {/* Display item quantity */}
                      <button
                        onClick={() => handleAddToCart(product)} // Add item to cart
                        className="add"
                      >
                        <i className="fa-solid fa-circle-plus"></i>
                      </button>
                    </div>
                  ) : (
                    <button
                      onClick={() => handleAddToCart(product)} // Add to cart button
                      className="add-to-cart"
                    >
                      Add to Cart
                    </button>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      </div>
      <footer>
        <div className="footer-content">
          <h3>Order Inventory</h3>
          <p>Thank you for visiting our website.</p>
        </div>
        <div className="footer-bottom">
          <p>© 2024 Order Inventory. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
}
