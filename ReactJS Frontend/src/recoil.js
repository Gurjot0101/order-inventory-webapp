import { atom } from "recoil";

// Retrieve user from localStorage if available
const getUserFromLocalStorage = () => {
  const savedUser = localStorage.getItem("user");
  return savedUser ? JSON.parse(savedUser) : null; // Return null if no user is found
};

// Retrieve cart from localStorage if available
const getCartFromLocalStorage = () => {
  const savedCart = localStorage.getItem("cart");
  return savedCart ? JSON.parse(savedCart) : []; // Return an empty array if no cart is found
};

// Atom for user state
export const userState = atom({
  key: "user", // Unique key for the atom
  default: getUserFromLocalStorage(), // Default value is retrieved from localStorage
  effects: [
    ({ onSet }) => {
      // Effect to handle updates to the user state
      onSet((newUser) => {
        if (newUser) {
          // If a new user is set, store it in localStorage
          localStorage.setItem("user", JSON.stringify(newUser));
        } else {
          // If the user is cleared (e.g., logout), remove it from localStorage
          localStorage.removeItem("user");
        }
      });
    },
  ],
});

// Atom for cart state
export const cartState = atom({
  key: "cart", // Unique key for the atom
  default: getCartFromLocalStorage(), // Default value is retrieved from localStorage
  effects: [
    ({ onSet }) => {
      // Effect to handle updates to the cart state
      onSet((newCart) => {
        // Store the cart in localStorage whenever it is updated
        localStorage.setItem("cart", JSON.stringify(newCart));
      });
    },
  ],
});

// Atom for product list state
export const productListState = atom({
  key: "productList", // Unique key for the atom
  default: [], // Default value is an empty array
});
