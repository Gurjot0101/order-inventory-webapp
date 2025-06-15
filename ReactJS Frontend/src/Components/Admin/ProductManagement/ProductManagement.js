import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ProductManagement.css';

const ProductManagement = () => {
  // State variables
  const [products, setProducts] = useState([]); // To store fetched products
  const [formOpen, setFormOpen] = useState(false); // To control form visibility
  const [formData, setFormData] = useState({ // Form data for adding/editing products
    name: '',
    price: '',
    color: '',
    brand: '',
    size: '',
    rating: '',
    category: '',
    image: '',
  });
  const [searchParams, setSearchParams] = useState({ // Search parameters
    value: '',
    field: 'name', // Default search field
  });
  const [validationErrors, setValidationErrors] = useState({}); // To store form validation errors
  const [message, setMessage] = useState(''); // Message to display notifications to the user
  const [selectedProduct, setSelectedProduct] = useState(null); // To store the currently selected product for editing

  // Fetch products when component mounts
  useEffect(() => {
    fetchProducts();
  }, []);

  // Fetch products from the server
  const fetchProducts = async () => {
    try {
      const response = await axios.get('http://localhost:8084/api/v1/products');
      setProducts(response.data); // Update state with fetched products
    } catch (error) {
      console.error('Error fetching products:', error);
      setMessage('Error fetching products.'); // Set error message
      setTimeout(() => setMessage(''), 3000); // Clear message after 3 seconds
    }
  };

  // Handle input changes for form fields
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value }); // Update specific field in form data
  };

  // Handle input changes for search fields
  const handleSearchInputChange = (e) => {
    const { name, value } = e.target;
    setSearchParams({ ...searchParams, [name]: value }); // Update search parameters
  };

  // Handle change in search field selection
  const handleSearchFieldChange = (e) => {
    setSearchParams({ ...searchParams, field: e.target.value }); // Update search field
  };

  // Handle product search
  const handleSearchSubmit = async (e) => {
    e.preventDefault(); // Prevent default form submission

    if (!searchParams.value.trim()) {
      setMessage('Search value cannot be empty.'); // Show error if search value is empty
      setTimeout(() => setMessage(''), 3000); // Clear message after 3 seconds
      return;
    }

    try {
      // Construct search URL and fetch data
      const url = `http://localhost:8084/api/v1/products/${searchParams.field}/${searchParams.value.trim()}`;
      const response = await axios.get(url);
      setProducts(response.data); // Update products with search results
    } catch (error) {
      console.error('Error searching products:', error.response ? error.response.data : error.message);
      setMessage('Cannot find the product'); // Set error message
      setTimeout(() => setMessage(''), 3000); // Clear message after 3 seconds
    }
  };

  // Handle image file change
  const handleImageChange = (e) => {
    const file = e.target.files[0]; // Get the selected file
    const reader = new FileReader();
    reader.onloadend = () => {
      setFormData({ ...formData, image: reader.result.split(',')[1] }); // Update form data with image data
    };
    if (file) reader.readAsDataURL(file); // Read the file as a data URL
  };

  // Validate form data before submission
  const validateFormData = (data) => {
    const errors = {};
    // Validate product name
    if (!/^[A-Za-z\s]{2,100}$/.test(data.name)) {
      errors.name = 'Product name must be between 2 and 100 characters and contain only alphabets and spaces.';
    }
    // Validate price
    if (!/^\d+(\.\d{1,2})?$/.test(data.price)) {
      errors.price = 'Invalid price format. Should be a number with up to 2 decimal places.';
    }
    // Validate color, brand, and category
    ['color', 'brand', 'category'].forEach((field) => {
      if (data[field] && !/^[A-Za-z& ]+$/.test(data[field])) {
        errors[field] = `${field.charAt(0).toUpperCase() + field.slice(1)} must contain only alphabets, spaces, and '&' character.`;
      }
    });
    // Validate size
    if (!/^(XS|S|M|L|XL|XXL|XXXL)$/.test(data.size)) {
      errors.size = 'Size must be one of the following: XS, S, M, L, XL, XXL, XXXL.';
    }
    // Validate rating
    if (data.rating < 1 || data.rating > 5) {
      errors.rating = 'Rating must be between 1 and 5.';
    }
    return errors; // Return any validation errors found
  };

  // Handle form submission for adding/editing products
  const handleFormSubmit = async (e) => {
    e.preventDefault(); // Prevent default form submission
    const errors = validateFormData(formData); // Validate form data
    if (Object.keys(errors).length > 0) {
      setValidationErrors(errors); // Set validation errors in state
      setMessage('Please correct the errors in the form.'); // Set message to notify user
      setTimeout(() => setMessage(''), 3000); // Clear message after 3 seconds
      return;
    }
    setValidationErrors({}); // Clear previous validation errors
    try {
      // If editing an existing product
      if (selectedProduct) {
        await axios.put(`http://localhost:8084/api/v1/products/${selectedProduct.id}`, formData, {
          headers: { 'Content-Type': 'application/json' },
        });
        setMessage('Product updated successfully!'); // Success message
      } else {
        // If adding a new product
        await axios.post('http://localhost:8084/api/v1/products', formData, {
          headers: { 'Content-Type': 'application/json' },
        });
        setMessage('Product added successfully!'); // Success message
      }
      fetchProducts(); // Fetch updated products list
      resetForm(); // Reset the form after submission
    } catch (error) {
      console.error('Error saving product:', error);
      setMessage('Failed to save product. Please try again.'); // Set error message
    }
    setTimeout(() => setMessage(''), 3000); // Clear message after 3 seconds
  };

  // Prepare form for editing a selected product
  const handleEditProduct = (product) => {
    console.log(product);
    setSelectedProduct(product); // Set selected product for editing
    // Populate form fields with selected product's data
    setFormData({
      name: product.name,
      price: product.price,
      color: product.color,
      brand: product.brand,
      size: product.size,
      rating: product.rating,
      category: product.category,
      image: product.image || '',
    });
    setFormOpen(true); // Open the form for editing
  };

  // Handle product deletion
  const handleDeleteProduct = async (productId) => {
    try {
      await axios.delete(`http://localhost:8084/api/v1/products/${productId}`); // Delete product
      setMessage('Product deleted successfully!'); // Success message
      fetchProducts(); // Fetch updated products list
    } catch (error) {
      console.error('Error deleting product:', error);
      setMessage('Failed to delete product. Please try again.'); // Set error message
    }
    setTimeout(() => setMessage(''), 3000); // Clear message after 3 seconds
  };

  // Reset the form fields
  const resetForm = () => {
    setSelectedProduct(null); // Clear selected product
    setFormData({ // Reset form data
      name: '',
      price: '',
      color: '',
      brand: '',
      size: '',
      rating: '',
      category: '',
      image: '',
    });
    setFormOpen(false); // Close the form
  };

  return (
    <div className="product-management">
      <h2>Product Management</h2>

      {message && <div className="popup-message">{message}</div>} {/* Display any messages */}

      <div className="search-bar">
        <form onSubmit={handleSearchSubmit} className="search-form">
          <select name="field" value={searchParams.field} onChange={handleSearchFieldChange}>
            <option value="name">Name</option>
            <option value="category">Category</option>
            <option value="brand">Brand</option>
            <option value="color">Color</option>
          </select>
          <input
            type="text"
            name="value"
            placeholder={`Search by ${searchParams.field}`}
            value={searchParams.value}
            onChange={handleSearchInputChange}
          />
          <button type="submit">Search</button>
        </form>
      </div>

      <div className="product-list">
        {products.map((product) => (
          <div key={product.id} className="product-item">
            <img src={product.image} alt={product.name} />
            <h3>{product.name}</h3>
            <p>Price: ${product.price}</p>
            <p>Brand: {product.brand}</p>
            <p>Color: {product.color}</p>
            <p>Size: {product.size}</p>
            <p>Rating: {product.rating}</p>
            <p>Category: {product.category}</p>
            <button onClick={() => handleEditProduct(product)}>Edit</button>
            <button onClick={() => handleDeleteProduct(product.id)}>Delete</button>
          </div>
        ))}
      </div>

      <button onClick={() => setFormOpen(true)}>Add New Product</button>

      {formOpen && (
        <form onSubmit={handleFormSubmit} className="product-form">
          <h3>{selectedProduct ? 'Edit Product' : 'Add Product'}</h3>
          <input
            type="text"
            name="name"
            placeholder="Product Name"
            value={formData.name}
            onChange={handleInputChange}
            required
          />
          {validationErrors.name && <span className="error">{validationErrors.name}</span>} {/* Show name validation error */}

          <input
            type="number"
            name="price"
            placeholder="Price"
            value={formData.price}
            onChange={handleInputChange}
            required
          />
          {validationErrors.price && <span className="error">{validationErrors.price}</span>} {/* Show price validation error */}

          <input
            type="text"
            name="color"
            placeholder="Color"
            value={formData.color}
            onChange={handleInputChange}
          />
          {validationErrors.color && <span className="error">{validationErrors.color}</span>} {/* Show color validation error */}

          <input
            type="text"
            name="brand"
            placeholder="Brand"
            value={formData.brand}
            onChange={handleInputChange}
          />
          {validationErrors.brand && <span className="error">{validationErrors.brand}</span>} {/* Show brand validation error */}

          <select name="size" value={formData.size} onChange={handleInputChange} required>
            <option value="">Select Size</option>
            <option value="XS">XS</option>
            <option value="S">S</option>
            <option value="M">M</option>
            <option value="L">L</option>
            <option value="XL">XL</option>
            <option value="XXL">XXL</option>
            <option value="XXXL">XXXL</option>
          </select>
          {validationErrors.size && <span className="error">{validationErrors.size}</span>} {/* Show size validation error */}

          <input
            type="number"
            name="rating"
            placeholder="Rating (1-5)"
            value={formData.rating}
            onChange={handleInputChange}
            required
          />
          {validationErrors.rating && <span className="error">{validationErrors.rating}</span>} {/* Show rating validation error */}

          <input
            type="text"
            name="category"
            placeholder="Category"
            value={formData.category}
            onChange={handleInputChange}
            required
          />
          {validationErrors.category && <span className="error">{validationErrors.category}</span>} {/* Show category validation error */}

          <input type="file" accept="image/*" onChange={handleImageChange} /> {/* File input for image upload */}
          <button type="submit">{selectedProduct ? 'Update Product' : 'Add Product'}</button>
          <button type="button" onClick={resetForm}>Cancel</button> {/* Button to reset the form */}
        </form>
      )}
    </div>
  );
};

export default ProductManagement;
