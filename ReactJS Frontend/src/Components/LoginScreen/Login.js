import React, { useState } from "react"; // Import React and useState hook
import "./Login.css"; // Import CSS styles for the Login component
import SignIn from "./SignIn"; // Import the SignIn component
import SignUp from "./SignUp"; // Import the SignUp component
import Navbar from "../Util Components/NavBar/Navbar"; // Import the Navbar component

function Login() {
  const [selector, setSelector] = useState(false); // State to toggle between SignIn and SignUp

  return (
    <div className="login-page"> {/* Main wrapper for the login page */}
      <Navbar /> {/* Render the Navbar component */}
      <div className="login"> {/* Container for the login section */}
        <div className="login__container"> {/* Container for the login form */}
          <div className="login_text"> {/* Container for the title */}
            <h1>Order Inventory</h1> {/* Title of the login page */}
          </div>
          {selector ? <SignUp /> : <SignIn />} {/* Render SignUp if selector is true, else render SignIn */}
          <div className="change-Login-btn"> {/* Container for the toggle button */}
            {selector ? ( // Conditional rendering based on the selector state
              <span onClick={() => setSelector(!selector)} className="signtext">
                SignIn {/* Button text to switch to SignIn */}
              </span>
            ) : (
              <span onClick={() => setSelector(!selector)} className="signtext">
                SignUp {/* Button text to switch to SignUp */}
              </span>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login; // Export the Login component for use in other parts of the application
