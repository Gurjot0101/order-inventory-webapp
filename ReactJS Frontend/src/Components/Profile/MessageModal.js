import React from 'react'; // Importing React to use JSX syntax and React features
import './MessageModal.css'; // Importing the CSS file for styling the modal

// Define the MessageModal functional component
function MessageModal({ message, type, onClose }) {
  // Determine the text color based on the type of message ('success' or 'error')
  const messageColor = type === 'success' ? '#4caf50' : '#f44336';

  return (
    <div className="modal-overlay"> {/* Container for the modal overlay */}
      <div className="modal-content" style={{ color: messageColor }}> {/* Modal content container with dynamic text color */}
        <p>{message}</p> {/* Display the message passed as a prop */}
        <button onClick={onClose}>OK</button> {/* Button to close the modal, triggers onClose function when clicked */}
      </div>
    </div>
  );
}

export default MessageModal; // Exporting the component for use in other parts of the application
