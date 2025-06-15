import React from "react"; // Imports the React library
import "./aboutUs.css"; // Imports the CSS file for styling
import about_image from "./about_image.jpg";
import Navbar from "../Util Components/NavBar/Navbar";

const AboutUs = () => {
  return (
    <div className="container">
      {" "}
      {/* Container that holds both the image and card sections */}
      <Navbar />
      <div className="image-section">
        {" "}
        {/* Section for displaying the image */}
        <img src={about_image} alt="Placeholder" className="image" />{" "}
        {/* Image with applied CSS styling */}
      </div>
      <div className="cards-section">
        {" "}
        {/* Section for displaying the cards */}
        <h1 className="heading">About Us</h1>{" "}
        {/* Main heading for the cards section */}
        {/* First card displaying a welcome message and project description */}
        <div className="card">
          <h2 className="card-title">
            Welcome to the documentation for Order Inventory project!
          </h2>{" "}
          {/* Card title */}
          <p className="card-content">
            {" "}
            {/* Card content describing the project */}
            The Order Inventory system aims to streamline order and inventory
            management by providing a centralized platform for efficient
            operations. Its two main features are Order Management, which
            includes order creation, real-time tracking, and order history, and
            Inventory Management, which involves maintaining stock levels,
            updating inventory, and integrating with the order system. Together,
            these features optimize control and improve order fulfillment.
          </p>
        </div>
        {/* Second card displaying information about the team */}
        <div className="card">
          <h2 className="card-title">Work by the team:</h2> {/* Card title */}
          <p className="card-content">
            {" "}
            {/* Card content describing the team */}
            This app has been meticulously developed by a dedicated team of
            members, including Gurjot Singh, Samyak Chaure, Ujjwal, Siddhart
            Rikame, and Gaurav Neve. Each member brought their unique skills and
            expertise to the project, ensuring a robust and efficient
            application. Their collaborative efforts have resulted in a seamless
            integration of order management and inventory management features,
            providing users with a comprehensive tool for managing their
            operations effectively.
          </p>
        </div>
      </div>
    </div>
  );
};

export default AboutUs; // Exports the PageContent component for use in other parts of the application
