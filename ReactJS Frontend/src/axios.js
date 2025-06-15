import axios from "axios";

// Create an instance of axios with a predefined base URL
const instance = axios.create({
  baseURL: "http://localhost:8080" // Base URL for all requests
});

// Export the axios instance for use in other parts of the application
export default instance;
