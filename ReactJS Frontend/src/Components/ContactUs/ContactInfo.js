import React from "react";
import styles from "./contactInfo.module.css"; // Importing CSS module for styling
import Navbar from "../Util Components/NavBar/Navbar"; // Importing Navbar component

const ContactInfo = () => {
  return (
    <div className={styles["contact-info"]}>
      {" "}
      {/* Main container for contact information */}
      <Navbar /> {/* Rendering the Navbar component */}
      <h1>Contact Us</h1> {/* Heading for the contact page */}
      <div className={styles["info-section"]}>
        {" "}
        {/* Section containing contact information cards */}
        {/* Address Card */}
        <div className={styles["card"]} id="address">
          <div className={styles["card-header"]}>
            {" "}
            {/* Card header section */}
            <div
              className={styles["logo"] + " " + styles["address-logo"]}
            ></div>{" "}
            {/* Logo for address */}
            <h2 className={styles["heading"]}>ADDRESS</h2>{" "}
            {/* Heading for address */}
          </div>
          <div className={styles["card-content"]}>
            {" "}
            <p>#</p>{" "}
          </div>
        </div>
        {/* Phone Card */}
        <div className={styles["card"]} id="phone">
          <div className={styles["card-header"]}>
            {" "}
            {/* Card header section */}
            <div
              className={styles["logo"] + " " + styles["phone-logo"]}
            ></div>{" "}
            {/* Logo for phone */}
            <h2 className={styles["heading"]}>PHONE</h2>{" "}
            {/* Heading for phone */}
          </div>
          <div className={styles["card-content"]}>
            {" "}
            {/* Card content section */}
            <p>Contact us on:</p>
            <p>+91 ##########</p> {/* Phone number */}
          </div>
        </div>
        {/* Email Card */}
        <div className={styles["card"]} id="email">
          <div className={styles["card-header"]}>
            {" "}
            {/* Card header section */}
            <div
              className={styles["logo"] + " " + styles["email-logo"]}
            ></div>{" "}
            {/* Logo for email */}
            <h2 className={styles["heading"]}>EMAIL</h2>{" "}
            {/* Heading for email */}
          </div>
          <div className={styles["card-content"]}>
            {" "}
            {/* Card content section */}
            <p>@email.com</p> {/* Email address */}
          </div>
        </div>
      </div>
      {/* Footer Section */}
      <footer className={styles["footer"]}>
        {" "}
        {/* Footer for social media links */}
        <h2 className={styles["footer-heading"]}>Follow us on</h2>{" "}
        {/* Footer heading */}
        <div className={styles["footer-logos"]}>
          {" "}
          {/* Container for social media logos */}
          <a
            href="https://example.com/address" // Placeholder link for Instagram
            className={styles["footer-logo"] + " " + styles["logo1"]}
            aria-label="Instagram link" // Accessible label for screen readers
            target="_blank" // Opens link in new tab
            rel="noopener noreferrer" // Security for external links
          ></a>
          <a
            href="https://example.com/phone" // Placeholder link for Facebook
            className={styles["footer-logo"] + " " + styles["logo2"]}
            aria-label="Facebook link" // Accessible label for screen readers
            target="_blank"
            rel="noopener noreferrer"
          ></a>
          <a
            href="https://example.com/email" // Placeholder link for LinkedIn
            className={styles["footer-logo"] + " " + styles["logo3"]}
            aria-label="LinkedIn link" // Accessible label for screen readers
            target="_blank"
            rel="noopener noreferrer"
          ></a>
          <a
            href="https://example.com/other" // Placeholder link for YouTube
            className={styles["footer-logo"] + " " + styles["logo4"]}
            aria-label="YouTube link" // Accessible label for screen readers
            target="_blank"
            rel="noopener noreferrer"
          ></a>
        </div>
      </footer>
    </div>
  );
};

export default ContactInfo; // Exporting the ContactInfo component
